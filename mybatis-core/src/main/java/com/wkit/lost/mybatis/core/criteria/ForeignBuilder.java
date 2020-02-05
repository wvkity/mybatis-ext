package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.lambda.LambdaResolver;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.function.Function;

/**
 * 联表查询条件对象创建接口
 * @param <T>       实体泛型类型
 * @param <Context> 当前对象
 * @param <R>       Lambda对象
 */
public interface ForeignBuilder<T, Context, R> extends LambdaResolver<R> {

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @return 属性名
     */
    String methodToProperty( Property<?, ?> property );

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master,
                                                  Property<E, ?> foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master,
                                                  String foreign, Criterion<?>... withClauses ) {
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, Criterion<?>... withClauses ) {
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                                  JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign,
                                                  JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, master, foreign, joinMode, withClauses );
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, JoinMode joinMode,
                                                  Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), withClauses );
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master,
                                                  Property<E, ?> foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master,
                                                  String foreign, Collection<Criterion<?>> withClauses ) {
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, Collection<Criterion<?>> withClauses ) {
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                                  JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign,
                                                  JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, JoinMode joinMode,
                                                  Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), withClauses );
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
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                                  Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign,
                                                  Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, Function<ForeignCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, Function<ForeignCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param joinMode 连接方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                                  JoinMode joinMode, Function<ForeignCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, null, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), function );
    }

    /**
     * 创建联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param joinMode 连接方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign,
                                                  JoinMode joinMode, Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, null, new Foreign( master, foreign, joinMode ), function );
    }

    /**
     * 创建联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param joinMode  连接方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, R master,
                                                  Property<E, ?> foreign, JoinMode joinMode,
                                                  Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), function );
    }

    /**
     * 创建联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param joinMode  连接方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master,
                                                  String foreign, JoinMode joinMode,
                                                  Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), function );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign,
                                                  Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                                  Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign,
                                                  Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, foreign, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                          Collection<Criterion<?>> withClauses );

    /**
     * 创建联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param foreign  联表方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign,
                                                  Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( entity, alias, null, foreign, function );
    }

    /**
     * 创建联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param foreign   联表方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 联表条件对象
     */
    <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                          Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function );

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master,
                                                     String foreign, JoinMode joinMode ) {
        return createForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master,
                                                     Property<E, ?> foreign, JoinMode joinMode ) {
        return createForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String master,
                                                     String foreign, JoinMode joinMode ) {
        return createForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master, String foreign,
                                                     JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign,
                                                     JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String master, String foreign,
                                                     JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     String foreign, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     Property<E, ?> foreign, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, String master,
                                                     String foreign, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference,
                                                     R master, Property<E, ?> foreign,
                                                     JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ), foreign, joinMode ), withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, String master,
                                                     String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master, String foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String master, String foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param joinMode    连接模式
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master,
                                                     Property<E, ?> foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接模式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, R master,
                                                     String foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接模式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String master,
                                                     String foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     String foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     Property<E, ?> foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, String master,
                                                     String foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     String foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ),
                foreign, joinMode ), function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, R master,
                                                     Property<E, ?> foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, String master,
                                                     String foreign, JoinMode joinMode,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, reference, new Foreign( master, foreign, joinMode ), function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param foreign     联表方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, Foreign foreign,
                                                     Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return createForeign( subCriteria, null, foreign, function );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, Foreign foreign,
                                                     Criterion<?>... withClauses ) {
        return createForeign( subCriteria, null, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference,
                                                     Foreign foreign, Criterion<?>... withClauses ) {
        return createForeign( subCriteria, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference,
                                             Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 创建子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                             Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( entity, alias, lambdaToProperty( master ), methodToProperty( foreign ), withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, master, foreign, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master, Property<E, ?> foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), ArrayUtil.toList( withClauses ) );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ),
                ArrayUtil.toList( withClauses ) );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master, Property<E, ?> foreign,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign, JoinMode joinMode,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference,
                new Foreign( lambdaToProperty( master ), methodToProperty( foreign ), joinMode ), withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, foreign, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, Foreign foreign,
                                    Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, foreign, withClauses );
    }

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @param <E>         泛型类型
     * @return 当前对象
     */
    <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                            Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master, Property<E, ?> foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param joinMode 连接方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, R master, Property<E, ?> foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, function );
    }

    /**
     * 添加联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param master   主表属性
     * @param foreign  副表属性
     * @param joinMode 连接方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, function );
    }

    /**
     * 添加联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param joinMode  连接方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, R master,
                                    Property<E, ?> foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, reference, lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode, function );
    }

    /**
     * 添加联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param master    主表属性
     * @param foreign   副表属性
     * @param joinMode  连接方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign,
                                    JoinMode joinMode, Function<ForeignCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), function );
    }

    /**
     * 添加联表条件对象
     * @param entity   实体类
     * @param alias    别名
     * @param foreign  联表方式
     * @param function lambda function对象
     * @param <E>      泛型类型
     * @return 当前对象
     */
    default <E> Context addForeign( Class<E> entity, String alias, Foreign foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( entity, alias, null, foreign, function );
    }

    /**
     * 添加联表条件对象
     * @param entity    实体类
     * @param alias     别名
     * @param reference 引用属性
     * @param foreign   联表方式
     * @param function  lambda function对象
     * @param <E>       泛型类型
     * @return 当前对象
     */
    <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                            Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function );

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, String foreign ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String master, String foreign ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign, JoinMode joinMode ) {
        return addForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, String foreign, JoinMode joinMode ) {
        return addForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String master, String foreign, JoinMode joinMode ) {
        return addForeign( subCriteria, null, master, foreign, joinMode );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, Property<E, ?> foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, String foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, String master, String foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ),
                methodToProperty( foreign ), joinMode ), withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ), foreign, joinMode ), withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, String master, String foreign,
                                    JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接模式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, Property<E, ?> foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接模式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, R master, String foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接模式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String master, String foreign, JoinMode joinMode,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, master, foreign, joinMode, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, Property<E, ?> foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, String master, String foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, master, foreign, JoinMode.INNER, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, Property<E, ?> foreign,
                                    JoinMode joinMode, Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ), methodToProperty( foreign ),
                joinMode ), function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, R master, String foreign,
                                    JoinMode joinMode, Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, new Foreign( lambdaToProperty( master ), foreign,
                joinMode ), function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, String master, String foreign,
                                    JoinMode joinMode, Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, reference, new Foreign( master, foreign, joinMode ), function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param foreign     联表方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, Foreign foreign,
                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( subCriteria, null, foreign, function );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, Foreign foreign, Criterion<?>... withClauses ) {
        return addForeign( subCriteria, null, foreign, withClauses );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    default <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                    Criterion<?>... withClauses ) {
        return addForeign( subCriteria, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                            Collection<Criterion<?>> withClauses );

    /**
     * 添加子查询联表条件对象
     * @param subCriteria 子查询条件对象
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param function    lambda function对象
     * @param <E>         泛型类型
     * @return 联表条件对象
     */
    <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                            Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function );
}
