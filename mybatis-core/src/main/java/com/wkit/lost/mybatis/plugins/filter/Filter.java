package com.wkit.lost.mybatis.plugins.filter;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 过滤器
 * @author wvkity
 */
@FunctionalInterface
public interface Filter {


    /**
     * 过滤
     * @param ms        {@link MappedStatement}
     * @param parameter 方法参数
     * @return boolean
     */
    boolean filter(MappedStatement ms, Object parameter);
}
