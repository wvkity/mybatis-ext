package com.wkit.lost.mybatis.plugins.paging.dbs.adapter;

import com.wkit.lost.mybatis.plugins.paging.config.PageableConfig;
import com.wkit.lost.mybatis.plugins.paging.config.ThreadLocalPageable;
import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.PageableDialect;
import com.wkit.lost.mybatis.plugins.paging.dbs.proxy.PageableDialectDelegate;
import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.Optional;
import java.util.Properties;

public class PageableAdapter extends RangePageableAdapter implements PageableDialect {

    private PageableConfig pageableConfig;

    @Override
    public boolean filter( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        // 检测是否已存在自定义分页查询映射
        if ( statement.getId().endsWith( PAGEABLE_RECORD_SUFFIX ) ) {
            throw new MyBatisPluginException( "Multiple paging plug-ins have been found in the current system, please check the paging plug-in configuration!" );
        }
        // 获取分页对象
        Pageable pageable = pageableConfig.getPageable( parameter, rowBounds );
        if ( pageable != null ) {
            proxy.initDialect( statement, "PAGEABLE_" );
            return true;
        }
        return false;
    }

    @Override
    public boolean beforeOfQueryRecord( MappedStatement statement, Object parameter, RowBounds rowBounds ) {
        return ( ( PageableDialectDelegate ) proxy ).getDelegate().beforeOfQueryRecord( statement, parameter, rowBounds );
    }

    @Override
    public boolean afterOfQueryRecord( long records, Object parameter, RowBounds rowBounds ) {
        return ( ( PageableDialectDelegate ) proxy ).getDelegate().afterOfQueryRecord( records, parameter, rowBounds );
    }

    @Override
    public void completed() {
        Optional.ofNullable( proxy.getDelegate() )
                .ifPresent( delegate -> {
                    delegate.completed();
                    this.proxy.clearDelegateOfThread();
                } );
        ThreadLocalPageable.remove();
    }

    @Override
    public void setProperties( Properties props ) {
        this.proxy = new PageableDialectDelegate();
        this.proxy.setProperties( props );
        this.pageableConfig = new PageableConfig();
        pageableConfig.setProperties( props );
    }
}
