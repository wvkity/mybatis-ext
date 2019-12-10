package com.wkit.lost.mybatis.scripting.defaults;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 参数处理
 * <p>
 *     <ul>
 *         <li>原本打算用来处理自动填充值，出现一个问题，由于boundSql对象已经生成(绑定的sql已经确定)，
 *         如果保存、更新操作时是selective情况下就算填充值也无效，最终还是修改成交由拦截处理.</li>
 *     </ul>
 * </p>
 * @author wvkity
 * @see com.wkit.lost.mybatis.plugins.interceptor.MetaObjectFillingInterceptor
 */
public class MyBatisDefaultParameterHandler extends DefaultParameterHandler {

    private final TypeHandlerRegistry typeHandlerRegistry;
    private final MappedStatement mappedStatement;
    private final Object parameterObject;
    private final BoundSql boundSql;
    private final Configuration configuration;

    public MyBatisDefaultParameterHandler( MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql ) {
        super( mappedStatement, parameterObject, boundSql );
        this.mappedStatement = mappedStatement;
        this.configuration = mappedStatement.getConfiguration();
        this.typeHandlerRegistry = mappedStatement.getConfiguration().getTypeHandlerRegistry();
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
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
