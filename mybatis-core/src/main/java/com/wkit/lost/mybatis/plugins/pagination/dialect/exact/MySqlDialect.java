package com.wkit.lost.mybatis.plugins.pagination.dialect.exact;

import com.wkit.lost.mybatis.plugins.pagination.dialect.AbstractPageableDialect;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;

import java.util.Map;

/**
 * MySql分页方言
 * @author DT
 */
public class MySqlDialect extends AbstractPageableDialect {

    @Override
    public Object processPageableParameter( MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Pageable pageable ) {
        parameter.put( OFFSET_PARAMETER, pageable.offset() );
        parameter.put( LIMIT_PARAMETER, pageable.getSize() );
        cacheKey.update( pageable.offset() );
        cacheKey.update( pageable.getSize() );
        handleParameter( statement, boundSql, pageable );
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql( String sql, Pageable pageable, CacheKey cacheKey ) {
        StringBuffer buffer = new StringBuffer( sql.length() + 20 );
        buffer.append( sql );
        if ( pageable.getPage() == 0 ) {
            buffer.append( " LIMIT ? " );
        } else {
            buffer.append( " LIMIT ?, ? " );
        }
        return buffer.toString();
    }

}
