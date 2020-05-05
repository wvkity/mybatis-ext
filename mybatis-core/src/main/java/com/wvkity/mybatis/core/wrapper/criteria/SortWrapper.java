package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.List;

/**
 * 排序接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface SortWrapper<T, Chain extends SortWrapper<T, Chain>> extends PropertyConverter<T> {


    // region ASC

    /**
     * ASC排序
     * @param properties 属性数组
     * @param <V>        属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain asc(Property<T, V>... properties) {
        return asc(convert(ArrayUtil.toList(properties)));
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
    Chain asc(List<String> properties);

    /**
     * ASC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain ascWith(String... columns) {
        return ascWith(ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain ascWith(List<String> columns) {
        return ascWithAlias(null, columns);
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain ascWithAlias(String alias, String... columns) {
        return ascWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain ascWithAlias(String alias, List<String> columns);

    /**
     * ASC排序
     * @param functions 聚合函数数组
     * @return 当前对象
     */
    Chain asc(Function... functions);

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
    Chain aggregateAsc(List<String> aliases);

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    <V> Chain foreignAsc(String foreignAlias, Property<T, V>... properties);

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
    Chain foreignAsc(String foreignAlias, List<String> properties);
    // endregion

    // region DESC

    /**
     * DESC排序
     * @param properties 属性数组
     * @param <V>        属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain desc(Property<T, V>... properties) {
        return desc(convert(ArrayUtil.toList(properties)));
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
    Chain desc(List<String> properties);

    /**
     * DESC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain descWith(String... columns) {
        return descWith(ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain descWith(List<String> columns) {
        return descWithAlias(null, columns);
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain descWithAlias(String alias, String... columns) {
        return descWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain descWithAlias(String alias, List<String> columns);

    /**
     * DESC排序
     * @param functions 聚合函数数组
     * @return 当前对象
     */
    Chain desc(Function... functions);

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
    Chain aggregateDesc(List<String> aliases);

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    <V> Chain foreignDesc(String foreignAlias, Property<T, V>... properties);

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
    Chain foreignDesc(String foreignAlias, List<String> properties);
    // endregion

    /**
     * 添加排序
     * @param order 排序对象
     * @return 当前对象
     */
    Chain order(AbstractOrderWrapper<?> order);

    /**
     * 添加排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    default Chain orders(AbstractOrderWrapper<?>... orders) {
        return orders(ArrayUtil.toList(orders));
    }

    /**
     * 添加排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Chain orders(List<AbstractOrderWrapper<?>> orders);
}
