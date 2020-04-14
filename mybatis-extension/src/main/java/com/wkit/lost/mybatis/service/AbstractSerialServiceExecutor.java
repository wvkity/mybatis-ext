package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.SerialMapperExecutor;

import java.io.Serializable;

/**
 * 通用Service抽象类
 * @param <Executor> Mapper接口
 * @param <T>        实体类型
 * @param <V>        返回值类型
 * @author wvkity
 */
public abstract class AbstractSerialServiceExecutor<Executor extends SerialMapperExecutor<T, V>, T, V> extends
        AbstractBaseServiceExecutor<Executor, T, V, Serializable> implements SerialServiceExecutor<T, V> {
}
