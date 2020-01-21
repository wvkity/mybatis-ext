package com.wkit.lost.mybatis.plugins.locker;

import com.wkit.lost.mybatis.plugins.executor.OptimisticLockerExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * 乐观锁拦截器
 * @author wvkity
 */
@Intercepts( {
        @Signature( type = Executor.class, method = "update", args = { MappedStatement.class, Object.class } )
} )
public class OptimisticLockerInterceptor implements Interceptor {
    
    private final OptimisticLockerExecutor executor = new OptimisticLockerExecutor();

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
        // ignore
    }
}
