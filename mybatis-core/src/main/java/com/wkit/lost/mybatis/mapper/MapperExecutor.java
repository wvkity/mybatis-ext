package com.wkit.lost.mybatis.mapper;

/**
 * MyBatis泛型Mapper接口
 * @param <T>  实体类型
 * @param <R>  返回值类型
 */
public interface MapperExecutor<T, R> extends WriterMapper<T>, ReaderMapper<T, R> {
}
