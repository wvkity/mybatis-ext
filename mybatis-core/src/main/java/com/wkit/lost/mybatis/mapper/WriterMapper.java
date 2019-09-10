package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 写操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 */
@EnableMapper
public interface WriterMapper<T, PK> extends InsertMapper<T, PK>,
        UpdateMapper<T, PK>, DeleteMapper<T, PK> {
}
