package com.wkit.lost.mybatis.scripting.defaults;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceWorker;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MyBatisDefaultParameterHandler extends DefaultParameterHandler {

    private final TypeHandlerRegistry typeHandlerRegistry;
    private final MappedStatement mappedStatement;
    private final Object parameterObject;
    private final BoundSql boundSql;
    private final Configuration configuration;

    public MyBatisDefaultParameterHandler( MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql ) {
        super( mappedStatement, processFillValue( mappedStatement, parameterObject ), boundSql );
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    @SuppressWarnings( "unchecked" )
    private static Object processFillValue( MappedStatement statement, Object parameterObject ) {
        if ( parameterObject == null
                || PrimitiveRegistry.isPrimitiveOrWrapper( parameterObject )
                || parameterObject.getClass() == String.class ) {
            return null;
        }
        boolean isFill = false;
        boolean isInsert = false;
        SqlCommandType execType = statement.getSqlCommandType();
        if ( execType == SqlCommandType.INSERT ) {
            isFill = true;
            isInsert = true;
        } else if ( execType == SqlCommandType.UPDATE ) {
            isFill = true;
        }
        if ( isFill ) {
            Collection<Object> parameters = getParameters( parameterObject );
            if ( parameters != null ) {
                List<Object> objects = new ArrayList<>( parameters.size() );
                for ( Object parameter : parameters ) {
                    Table table = EntityHandler.getTable( parameter.getClass() );
                    if ( table != null ) {
                        objects.add( fillValue( statement, parameter, table, isInsert ) );
                    } else {
                        objects.add( parameter );
                    }
                }
                return objects;
            } else {
                Table table = null;
                if ( parameterObject instanceof Map ) {
                    Map<String, Object> map = ( Map<String, Object> ) parameterObject;
                    Object entity = map.get( Constants.PARAM_ENTITY );
                    if ( entity != null ) {
                        table = EntityHandler.getTable( entity.getClass() );
                    }
                } else {
                    table = EntityHandler.getTable( parameterObject.getClass() );
                }
                return fillValue( statement, parameterObject, table, isInsert );
            }
        }
        return parameterObject;
    }

    private static Object fillValue( MappedStatement statement, Object parameter, Table table, boolean insert ) {
        if ( table == null ) {
            return parameter;
        }
        MetaObject metaObject = statement.getConfiguration().newMetaObject( parameter );
        MyBatisCustomConfiguration customConfiguration = MyBatisConfigCache.getCustomConfiguration( statement.getConfiguration() );
        // 保存操作填充主键值
        if ( insert && table.getPrimaryKey() != null ) {
            Column primaryKey = table.getPrimaryKey();
            Sequence sequence = customConfiguration.getSequence();
            if ( primaryKey.isUuid() ) {
                // guid
                metaObject.setValue( primaryKey.getProperty(), customConfiguration.getKeyGenerator().value() );
            } else if ( primaryKey.isWorker() ) {
                // 雪花算法主键(如果不开启注入Sequence对象，则默认使用mac分配的Sequence对象)
                metaObject.setValue( primaryKey.getProperty(), Optional.ofNullable( sequence ).map( Sequence::nextId ).orElse( SequenceWorker.nextId() ) );
            } else if ( primaryKey.isWorkerString() ) {
                // 雪花算法字符串主键(如果不开启注入Sequence对象，则默认使用mac分配的Sequence对象)
                metaObject.setValue( primaryKey.getProperty(), Optional.ofNullable( sequence ).map( Sequence::nextStringId ).orElse( SequenceWorker.nextStringId() ) );
            }
        }
        return metaObject.getOriginalObject();
    }

    @SuppressWarnings( { "unchecked" } )
    private static Collection<Object> getParameters( Object parameter ) {
        Collection<Object> parameters = null;
        if ( parameter instanceof Collection ) {
            parameters = ( Collection<Object> ) parameter;
        } else if ( parameter instanceof Map ) {
            Map<String, Object> map = ( Map<String, Object> ) parameter;
            if ( map.containsKey( "collection" ) ) {
                parameters = ( Collection<Object> ) map.get( "collection" );
            } else if ( map.containsKey( "list" ) ) {
                parameters = ( Collection<Object> ) map.get( "list" );
            } else if ( map.containsKey( "array" ) ) {
                parameters = Arrays.asList( ( Object[] ) map.get( "array" ) );
            }
        }
        return parameters;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public void setParameters( PreparedStatement ps ) {
        ErrorContext.instance().activity( "setting parameters" ).object( mappedStatement.getParameterMap().getId() );
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if ( parameterMappings != null ) {
            for ( int i = 0; i < parameterMappings.size(); i++ ) {
                ParameterMapping parameterMapping = parameterMappings.get( i );
                if ( parameterMapping.getMode() != ParameterMode.OUT ) {
                    Object value;
                    String propertyName = parameterMapping.getProperty();
                    if ( boundSql.hasAdditionalParameter( propertyName ) ) { // issue #448 ask first for additional params
                        value = boundSql.getAdditionalParameter( propertyName );
                    } else if ( parameterObject == null ) {
                        value = null;
                    } else if ( typeHandlerRegistry.hasTypeHandler( parameterObject.getClass() ) ) {
                        value = parameterObject;
                    } else {
                        MetaObject metaObject = configuration.newMetaObject( parameterObject );
                        value = metaObject.getValue( propertyName );
                    }
                    TypeHandler typeHandler = parameterMapping.getTypeHandler();
                    JdbcType jdbcType = parameterMapping.getJdbcType();
                    if ( value == null && jdbcType == null ) {
                        jdbcType = configuration.getJdbcTypeForNull();
                    }
                    try {
                        typeHandler.setParameter( ps, i + 1, value, jdbcType );
                    } catch ( TypeException | SQLException e ) {
                        throw new TypeException( "Could not set parameters for mapping: " + parameterMapping + ". Cause: " + e, e );
                    }
                }
            }
        }
    }
}
