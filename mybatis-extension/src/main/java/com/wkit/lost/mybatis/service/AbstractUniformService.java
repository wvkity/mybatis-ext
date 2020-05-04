package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.UniformMapper;

/**
 * 通用Service抽象类
 * @param <Mapper> Mapper接口
 * @param <T>      实体类型、返回值类型
 * @param <PK>     主键类型
 * @author wvkity
 */
public abstract class AbstractUniformService<Mapper extends UniformMapper<T, PK>, T, PK>
        extends AbstractBaseService<Mapper, T, T, PK> implements UniformService<T, PK> {
}
