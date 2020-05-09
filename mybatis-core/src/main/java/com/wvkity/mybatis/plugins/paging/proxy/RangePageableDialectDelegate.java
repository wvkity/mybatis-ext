package com.wvkity.mybatis.plugins.paging.proxy;

import com.wvkity.mybatis.plugins.paging.dialect.AbstractDialect;

import java.util.Optional;

public class RangePageableDialectDelegate extends AbstractDialectDelegate {

    @Override
    public AbstractDialect getDelegate() {
        return Optional.ofNullable(this.delegate).orElse(delegateThreadLocal.get());
    }

}
