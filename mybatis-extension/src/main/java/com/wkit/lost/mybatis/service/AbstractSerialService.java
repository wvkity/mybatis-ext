package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.SerialMapper;

import java.io.Serializable;

/**
 * 通用Service抽象类
 * @param <Mapper> Mapper接口
 * @param <T>      实体类型
 * @param <V>      返回值类型
 * @author wvkity
 */
public abstract class AbstractSerialService<Mapper extends SerialMapper<T, V>, T, V> extends
        AbstractBaseService<Mapper, T, V, Serializable> implements SerialService<T, V> {
}
