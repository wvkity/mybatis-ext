package com.wkit.lost.mybatis.plugins.dbs.dialect.exact;

import com.wkit.lost.mybatis.plugins.dbs.dialect.AbstractPageableDialect;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * ORACLE数据库方言
 * @author DT
 */
public class OracleDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, long rowStart, long rowEnd, long offset ) {
        return null;
    }

    @Override
    public String generateCorrespondPageableSql( String sql, CacheKey cacheKey, long rowStart, long rowEnd ) {
        return null;
    }
}
