package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 删除数据操作接口
 * @param <T>  泛型类
 * @param <PK> 主键类型
 */
@EnableMapper
public interface DeleteMapper<T, PK> {

    /**
     * 根据指定对象删除记录
     * @param entity 指定对象
     * @return 受影响行数
     */
    int delete( T entity );

    /**
     * 根据主键删除记录
     * @param primaryKey 主键
     * @return 受影响行数
     */
    int deleteById( PK primaryKey );

    /**
     * 根据指定对象批量删除记录
     * @param entities 对象集合
     * @return 受影响行数
     */
    int batchDelete( @Param( "entities" ) Collection<T> entities );

    /**
     * 根据主键批量删除记录
     * @param primaryKeys 主键集合
     * @return 受影响行数
     */
    int batchDeleteById( @Param( "primaryKeys" ) List<PK> primaryKeys );
    
}
