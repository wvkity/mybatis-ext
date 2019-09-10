package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;

/**
 * 更新数据操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 */
@EnableMapper
public interface UpdateMapper<T, PK> {

    /**
     * 根据指定对象更新记录(主键为条件，更新所有字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int update( T entity );

    /**
     * 根据指定对象更新记录(主键为条件，更新可选字段[updatable=true])
     * @param entity 指定对象
     * @return 受影响行数
     */
    int updateSelective( T entity );
}
