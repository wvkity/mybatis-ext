package com.wkit.lost.mybatis.plugins.paging.dbs.sql.replace;

public class SimpleWithNoLockReplace extends AbstractWithNoLockReplace {

    @Override
    public String replace( String originalSql ) {
        return originalSql.replaceAll( "((?i)with\\s*\\(nolock\\))", PAGE_WITH_NO_LOCK );
    }

    @Override
    public String restore( String replacementSql ) {
        return replacementSql.replaceAll( PAGE_WITH_NO_LOCK, "WITH(NOLOCK)" );
    }
}
