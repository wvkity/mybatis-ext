package com.wkit.lost.mybatis.plugins.dbs.sql.replace;

public abstract class AbstractWithNoLockReplace implements WithNoLockReplace {

    /**
     * WITH(NOLOCK)
     */
    protected String PAGE_WITH_NO_LOCK = ", PAGE_WITH_NO_LOCK";
}
