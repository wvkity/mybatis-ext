package com.wkit.lost.mybatis.service;

/**
 * 读操作接口
 * @param <T> 泛型类
 * @param <R> 返回值类型
 */
public interface ReaderService<T, R> extends QueryService<T, R>, CriteriaService<T, R> {
}
