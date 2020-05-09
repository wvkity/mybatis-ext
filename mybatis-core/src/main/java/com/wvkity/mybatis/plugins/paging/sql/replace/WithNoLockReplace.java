package com.wvkity.mybatis.plugins.paging.sql.replace;

/**
 * SQL SERVER数据库防止锁住查询出现异常
 */
public interface WithNoLockReplace {

    /**
     * 替换原SQL
     * @param originalSql 原SQL
     * @return 替换后的SQL
     */
    String replace(String originalSql);

    /**
     * 还原替换的SQL
     * @param replacementSql 替换的SQL
     * @return 原SQL
     */
    String restore(String replacementSql);
}
