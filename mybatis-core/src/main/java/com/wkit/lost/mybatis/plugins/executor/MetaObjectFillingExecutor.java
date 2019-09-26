package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.filling.MetaObjectFillingHandler;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.snowflake.sequence.Sequence;
import com.wkit.lost.mybatis.snowflake.worker.SequenceWorker;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.PrimitiveRegistry;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 自动填充值执行器
 * @author DT
 * @see com.wkit.lost.mybatis.scripting.defaults.MyBatisDefaultParameterHandler
 */
public class MetaObjectFillingExecutor {

    public Object intercept( Invocation invocation ) throws Throwable {
        MappedStatement statement = ( MappedStatement ) invocation.getArgs()[ 0 ];
        Object parameterObject = invocation.getArgs()[ 1 ];
        if ( parameterObject != null ) {
            Object newParameter = processFillValue( statement, parameterObject );
            invocation.getArgs()[ 1 ] = parameterObject;
        }
        return invocation.proceed();
    }

    @SuppressWarnings( "unchecked" )
    private static Object processFillValue( MappedStatement statement, Object parameterObject ) {
        if ( PrimitiveRegistry.isPrimitiveOrWrapper( parameterObject )
                || parameterObject.getClass() == String.class ) {
            return parameterObject;
        }
        boolean isInsert = statement.getSqlCommandType() == SqlCommandType.INSERT;
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

    private static Object fillValue( MappedStatement statement, Object parameter, Table table, boolean isInsert ) {
        if ( table == null ) {
            return parameter;
        }
        MetaObject metaObject = statement.getConfiguration().newMetaObject( parameter );
        // 保存操作填充主键值
        MyBatisCustomConfiguration customConfiguration = MyBatisConfigCache.getCustomConfiguration( statement.getConfiguration() );
        if ( isInsert && table.getPrimaryKey() != null ) {
            Column primaryKey = table.getPrimaryKey();
            Object value = metaObject.getValue( primaryKey.getProperty() );
            if ( isNullOrEmpty( value ) ) {
                Sequence sequence = customConfiguration.getSequence();
                if ( primaryKey.isUuid() ) {
                    // guid
                    metaObject.setValue( primaryKey.getProperty(), customConfiguration.getKeyGenerator().value() );
                } else if ( primaryKey.isWorker() ) {
                    // 雪花算法主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                    metaObject.setValue( primaryKey.getProperty(), Optional.ofNullable( sequence ).map( Sequence::nextId ).orElse( SequenceWorker.nextId() ) );
                } else if ( primaryKey.isWorkerString() ) {
                    // 雪花算法字符串主键(如果不开启注入Sequence对象，则默认使用mac地址分配的Sequence对象)
                    metaObject.setValue( primaryKey.getProperty(), Optional.ofNullable( sequence ).map( Sequence::nextStringId ).orElse( SequenceWorker.nextStringId() ) );
                }
            }
        }
        MetaObjectFillingHandler fillingHandler = customConfiguration.getMetaObjectFillingHandler();
        if ( fillingHandler != null ) {
            if ( isInsert && fillingHandler.enableInsert() ) {
                // 保存操作自动填充
                fillingHandler.insertFilling( metaObject );
            } else if ( !isInsert ) {
                String id = statement.getId();
                String execTarget = id.substring( id.lastIndexOf( "." ) + 1 );
                // 检查是否为逻辑删除
                if ( fillingHandler.enableDelete() && "logicDelete".equals( execTarget ) ) {
                    // 逻辑删除自动填充
                    fillingHandler.deleteFilling( metaObject );
                } else if ( fillingHandler.enableUpdate() ) {
                    // 更新操作自动填充
                    fillingHandler.updateFilling( metaObject );
                }
            }
        }
        return metaObject.getOriginalObject();
    }

    private static boolean isNullOrEmpty( Object value ) {
        if ( value != null ) {
            if ( value instanceof CharSequence ) {
                return Ascii.isNullOrEmpty( value.toString() );
            }
            return false;
        }
        return true;
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
}
