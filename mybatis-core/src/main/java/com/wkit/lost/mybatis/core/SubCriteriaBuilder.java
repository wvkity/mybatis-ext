package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.function.Function;

public interface SubCriteriaBuilder<T> {

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param subTempTabAlias 子查询别名
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String subTempTabAlias ) {
        return createSub( entity, null, subTempTabAlias );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias ) {
        return createSub( entity, alias, subTempTabAlias, ( Collection<Criterion<?>> ) null );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String subTempTabAlias, Criterion<?>... withClauses ) {
        return createSub( entity, null, subTempTabAlias, withClauses );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias, Criterion<?>... withClauses ) {
        return createSub( entity, alias, subTempTabAlias, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String subTempTabAlias,
                                          Collection<Criterion<?>> withClauses ) {
        return createSub( entity, null, subTempTabAlias, withClauses );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias,
                                  Collection<Criterion<?>> withClauses );

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param subTempTabAlias 子查询别名
     * @param function        lambda function对象
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entity, String subTempTabAlias, Function<SubCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return createSub( entity, null, subTempTabAlias, function );
    }

    /**
     * 创建子查询条件对象
     * @param entity          实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param function        lambda function对象
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias, Function<SubCriteria<E>,
            AbstractQueryCriteria<E>> function );

}
