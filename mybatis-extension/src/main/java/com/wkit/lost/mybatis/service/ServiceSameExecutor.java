package com.wkit.lost.mybatis.service;

/**
 * 业务泛型接口
 * @param <T> 实体、返回值类型
 * @author DT
 */
public interface ServiceSameExecutor<T> extends ServiceExecutor<T, T> {
}
