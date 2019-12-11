package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.UnifyMapperExecutor;

/**
 * 泛型service抽象类
 * @param <Executor> Mapper接口
 * @param <T> 实体、返回值类型
 * @author wvkity
 */
public abstract class AbstractUnifyServiceExecutor<Executor extends UnifyMapperExecutor<T>, T> extends AbstractServiceExecutor<Executor, T, T> {
}
