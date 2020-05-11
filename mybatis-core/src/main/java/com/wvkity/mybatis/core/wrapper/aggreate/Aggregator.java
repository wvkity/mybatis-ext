package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.Constants;

/**
 * 聚合函数工具类
 * @author wvkity
 */
public final class Aggregator {

    private Aggregator() {
    }

    // region count

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param _case    {@link Case}
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, Case _case) {
        return FunctionBuilder.create()._case(_case).criteria(criteria).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property) {
        return count(criteria, property, false);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria,
                                                      Property<T, V> property, boolean distinct) {
        return count(criteria, property, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria,
                                                      Property<T, V> property, String alias) {
        return count(criteria, property, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property) {
        return count(criteria, property, false);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, boolean distinct) {
        return count(criteria, property, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, String alias) {
        return count(criteria, property, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property,
                                               boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      Comparator comparator, Object value, Logic logic) {
        return count(criteria, property, false, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      boolean distinct, Comparator comparator,
                                                      Object value, Logic logic) {
        return count(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      String alias, Comparator comparator,
                                                      Object value, Logic logic) {
        return count(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      boolean distinct, String alias, Comparator comparator,
                                                      Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, Comparator comparator,
                                               Object value, Logic logic) {
        return count(criteria, property, false, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, boolean distinct,
                                               Comparator comparator, Object value, Logic logic) {
        return count(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, String alias,
                                               Comparator comparator, Object value, Logic logic) {
        return count(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, boolean distinct,
                                               String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      Comparator comparator, Object min, Object max, Logic logic) {
        return count(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property, boolean distinct,
                                                      Comparator comparator, Object min, Object max, Logic logic) {
        return count(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property, String alias,
                                                      Comparator comparator, Object min, Object max, Logic logic) {
        return count(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction count(Criteria<T> criteria, Property<T, V> property,
                                                      boolean distinct, String alias, Comparator comparator,
                                                      Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, Comparator comparator,
                                               Object min, Object max, Logic logic) {
        return count(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, boolean distinct,
                                               Comparator comparator, Object min, Object max, Logic logic) {
        return count(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, String alias,
                                               Comparator comparator, Object min, Object max, Logic logic) {
        return count(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction count(Criteria<?> criteria, String property, boolean distinct, String alias,
                                               Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).count();
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(String tableAlias, String column) {
        return countWith(tableAlias, column, false);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(String tableAlias, String column, boolean distinct) {
        return countWith(tableAlias, column, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(String tableAlias, String column, String alias) {
        return countWith(tableAlias, column, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(String tableAlias, String column,
                                                   boolean distinct, String alias) {
        return DirectFunctionBuilder.create().tableAlias(tableAlias).column(column).distinct(distinct)
                .alias(alias).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column) {
        return countWith(criteria, column, false);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, boolean distinct) {
        return countWith(criteria, column, distinct, null);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, String alias) {
        return countWith(criteria, column, false, alias);
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column,
                                                   boolean distinct, String alias) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column,
                                                   Comparator comparator, Object value, Logic logic) {
        return countWith(criteria, column, false, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, boolean distinct,
                                                   Comparator comparator, Object value, Logic logic) {
        return countWith(criteria, column, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, String alias,
                                                   Comparator comparator, Object value, Logic logic) {
        return countWith(criteria, column, false, alias, comparator, value, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, boolean distinct,
                                                   String alias, Comparator comparator, Object value, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).count();
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, Comparator comparator,
                                                   Object min, Object max, Logic logic) {
        return countWith(criteria, column, false, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, boolean distinct,
                                                   Comparator comparator, Object min, Object max, Logic logic) {
        return countWith(criteria, column, distinct, null, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, String alias,
                                                   Comparator comparator, Object min, Object max, Logic logic) {
        return countWith(criteria, column, false, alias, comparator, min, max, logic);
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction countWith(Criteria<?> criteria, String column, boolean distinct,
                                                   String alias, Comparator comparator, Object min,
                                                   Object max, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).count();
    }

    // endregion

    // region sum

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param _case    {@link Case}
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, Case _case) {
        return FunctionBuilder.create()._case(_case).criteria(criteria).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property) {
        return sum(criteria, property, false);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct) {
        return sum(criteria, property, distinct, null);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction
    sum(Criteria<T> criteria, Property<T, V> property, String alias) {
        return sum(criteria, property, false, alias);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property) {
        return sum(criteria, property, false);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct) {
        return sum(criteria, property, distinct, null);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, String alias) {
        return sum(criteria, property, false, alias);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct, String
            alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object value, Logic logic) {
        return sum(criteria, property, false, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object value, Logic logic) {
        return sum(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return sum(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, Comparator comparator,
                                             Object value, Logic logic) {
        return sum(criteria, property, false, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object value, Logic logic) {
        return sum(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object value, Logic logic) {
        return sum(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct,
                                             String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return sum(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, Comparator comparator, Object min,
                                                    Object max, Logic logic) {
        return sum(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property, String alias,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return sum(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction sum(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias, Comparator comparator,
                                                    Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, Comparator comparator,
                                             Object min, Object max, Logic logic) {
        return sum(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return sum(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return sum(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction sum(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).sum();
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(String tableAlias, String column) {
        return sumWith(tableAlias, column, false);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(String tableAlias, String column, boolean distinct) {
        return sumWith(tableAlias, column, distinct, null);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(String tableAlias, String column, String alias) {
        return sumWith(tableAlias, column, false, alias);
    }

    /**
     * SUM聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(String tableAlias, String column,
                                                 boolean distinct, String alias) {
        return DirectFunctionBuilder.create().tableAlias(tableAlias).column(column).distinct(distinct)
                .alias(alias).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column) {
        return sumWith(criteria, column, false);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, boolean distinct) {
        return sumWith(criteria, column, distinct, null);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, String alias) {
        return sumWith(criteria, column, false, alias);
    }

    /**
     * SUM聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column,
                                                 boolean distinct, String alias) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column,
                                                 Comparator comparator, Object value, Logic logic) {
        return sumWith(criteria, column, false, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, boolean distinct,
                                                 Comparator comparator, Object value, Logic logic) {
        return sumWith(criteria, column, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object value, Logic logic) {
        return sumWith(criteria, column, false, alias, comparator, value, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, boolean distinct,
                                                 String alias, Comparator comparator, Object value, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).sum();
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, Comparator comparator,
                                                 Object min, Object max, Logic logic) {
        return sumWith(criteria, column, false, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, boolean distinct,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return sumWith(criteria, column, distinct, null, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return sumWith(criteria, column, false, alias, comparator, min, max, logic);
    }

    /**
     * SUM聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction sumWith(Criteria<?> criteria, String column, boolean distinct,
                                                 String alias, Comparator comparator, Object min,
                                                 Object max, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).sum();
    }

    // endregion

    // region avg

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param _case    {@link Case}
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, Case _case) {
        return FunctionBuilder.create()._case(_case).criteria(criteria).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property) {
        return avg(criteria, property, false);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct) {
        return avg(criteria, property, distinct, null);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction
    avg(Criteria<T> criteria, Property<T, V> property, String alias) {
        return avg(criteria, property, false, alias);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property) {
        return avg(criteria, property, false);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct) {
        return avg(criteria, property, distinct, null);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, String alias) {
        return avg(criteria, property, false, alias);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct, String
            alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object value, Logic logic) {
        return avg(criteria, property, false, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object value, Logic logic) {
        return avg(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return avg(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, Comparator comparator,
                                             Object value, Logic logic) {
        return avg(criteria, property, false, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object value, Logic logic) {
        return avg(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object value, Logic logic) {
        return avg(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return avg(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return avg(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object min, Object max, Logic logic) {
        return avg(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction avg(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, Comparator comparator,
                                             Object min, Object max, Logic logic) {
        return avg(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return avg(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return avg(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction avg(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).avg();
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(String tableAlias, String column) {
        return avgWith(tableAlias, column, false);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(String tableAlias, String column, boolean distinct) {
        return avgWith(tableAlias, column, distinct, null);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(String tableAlias, String column, String alias) {
        return avgWith(tableAlias, column, false, alias);
    }

    /**
     * AVG聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(String tableAlias, String column,
                                                 boolean distinct, String alias) {
        return DirectFunctionBuilder.create().tableAlias(tableAlias).column(column).distinct(distinct)
                .alias(alias).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column) {
        return avgWith(criteria, column, false);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, boolean distinct) {
        return avgWith(criteria, column, distinct, null);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, String alias) {
        return avgWith(criteria, column, false, alias);
    }

    /**
     * AVG聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column,
                                                 boolean distinct, String alias) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column,
                                                 Comparator comparator, Object value, Logic logic) {
        return avgWith(criteria, column, false, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, boolean distinct,
                                                 Comparator comparator, Object value, Logic logic) {
        return avgWith(criteria, column, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object value, Logic logic) {
        return avgWith(criteria, column, false, alias, comparator, value, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, boolean distinct,
                                                 String alias, Comparator comparator, Object value, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).avg();
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, Comparator comparator,
                                                 Object min, Object max, Logic logic) {
        return avgWith(criteria, column, false, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, boolean distinct,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return avgWith(criteria, column, distinct, null, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return avgWith(criteria, column, false, alias, comparator, min, max, logic);
    }

    /**
     * AVG聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction avgWith(Criteria<?> criteria, String column, boolean distinct,
                                                 String alias, Comparator comparator, Object min,
                                                 Object max, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).avg();
    }

    // endregion

    // region min

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param _case    {@link Case}
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, Case _case) {
        return FunctionBuilder.create()._case(_case).criteria(criteria).min();
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property) {
        return min(criteria, property, false);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct) {
        return min(criteria, property, distinct, null);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction
    min(Criteria<T> criteria, Property<T, V> property, String alias) {
        return min(criteria, property, false, alias);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).min();
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property) {
        return min(criteria, property, false);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct) {
        return min(criteria, property, distinct, null);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, String alias) {
        return min(criteria, property, false, alias);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct, String
            alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object value, Logic logic) {
        return min(criteria, property, false, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object value, Logic logic) {
        return min(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return min(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, Comparator comparator,
                                             Object value, Logic logic) {
        return min(criteria, property, false, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object value, Logic logic) {
        return min(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object value, Logic logic) {
        return min(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return min(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return min(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object min, Object max, Logic logic) {
        return min(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction min(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias, Comparator comparator,
                                                    Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, Comparator comparator,
                                             Object min, Object max, Logic logic) {
        return min(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return min(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return min(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction min(Criteria<?> criteria, String property, boolean distinct, String alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).min();
    }

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(String tableAlias, String column) {
        return minWith(tableAlias, column, null);
    }

    /**
     * MIN聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(String tableAlias, String column, String alias) {
        return DirectFunctionBuilder.create().tableAlias(tableAlias).column(column).alias(alias).min();
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column) {
        return minWith(criteria, column, null);
    }

    /**
     * MIN聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column, String alias) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column,
                                                 Comparator comparator, Object value, Logic logic) {
        return minWith(criteria, column, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object value, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias)
                .comparator(comparator).value(value).logic(logic).min();
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column, Comparator comparator,
                                                 Object min, Object max, Logic logic) {
        return minWith(criteria, column, null, comparator, min, max, logic);
    }

    /**
     * MIN聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction minWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias)
                .comparator(comparator).minValue(min).maxValue(max).logic(logic).min();
    }

    // endregion

    // region max

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param _case    {@link Case}
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, Case _case) {
        return FunctionBuilder.create()._case(_case).criteria(criteria).max();
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property) {
        return max(criteria, property, false);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct) {
        return max(criteria, property, distinct, null);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction
    max(Criteria<T> criteria, Property<T, V> property, String alias) {
        return max(criteria, property, false, alias);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct, String alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).max();
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property) {
        return max(criteria, property, false);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct) {
        return max(criteria, property, distinct, null);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, String alias) {
        return max(criteria, property, false, alias);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param property 属性
     * @param distinct 是否去重
     * @param alias    别名
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct, String
            alias) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct).alias(alias).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object value, Logic logic) {
        return max(criteria, property, false, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object value, Logic logic) {
        return max(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return max(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, Comparator comparator,
                                             Object value, Logic logic) {
        return max(criteria, property, false, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object value, Logic logic) {
        return max(criteria, property, distinct, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object value, Logic logic) {
        return max(criteria, property, false, alias, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object value, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).value(value).logic(logic).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return max(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    Comparator comparator, Object min, Object max, Logic logic) {
        return max(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    String alias, Comparator comparator, Object min, Object max, Logic logic) {
        return max(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @param <V>        属性值类型
     * @return {@link AbstractColumnFunction}
     */
    public static <T, V> AbstractColumnFunction max(Criteria<T> criteria, Property<T, V> property,
                                                    boolean distinct,
                                                    String alias, Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, Comparator comparator,
                                             Object min, Object max, Logic logic) {
        return max(criteria, property, false, null, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return max(criteria, property, distinct, null, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, String alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return max(criteria, property, false, alias, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param property   属性
     * @param distinct   是否去重
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractColumnFunction}
     */
    public static AbstractColumnFunction max(Criteria<?> criteria, String property, boolean distinct, String
            alias,
                                             Comparator comparator, Object min, Object max, Logic logic) {
        return FunctionBuilder.create().criteria(criteria).property(property).distinct(distinct)
                .alias(alias).comparator(comparator).minValue(min).maxValue(max).logic(logic).max();
    }

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(String tableAlias, String column) {
        return maxWith(tableAlias, column, null);
    }

    /**
     * MAX聚合函数
     * @param tableAlias 表别名
     * @param column     字段
     * @param alias      别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(String tableAlias, String column, String alias) {
        return DirectFunctionBuilder.create().tableAlias(tableAlias).column(column).alias(alias).max();
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column) {
        return maxWith(criteria, column, null);
    }

    /**
     * MAX聚合函数
     * @param criteria 条件包装对象
     * @param column   字段
     * @param alias    别名
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column, String alias) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column,
                                                 Comparator comparator, Object value, Logic logic) {
        return maxWith(criteria, column, Constants.EMPTY, comparator, value, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param value      值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object value, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias)
                .comparator(comparator).value(value).logic(logic).max();
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column, Comparator comparator,
                                                 Object min, Object max, Logic logic) {
        return maxWith(criteria, column, null, comparator, min, max, logic);
    }

    /**
     * MAX聚合函数
     * @param criteria   条件包装对象
     * @param column     字段
     * @param alias      别名
     * @param comparator 比较运算符
     * @param min        最小值
     * @param max        最大值
     * @param logic      逻辑符号
     * @return {@link AbstractDirectFunction}
     */
    public static AbstractDirectFunction maxWith(Criteria<?> criteria, String column, String alias,
                                                 Comparator comparator, Object min, Object max, Logic logic) {
        return DirectFunctionBuilder.create().criteria(criteria).column(column).alias(alias)
                .comparator(comparator).minValue(min).maxValue(max).logic(logic).max();
    }
    // endregion

}
