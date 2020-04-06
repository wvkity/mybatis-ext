package com.wkit.lost.mybatis.core.metadata;

/**
 * 构建器
 * @param <T> 泛型类型
 * @author wvkity
 */
@FunctionalInterface
public interface Builder<T> {

    /**
     * 构建
     * @return 泛型对象
     */
    T build();

}
