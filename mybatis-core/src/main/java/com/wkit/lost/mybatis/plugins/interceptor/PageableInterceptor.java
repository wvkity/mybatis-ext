package com.wkit.lost.mybatis.plugins.interceptor;

import com.wkit.lost.mybatis.plugins.executor.PageableQueryExecutor;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * 分页拦截器
 * @author DT
 */
@Intercepts( {
        @Signature( type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class } ),
        @Signature( type = Executor.class, method = "query", args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class } )
} )
public class PageableInterceptor implements Interceptor {

    private final PageableQueryExecutor executor = new PageableQueryExecutor();

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
        executor.setProperties( properties );
    }
}
