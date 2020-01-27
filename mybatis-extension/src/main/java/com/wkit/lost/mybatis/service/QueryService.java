package com.wkit.lost.mybatis.service;

import com.wkit.lost.paging.Pageable;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 查询操作接口
 * @param <T>  泛型类
 */
interface QueryService<T, R> {

    /**
     * 根据指定对象查询记录是否存在
     * @param entity 指定对象
     * @return true: 存在 | false: 不存在
     */
    boolean exists( T entity );

    /**
     * 根据主键查询记录是否存在
     * @param id 主键值
     * @return true: 存在 | false: 不存在
     */
    boolean exists( Serializable id );

    /**
     * 根据指定对象查询记录数
     * @param entity 指定对象
     * @return 记录数
     */
    long count( T entity );

    /**
     * 根据条件查询记录
     * @param id 主键
     * @return 对应数据
     */
    Optional<R> selectOne( Serializable id );

    /**
     * 根据多个主键查询记录
     * @param idArray 主键数组
     * @return 多条记录
     */
    List<R> list( Serializable... idArray );

    /**
     * 根据多个主键查询记录
     * @param idList 主键集合
     * @return 多条记录
     */
    List<R> list( Collection<? extends Serializable> idList );

    /**
     * 根据制定对象查询记录
     * @param entity 制定对象
     * @return 多条记录
     */
    List<R> list( T entity );

    /**
     * 根据多个对象查询记录
     * @param entities 对象数组
     * @return 多条记录
     */
    @SuppressWarnings( "unchecked" )
    List<R> list( T... entities );

    /**
     * 根据多个对象查询记录
     * @param entities 对象集合
     * @return 多条记录
     */
    List<R> listByEntities( Collection<T> entities );

    /**
     * 分页查询记录
     * @param entity   指定对象
     * @param pageable 分页对象
     * @return 多条记录
     */
    List<R> list( T entity, Pageable pageable);
}
