package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.converter.PropertyConverter;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 分组
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface GroupWrapper<T, Chain extends GroupWrapper<T, Chain>> extends PropertyConverter<T> {


    /**
     * 分组
     * @param properties 属性数组
     * @param <V>        返回值类型
     * @return 当前对象
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain group(Property<T, V>... properties) {
        return group(convert(ArrayUtil.toList(properties)));
    }

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
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directGroup(String... columns) {
        return directGroup(ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain directGroup(Collection<String> columns) {
        return directGroupWithAlias(null, columns);
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain directGroupWithAlias(String alias, String... columns) {
        return directGroupWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain directGroupWithAlias(String alias, Collection<String> columns);

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @param <V>          返回值类型
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
    Chain foreignGroup(String foreignAlias, Collection<String> properties);

    /**
     * 添加分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    default Chain add(AbstractGroupWrapper<?, ?>... groups) {
        return addGroup(ArrayUtil.toList(groups));
    }

    /**
     * 添加分组
     * @param groups 分组对象集合
     * @return 当前对象
     */
    Chain addGroup(Collection<AbstractGroupWrapper<?, ?>> groups);
}
