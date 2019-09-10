package com.wkit.lost.mybatis.mapper;

import com.wkit.lost.mybatis.annotation.EnableMapper;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * 查询数据操作接口
 * @param <T>  泛型类
 * @param <PK> 主键
 * @param <R>  返回值类
 */
@EnableMapper
public interface QueryMapper<T, PK, R> {

    /**
     * 根据指定对象查询记录是否存在
     * @param entity 指定对象
     * @return 1: 存在 | 0: 不存在
     */
    int exists( T entity );

    /**
     * 根据主键查询记录是否存在
     * @param primaryKey 主键值
     * @return 1: 存在 | 0: 不存在
     */
    int existsById( PK primaryKey );

    /**
     * 根据指定对象查询记录数
     * @param entity 指定对象
     * @return 记录数
     */
    long count( T entity );

    /**
     * 根据条件查询记录
     * @param primaryKey 主键
     * @return 对应数据
     */
    Optional<R> selectOne( PK primaryKey );

    /**
     * 根据多个主键查询记录
     * @param primaryKeys 主键集合
     * @return 多条记录
     */
    List<R> list( @Param( "primaryKeys" ) Collection<PK> primaryKeys );

    /**
     * 根据制定对象查询记录
     * @param entity 制定对象
     * @return 多条记录
     */
    List<R> listByEntity( T entity );

    /**
     * 根据多个对象查询记录
     * @param entities 对象集合
     * @return 多条记录
     */
    List<R> listByEntities( @Param( "entities" ) Collection<T> entities );

    /**
     * 分页查询记录
     * @param pageable 分页对象
     * @param entity   指定对象
     * @return 多条记录
     */
    List<R> pageableList( @Param( "pageable" ) Pageable pageable, @Param( "entity" ) T entity );
}
