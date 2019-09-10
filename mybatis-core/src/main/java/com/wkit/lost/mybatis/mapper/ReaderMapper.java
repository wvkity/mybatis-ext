package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 读操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 * @param <R>  返回值类
 */
@EnableMapper
public interface ReaderMapper<T, PK, R> extends QueryMapper<T, PK, R>, CriteriaMapper<T, PK, R> {
}
