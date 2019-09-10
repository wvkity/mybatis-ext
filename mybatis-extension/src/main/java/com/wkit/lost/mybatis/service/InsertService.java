package com.wkit.lost.mybatis.service;

/**
 * 保存操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 */
public interface InsertService<T, PK> {

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int save( final T entity );

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int saveSelective( final T entity );
}
