package com.wkit.lost.mybatis.plugins.pagination.dialect;

import com.wkit.lost.mybatis.plugins.pagination.config.PageableConfiguration;
import com.wkit.lost.mybatis.plugins.pagination.config.ThreadLocalPageable;
import com.wkit.lost.mybatis.plugins.pagination.exception.PageableException;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * 分页方言代理工厂
 * @author DT
 */
public class PageableDialectProxyFactory implements Dialect {

    /**
     * 分页配置
     */
    private PageableConfiguration configuration;

    /**
     * 分页方言委托代理对象
     */
    private PageableDialectProxy delegate;

    @Override
    public boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        // 检测是否已存在自定义分页查询映射
        if ( statement.getId().endsWith( PAGEABLE_RECORD_SUFFIX ) ) {
            throw new PageableException( "Multiple paging plug-ins have been found in the current system, please check the paging plug-in configuration!" );
        }
        // 获取分页对象
        Pageable pageable = configuration.getPageable( parameter, rowBounds );
        if ( pageable == null ) {
            return true;
        }
        // 初始化方言
        delegate.initDelegateDialect( statement );
        return false;
    }

    @Override
    public boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return delegate.getDelegate().beforeOfQueryRecord( statement, parameter, rowBounds );
    }

    @Override
    public String generateQueryRecordSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return delegate.getDelegate().generateQueryRecordSql( statement, boundSql, parameter, rowBounds, cacheKey );
    }

    @Override
    public boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds ) {
        return delegate.getDelegate().afterOfQueryRecord( records, parameter, rowBounds );
    }

    @Override
    public Object processParameter( MappedStatement statement, BoundSql boundSql, Object parameter, CacheKey cacheKey ) {
        return delegate.getDelegate().processParameter( statement, boundSql, parameter, cacheKey );
    }

    @Override
    public boolean executePagingOnBefore( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return delegate.getDelegate().executePagingOnBefore( statement, parameter, rowBounds );
    }

    @Override
    public String generatePageableSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return delegate.getDelegate().generatePageableSql( statement, boundSql, parameter, rowBounds, cacheKey );
    }

    @Override
    public Object executePagingOnAfter( List result, Object parameter, RowBounds rowBounds ) {
        return Optional.ofNullable( delegate.getDelegate() )
                .map( delegate -> delegate.executePagingOnAfter( result, parameter, rowBounds ) )
                .orElse( result );
    }

    @Override
    public void completed() {
        Optional.ofNullable( delegate.getDelegate() )
                .ifPresent( delegate -> {
                    delegate.completed();
                    this.delegate.clearDelegate();
                } );
        ThreadLocalPageable.clearPageable();
    }

    @Override
    public void setProperties( Properties props ) {
        this.delegate = new PageableDialectProxy();
        delegate.setProperties( props );
        this.configuration = new PageableConfiguration();
        configuration.setProperties( props );
    }
}
