package com.wkit.lost.mybatis.plugins.dbs.proxy;

import com.wkit.lost.mybatis.plugins.dbs.dialect.AbstractDialect;
import com.wkit.lost.mybatis.plugins.dbs.dialect.AbstractPageableDialect;

import java.util.Optional;

public class PageableDialectDelegate extends LimitDialectDelegate {

    @Override
    public AbstractPageableDialect getDelegate() {
        AbstractDialect dialect = Optional.ofNullable( this.delegate ).orElse( delegateThreadLocal.get() );
        return ( AbstractPageableDialect ) Optional.ofNullable( dialect ).orElse( null );
    }
}
