package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.Constants;

import java.util.Collection;

/**
 * 聚合函数接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface FunctionWrapper<T, Chain extends FunctionWrapper<T, Chain>> extends PropertyConverter<T> {

    // region count

    /**
     * COUNT聚合函数
     * @return {@code this}
     */
    Chain count();

    /**
     * COUNT聚合函数
     * @param _case {@link Case}
     * @return {@code this}
     */
    Chain count(Case _case);

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property) {
        return count(property, false);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, String alias) {
        return count(property, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct) {
        return count(property, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct, String alias) {
        return count(convert(property), distinct, alias);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, Comparator comparator, Object value) {
        return count(property, false, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, String alias, Comparator comparator, Object value) {
        return count(property, false, alias, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct, Comparator comparator, Object value) {
        return count(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct, String alias,
                            Comparator comparator, Object value) {
        return count(convert(property), distinct, alias, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, Comparator comparator, Object min, Object max) {
        return count(property, false, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct, Comparator comparator,
                            Object min, Object max) {
        return count(property, distinct, null, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, String alias, Comparator comparator, Object min, Object max) {
        return count(property, false, alias, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain count(Property<T, V> property, boolean distinct, String alias,
                            Comparator comparator, Object min, Object max) {
        return count(convert(property), distinct, alias, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @return {@code this}
     */
    default Chain count(String property) {
        return count(property, false);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param alias    别名
     * @return {@code this}
     */
    default Chain count(String property, String alias) {
        return count(property, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain count(String property, boolean distinct) {
        return count(property, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain count(String property, boolean distinct, String alias);

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain count(String property, Comparator comparator, Object value) {
        return count(property, false, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain count(String property, String alias, Comparator comparator, Object value) {
        return count(property, false, alias, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain count(String property, boolean distinct, Comparator comparator, Object value) {
        return count(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain count(String property, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain count(String property, Comparator comparator, Object min, Object max) {
        return count(property, false, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain count(String property, boolean distinct, Comparator comparator, Object min, Object max) {
        return count(property, distinct, null, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain count(String property, String alias, Comparator comparator, Object min, Object max) {
        return count(property, false, alias, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain count(String property, boolean distinct, String alias, Comparator comparator, Object min, Object max);

    /**
     * COUNT聚合函数
     * @return {@code this}
     */
    default Chain countWith() {
        return countWith("*");
    }

    /**
     * COUNT聚合函数
     * @param alias 别名
     * @return {@code this}
     */
    default Chain countWithAlias(String alias) {
        return countWith("*", alias);
    }

    /**
     * COUNT聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain countWith(String column) {
        return countWith(column, false);
    }

    /**
     * COUNT聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain countWith(String column, boolean distinct) {
        return countWith(column, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    default Chain countWith(String column, String alias) {
        return countWith(column, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain countWith(String column, boolean distinct, String alias);

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain countWithAlias(String tableAlias, String column) {
        return countWith(tableAlias, column, false);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@code this}
     */
    default Chain countWith(String tableAlias, String column, boolean distinct) {
        return countWith(tableAlias, column, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    default Chain countWith(String tableAlias, String column, String alias) {
        return countWith(tableAlias, column, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@code this}
     */
    Chain countWith(String tableAlias, String column, boolean distinct, String alias);

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain countWith(String column, Comparator comparator, Object value) {
        return countWith(column, false, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain countWith(String column, boolean distinct, Comparator comparator, Object value) {
        return countWith(column, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain countWith(String column, String alias, Comparator comparator, Object value) {
        return countWith(column, false, alias, comparator, value);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain countWith(String column, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain countWith(String column, Comparator comparator, Object min, Object max) {
        return countWith(column, false, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain countWith(String column, boolean distinct, Comparator comparator, Object min, Object max) {
        return countWith(column, distinct, null, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain countWith(String column, String alias, Comparator comparator, Object min, Object max) {
        return countWith(column, false, alias, comparator, min, max);
    }

    /**
     * COUNT聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain countWith(String column, boolean distinct, String alias, Comparator comparator, Object min, Object max);

    // endregion

    // region sum

    /**
     * SUM聚合函数
     * @param _case {@link Case}
     * @return {@code this}
     */
    Chain sum(Case _case);

    /**
     * SUM聚合函数
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property) {
        return sum(property, false);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias) {
        return sum(property, false, alias);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct) {
        return sum(property, distinct, Constants.EMPTY);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias) {
        return sum(convert(property), distinct, alias);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, Comparator comparator, Object value) {
        return sum(property, false, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias, Comparator comparator, Object value) {
        return sum(property, false, alias, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, Comparator comparator, Object value) {
        return sum(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias,
                          Comparator comparator, Object value) {
        return sum(convert(property), distinct, alias, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, Comparator comparator, Object min, Object max) {
        return sum(property, false, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, Comparator comparator,
                          Object min, Object max) {
        return sum(property, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias, Comparator comparator, Object min, Object max) {
        return sum(property, false, alias, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias,
                          Comparator comparator, Object min, Object max) {
        return sum(convert(property), distinct, alias, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @return {@code this}
     */
    default Chain sum(String property) {
        return sum(property, false);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param alias    别名
     * @return {@code this}
     */
    default Chain sum(String property, String alias) {
        return sum(property, false, alias);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct) {
        return sum(property, distinct, Constants.EMPTY);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias);

    /**
     * SUM聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, Comparator comparator, Object value) {
        return sum(property, false, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, String alias, Comparator comparator, Object value) {
        return sum(property, false, alias, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct, Comparator comparator, Object value) {
        return sum(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * SUM聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, Comparator comparator, Object min, Object max) {
        return sum(property, false, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct, Comparator comparator, Object min, Object max) {
        return sum(property, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, String alias, Comparator comparator, Object min, Object max) {
        return sum(property, false, alias, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias, Comparator comparator, Object min, Object max);


    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, Integer scale) {
        return sum(property, false, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias, Integer scale) {
        return sum(property, false, alias, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, Integer scale) {
        return sum(property, distinct, Constants.EMPTY, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias, Integer scale) {
        return sum(convert(property), distinct, alias, scale);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, Integer scale, Comparator comparator, Object value) {
        return sum(property, false, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias,
                          Integer scale, Comparator comparator, Object value) {
        return sum(property, false, alias, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct,
                          Integer scale, Comparator comparator, Object value) {
        return sum(property, distinct, null, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias,
                          Integer scale, Comparator comparator, Object value) {
        return sum(convert(property), distinct, alias, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, Integer scale, Comparator comparator, Object min, Object max) {
        return sum(convert(property), false, null, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return sum(property, distinct, null, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return sum(property, false, alias, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain sum(Property<T, V> property, boolean distinct, String alias,
                          Integer scale, Comparator comparator, Object min, Object max) {
        return sum(convert(property), distinct, alias, scale, comparator, min, max);
    }


    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain sum(String property, Integer scale) {
        return sum(property, false, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain sum(String property, String alias, Integer scale) {
        return sum(property, false, alias, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct, Integer scale) {
        return sum(property, distinct, Constants.EMPTY, scale);
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias, Integer scale);

    /**
     * SUM聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, Integer scale, Comparator comparator, Object value) {
        return sum(property, false, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, String alias, Integer scale, Comparator comparator, Object value) {
        return sum(property, false, alias, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct, Integer scale, Comparator comparator, Object value) {
        return sum(property, distinct, null, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * SUM聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, Integer scale, Comparator comparator, Object min, Object max) {
        return sum(property, false, null, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, boolean distinct, Integer scale,
                      Comparator comparator, Object min, Object max) {
        return sum(property, distinct, null, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sum(String property, String alias, Integer scale,
                      Comparator comparator, Object min, Object max) {
        return sum(property, false, alias, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain sum(String property, boolean distinct, String alias, Integer scale,
              Comparator comparator, Object min, Object max);

    /**
     * SUM聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain sumWith(String column) {
        return sumWith(column, false);
    }

    /**
     * SUM聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct) {
        return sumWith(column, distinct, Constants.EMPTY);
    }

    /**
     * SUM聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias) {
        return sumWith(column, false, alias);
    }

    /**
     * SUM聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias);

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain sumWithAlias(String tableAlias, String column) {
        return sumWith(tableAlias, column, false);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@code this}
     */
    default Chain sumWith(String tableAlias, String column, boolean distinct) {
        return sumWith(tableAlias, column, distinct, Constants.EMPTY);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    default Chain sumWith(String tableAlias, String column, String alias) {
        return sumWith(tableAlias, column, false, alias);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@code this}
     */
    Chain sumWith(String tableAlias, String column, boolean distinct, String alias);

    /**
     * SUM聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, Comparator comparator, Object value) {
        return sumWith(column, false, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct, Comparator comparator, Object value) {
        return sumWith(column, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias, Comparator comparator, Object value) {
        return sumWith(column, false, alias, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * SUM聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, Comparator comparator, Object min, Object max) {
        return sumWith(column, false, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct, Comparator comparator, Object min, Object max) {
        return sumWith(column, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias, Comparator comparator, Object min, Object max) {
        return sumWith(column, false, alias, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias, Comparator comparator, Object min, Object max);


    /**
     * SUM聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain sumWith(String column, Integer scale) {
        return sumWith(column, false, scale);
    }

    /**
     * SUM聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct, Integer scale) {
        return sumWith(column, distinct, Constants.EMPTY, scale);
    }

    /**
     * SUM聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias, Integer scale) {
        return sumWith(column, false, alias, scale);
    }

    /**
     * SUM聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias, Integer scale);

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain sumWithAlias(String tableAlias, String column, Integer scale) {
        return sumWith(tableAlias, column, false, scale);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@code this}
     */
    default Chain sumWith(String tableAlias, String column, boolean distinct, Integer scale) {
        return sumWith(tableAlias, column, distinct, null, scale);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    default Chain sumWith(String tableAlias, String column, String alias, Integer scale) {
        return sumWith(tableAlias, column, false, alias, scale);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@code this}
     */
    Chain sumWith(String tableAlias, String column, boolean distinct, String alias, Integer scale);

    /**
     * SUM聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, Integer scale, Comparator comparator, Object value) {
        return sumWith(column, false, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct, Integer scale, Comparator comparator, Object value) {
        return sumWith(column, distinct, null, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias, Integer scale, Comparator comparator, Object value) {
        return sumWith(column, false, alias, scale, comparator, value);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * SUM聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, Integer scale, Comparator comparator, Object min, Object max) {
        return sumWith(column, false, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, boolean distinct, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return sumWith(column, distinct, null, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain sumWith(String column, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return sumWith(column, false, alias, scale, comparator, min, max);
    }

    /**
     * SUM聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain sumWith(String column, boolean distinct, String alias, Integer scale,
                  Comparator comparator, Object min, Object max);

    // endregion

    // region avg

    /**
     * AVG聚合函数
     * @param _case {@link Case}
     * @return {@code this}
     */
    Chain avg(Case _case);

    /**
     * AVG聚合函数
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property) {
        return avg(property, false);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias) {
        return avg(property, false, alias);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct) {
        return avg(property, distinct, Constants.EMPTY);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias) {
        return avg(convert(property), distinct, alias);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, Comparator comparator, Object value) {
        return avg(property, false, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias, Comparator comparator, Object value) {
        return avg(property, false, alias, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, Comparator comparator, Object value) {
        return avg(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias,
                          Comparator comparator, Object value) {
        return avg(convert(property), distinct, alias, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, Comparator comparator, Object min, Object max) {
        return avg(property, false, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, Comparator comparator,
                          Object min, Object max) {
        return avg(property, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias, Comparator comparator, Object min, Object max) {
        return avg(property, false, alias, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias,
                          Comparator comparator, Object min, Object max) {
        return avg(convert(property), distinct, alias, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @return {@code this}
     */
    default Chain avg(String property) {
        return avg(property, false);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param alias    别名
     * @return {@code this}
     */
    default Chain avg(String property, String alias) {
        return avg(property, false, alias);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct) {
        return avg(property, distinct, Constants.EMPTY);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias);

    /**
     * AVG聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, Comparator comparator, Object value) {
        return avg(property, false, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, String alias, Comparator comparator, Object value) {
        return avg(property, false, alias, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct, Comparator comparator, Object value) {
        return avg(property, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * AVG聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, Comparator comparator, Object min, Object max) {
        return avg(property, false, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct, Comparator comparator, Object min, Object max) {
        return avg(property, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, String alias, Comparator comparator, Object min, Object max) {
        return avg(property, false, alias, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias, Comparator comparator, Object min, Object max);


    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, Integer scale) {
        return avg(property, false, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias, Integer scale) {
        return avg(property, false, alias, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, Integer scale) {
        return avg(property, distinct, Constants.EMPTY, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias, Integer scale) {
        return avg(convert(property), distinct, alias, scale);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, Integer scale, Comparator comparator, Object value) {
        return avg(property, false, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias,
                          Integer scale, Comparator comparator, Object value) {
        return avg(property, false, alias, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct,
                          Integer scale, Comparator comparator, Object value) {
        return avg(property, distinct, null, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias,
                          Integer scale, Comparator comparator, Object value) {
        return avg(convert(property), distinct, alias, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, Integer scale, Comparator comparator, Object min, Object max) {
        return avg(convert(property), false, null, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return avg(property, distinct, null, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return avg(property, false, alias, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain avg(Property<T, V> property, boolean distinct, String alias,
                          Integer scale, Comparator comparator, Object min, Object max) {
        return avg(convert(property), distinct, alias, scale, comparator, min, max);
    }


    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain avg(String property, Integer scale) {
        return avg(property, false, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain avg(String property, String alias, Integer scale) {
        return avg(property, false, alias, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct, Integer scale) {
        return avg(property, distinct, Constants.EMPTY, scale);
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias, Integer scale);

    /**
     * AVG聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, Integer scale, Comparator comparator, Object value) {
        return avg(property, false, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, String alias, Integer scale, Comparator comparator, Object value) {
        return avg(property, false, alias, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct, Integer scale, Comparator comparator, Object value) {
        return avg(property, distinct, null, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * AVG聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, Integer scale, Comparator comparator, Object min, Object max) {
        return avg(property, false, null, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, boolean distinct, Integer scale,
                      Comparator comparator, Object min, Object max) {
        return avg(property, distinct, null, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avg(String property, String alias, Integer scale,
                      Comparator comparator, Object min, Object max) {
        return avg(property, false, alias, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain avg(String property, boolean distinct, String alias, Integer scale,
              Comparator comparator, Object min, Object max);

    /**
     * AVG聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain avgWith(String column) {
        return avgWith(column, false);
    }

    /**
     * AVG聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct) {
        return avgWith(column, distinct, Constants.EMPTY);
    }

    /**
     * AVG聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias) {
        return avgWith(column, false, alias);
    }

    /**
     * AVG聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias);

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain avgWithAlias(String tableAlias, String column) {
        return avgWith(tableAlias, column, false);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@code this}
     */
    default Chain avgWith(String tableAlias, String column, boolean distinct) {
        return avgWith(tableAlias, column, distinct, Constants.EMPTY);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    default Chain avgWith(String tableAlias, String column, String alias) {
        return avgWith(tableAlias, column, false, alias);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@code this}
     */
    Chain avgWith(String tableAlias, String column, boolean distinct, String alias);

    /**
     * AVG聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, Comparator comparator, Object value) {
        return avgWith(column, false, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct, Comparator comparator, Object value) {
        return avgWith(column, distinct, Constants.EMPTY, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias, Comparator comparator, Object value) {
        return avgWith(column, false, alias, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias, Comparator comparator, Object value);

    /**
     * AVG聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, Comparator comparator, Object min, Object max) {
        return avgWith(column, false, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct, Comparator comparator, Object min, Object max) {
        return avgWith(column, distinct, Constants.EMPTY, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias, Comparator comparator, Object min, Object max) {
        return avgWith(column, false, alias, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias, Comparator comparator, Object min, Object max);


    /**
     * AVG聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain avgWith(String column, Integer scale) {
        return avgWith(column, false, scale);
    }

    /**
     * AVG聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct, Integer scale) {
        return avgWith(column, distinct, Constants.EMPTY, scale);
    }

    /**
     * AVG聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias, Integer scale) {
        return avgWith(column, false, alias, scale);
    }

    /**
     * AVG聚合函数
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias, Integer scale);

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain avgWithAlias(String tableAlias, String column, Integer scale) {
        return avgWith(tableAlias, column, false, scale);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@code this}
     */
    default Chain avgWith(String tableAlias, String column, boolean distinct, Integer scale) {
        return avgWith(tableAlias, column, distinct, null, scale);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    default Chain avgWith(String tableAlias, String column, String alias, Integer scale) {
        return avgWith(tableAlias, column, false, alias, scale);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@code this}
     */
    Chain avgWith(String tableAlias, String column, boolean distinct, String alias, Integer scale);

    /**
     * AVG聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, Integer scale, Comparator comparator, Object value) {
        return avgWith(column, false, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct, Integer scale, Comparator comparator, Object value) {
        return avgWith(column, distinct, null, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias, Integer scale, Comparator comparator, Object value) {
        return avgWith(column, false, alias, scale, comparator, value);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * AVG聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, Integer scale, Comparator comparator, Object min, Object max) {
        return avgWith(column, false, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, boolean distinct, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return avgWith(column, distinct, null, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain avgWith(String column, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return avgWith(column, false, alias, scale, comparator, min, max);
    }

    /**
     * AVG聚合函数
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain avgWith(String column, boolean distinct, String alias, Integer scale,
                  Comparator comparator, Object min, Object max);

    // endregion

    // region min

    /**
     * MIN聚合函数
     * @param _case {@link Case}
     * @return {@code this}
     */
    Chain min(Case _case);

    /**
     * MIN聚合函数
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property) {
        return min(convert(property));
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, String alias) {
        return min(convert(property), alias);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, Comparator comparator, Object value) {
        return min(convert(property), comparator, value);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, Comparator comparator, Object min, Object max) {
        return min(convert(property), comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, String alias, Comparator comparator, Object value) {
        return min(convert(property), alias, comparator, value);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, String alias, Comparator comparator, Object min, Object max) {
        return min(convert(property), alias, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @return {@code this}
     */
    default Chain min(String property) {
        return min(property, Constants.EMPTY);
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @param alias    别名
     * @return {@code this}
     */
    Chain min(String property, String alias);

    /**
     * MIN聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain min(String property, Comparator comparator, Object value) {
        return min(property, Constants.EMPTY, comparator, value);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain min(String property, String alias, Comparator comparator, Object value);

    /**
     * MIN聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain min(String property, Comparator comparator, Object min, Object max) {
        return min(property, Constants.EMPTY, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain min(String property, String alias, Comparator comparator, Object min, Object max);

    /**
     * MIN聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, Integer scale) {
        return min(convert(property), scale);
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, String alias, Integer scale) {
        return min(convert(property), alias, scale);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, Integer scale, Comparator comparator, Object min, Object max) {
        return min(property, null, scale, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain min(Property<T, V> property, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return min(convert(property), alias, scale, comparator, min, max);
    }


    /**
     * MIN聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain min(String property, Integer scale) {
        return min(property, Constants.EMPTY, scale);
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    Chain min(String property, String alias, Integer scale);

    /**
     * MIN聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain min(String property, Integer scale, Comparator comparator, Object min, Object max) {
        return min(property, null, scale, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain min(String property, String alias, Integer scale,
              Comparator comparator, Object min, Object max);

    /**
     * MIN聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain minWith(String column) {
        return minWith(column, Constants.EMPTY);
    }

    /**
     * MIN聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    Chain minWith(String column, String alias);

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain minWithAlias(String tableAlias, String column) {
        return minWith(tableAlias, column, Constants.EMPTY);
    }

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    Chain minWith(String tableAlias, String column, String alias);

    /**
     * MIN聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain minWith(String column, Comparator comparator, Object value) {
        return minWith(column, Constants.EMPTY, comparator, value);
    }

    /**
     * MIN聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain minWith(String column, String alias, Comparator comparator, Object value);

    /**
     * MIN聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain minWith(String column, Comparator comparator, Object min, Object max) {
        return minWith(column, Constants.EMPTY, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain minWith(String column, String alias, Comparator comparator, Object min, Object max);


    /**
     * MIN聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain minWith(String column, Integer scale) {
        return minWith(column, Constants.EMPTY, scale);
    }

    /**
     * MIN聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    Chain minWith(String column, String alias, Integer scale);

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain minWithAlias(String tableAlias, String column, Integer scale) {
        return minWith(tableAlias, column, Constants.EMPTY, scale);
    }

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    Chain minWith(String tableAlias, String column, String alias, Integer scale);

    /**
     * MIN聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain minWith(String column, Integer scale, Comparator comparator, Object value) {
        return minWith(column, Constants.EMPTY, scale, comparator, value);
    }

    /**
     * MIN聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain minWith(String column, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * MIN聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain minWith(String column, Integer scale, Comparator comparator, Object min, Object max) {
        return minWith(column, Constants.EMPTY, scale, comparator, min, max);
    }

    /**
     * MIN聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain minWith(String column, String alias, Integer scale, Comparator comparator, Object min, Object max);

    // endregion

    // region max

    /**
     * MAX聚合函数
     * @param _case {@link Case}
     * @return {@code this}
     */
    Chain max(Case _case);

    /**
     * MAX聚合函数
     * @param property 属性
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property) {
        return max(convert(property));
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @param alias    别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, String alias) {
        return max(convert(property), alias);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, Comparator comparator, Object value) {
        return max(convert(property), comparator, value);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, Comparator comparator, Object min, Object max) {
        return max(convert(property), comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, String alias, Comparator comparator, Object value) {
        return max(convert(property), alias, comparator, value);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, String alias, Comparator comparator, Object min, Object max) {
        return max(convert(property), alias, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @return {@code this}
     */
    default Chain max(String property) {
        return max(property, Constants.EMPTY);
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @param alias    别名
     * @return {@code this}
     */
    Chain max(String property, String alias);

    /**
     * MAX聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain max(String property, Comparator comparator, Object value) {
        return max(property, Constants.EMPTY, comparator, value);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain max(String property, String alias, Comparator comparator, Object value);

    /**
     * MAX聚合函数
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain max(String property, Comparator comparator, Object min, Object max) {
        return max(property, Constants.EMPTY, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain max(String property, String alias, Comparator comparator, Object min, Object max);

    /**
     * MAX聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, Integer scale) {
        return max(convert(property), scale);
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, String alias, Integer scale) {
        return max(convert(property), alias, scale);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, Integer scale, Comparator comparator, Object min, Object max) {
        return max(property, null, scale, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param <V>        属性值类型
     * @return {@code this}
     */
    default <V> Chain max(Property<T, V> property, String alias, Integer scale,
                          Comparator comparator, Object min, Object max) {
        return max(convert(property), alias, scale, comparator, min, max);
    }


    /**
     * MAX聚合函数
     * @param property 属性
     * @param scale    小数位数
     * @return {@code this}
     */
    default Chain max(String property, Integer scale) {
        return max(property, Constants.EMPTY, scale);
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @param alias    别名
     * @param scale    小数位数
     * @return {@code this}
     */
    Chain max(String property, String alias, Integer scale);

    /**
     * MAX聚合函数
     * @param property   属性
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain max(String property, Integer scale, Comparator comparator, Object min, Object max) {
        return max(property, null, scale, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param property   属性
     * @param alias      别名
     * @param scale      小数位数
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain max(String property, String alias, Integer scale,
              Comparator comparator, Object min, Object max);

    /**
     * MAX聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain maxWith(String column) {
        return maxWith(column, Constants.EMPTY);
    }

    /**
     * MAX聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    Chain maxWith(String column, String alias);

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain maxWithAlias(String tableAlias, String column) {
        return maxWith(tableAlias, column, Constants.EMPTY);
    }

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    Chain maxWith(String tableAlias, String column, String alias);

    /**
     * MAX聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain maxWith(String column, Comparator comparator, Object value) {
        return maxWith(column, Constants.EMPTY, comparator, value);
    }

    /**
     * MAX聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain maxWith(String column, String alias, Comparator comparator, Object value);

    /**
     * MAX聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain maxWith(String column, Comparator comparator, Object min, Object max) {
        return maxWith(column, Constants.EMPTY, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain maxWith(String column, String alias, Comparator comparator, Object min, Object max);


    /**
     * MAX聚合函数
     * @param column 字段
     * @return {@code this}
     */
    default Chain maxWith(String column, Integer scale) {
        return maxWith(column, Constants.EMPTY, scale);
    }

    /**
     * MAX聚合函数
     * @param column 字段
     * @param alias  别名
     * @return {@code this}
     */
    Chain maxWith(String column, String alias, Integer scale);

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    default Chain maxWithAlias(String tableAlias, String column, Integer scale) {
        return maxWith(tableAlias, column, Constants.EMPTY, scale);
    }

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@code this}
     */
    Chain maxWith(String tableAlias, String column, String alias, Integer scale);

    /**
     * MAX聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    default Chain maxWith(String column, Integer scale, Comparator comparator, Object value) {
        return maxWith(column, Constants.EMPTY, scale, comparator, value);
    }

    /**
     * MAX聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @return {@code this}
     */
    Chain maxWith(String column, String alias, Integer scale, Comparator comparator, Object value);

    /**
     * MAX聚合函数
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    default Chain maxWith(String column, Integer scale, Comparator comparator, Object min, Object max) {
        return maxWith(column, Constants.EMPTY, scale, comparator, min, max);
    }

    /**
     * MAX聚合函数
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @return {@code this}
     */
    Chain maxWith(String column, String alias, Integer scale, Comparator comparator, Object min, Object max);

    // endregion

    /**
     * 添加聚合函数
     * @param function 聚合函数
     * @return {@code this}
     */
    Chain function(Function function);

    /**
     * 添加聚合函数
     * @param functions 聚合函数数组
     * @return {@code this}
     */
    default Chain functions(Function... functions) {
        return functions(ArrayUtil.toList(functions));
    }

    /**
     * 添加聚合函数
     * @param functions 聚合函数集合
     * @return {@code this}
     */
    Chain functions(Collection<Function> functions);
}
