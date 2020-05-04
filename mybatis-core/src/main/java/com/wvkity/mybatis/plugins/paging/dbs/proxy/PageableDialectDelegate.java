package com.wvkity.mybatis.plugins.paging.dbs.proxy;

import com.wvkity.mybatis.plugins.paging.dbs.dialect.AbstractDialect;
import com.wvkity.mybatis.plugins.paging.dbs.dialect.AbstractPageableDialect;

import java.util.Optional;

public class PageableDialectDelegate extends RangePageableDialectDelegate {

    @Override
    public AbstractPageableDialect getDelegate() {
        AbstractDialect dialect = Optional.ofNullable(this.delegate).orElse(delegateThreadLocal.get());
        return (AbstractPageableDialect) Optional.ofNullable(dialect).orElse(null);
    }
}
