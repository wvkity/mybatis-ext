package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 保存数据操作接口
 * @param <T>  泛型类
 */
@EnableMapper
public interface InsertMapper<T> {

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insert( T entity );

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insertSelective( T entity );
}
