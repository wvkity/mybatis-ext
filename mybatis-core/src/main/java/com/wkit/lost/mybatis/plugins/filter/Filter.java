package com.wkit.lost.mybatis.plugins.filter;

import com.wkit.lost.mybatis.plugins.executor.AbstractQueryExecutor;

/**
 * 过滤器
 * @author DT
 */
@FunctionalInterface
public interface Filter {

    /**
     * 过滤
     * @param argument 参数
     * @return boolean
     */
    boolean filter( AbstractQueryExecutor.Argument argument );
}
