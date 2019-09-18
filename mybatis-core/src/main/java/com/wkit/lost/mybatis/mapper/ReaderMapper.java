package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 读操作接口
 * @param <T>  泛型类
 * @param <R>  返回值类
 */
@EnableMapper
public interface ReaderMapper<T, R> extends QueryMapper<T, R>, CriteriaMapper<T, R> {
}
