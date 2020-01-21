package com.wkit.lost.mybatis.plugins.filter;

import com.wkit.lost.mybatis.plugins.executor.Argument;
import org.apache.ibatis.mapping.MappedStatement;

/**
 * 过滤器
 * @author wvkity
 */
public interface Filter {

    /**
     * 过滤
     * @param arg 参数对象
     * @return boolean
     */
    default boolean filter( Argument arg ) {
        return false;
    }

    /**
     * 过滤
     * @param ms {@link MappedStatement}
     * @param parameter 方法参数
     * @return boolean
     */
    default boolean filter( MappedStatement ms, Object parameter ) {
        return false;
    }
}
