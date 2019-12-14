package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 写操作接口
 * @param <T> 泛型类
 */
@EnableMapper
interface WriterMapper<T> extends InsertMapper<T>,
        UpdateMapper<T>, DeleteMapper<T> {
}
