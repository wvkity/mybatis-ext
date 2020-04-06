package com.wkit.lost.mybatis.plugins.data.auditing;

import com.wkit.lost.mybatis.plugins.processor.Processor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.util.Properties;

/**
 * 系统内置默认拦截器
 * <p>处理主键生成/逻辑删除值审计</p>
 * @author wvkity
 */
@Intercepts( {
        @Signature( type = Executor.class, method = "update", args = { MappedStatement.class, Object.class } )
} )
public class SystemBuiltinAuditingInterceptor implements Interceptor {

    private final Processor processor = new SystemBuiltinAuditingProcessor();

    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        return processor.intercept( invocation );
    }

    @Override
    public Object plugin( Object target ) {
        return Plugin.wrap( target, this );
    }

    @Override
    public void setProperties( Properties properties ) {
        processor.setProperties( properties );
    }
}
