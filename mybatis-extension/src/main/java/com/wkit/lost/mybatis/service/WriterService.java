package com.wkit.lost.mybatis.service;

/**
 * 写操作接口
 * @param <T>  泛型类
 */
interface WriterService<T> extends InsertService<T>,
        UpdateService<T>, DeleteService<T> {
}
