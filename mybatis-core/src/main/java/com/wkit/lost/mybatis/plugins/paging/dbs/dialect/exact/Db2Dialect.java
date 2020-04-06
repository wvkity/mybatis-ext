package com.wkit.lost.mybatis.plugins.paging.dbs.dialect.exact;

import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.AbstractPageableDialect;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

public class Db2Dialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset ) {
        parameter.put( OFFSET_PARAMETER, rowStart + 1 );
        parameter.put( LIMIT_PARAMETER, rowEnd );
        // 处理Key
        cacheKey.update( rowStart + 1 );
        cacheKey.update( rowEnd );
        // 处理参数
        handleParameter( statement, boundSql, rowStart, rowEnd );
        return null;
    }

    @Override
    public String generateCorrespondPageableSql( String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize ) {
        return "SELECT * FROM (SELECT TMP_INNER_PAGE_TAB.*, ROWNUMBER() OVER() AS ROW_ID FROM ( " + sql
                + ") AS TMP_INNER_PAGE_TAB) TMP_OUTER_PAGE_TAB WHERE ROW_ID BETWEEN ? AND ?";
    }
}
