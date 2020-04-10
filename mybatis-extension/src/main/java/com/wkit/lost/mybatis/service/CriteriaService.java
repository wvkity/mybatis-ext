package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.paging.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 查询操作接口
 * @param <T> 泛型类
 * @param <V> 返回值类
 */
public interface CriteriaService<T, V> {

    /**
     * 根据Criteria对象查询记录是否存在
     * @param criteria 条件对象
     * @return true: 存在 , false: 不存在
     */
    boolean exists(Criteria<T> criteria);

    /**
     * 根据指定条件对象更新记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int update(Criteria<T> criteria);

    /**
     * 根据指定条件对象、实体对象更新记录
     * @param entity   实体对象(更新值部分)
     * @param criteria 条件对象(条件部分)
     * @return 受影响行数
     */
    int updateNotWithNull(T entity, Criteria<T> criteria);

    /**
     * 根据条件对象删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int delete(Criteria<T> criteria);

    /**
     * 根据Criteria对象执行逻辑删除记录
     * @param criteria 条件对象
     * @return 受影响行数
     */
    int logicDelete(Criteria<T> criteria);

    /**
     * 查询列表
     * @param criteria 条件对象
     * @return 列表
     */
    List<V> list(Criteria<T> criteria);

    /**
     * 查询列表(返回自定义类型)
     * @param criteria 条件对象
     * @param <E>      返回值泛型
     * @return 列表
     */
    <E> List<E> custom(Criteria<T> criteria);

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object> objects(Criteria<T> criteria);

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Object集合
     */
    List<Object[]> array(Criteria<T> criteria);

    /**
     * 查询数据
     * @param criteria 条件对象
     * @return Map
     */
    List<Map<String, Object>> map(Criteria<T> criteria);

    /**
     * 分页查询列表
     * @param criteria 条件对象
     * @param pageable 分页对象
     * @return 列表
     */
    List<V> list(Criteria<T> criteria, Pageable pageable);
}
