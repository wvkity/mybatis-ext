package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.MapperSameExecutor;

/**
 * 泛型service抽象类
 * @param <Executor> Mapper接口
 * @param <T> 实体、返回值类型
 * @author DT
 */
public abstract class AbstractServiceSameExecutor<Executor extends MapperSameExecutor<T>, T> extends AbstractServiceExecutor<Executor, T, T> {
}
