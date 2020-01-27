package com.wkit.lost.mybatis.plugins.paging.dbs.sql.replace;

public class RegexWithNoLockReplace extends AbstractWithNoLockReplace {

    @Override
    public String replace( String originalSql ) {
        return originalSql.replaceAll( "((?i)\\s*(\\w+)\\s*with\\s*\\(nolock\\))", "$2_PAGE_WITH_NO_LOCK" );
    }

    @Override
    public String restore( String replacementSql ) {
        return replacementSql.replaceAll( "\\s*(\\w*?)_PAGE_WITH_NO_LOCK", "$1 WITH(NOLOCK)" );
    }
}
