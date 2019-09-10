package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * MyBatis泛型Mapper接口
 * @param <T>  实体类型
 * @param <PK> 主键类型
 * @param <R>  返回值类型
 */
@EnableMapper
public interface MapperExecutor<T, PK, R> extends WriterMapper<T, PK>, ReaderMapper<T, PK, R> {
}
