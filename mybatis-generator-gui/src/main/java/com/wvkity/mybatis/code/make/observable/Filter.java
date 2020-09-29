package com.wvkity.mybatis.code.make.observable;

import javafx.beans.value.ObservableValue;

/**
 * 过滤器
 * @param <T> 值类型
 * @param <V> 值类型
 * @author wvkity
 */
@FunctionalInterface
public interface Filter<T, V> {

    /**
     * 过滤
     * @param observable {@link ObservableValue}
     * @param oldValue   旧值
     * @param newValue   新值
     * @return boolean
     */
    boolean filter(final ObservableValue<? extends V> observable, final V oldValue, final V newValue);

    /**
     * 过滤
     * @return boolean
     */
    default boolean filter() {
        return true;
    }
}
