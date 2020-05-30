package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.List;

/**
 * 分组
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface GroupWrapper<T, Chain extends GroupWrapper<T, Chain>> extends PropertyConverter<T> {

    /**
     * 分组
     * @param property 属性
     * @param <V>      属性值类型
     * @return 当前对象
     */
    default <V> Chain group(Property<T, V> property) {
        return group(convert(property));
    }

    /**
     * 分组
     * @param properties 属性数组
     * @param <V>        属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain group(Property<T, V>... properties) {
        return group(convert(ArrayUtil.toList(properties)));
    }

    /**
     * 分组
     * @param property 属性
     * @return 当前对象
     */
    Chain group(String property);

    /**
     * 分组
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain group(String... properties) {
        return group(ArrayUtil.toList(properties));
    }

    /**
     * 分组
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain group(Collection<String> properties);

    /**
     * 分组
     * @param column 字段
     * @return 当前对象
     */
    Chain groupWith(String column);

    /**
     * 分组
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain groupWith(String... columns) {
        return groupWith(ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain groupWith(List<String> columns);

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain groupWithAlias(String alias, String... columns) {
        return groupWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain groupWithAlias(String alias, List<String> columns);

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          属性值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    <V> Chain foreignGroup(String foreignAlias, Property<T, V>... properties);

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignGroup(String foreignAlias, String... properties) {
        return foreignGroup(foreignAlias, ArrayUtil.toList(properties));
    }

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignGroup(String foreignAlias, List<String> properties);

    /**
     * 添加分组
     * @param group 分组对象集合
     * @return 当前对象
     */
    Chain group(AbstractGroupWrapper<?> group);

    /**
     * 添加分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    default Chain group(AbstractGroupWrapper<?>... groups) {
        return group(ArrayUtil.toList(groups));
    }

    /**
     * 添加分组
     * @param groups 分组对象集合
     * @return 当前对象
     */
    Chain group(List<AbstractGroupWrapper<?>> groups);
}
