package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.MapperExecutor;

/**
 * 通用Service类
 * @param <Executor> Mapper接口
 * @param <T>        实体、返回值类型
 * @author wvkity
 */
public abstract class AbstractServiceExecutor<Executor extends MapperExecutor<T>, T> extends
        AbstractSerialServiceExecutor<Executor, T, T> implements ServiceExecutor<T> {
}
