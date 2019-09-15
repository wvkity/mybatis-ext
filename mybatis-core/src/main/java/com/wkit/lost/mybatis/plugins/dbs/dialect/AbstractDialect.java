package com.wkit.lost.mybatis.plugins.dbs.dialect;

import com.wkit.lost.mybatis.plugins.dbs.sql.OriginalSqlParser;

/**
 * 抽象数据库方言
 * @author DT
 */
public abstract class AbstractDialect implements Dialect {

    /**
     * SQL解析器
     */
    protected OriginalSqlParser sqlParser = new OriginalSqlParser();
}
