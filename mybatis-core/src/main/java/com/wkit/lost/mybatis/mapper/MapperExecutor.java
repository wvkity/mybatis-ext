package com.wkit.lost.mybatis.mapper;

/**
 * 实体、返回值一样Mapper通用接口
 * @param <T> 实体、返回值类型
 * @author wvkity
 */
public interface MapperExecutor<T> extends BaseMapperExecutor<T, T> {
}
