package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.UniformMapperExecutor;

/**
 * 通用Service抽象类
 * @param <Executor> Mapper接口
 * @param <T>        实体类型、返回值类型
 * @param <PK>       主键类型
 * @author wvkity
 */
public abstract class AbstractUniformServiceExecutor<Executor extends UniformMapperExecutor<T, PK>, T, PK>
        extends AbstractBaseServiceExecutor<Executor, T, T, PK> implements UniformServiceExecutor<T, PK> {
}
