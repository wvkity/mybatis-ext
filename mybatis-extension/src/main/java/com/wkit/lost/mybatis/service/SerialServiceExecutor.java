package com.wkit.lost.mybatis.service;

import java.io.Serializable;

/**
 * 通用Service接口
 * @param <T> 实体类型
 * @param <V> 返回值类型
 * @author wvkity
 */
public interface SerialServiceExecutor<T, V> extends BaseServiceExecutor<T, V, Serializable> {
}
