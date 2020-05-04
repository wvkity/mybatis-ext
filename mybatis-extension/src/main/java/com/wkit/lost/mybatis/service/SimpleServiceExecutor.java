package com.wkit.lost.mybatis.service;

/**
 * 泛型Service接口
 * @param <T> 实体、返回值类型
 * @author wvkity
 */
public interface SimpleServiceExecutor<T> extends SerialServiceExecutor<T, T> {
}
