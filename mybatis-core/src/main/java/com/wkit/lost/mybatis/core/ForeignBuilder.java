package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

public interface ForeignBuilder<T> {

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, foreign, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param foreignCriteria 联表条件对象
     * @return 当前对象
     */
    Criteria<T> addForeign( ForeignCriteria<?> foreignCriteria );

    /**
     * 添加联表条件对象
     * @param foreignCriteriaArray 联表条件对象数组
     * @return 当前对象
     */
    Criteria<T> addForeign( ForeignCriteria<?>... foreignCriteriaArray );

    /**
     * 添加联表条件对象
     * @param foreignCriteriaList 联表条件对象集合
     * @return 当前对象
     */
    Criteria<T> addForeign( Collection<ForeignCriteria<?>> foreignCriteriaList );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( Class<E> entity );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias, Class<E> entity );

}
