package com.wkit.lost.mybatis.plugins.batch;

import com.wkit.lost.mybatis.plugins.processor.Processor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

import java.sql.PreparedStatement;
import java.util.Properties;

@Intercepts( { @Signature( type = ParameterHandler.class, method = "setParameters", args = { PreparedStatement.class } ) } )
public class BatchParameterFilterInterceptor implements Interceptor {

    private final Processor processor = new BatchParameterFilterProcessor();

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
