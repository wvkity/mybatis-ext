package com.wkit.lost.mybatis.plugins.dbs.proxy;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.plugins.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.executor.Argument;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class LimitProxyFactory implements Dialect {

    private LimitDialectDelegate proxy;

    @Override
    public void setProperties( Properties props ) {
        this.proxy = new LimitDialectDelegate();
        this.proxy.setProperties( props );
    }

    @Override
    public boolean filter( Argument arg ) {
        Criteria<?> criteria = arg.getParameter( "criteria" );
        if ( criteria != null && criteria.isLimit() ) {
            // 初始化方言
            this.proxy.initDialect( arg.getStatement(), "LIMIT_" );
            return true;
        }
        return false;
    }

    @Override
    public Object processParameter( Argument arg ) {
        return proxy.getDelegate().processParameter( arg );
    }

    @Override
    public String generatePageableSql( Argument arg ) {
        return proxy.getDelegate().generatePageableSql( arg );
    }

    @Override
    public Object executePagingOnAfter( List result, Argument arg ) {
        return Optional.ofNullable( proxy.getDelegate() )
                .map( delegate -> delegate.executePagingOnAfter( result, arg ) )
                .orElse( result );
    }

    @Override
    public void completed() {
        Optional.ofNullable( proxy.getDelegate() ).ifPresent( delegate -> {
            delegate.completed();
            this.proxy.clearDelegateOfThread();
        } );
    }
}
