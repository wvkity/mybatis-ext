package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.MapperExecutor;

/**
 * 泛型service抽象类
 * @param <Executor> Mapper接口
 * @param <T> 实体、返回值类型
 * @author wvkity
 */
public abstract class AbstractServiceExecutor<Executor extends MapperExecutor<T>, T> extends
        AbstractServiceExecutorCallable<Executor, T, T> {
}
