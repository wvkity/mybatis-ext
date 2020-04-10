package com.wkit.lost.mybatis.plugins.paging.dbs.proxy;

import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.AbstractDialect;

import java.util.Optional;

public class RangePageableDialectDelegate extends AbstractDialectDelegate {

    @Override
    public AbstractDialect getDelegate() {
        return Optional.ofNullable(this.delegate).orElse(delegateThreadLocal.get());
    }

}
