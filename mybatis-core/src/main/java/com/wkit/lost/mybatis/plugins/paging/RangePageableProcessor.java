package com.wkit.lost.mybatis.plugins.paging;

import com.wkit.lost.mybatis.plugins.paging.dbs.dialect.Dialect;
import com.wkit.lost.mybatis.plugins.executor.Executors;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Optional;

/**
 * 指定范围分页查询处理器
 * @author wvikty
 */
@Log4j2
public class RangePageableProcessor extends PagingProcessor {

    @Override
    protected PageMode getMode() {
        return PageMode.RANGE;
    }

    @Override
    protected String getDefaultDialect() {
        return "com.wkit.lost.mybatis.plugins.paging.dbs.adapter.RangePageableAdapter";
    }

    @Override
    public Object doProcess(Invocation invocation, Executor executor, MappedStatement ms, Object parameter,
                            RowBounds rowBounds, ResultHandler<?> resultHandler, CacheKey cacheKey,
                            BoundSql boundSql) throws Exception {
        try {
            // 检查代理对象是否存在
            validateDialectExists();
            // 检查是否需要执行
            List<?> result;
            if (this.factory.filter(ms, parameter, rowBounds)) {
                // 执行分段查询
                result = Executors.executeQueryPageableOfCustom(this.factory, executor, ms, parameter,
                        rowBounds, resultHandler, boundSql, cacheKey);
            } else {
                // 执行原查询
                result = executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);
            }
            return factory.executePagingOnAfter(result, parameter, rowBounds);
        } catch (Exception e) {
            log.warn("Limit query plug-in failed to execute: `{}` -- {}", ms.getId(), e);
            throw e;
        } finally {
            Optional.ofNullable(this.factory).ifPresent(Dialect::completed);
        }
    }
}
