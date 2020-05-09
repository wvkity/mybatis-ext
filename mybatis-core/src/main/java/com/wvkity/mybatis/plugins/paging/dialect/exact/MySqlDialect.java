package com.wvkity.mybatis.plugins.paging.dialect.exact;

import com.wvkity.mybatis.plugins.paging.dialect.AbstractPageableDialect;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * MySql数据库方言
 * @author wvkity
 */
public class MySqlDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter(MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql,
                                           CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset) {
        parameter.put(OFFSET_PARAMETER, rowStart);
        parameter.put(LIMIT_PARAMETER, offset);
        cacheKey.update(rowStart);
        cacheKey.update(offset);
        handleParameter(statement, boundSql, rowStart, offset);
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql(String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize) {
        return sql + " LIMIT ?, ? ";
    }
}
