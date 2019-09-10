package com.wkit.lost.mybatis.plugins.pagination.dialect;

import com.wkit.lost.mybatis.plugins.sql.parser.OriginalSqlParser;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

/**
 * 抽象数据库方言
 * @author DT
 */
public abstract class AbstractDialect implements Dialect {

    /**
     * SQL解析器
     */
    protected OriginalSqlParser sqlParser = new OriginalSqlParser();

    @Override
    public String generateQueryRecordSql( MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey ) {
        return sqlParser.smartTransform( boundSql.getSql() );
    }
}
