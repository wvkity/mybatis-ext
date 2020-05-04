package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.mapper.SimpleMapper;

/**
 * 通用Service类
 * @param <Mapper> Mapper接口
 * @param <T>      实体、返回值类型
 * @author wvkity
 */
public abstract class AbstractSimpleService<Mapper extends SimpleMapper<T>, T> extends
        AbstractSerialService<Mapper, T, T> implements SimpleService<T> {
}
