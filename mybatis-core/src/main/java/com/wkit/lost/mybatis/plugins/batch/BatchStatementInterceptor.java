package com.wkit.lost.mybatis.plugins.batch;

import com.wkit.lost.mybatis.plugins.processor.Processor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.Statement;
import java.util.Properties;

/**
 * 批量保存拦截器
 * @author wvkity
 */
@Intercepts( {
        @Signature( type = StatementHandler.class, method = "update", args = { Statement.class } ),
        @Signature( type = StatementHandler.class, method = "batch", args = { Statement.class } )
} )
public class BatchStatementInterceptor implements Interceptor {

    private final Processor processor = new BatchStatementProcessor();

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
