package com.wkit.lost.mybatis.plugins.dbs.decorator;

import com.wkit.lost.mybatis.plugins.config.Limit;
import com.wkit.lost.mybatis.plugins.config.LimitConfig;
import com.wkit.lost.mybatis.plugins.config.ThreadLocalLimit;
import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.dbs.proxy.LimitDialectDelegate;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class LimitDecorator implements Dialect {

    private LimitConfig limitConfig;
    protected LimitDialectDelegate proxy;

    @Override
    public boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        Limit limit = limitConfig.getLimit( parameter );
        if ( limit != null && limit.isApply() ) {
            // 初始化方言
            this.proxy.initDialect( statement, "LIMIT_" );
            return true;
        }
        return false;
    }

    @Override
    public Object processParameter( MappedStatement statement, BoundSql boundSql, Object parameter, CacheKey cacheKey ) {
        return proxy.getDelegate().processParameter( statement, boundSql, parameter, cacheKey );
    }

    @Override
    public String generateQueryRecordSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return proxy.getDelegate().generateQueryRecordSql( statement, boundSql, parameter, rowBounds, cacheKey );
    }

    @Override
    public String generatePageableSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return proxy.getDelegate().generatePageableSql( statement, boundSql, parameter, rowBounds, cacheKey );
    }

    @Override
    public boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return proxy.getDelegate().executePagingOnBefore( statement, parameter, rowBounds );
    }

    @Override
    public Object executePagingOnAfter( List result, Object parameter, RowBounds rowBounds ) {
        return Optional.ofNullable( proxy.getDelegate() )
                .map( delegate -> delegate.executePagingOnAfter( result, parameter, rowBounds ) )
                .orElse( result );
    }

    @Override
    public void completed() {
        Optional.ofNullable( proxy.getDelegate() ).ifPresent( delegate -> {
            delegate.completed();
            this.proxy.clearDelegateOfThread();
        } );
        ThreadLocalLimit.remove();
    }

    @Override
    public void setProperties( Properties props ) {
        this.proxy = new LimitDialectDelegate();
        this.proxy.setProperties( props );
        this.limitConfig = new LimitConfig();
    }
}
