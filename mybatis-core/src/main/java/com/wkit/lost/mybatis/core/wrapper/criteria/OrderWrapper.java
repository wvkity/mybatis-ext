package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.LambdaConverter;
import com.wkit.lost.mybatis.core.wrapper.aggreate.Aggregation;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 排序
 * @param <Chain> 子类
 * @param <P>     Lambda类
 * @author wvkity
 */
public interface OrderWrapper<Chain extends OrderWrapper<Chain, P>, P> extends LambdaConverter<P> {


    // region ASC

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default Chain asc(P... properties) {
        return asc(lambdaToProperties(properties));
    }

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain asc(String... properties) {
        return asc(ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain asc(Collection<String> properties);

    /**
     * ASC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directAsc(String... columns) {
        return directAsc(ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain directAsc(Collection<String> columns) {
        return directAscWithAlias(null, columns);
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directAscWithAlias(String alias, String... columns) {
        return directAscWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain directAscWithAlias(String alias, Collection<String> columns);

    /**
     * ASC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Chain asc(Aggregation... aggregations);

    /**
     * ASC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Chain aggregateAsc(String... aliases) {
        return aggregateAsc(ArrayUtil.toList(aliases));
    }

    /**
     * ASC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Chain aggregateAsc(Collection<String> aliases);

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    Chain foreignAsc(String foreignAlias, P... properties);

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignAsc(String foreignAlias, String... properties) {
        return foreignAsc(foreignAlias, ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignAsc(String foreignAlias, Collection<String> properties);
    // endregion

    // region DESC

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default Chain desc(P... properties) {
        return desc(lambdaToProperties(properties));
    }

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain desc(String... properties) {
        return desc(ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain desc(Collection<String> properties);

    /**
     * DESC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directDesc(String... columns) {
        return directDesc(ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain directDesc(Collection<String> columns) {
        return directDescWithAlias(null, columns);
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directDescWithAlias(String alias, String... columns) {
        return directDescWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain directDescWithAlias(String alias, Collection<String> columns);

    /**
     * DESC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Chain desc(Aggregation... aggregations);

    /**
     * DESC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Chain aggregateDesc(String... aliases) {
        return aggregateDesc(ArrayUtil.toList(aliases));
    }

    /**
     * DESC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Chain aggregateDesc(Collection<String> aliases);

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    Chain foreignDesc(String foreignAlias, P... properties);

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignDesc(String foreignAlias, String... properties) {
        return foreignDesc(foreignAlias, ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignDesc(String foreignAlias, Collection<String> properties);
    // endregion

    /**
     * 添加排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    default Chain add(AbstractOrderWrapper<?, ?>... orders) {
        return addOrder(ArrayUtil.toList(orders));
    }

    /**
     * 添加排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Chain addOrder(Collection<AbstractOrderWrapper<?, ?>> orders);
}
