package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Join;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 连表条件包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface ForeignCriteriaWrapper<T, Chain extends ForeignCriteriaWrapper<T, Chain>> {

    /**
     * 创建INNER JOIN连表条件包装对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> innerJoin(Class<E> entity) {
        return join(entity, Join.INNER);
    }

    /**
     * 创建INNER JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link Consumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> innerJoin(Class<E> entity, Consumer<ForeignCriteria<E>> consumer) {
        return join(entity, Join.INNER, consumer);
    }

    /**
     * 创建INNER JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link BiConsumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> innerJoin(Class<E> entity, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignCriteria<E>> consumer) {
        return join(entity, Join.INNER, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> leftJoin(Class<E> entity) {
        return join(entity, Join.LEFT);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link Consumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> leftJoin(Class<E> entity, Consumer<ForeignCriteria<E>> consumer) {
        return join(entity, Join.LEFT, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link BiConsumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> leftJoin(Class<E> entity, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignCriteria<E>> consumer) {
        return join(entity, Join.LEFT, consumer);
    }

    /**
     * 创建RIGHT JOIN连表条件包装对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> rightJoin(Class<E> entity) {
        return join(entity, Join.RIGHT);
    }

    /**
     * 创建RIGHT JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link Consumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> rightJoin(Class<E> entity, Consumer<ForeignCriteria<E>> consumer) {
        return join(entity, Join.RIGHT, consumer);
    }

    /**
     * 创建RIGHT JOIN连表条件包装对象
     * @param entity   实体类
     * @param consumer {@link BiConsumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    default <E> ForeignCriteria<E> rightJoin(Class<E> entity, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignCriteria<E>> consumer) {
        return join(entity, Join.RIGHT, consumer);
    }

    /**
     * 创建连表条件包装对象
     * @param entity 实体类
     * @param join   连表模式
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}
     */
    <E> ForeignCriteria<E> join(final Class<E> entity, Join join);

    /**
     * 创建连表条件包装对象
     * @param entity   实体类
     * @param join     连表模式
     * @param consumer {@link Consumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    <E> ForeignCriteria<E> join(final Class<E> entity, Join join, Consumer<ForeignCriteria<E>> consumer);

    /**
     * 创建连表条件包装对象
     * @param entity   实体类
     * @param join     连表模式
     * @param consumer {@link BiConsumer}
     * @param <E>      泛型类型
     * @return {@link ForeignCriteria}
     */
    <E> ForeignCriteria<E> join(Class<E> entity, Join join, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignCriteria<E>> consumer);

    /**
     * 添加连表条件包装对象
     * @param array 连表条件包装对象数组
     * @return {@code this}
     */
    default Chain add(ForeignCriteria<?>... array) {
        return add(ArrayUtil.toList(array));
    }

    /**
     * 添加连表条件包装对象
     * @param list 连表条件包装对象集合
     * @return {@code this}
     */
    Chain add(List<ForeignCriteria<?>> list);

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @param <E>   泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(String alias);

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(Class<E> entity);

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign(String alias, Class<E> entity);

}
