package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.basic.AbstractSortWrapper;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.List;

/**
 * 排序接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface OrderWrapper<T, Chain extends OrderWrapper<T, Chain>> extends PropertyConverter<T> {


    // region ASC

    /**
     * ASC排序
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain asc(Property<T, V> property) {
        return asc(convert(property));
    }

    /**
     * ASC排序
     * @param properties 属性数组
     * @param <V>        属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain asc(Property<T, V>... properties) {
        return asc(convert(ArrayUtil.toList(properties)));
    }

    /**
     * ASC排序
     * @param property 属性
     * @return {@code this}
     */
    Chain asc(String property);

    /**
     * ASC排序
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain asc(String... properties) {
        return asc(ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain asc(List<String> properties);

    /**
     * ASC排序
     * @param column 字段
     * @return {@code this}
     */
    Chain ascWith(String column);

    /**
     * ASC排序
     * @param columns 字段数组
     * @return {@code this}
     */
    default Chain ascWith(String... columns) {
        return ascWith(ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param columns 字段集合
     * @return {@code this}
     */
    default Chain ascWith(List<String> columns) {
        return ascWithAlias(null, columns);
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return {@code this}
     */
    default Chain ascWithAlias(String alias, String... columns) {
        return ascWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return {@code this}
     */
    Chain ascWithAlias(String alias, List<String> columns);

    /**
     * ASC排序
     * @param function 聚合函数
     * @return {@code this}
     */
    Chain asc(Function function);

    /**
     * ASC排序
     * @param functions 聚合函数数组
     * @return {@code this}
     */
    default Chain asc(Function... functions) {
        return asc(ArrayUtil.toList(functions));
    }

    /**
     * ASC排序
     * @param functions 聚合函数集合
     * @return {@code this}
     */
    Chain asc(Collection<Function> functions);

    /**
     * ASC排序
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    Chain funcAsc(String alias);

    /**
     * ASC排序
     * @param aliases 聚合函数别名数组
     * @return {@code this}
     */
    default Chain funcAsc(String... aliases) {
        return funcAsc(ArrayUtil.toList(aliases));
    }

    /**
     * ASC排序
     * @param aliases 聚合函数别名集合
     * @return {@code this}
     */
    Chain funcAsc(List<String> aliases);

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    <V> Chain foreignAsc(String foreignAlias, Property<T, V>... properties);

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return {@code this}
     */
    default Chain foreignAsc(String foreignAlias, String... properties) {
        return foreignAsc(foreignAlias, ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return {@code this}
     */
    Chain foreignAsc(String foreignAlias, List<String> properties);
    // endregion

    // region DESC

    /**
     * DESC排序
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain desc(Property<T, V> property) {
        return desc(convert(property));
    }

    /**
     * DESC排序
     * @param properties 属性数组
     * @param <V>        属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain desc(Property<T, V>... properties) {
        return desc(convert(ArrayUtil.toList(properties)));
    }

    /**
     * DESC排序
     * @param property 属性
     * @return {@code this}
     */
    Chain desc(String property);

    /**
     * DESC排序
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain desc(String... properties) {
        return desc(ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain desc(List<String> properties);

    /**
     * DESC排序
     * @param column 字段
     * @return {@code this}
     */
    Chain descWith(String column);

    /**
     * DESC排序
     * @param columns 字段数组
     * @return {@code this}
     */
    default Chain descWith(String... columns) {
        return descWith(ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param columns 字段集合
     * @return {@code this}
     */
    default Chain descWith(List<String> columns) {
        return descWithAlias(null, columns);
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return {@code this}
     */
    default Chain descWithAlias(String alias, String... columns) {
        return descWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return {@code this}
     */
    Chain descWithAlias(String alias, List<String> columns);

    /**
     * DESC排序
     * @param function 聚合函数
     * @return {@code this}
     */
    Chain desc(Function function);

    /**
     * DESC排序
     * @param functions 聚合函数数组
     * @return {@code this}
     */
    default Chain desc(Function... functions) {
        return desc(ArrayUtil.toList(functions));
    }

    /**
     * DESC排序
     * @param functions 聚合函数数组
     * @return {@code this}
     */
    Chain desc(Collection<Function> functions);

    /**
     * DESC排序
     * @param alias 聚合函数别名
     * @return {@code this}
     */
    Chain funcDesc(String alias);

    /**
     * DESC排序
     * @param aliases 聚合函数别名数组
     * @return {@code this}
     */
    default Chain funcDesc(String... aliases) {
        return funcDesc(ArrayUtil.toList(aliases));
    }

    /**
     * DESC排序
     * @param aliases 聚合函数别名集合
     * @return {@code this}
     */
    Chain funcDesc(List<String> aliases);

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    <V> Chain foreignDesc(String foreignAlias, Property<T, V>... properties);

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return {@code this}
     */
    default Chain foreignDesc(String foreignAlias, String... properties) {
        return foreignDesc(foreignAlias, ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return {@code this}
     */
    Chain foreignDesc(String foreignAlias, List<String> properties);
    // endregion

    /**
     * 添加排序
     * @param sort 排序对象
     * @return {@code this}
     */
    Chain sort(AbstractSortWrapper<?> sort);

    /**
     * 添加排序
     * @param sorts 排序对象数组
     * @return {@code this}
     */
    default Chain sort(AbstractSortWrapper<?>... sorts) {
        return sort(ArrayUtil.toList(sorts));
    }

    /**
     * 添加排序
     * @param sorts 排序对象集合
     * @return {@code this}
     */
    Chain sort(List<AbstractSortWrapper<?>> sorts);
}
