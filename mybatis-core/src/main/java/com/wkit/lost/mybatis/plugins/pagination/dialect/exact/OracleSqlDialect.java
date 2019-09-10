package com.wkit.lost.mybatis.plugins.pagination.dialect.exact;

import com.wkit.lost.mybatis.plugins.pagination.dialect.AbstractPageableDialect;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * ORACLE分页方言
 * @author DT
 */
public class OracleSqlDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Pageable pageable ) {
        long startRow = pageable.offset();
        long endRow = startRow + pageable.getSize();
        parameter.put( OFFSET_PARAMETER, endRow );
        parameter.put( LIMIT_PARAMETER, startRow );
        cacheKey.update( endRow );
        cacheKey.update( startRow );
        handleParameter( statement, boundSql, null );
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql( String sql, Pageable pageable, CacheKey cacheKey ) {
        return "SELECT * FROM ( " +
                "SELECT TAB_PAGE.*, ROWNUM ROW_ID FROM ( " +
                sql +
                " ) TAB_PAGE ) " +
                "WHERE ROW_ID <= ? AND ROW_ID > ?";
    }
}
