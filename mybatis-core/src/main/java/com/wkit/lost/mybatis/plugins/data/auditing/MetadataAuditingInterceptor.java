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
 * 元数据审计拦截器
 * @author wvkity
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class MetadataAuditingInterceptor implements Interceptor {

    private final Processor processor = new MetadataAuditingProcessor();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        return processor.intercept(invocation);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        processor.setProperties(properties);
    }
}
