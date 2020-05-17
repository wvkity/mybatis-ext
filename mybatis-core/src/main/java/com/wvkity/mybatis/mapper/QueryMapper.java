package com.wvkity.mybatis.mapper;

import com.wvkity.mybatis.utils.Constants;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 查询数据操作接口
 * @param <T> 泛型类
 * @param <V> 返回值类
 */
public interface QueryMapper<T, V, PK> {

    /**
     * 根据指定对象查询记录是否存在
     * @param entity 指定对象
     * @return 1: 存在 | 0: 不存在
     */
    int exists(@Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据主键查询记录是否存在
     * @param id 主键值
     * @return 1: 存在 | 0: 不存在
     */
    int existsById(PK id);

    /**
     * 查询所有记录数
     * @return 记录数
     */
    long allCount();

    /**
     * 根据指定对象查询记录数
     * @param entity 指定对象
     * @return 记录数
     */
    long count(@Param(Constants.PARAM_ENTITY) T entity);

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
    List<V> allList();

    /**
     * 根据多个主键查询记录
     * @param idList 主键集合
     * @return 多条记录
     */
    List<V> list(@Param(Constants.PARAM_PRIMARY_KEYS) Collection<PK> idList);

    /**
     * 根据制定对象查询记录
     * @param entity 制定对象
     * @return 多条记录
     */
    List<V> listByEntity(@Param(Constants.PARAM_ENTITY) T entity);

    /**
     * 根据多个对象查询记录
     * @param entities 对象集合
     * @return 多条记录
     */
    List<V> listByEntities(@Param(Constants.PARAM_ENTITIES) Collection<T> entities);

}
