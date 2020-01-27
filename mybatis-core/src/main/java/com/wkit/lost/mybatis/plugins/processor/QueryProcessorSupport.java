package com.wkit.lost.mybatis.plugins.processor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * 查询拦截处理器
 * @author wvkity
 */
public abstract class QueryProcessorSupport extends Processor {

    @Override
    public Object intercept( Invocation invocation ) throws Throwable {
        // 参数
        Object[] args = invocation.getArgs();
        int size = args.length;
        MappedStatement ms = ( MappedStatement ) args[ 0 ];
        Object parameter = args[ 1 ];
        RowBounds rowBounds = ( RowBounds ) args[ 2 ];
        ResultHandler<?> resultHandler = ( ResultHandler<?> ) args[ 3 ];
        Executor executor = ( Executor ) invocation.getTarget();
        CacheKey cacheKey;
        BoundSql boundSql;
        if ( size == 4 ) {
            boundSql = ms.getBoundSql( parameter );
            cacheKey = executor.createCacheKey( ms, parameter, rowBounds, boundSql );
        } else {
            cacheKey = ( CacheKey ) args[ 4 ];
            boundSql = ( BoundSql ) args[ 5 ];
        }
        if ( filter( ms, parameter ) ) {
            return doProcess( invocation, executor, ms, parameter, rowBounds, resultHandler, cacheKey, boundSql );
        } else {
            // 执行原查询
            return executor.query( ms, parameter, rowBounds, resultHandler, cacheKey, boundSql );
        }
    }

    /**
     * 拦截处理
     * @param invocation    代理对象
     * @param executor      执行器
     * @param ms            {@link MappedStatement}对象
     * @param parameter     方法参数
     * @param rowBounds     内存分页参数
     * @param resultHandler 结果集处理器
     * @param cacheKey      缓存key
     * @param boundSql      SQL绑定对象
     * @return 结果
     * @throws Exception 异常信息
     */
    public abstract Object doProcess( Invocation invocation, Executor executor, MappedStatement ms,
                                      Object parameter, RowBounds rowBounds, ResultHandler<?> resultHandler,
                                      CacheKey cacheKey, BoundSql boundSql ) throws Exception;
}
