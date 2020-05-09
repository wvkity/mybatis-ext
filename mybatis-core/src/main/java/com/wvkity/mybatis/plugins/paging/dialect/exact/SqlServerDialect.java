package com.wvkity.mybatis.plugins.paging.dialect.exact;

import com.wvkity.mybatis.plugins.cache.Cache;
import com.wvkity.mybatis.plugins.cache.CacheFactory;
import com.wvkity.mybatis.plugins.paging.dialect.AbstractPageableDialect;
import com.wvkity.mybatis.plugins.paging.sql.SqlServerPageSqlParser;
import com.wvkity.mybatis.plugins.paging.sql.replace.RegexWithNoLockReplace;
import com.wvkity.mybatis.plugins.paging.sql.replace.SimpleWithNoLockReplace;
import com.wvkity.mybatis.plugins.paging.sql.replace.WithNoLockReplace;
import com.wvkity.mybatis.plugins.exception.MyBatisPluginException;
import com.wvkity.mybatis.utils.StringUtil;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.RowBounds;

import java.util.Map;
import java.util.Properties;

public class SqlServerDialect extends AbstractPageableDialect {

    protected SqlServerPageSqlParser sqlServerSqlParser = new SqlServerPageSqlParser();
    protected WithNoLockReplace withNoLockReplace;
    protected Cache<String, String> WITH_NO_LOCK_COUNT_SQL_CACHE;
    protected Cache<String, String> WITH_NO_LOCK_PAGE_SQL_CACHE;

    @Override
    public String generateQueryRecordSql(MappedStatement statement, BoundSql boundSql, Object parameter, RowBounds rowBounds, CacheKey cacheKey) {
        String originalSql = boundSql.getSql();
        String cacheSql = WITH_NO_LOCK_COUNT_SQL_CACHE.get(originalSql);
        if (StringUtil.hasText(cacheSql)) {
            return cacheSql;
        }
        // 替换WITH(NOLOCK)
        String newCacheSql = withNoLockReplace.replace(originalSql);
        newCacheSql = countSqlParser.smartTransform(newCacheSql);
        newCacheSql = withNoLockReplace.restore(newCacheSql);
        WITH_NO_LOCK_COUNT_SQL_CACHE.put(originalSql, newCacheSql);
        return newCacheSql;
    }

    @Override
    public Object processPageableParameter(MappedStatement statement, Map<String, Object> parameter, BoundSql boundSql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long offset) {
        return parameter;
    }

    @Override
    public String generateCorrespondPageableSql(String sql, CacheKey cacheKey, Long rowStart, Long rowEnd, Long pageSize) {
        // 处理pageKey
        cacheKey.update(rowStart);
        cacheKey.update(pageSize);
        String cacheSql = WITH_NO_LOCK_PAGE_SQL_CACHE.get(sql);
        if (StringUtil.isBlank(cacheSql)) {
            cacheSql = sql;
            cacheSql = withNoLockReplace.replace(cacheSql);
            cacheSql = sqlServerSqlParser.smartTransform(cacheSql);
            cacheSql = withNoLockReplace.restore(cacheSql);
            WITH_NO_LOCK_PAGE_SQL_CACHE.put(sql, cacheSql);
        }
        // 替换分页参数
        cacheSql = cacheSql.replace(SqlServerPageSqlParser.START_ROW, String.valueOf(rowStart));
        cacheSql = cacheSql.replace(SqlServerPageSqlParser.END_ROW, String.valueOf(pageSize));
        return cacheSql;
    }

    @Override
    public void setProperties(Properties props) {
        super.setProperties(props);
        String replace = props.getProperty("withNoLockReplace");
        if (StringUtil.isBlank(replace) || "simple".equalsIgnoreCase(replace)) {
            this.withNoLockReplace = new SimpleWithNoLockReplace();
        } else if ("regex".equalsIgnoreCase(replace)) {
            this.withNoLockReplace = new RegexWithNoLockReplace();
        } else {
            try {
                this.withNoLockReplace = (WithNoLockReplace) Class.forName(replace).newInstance();
            } catch (Exception e) {
                throw new MyBatisPluginException("The 'withNoLockReplace' parameter is misconfigured. The system defaults to 'simple' and 'regex', " +
                        "or implements the '" + WithNoLockReplace.class.getCanonicalName() + "' interface. Please change the configuration.", e);
            }
        }
        // 缓存
        String cacheClass = props.getProperty("withNoLockCacheClass");
        WITH_NO_LOCK_COUNT_SQL_CACHE = CacheFactory.createCache(cacheClass, "WITH_NO_LOCK_COUNT", props);
        WITH_NO_LOCK_PAGE_SQL_CACHE = CacheFactory.createCache(cacheClass, "WITH_NO_LOCK_PAGE", props);
    }
}
