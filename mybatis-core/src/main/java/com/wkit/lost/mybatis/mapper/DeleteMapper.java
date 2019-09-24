package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;
import com.wkit.lost.mybatis.utils.Constants;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 删除数据操作接口
 * @param <T> 泛型类
 */
@EnableMapper
public interface DeleteMapper<T> {

    /**
     * 根据指定对象删除记录
     * @param entity 指定对象
     * @return 受影响行数
     */
    int delete( T entity );

    /**
     * 根据主键删除记录
     * @param id 主键
     * @return 受影响行数
     */
    int deleteById( Serializable id );

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象集合
     * @return 受影响行数
     */
    int batchDelete( @Param( Constants.PARAM_ENTITIES ) Collection<T> entities );

    /**
     * 根据主键批量删除记录
     * @param idList 主键集合
     * @return 受影响行数
     */
    int batchDeleteById( @Param( Constants.PARAM_PRIMARY_KEYS ) List<? extends Serializable> idList );

}
