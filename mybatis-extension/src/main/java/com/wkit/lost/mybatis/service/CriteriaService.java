package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.paging.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 查询操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 * @param <R>  返回值类
 */
public interface CriteriaService<T, PK, R> {

    /**
     * 根据Criteria对象查询记录是否存在
     * @param criteria 条件对象
     * @return true: 存在 , false: 不存在
     */
    boolean exists( Criteria<T> criteria );

    /**
     * 查询列表
     * @param criteria 条件对象
     * @return 列表
     */
    List<R> list( Criteria<T> criteria );

    /**
     * 查询列表(返回自定义类型)
     * @param criteria 条件对象
     * @param <E>      返回值泛型
     * @return 列表
     */
    <E> List<E> listForCustom( Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object> listForObject( Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object[]> listForArray( Criteria<T> criteria );

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Map
     */
    List<Map<String, Object>> listForMap( Criteria<T> criteria );

    /**
     * 分页查询列表
     * @param pageable 分页对象
     * @param criteria 条件对象
     * @return 列表
     */
    List<R> pageableList( Pageable pageable, Criteria<T> criteria );
}
