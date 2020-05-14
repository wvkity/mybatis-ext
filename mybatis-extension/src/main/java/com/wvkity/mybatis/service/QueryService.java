package com.wvkity.mybatis.service;

import com.wkit.lost.paging.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 查询操作接口
 * @param <T>  泛型类
 * @param <V>  返回值类型
 * @param <PK> 主键类型
 * @author wvkity
 */
public interface QueryService<T, V, PK> {

    /**
     * 根据指定对象查询记录是否存在
     * @param entity 指定对象
     * @return true: 存在 | false: 不存在
     */
    boolean exists(T entity);

    /**
     * 根据主键查询记录是否存在
     * @param id 主键值
     * @return true: 存在 | false: 不存在
     */
    boolean existsById(PK id);

    /**
     * 根据指定对象查询记录数
     * @param entity 指定对象
     * @return 记录数
     */
    long count(T entity);

    /**
     * 根据条件查询记录
     * @param id 主键
     * @return 对应数据
     */
    Optional<V> selectOne(PK id);

    /**
     * 查询所有记录
     * @return 多条记录
     */
    List<V> list();

    /**
     * 根据多个主键查询记录
     * @param ids 主键数组
     * @return 多条记录
     */
    @SuppressWarnings({"unchecked"})
    List<V> list(PK... ids);

    /**
     * 根据多个主键查询记录
     * @param ids 主键集合
     * @return 多条记录
     */
    List<V> list(Collection<PK> ids);

    /**
     * 根据制定对象查询记录
     * @param entity 制定对象
     * @return 多条记录
     */
    List<V> list(T entity);

    /**
     * 根据多个对象查询记录
     * @param entities 对象数组
     * @return 多条记录
     */
    @SuppressWarnings("unchecked")
    List<V> listByEntities(T... entities);

    /**
     * 根据多个对象查询记录
     * @param entities 对象集合
     * @return 多条记录
     */
    List<V> list(List<T> entities);

    /**
     * 分页查询记录
     * @param pageable 分页对象
     * @return 多条记录
     */
    List<V> list(Pageable pageable);

    /**
     * 分页查询记录
     * @param entity   指定对象
     * @param pageable 分页对象
     * @return 多条记录
     */
    List<V> list(T entity, Pageable pageable);
}
