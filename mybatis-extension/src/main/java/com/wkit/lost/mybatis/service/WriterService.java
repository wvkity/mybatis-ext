package com.wkit.lost.mybatis.service;

/**
 * 写操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 */
public interface WriterService<T> extends InsertService<T>,
        UpdateService<T>, DeleteService<T> {
}
