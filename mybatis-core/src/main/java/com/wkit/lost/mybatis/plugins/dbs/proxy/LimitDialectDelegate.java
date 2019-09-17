package com.wkit.lost.mybatis.plugins.dbs.proxy;

import com.wkit.lost.mybatis.plugins.dbs.dialect.AbstractDialect;

import java.util.Optional;

public class LimitDialectDelegate extends AbstractDialectDelegate {

    @Override
    public AbstractDialect getDelegate() {
        return Optional.ofNullable( this.delegate ).orElse( delegateThreadLocal.get() );
    }

}
