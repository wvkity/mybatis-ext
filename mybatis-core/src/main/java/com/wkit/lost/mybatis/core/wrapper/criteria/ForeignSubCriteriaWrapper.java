package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.constant.Join;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 子查询连表条件包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface ForeignSubCriteriaWrapper<T, Chain extends ForeignCriteriaWrapper<T, Chain>> {

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc 子查询对象
     * @return {@code this}
     */
    default ForeignSubCriteria<?> innerJoin(SubCriteria<?> sc) {
        return join(sc, Join.INNER);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default ForeignSubCriteria<?> innerJoin(SubCriteria<?> sc, Consumer<ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.INNER, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default <E> ForeignSubCriteria<?> innerJoin(SubCriteria<?> sc, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.INNER, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc 子查询对象
     * @return {@code this}
     */
    default <E> ForeignSubCriteria<?> leftJoin(SubCriteria<?> sc) {
        return join(sc, Join.LEFT);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default <E> ForeignSubCriteria<?> leftJoin(SubCriteria<?> sc, Consumer<ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.LEFT, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default ForeignSubCriteria<?> leftJoin(SubCriteria<?> sc, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.LEFT, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc 子查询对象
     * @return {@code this}
     */
    default ForeignSubCriteria<?> rightJoin(SubCriteria<?> sc) {
        return join(sc, Join.RIGHT);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default ForeignSubCriteria<?> rightJoin(SubCriteria<?> sc, Consumer<ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.RIGHT, consumer);
    }

    /**
     * 创建LEFT JOIN连表条件包装对象
     * @param sc       子查询对象
     * @param consumer {@link}
     * @return {@code this}
     */
    default ForeignSubCriteria<?> rightJoin(SubCriteria<?> sc, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignSubCriteria<?>> consumer) {
        return join(sc, Join.RIGHT, consumer);
    }

    /**
     * 创建连表条件包装对象
     * @param sc   子查询对象
     * @param join 连接模式
     * @return {@code this}
     */
    ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join);

    /**
     * 创建连表条件包装对象
     * @param sc       子查询对象
     * @param join     连接模式
     * @param consumer {@link}
     * @return {@code this}
     */
    ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join, Consumer<ForeignSubCriteria<?>> consumer);

    /**
     * 创建连表条件包装对象
     * @param sc       子查询对象
     * @param join     连接模式
     * @param consumer {@link}
     * @return {@code this}
     */
    ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignSubCriteria<?>> consumer);

}
