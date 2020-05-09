package com.wvkity.mybatis.plugins.paging.dialect.exact;

import com.wvkity.mybatis.plugins.paging.dialect.AbstractPageableDialect;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * ORACLE数据库方言
 * @author wvkity
 */
public class OracleDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter(MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset) {
        parameter.put(OFFSET_PARAMETER, rowEnd);
        parameter.put(LIMIT_PARAMETER, rowStart);
        cacheKey.update(rowEnd);
        cacheKey.update(rowStart);
        handleParameter(statement, boundSql, rowStart, rowEnd);
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql(String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize) {
        return "SELECT * FROM ( " +
                "SELECT TAB_PAGE.*, ROWNUM ROW_ID FROM ( " +
                sql +
                " ) TAB_PAGE ) " +
                "WHERE ROW_ID <= ? AND ROW_ID > ?";
    }
}
