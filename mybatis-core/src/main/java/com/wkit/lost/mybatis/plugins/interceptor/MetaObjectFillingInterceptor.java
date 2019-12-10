package com.wkit.lost.mybatis.plugins.interceptor;

import com.wkit.lost.mybatis.plugins.executor.MetaObjectFillingExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * 自动填充值拦截器
 * @author wvkity
 * @see MetaObjectFillingExecutor
 */
@Intercepts( {
        @Signature( type = Executor.class, method = "update", args = { MappedStatement.class, Object.class } )
} )
public class MetaObjectFillingInterceptor implements Interceptor {

    /**
     * 填充执行器
     */
    private final MetaObjectFillingExecutor executor = new MetaObjectFillingExecutor();

    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        return executor.intercept( invocation );
    }

    @Override
    public Object plugin( Object target ) {
        return Plugin.wrap( target, this );
    }

    @Override
    public void setProperties( Properties properties ) {
    }
}
