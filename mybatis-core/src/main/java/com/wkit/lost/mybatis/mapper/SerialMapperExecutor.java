package com.wkit.lost.mybatis.mapper;

import java.io.Serializable;

/**
 * 主键为实现Serializable通用Mapper接口
 * @param <T> 实体类型
 * @param <V> 返回值类型
 * @author wvkity
 */
public interface SerialMapperExecutor<T, V> extends BaseMapperExecutor<T, V, Serializable> {
}
