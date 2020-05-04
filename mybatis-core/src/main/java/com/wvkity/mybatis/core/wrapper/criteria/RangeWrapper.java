package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 范围条件接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface RangeWrapper<T, Chain extends RangeWrapper<T, Chain>> extends PropertyConverter<T> {

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain in(Property<T, V> property, V... values) {
        return in(property, ArrayUtil.toList(values));
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain in(String property, Object... values) {
        return in(property, ArrayUtil.toList(values));
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <V> Chain in(Property<T, V> property, Collection<V> values) {
        return in(convert(property), (Collection<Object>) values);
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain in(String property, Collection<Object> values);

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain orIn(Property<T, V> property, V... values) {
        return orIn(property, ArrayUtil.toList(values));
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orIn(String property, Object... values) {
        return orIn(property, ArrayUtil.toList(values));
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orIn(Property<T, V> property, Collection<V> values) {
        return orIn(convert(property), values);
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain orIn(String property, Collection<Object> values);

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain inWith(String column, Object... values) {
        return inWith(column, ArrayUtil.toList(values));
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain inWith(String column, Collection<Object> values);


    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain inWith(String tableAlias, String column, Object... values) {
        return inWith(tableAlias, column, ArrayUtil.toList(values));
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain inWith(String tableAlias, String column, Collection<Object> values);

    /**
     * 或IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain orInWith(String column, Object... values) {
        return orInWith(column, ArrayUtil.toList(values));
    }

    /**
     * 或IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain orInWith(String column, Collection<Object> values);

    /**
     * 或IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain orInWith(String tableAlias, String column, Object... values) {
        return orInWith(tableAlias, column, ArrayUtil.toList(values));
    }

    /**
     * 或IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain orInWith(String tableAlias, String column, Collection<Object> values);

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain notIn(Property<T, V> property, V... values) {
        return notIn(property, ArrayUtil.toList(values));
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain notIn(String property, Object... values) {
        return notIn(property, ArrayUtil.toList(values));
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <V> Chain notIn(Property<T, V> property, Collection<V> values) {
        return notIn(convert(property), (Collection<Object>) values);
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain notIn(String property, Collection<Object> values);

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    @SuppressWarnings("unchecked")
    default <V> Chain orNotIn(Property<T, V> property, V... values) {
        return orNotIn(property, ArrayUtil.toList(values));
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orNotIn(String property, Object... values) {
        return orNotIn(property, ArrayUtil.toList(values));
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orNotIn(Property<T, V> property, Collection<V> values) {
        return orNotIn(convert(property), values);
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain orNotIn(String property, Collection<Object> values);

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain notInWith(String column, Object... values) {
        return notInWith(column, ArrayUtil.toList(values));
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain notInWith(String column, Collection<Object> values);


    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain notInWith(String tableAlias, String column, Object... values) {
        return notInWith(tableAlias, column, ArrayUtil.toList(values));
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain notInWith(String tableAlias, String column, Collection<Object> values);

    /**
     * 或NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain orNotInWith(String column, Object... values) {
        return orNotInWith(column, ArrayUtil.toList(values));
    }

    /**
     * 或NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain orNotInWith(String column, Collection<Object> values);

    /**
     * 或NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain orNotInWith(String tableAlias, String column, Object... values) {
        return orNotInWith(tableAlias, column, ArrayUtil.toList(values));
    }

    /**
     * 或NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain orNotInWith(String tableAlias, String column, Collection<Object> values);

    /**
     * BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain between(Property<T, V> property, V begin, V end) {
        return between(convert(property), begin, end);
    }

    /**
     * BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain between(String property, Object begin, Object end);

    /**
     * 或BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orBetween(Property<T, V> property, V begin, V end) {
        return orBetween(convert(property), begin, end);
    }

    /**
     * 或BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain orBetween(String property, Object begin, Object end);

    /**
     * BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain betweenWith(String column, Object begin, Object end);

    /**
     * BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain betweenWith(String tableAlias, String column, Object begin, Object end);

    /**
     * BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain orBetweenWith(String column, Object begin, Object end);

    /**
     * BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain orBetweenWith(String tableAlias, String column, Object begin, Object end);

    /**
     * NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain notBetween(Property<T, V> property, V begin, V end) {
        return notBetween(convert(property), begin, end);
    }

    /**
     * NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain notBetween(String property, Object begin, Object end);

    /**
     * 或NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orNotBetween(Property<T, V> property, V begin, V end) {
        return orNotBetween(convert(property), begin, end);
    }

    /**
     * 或NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain orNotBetween(String property, Object begin, Object end);

    /**
     * NOT BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain notBetweenWith(String column, Object begin, Object end);

    /**
     * NOT BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain notBetweenWith(String tableAlias, String column, Object begin, Object end);

    /**
     * NOT BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain orNotBetweenWith(String column, Object begin, Object end);

    /**
     * NOT BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain orNotBetweenWith(String tableAlias, String column, Object begin, Object end);

}
