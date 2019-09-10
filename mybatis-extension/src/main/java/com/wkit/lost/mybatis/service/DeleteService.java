package com.wkit.lost.mybatis.service;

import java.util.Collection;

/**
 * 删除操作接口
 * @param <T> 泛型类
 * @param <PK> 主键类型
 */
public interface DeleteService<T, PK> {

    /**
     * 根据指定对象删除记录
     * <p>对象必须要有值，不然会删除整表数据，慎用</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int delete( T entity );

    /**
     * 根据主键删除记录
     * @param primaryKey 主键
     * @return 受影响行数
     */
    int deleteById( PK primaryKey );

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象数组
     * @return 受影响行数
     */
    @SuppressWarnings( "unchecked" )
    int batchDelete( T... entities );

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象集合
     * @return 受影响行数
     */
    int batchDelete( Collection<T> entities );

    /**
     * 根据主键批量删除记录
     * @param primaryKeys 主键数组
     * @return 受影响行数
     */
    @SuppressWarnings( "unchecked" )
    int batchDeleteById( PK... primaryKeys );
    
    /**
     * 根据主键批量删除记录
     * @param primaryKeys 主键集合
     * @return 受影响行数
     */
    int batchDeleteById( Collection<PK> primaryKeys );
}
