package com.wvkity.mybatis.plugins.paging.adapter;

import com.wvkity.mybatis.plugins.paging.config.RangePageable;
import com.wvkity.mybatis.plugins.paging.config.RangePageableConfig;
import com.wvkity.mybatis.plugins.paging.config.ThreadLocalRangePageable;
import com.wvkity.mybatis.plugins.paging.dialect.Dialect;
import com.wvkity.mybatis.plugins.paging.proxy.RangePageableDialectDelegate;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class RangePageableAdapter implements Dialect {

    private RangePageableConfig rangeConfig;
    protected RangePageableDialectDelegate proxy;

    @Override
    public boolean filter(MappedStatement statement, Object parameter, RowBounds rowBounds) {
        RangePageable range = rangeConfig.getRangePageable(parameter);
        if (range != null && range.isApply()) {
            // 初始化方言
            this.proxy.initDialect(statement, "LIMIT_");
            return true;
        }
        return false;
    }

    @Override
    public Object processParameter(MappedStatement statement, BoundSql boundSql,
                                   Object parameter, CacheKey cacheKey) {
        return proxy.getDelegate().processParameter(statement, boundSql, parameter, cacheKey);
    }

    @Override
    public String generateQueryRecordSql(MappedStatement statement, BoundSql boundSql, Object parameter,
                                         RowBounds rowBounds, CacheKey cacheKey) {
        return proxy.getDelegate().generateQueryRecordSql(statement, boundSql, parameter, rowBounds, cacheKey);
    }

    @Override
    public String generatePageableSql(MappedStatement statement, BoundSql boundSql, Object parameter,
                                      RowBounds rowBounds, CacheKey cacheKey) {
        return proxy.getDelegate().generatePageableSql(statement, boundSql, parameter, rowBounds, cacheKey);
    }

    @Override
    public boolean executePagingOnBefore(MappedStatement statement, Object parameter, RowBounds rowBounds) {
        return proxy.getDelegate().executePagingOnBefore(statement, parameter, rowBounds);
    }

    @Override
    public <E> Object executePagingOnAfter(List<E> result, Object parameter, RowBounds rowBounds) {
        return Optional.ofNullable(proxy.getDelegate())
                .map(delegate -> delegate.executePagingOnAfter(result, parameter, rowBounds))
                .orElse(result);
    }

    @Override
    public void completed() {
        Optional.ofNullable(proxy.getDelegate()).ifPresent(delegate -> {
            delegate.completed();
            this.proxy.clearDelegateOfThread();
        });
        ThreadLocalRangePageable.remove();
    }

    @Override
    public void setProperties(Properties props) {
        this.proxy = new RangePageableDialectDelegate();
        this.proxy.setProperties(props);
        this.rangeConfig = new RangePageableConfig();
    }
}
