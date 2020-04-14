package com.wkit.lost.mybatis.core.converter;

import java.io.Serializable;
import java.util.function.Function;

/**
 * Lambda属性
 * @param <T> 实体类型
 * @param <R> 返回值类型
 * @author wvkity
 * @see Function
 */
@FunctionalInterface
public interface Property<T, R> extends Function<T, R>, Serializable {
}
