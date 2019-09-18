package com.wkit.lost.mybatis.service;

/**
 * 更细操作接口
 * @param <T>  泛型类型
 * @author DT
 */
public interface UpdateService<T> {

    /**
     * 根据指定对象更新记录
     * @param entity 指定对象
     * @return 受影响行数
     */
    int update( final T entity );

    /**
     * 根据指定对象更新记录
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateSelective( final T entity );
}
