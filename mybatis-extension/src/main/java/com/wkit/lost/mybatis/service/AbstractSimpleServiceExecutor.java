package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.SimpleMapperExecutor;

/**
 * 通用Service类
 * @param <Executor> Mapper接口
 * @param <T>        实体、返回值类型
 * @author wvkity
 */
public abstract class AbstractSimpleServiceExecutor<Executor extends SimpleMapperExecutor<T>, T> extends
        AbstractSerialServiceExecutor<Executor, T, T> implements SimpleServiceExecutor<T> {
}
