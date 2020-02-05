package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.function.Function;

public interface SubCriteriaBuilder<T> {

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass ) {
        return createSub( entityClass, null );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param subTempTabAlias 子查询别名
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String subTempTabAlias ) {
        return createSub( entityClass, null, subTempTabAlias );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String alias, String subTempTabAlias ) {
        return createSub( entityClass, alias, subTempTabAlias, ( Collection<Criterion<?>> ) null );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String subTempTabAlias, Criterion<?>... withClauses ) {
        return createSub( entityClass, null, subTempTabAlias, withClauses );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String alias, String subTempTabAlias, Criterion<?>... withClauses ) {
        return createSub( entityClass, alias, subTempTabAlias, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String subTempTabAlias,
                                          Collection<Criterion<?>> withClauses ) {
        return createSub( entityClass, null, subTempTabAlias, withClauses );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    <E> SubCriteria<E> createSub( Class<E> entityClass, String alias, String subTempTabAlias,
                                  Collection<Criterion<?>> withClauses );

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param subTempTabAlias 子查询别名
     * @param function        lambda function对象
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    default <E> SubCriteria<E> createSub( Class<E> entityClass, String subTempTabAlias, Function<SubCriteria<E>,
            AbstractQueryCriteria<E>> function ) {
        return createSub( entityClass, null, subTempTabAlias, function );
    }

    /**
     * 创建子查询条件对象
     * @param entityClass     实体类
     * @param alias           表别名
     * @param subTempTabAlias 子查询别名
     * @param function        lambda function对象
     * @param <E>             泛型类型
     * @return 子查询条件对象
     */
    <E> SubCriteria<E> createSub( Class<E> entityClass, String alias, String subTempTabAlias, Function<SubCriteria<E>,
            AbstractQueryCriteria<E>> function );

}
