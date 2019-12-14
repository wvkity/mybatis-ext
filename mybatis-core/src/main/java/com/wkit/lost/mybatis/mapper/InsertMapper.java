package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

/**
 * 保存数据操作接口
 * @param <T> 泛型类
 */
@EnableMapper
interface InsertMapper<T> {

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insert( @Param( Constants.PARAM_ENTITY ) T entity );

    /**
     * 保存记录
     * @param entity 待保存对象
     * @return 受影响行数
     */
    int insertSelective( @Param( Constants.PARAM_ENTITY ) T entity );
}
