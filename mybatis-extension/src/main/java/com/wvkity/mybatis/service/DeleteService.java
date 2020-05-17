package com.wvkity.mybatis.service;

import java.util.Collection;
import java.util.List;

/**
 * 删除操作接口
 * @param <T> 泛型类
 */
public interface DeleteService<T, PK> {

    /**
     * 根据指定对象删除记录
     * <p>对象必须要有值，不然会删除整表数据，慎用</p>
     * @param entity 指定对象
     * @return 受影响行数
     */
    int delete(T entity);

    /**
     * 根据主键删除记录
     * @param id 主键
     * @return 受影响行数
     */
    int deleteById(PK id);

    /**
     * 逻辑删除
     * @param entity 实体
     * @return 受影响行数
     */
    int logicDelete(T entity);

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象数组
     * @return 受影响行数
     */
    @SuppressWarnings("unchecked")
    int batchDelete(T... entities);

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象集合
     * @return 受影响行数
     */
    int batchDelete(List<T> entities);

    /**
     * 根据主键批量删除记录
     * @param ids 主键数组
     * @return 受影响行数
     */
    @SuppressWarnings("unchecked")
    int batchDeleteById(PK... ids);

    /**
     * 根据主键批量删除记录
     * @param ids 主键集合
     * @return 受影响行数
     */
    int batchDeleteById(Collection<PK> ids);
}
