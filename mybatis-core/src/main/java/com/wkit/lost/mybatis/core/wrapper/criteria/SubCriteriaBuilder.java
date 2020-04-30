package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 构建子查询条件包装器接口
 * @param <T> 实体类型
 * @author wvkity
 */
public interface SubCriteriaBuilder<T> {

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    default <E> SubCriteria<E> sub(Class<E> entityClass) {
        return sub(entityClass, null, null);
    }

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param alias       别名
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    default <E> SubCriteria<E> sub(Class<E> entityClass, String alias) {
        return sub(entityClass, alias, null);
    }

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param clauses     条件
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    default <E> SubCriteria<E> sub(Class<E> entityClass, Criterion... clauses) {
        return sub(entityClass, null, ArrayUtil.toList(clauses));
    }

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param clauses     条件
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    default <E> SubCriteria<E> sub(Class<E> entityClass, Collection<Criterion> clauses) {
        return sub(entityClass, null, clauses);
    }

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param alias       别名
     * @param clauses     条件
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    <E> SubCriteria<E> sub(Class<E> entityClass, String alias, Collection<Criterion> clauses);

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param consumer    {@link Consumer}
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    <E> SubCriteria<E> sub(Class<E> entityClass, Consumer<SubCriteria<E>> consumer);

    /**
     * 创建子查询条件包装对象
     * @param entityClass 实体类
     * @param consumer    {@link BiConsumer}
     * @param <E>         实体类型
     * @return 子查询条件包装对象
     */
    <E> SubCriteria<E> sub(Class<E> entityClass, BiConsumer<AbstractCriteriaWrapper<T>, SubCriteria<E>> consumer);
}
