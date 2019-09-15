package com.wkit.lost.mybatis.plugins.executor;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PageableQueryExecutor extends AbstractQueryExecutor {
    /**
     * 锁
     */
    private Lock lock = new ReentrantLock();

    @Override
    protected Mode getTarget() {
        return Mode.PAGEABLE;
    }

    @Override
    public boolean filter( Argument arg ) {
        return false;
    }

    @Override
    protected Object doIntercept( Argument arg ) throws Exception {
        return null;
    }

    @Override
    protected String getDefaultDialect() {
        return "com.wkit.lost.mybatis.plugins.dbs.proxy.PageableProxyFactory";
    }
}
