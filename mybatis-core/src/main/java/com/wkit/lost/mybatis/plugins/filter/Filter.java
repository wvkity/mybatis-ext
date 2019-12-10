package com.wkit.lost.mybatis.plugins.filter;

import com.wkit.lost.mybatis.plugins.executor.Argument;

/**
 * 过滤器
 * @author wvkity
 */
@FunctionalInterface
public interface Filter {

    /**
     * 过滤
     * @param arg 参数对象
     * @return boolean
     */
    boolean filter( Argument arg );
}
