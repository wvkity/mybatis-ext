package com.wkit.lost.mybatis.service;

/**
 * 读操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 * @param <R>  返回值类型
 */
public interface ReaderService<T, PK, R> extends QueryService<T, PK, R>, CriteriaService<T, PK, R> {
}
