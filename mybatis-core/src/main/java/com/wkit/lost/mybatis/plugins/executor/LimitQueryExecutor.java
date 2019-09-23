package com.wkit.lost.mybatis.plugins.executor;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import com.wkit.lost.paging.Pageable;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * LIMIT查询执行器
 * @author DT
 */
@Log4j2
public class LimitQueryExecutor extends AbstractQueryExecutor {

    @Override
    protected Mode getTarget() {
        return Mode.LIMIT;
    }

    @Override
    protected Object doIntercept( Executor executor, MappedStatement statement, Object parameter, RowBounds rowBounds,
                                  ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql ) throws Exception {
        try {
            // 检查代理对象是否存在
            validateDialectExists();
            // 检查是否需要执行
            List result;
            if ( this.factory.filter( statement, parameter, rowBounds ) ) {
                // 执行分段查询
                result = Executors.executeQueryPageableOfCustom( this.factory, executor, statement, parameter, rowBounds, resultHandler, boundSql, cacheKey );
            } else {
                // 执行原查询
                result = executor.query( statement, parameter, rowBounds, resultHandler, cacheKey, boundSql );
            }
            return factory.executePagingOnAfter( result, parameter, rowBounds );
        } catch ( Exception e ) {
            log.warn( "Limit query plug-in failed to execute: `{}` -- {}", statement.getId(), e );
            throw e;
        } finally {
            Optional.ofNullable( this.factory ).ifPresent( Dialect::completed );
        }
    }

    @Override
    public boolean filter( Argument arg ) {
        Criteria<?> criteria = arg.getParameter( Criteria.class );
        Pageable pageable = arg.getParameter( Pageable.class );
        return pageable == null && Optional.ofNullable( criteria ).map( Criteria::isLimit ).orElse( false );
    }

    @Override
    public void setProperties( Properties properties ) {
        super.setProperties( properties );
    }

    @Override
    protected String getDefaultDialect() {
        return "com.wkit.lost.mybatis.plugins.dbs.decorator.LimitDecorator";
    }
}
