package com.wkit.lost.mybatis.core.conditional;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.conditional.expression.Between;
import com.wkit.lost.mybatis.core.conditional.expression.DirectBetween;
import com.wkit.lost.mybatis.core.conditional.expression.DirectEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectGreaterThan;
import com.wkit.lost.mybatis.core.conditional.expression.DirectGreaterThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectIn;
import com.wkit.lost.mybatis.core.conditional.expression.DirectLessThan;
import com.wkit.lost.mybatis.core.conditional.expression.DirectLessThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectLike;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNormalEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotIn;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotLike;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotNull;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNull;
import com.wkit.lost.mybatis.core.conditional.expression.DirectSubQuery;
import com.wkit.lost.mybatis.core.conditional.expression.DirectTemplate;
import com.wkit.lost.mybatis.core.conditional.expression.Equal;
import com.wkit.lost.mybatis.core.conditional.expression.GreaterThan;
import com.wkit.lost.mybatis.core.conditional.expression.GreaterThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.IdEqual;
import com.wkit.lost.mybatis.core.conditional.expression.In;
import com.wkit.lost.mybatis.core.conditional.expression.LessThan;
import com.wkit.lost.mybatis.core.conditional.expression.LessThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.Like;
import com.wkit.lost.mybatis.core.conditional.expression.Nested;
import com.wkit.lost.mybatis.core.conditional.expression.NormalEqual;
import com.wkit.lost.mybatis.core.conditional.expression.NotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.NotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.NotIn;
import com.wkit.lost.mybatis.core.conditional.expression.NotLike;
import com.wkit.lost.mybatis.core.conditional.expression.NotNull;
import com.wkit.lost.mybatis.core.conditional.expression.Null;
import com.wkit.lost.mybatis.core.conditional.expression.SubQuery;
import com.wkit.lost.mybatis.core.conditional.expression.Template;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.core.wrapper.criteria.SubCriteria;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 条件工具类
 * @author wvkity
 */
public final class Restrictions {

    private Restrictions() {
    }

    // region simple expression

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @return 条件对象
     */
    public static IdEqual idEq(Criteria<?> criteria, Object value) {
        return idEq(criteria, value, Logic.AND);
    }

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static IdEqual idEq(Criteria<?> criteria, Object value, Logic logic) {
        return IdEqual.create().criteria(criteria).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Equal eq(Criteria<T> criteria, Property<T, V> property, V value) {
        return eq(criteria, property, value, Logic.AND);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static Equal eq(Criteria<?> criteria, String property, Object value) {
        return eq(criteria, property, value, Logic.AND);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Equal eq(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return Equal.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Equal eq(Criteria<?> criteria, String property, Object value, Logic logic) {
        return Equal.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @return 条件对象
     */
    public static DirectEqual eqWith(String column, Object value) {
        return eqWith(column, value, Logic.AND);
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectEqual eqWith(String column, Object value, Logic logic) {
        return DirectEqual.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectEqual eqWith(String tableAlias, String column, Object value) {
        return eqWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectEqual eqWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectEqual.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectEqual eqWith(Criteria<?> criteria, String column, Object value) {
        return eqWith(criteria, column, value, Logic.AND);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectEqual eqWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectEqual.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotEqual ne(Criteria<T> criteria, Property<T, V> property, V value) {
        return ne(criteria, property, value, Logic.AND);
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotEqual ne(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return NotEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static NotEqual ne(Criteria<?> criteria, String property, Object value) {
        return ne(criteria, property, value, Logic.AND);
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotEqual ne(Criteria<?> criteria, String property, Object value, Logic logic) {
        return NotEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return 条件对象
     */
    public static DirectNotEqual neWith(String column, Object value) {
        return neWith(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectNotEqual neWith(String column, Object value, Logic logic) {
        return DirectNotEqual.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectNotEqual neWith(String tableAlias, String column, Object value) {
        return neWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotEqual neWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectNotEqual.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectNotEqual neWith(Criteria<?> criteria, String column, Object value) {
        return neWith(criteria, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotEqual neWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectNotEqual.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> LessThan lt(Criteria<T> criteria, Property<T, V> property, V value) {
        return lt(criteria, property, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> LessThan lt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return LessThan.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static LessThan lt(Criteria<?> criteria, String property, Object value) {
        return lt(criteria, property, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static LessThan lt(Criteria<?> criteria, String property, Object value, Logic logic) {
        return LessThan.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return 条件对象
     */
    public static DirectLessThan ltWith(String column, Object value) {
        return ltWith(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectLessThan ltWith(String column, Object value, Logic logic) {
        return DirectLessThan.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectLessThan ltWith(String tableAlias, String column, Object value) {
        return ltWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLessThan ltWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectLessThan.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectLessThan ltWith(Criteria<?> criteria, String column, Object value) {
        return ltWith(criteria, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectLessThan ltWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectLessThan.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> LessThanOrEqual le(Criteria<T> criteria, Property<T, V> property, V value) {
        return le(criteria, property, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> LessThanOrEqual le(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return LessThanOrEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static LessThanOrEqual le(Criteria<?> criteria, String property, Object value) {
        return le(criteria, property, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static LessThanOrEqual le(Criteria<?> criteria, String property, Object value, Logic logic) {
        return LessThanOrEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(String column, Object value) {
        return leWith(column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(String column, Object value, Logic logic) {
        return DirectLessThanOrEqual.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(String tableAlias, String column, Object value) {
        return leWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectLessThanOrEqual.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(Criteria<?> criteria, String column, Object value) {
        return leWith(criteria, column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static DirectLessThanOrEqual leWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectLessThanOrEqual.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThan gt(Criteria<T> criteria, Property<T, V> property, V value) {
        return gt(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThan gt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return GreaterThan.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static GreaterThan gt(Criteria<?> criteria, String property, Object value) {
        return gt(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static GreaterThan gt(Criteria<?> criteria, String property, Object value, Logic logic) {
        return GreaterThan.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(String column, Object value) {
        return gtWith(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(String column, Object value, Logic logic) {
        return DirectGreaterThan.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(String tableAlias, String column, Object value) {
        return gtWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectGreaterThan.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(Criteria<?> criteria, String column, Object value) {
        return gtWith(criteria, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThan gtWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectGreaterThan.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThanOrEqual ge(Criteria<T> criteria, Property<T, V> property, V value) {
        return ge(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThanOrEqual ge(Criteria<T> criteria, Property<T, V> property,
                                               V value, Logic logic) {
        return GreaterThanOrEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static GreaterThanOrEqual ge(Criteria<?> criteria, String property, Object value) {
        return ge(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static GreaterThanOrEqual ge(Criteria<?> criteria, String property, Object value, Logic logic) {
        return GreaterThanOrEqual.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(String column, Object value) {
        return geWith(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(String column, Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create().column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(String tableAlias, String column, Object value) {
        return geWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(String tableAlias, String column, Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(Criteria<?> criteria, String column, Object value) {
        return geWith(criteria, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectGreaterThanOrEqual geWith(Criteria<?> criteria, String column, Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    // endregion

    // region empty expression

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Null isNull(Criteria<T> criteria, Property<T, V> property) {
        return isNull(criteria, property, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Null isNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        return Null.create().criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @return 条件对象
     */
    public static Null isNull(Criteria<?> criteria, String property) {
        return isNull(criteria, property, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Null isNull(Criteria<?> criteria, String property, Logic logic) {
        return Null.create().criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return 条件对象
     */
    public static DirectNull isNullWith(String tableAlias, String column) {
        return isNullWith(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNull isNullWith(String tableAlias, String column, Logic logic) {
        return DirectNull.create().alias(tableAlias).column(column).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 条件对象
     */
    public static DirectNull isNullWith(Criteria<?> criteria, String column) {
        return isNullWith(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNull isNullWith(Criteria<?> criteria, String column, Logic logic) {
        return DirectNull.create().criteria(criteria).column(column).logic(logic).build();
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotNull notNull(Criteria<T> criteria, Property<T, V> property) {
        return notNull(criteria, property, Logic.AND);
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotNull notNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        return NotNull.create().criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @return 条件对象
     */
    public static NotNull notNull(Criteria<?> criteria, String property) {
        return notNull(criteria, property, Logic.AND);
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotNull notNull(Criteria<?> criteria, String property, Logic logic) {
        return NotNull.create().criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return 条件对象
     */
    public static DirectNotNull notNullWith(String tableAlias, String column) {
        return notNullWith(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotNull notNullWith(String tableAlias, String column, Logic logic) {
        return DirectNotNull.create().alias(tableAlias).column(column).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 条件对象
     */
    public static DirectNotNull notNullWith(Criteria<?> criteria, String column) {
        return notNullWith(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotNull notNullWith(Criteria<?> criteria, String column, Logic logic) {
        return DirectNotNull.create().criteria(criteria).column(column).logic(logic).build();
    }

    // endregion

    // region range expression

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> In in(Criteria<T> criteria, Property<T, V> property, Collection<V> values) {
        return in(criteria, property, values, Logic.AND);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> In in(Criteria<T> criteria, Property<T, V> property, Collection<V> values, Logic logic) {
        return In.create().criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    public static In in(Criteria<?> criteria, String property, Collection<Object> values) {
        return in(criteria, property, values, Logic.AND);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static In in(Criteria<?> criteria, String property, Collection<Object> values, Logic logic) {
        return In.create().criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @return 条件对象
     */
    public static DirectIn inWith(String column, Collection<Object> values) {
        return inWith(column, values, Logic.AND);
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectIn inWith(String column, Collection<Object> values, Logic logic) {
        return DirectIn.create().column(column).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return 条件对象
     */
    public static DirectIn inWith(String tableAlias, String column, Collection<Object> values) {
        return inWith(tableAlias, column, values, Logic.AND);
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectIn inWith(String tableAlias, String column, Collection<Object> values, Logic logic) {
        return DirectIn.create().alias(tableAlias).column(column).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @return 条件对象
     */
    public static DirectIn inWith(Criteria<?> criteria, String column, Collection<Object> values) {
        return inWith(criteria, column, values, Logic.AND);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectIn inWith(Criteria<?> criteria, String column, Collection<Object> values, Logic logic) {
        return DirectIn.create().criteria(criteria).column(column).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotIn notIn(Criteria<T> criteria, Property<T, V> property, Collection<V> values) {
        return notIn(criteria, property, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotIn notIn(Criteria<T> criteria, Property<T, V> property,
                                     Collection<V> values, Logic logic) {
        return NotIn.create().criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    public static NotIn notIn(Criteria<?> criteria, String property, Collection<Object> values) {
        return notIn(criteria, property, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotIn notIn(Criteria<?> criteria, String property, Collection<Object> values, Logic logic) {
        return NotIn.create().criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @return 条件对象
     */
    public static DirectNotIn notInWith(String column, Collection<Object> values) {
        return notInWith(column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectNotIn notInWith(String column, Collection<Object> values, Logic logic) {
        return DirectNotIn.create().column(column).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return 条件对象
     */
    public static DirectNotIn notInWith(String tableAlias, String column, Collection<Object> values) {
        return notInWith(tableAlias, column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotIn notInWith(String tableAlias, String column, Collection<Object> values, Logic logic) {
        return DirectNotIn.create().alias(tableAlias).column(column).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @return 条件对象
     */
    public static DirectNotIn notInWith(Criteria<?> criteria, String column, Collection<Object> values) {
        return notInWith(criteria, column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotIn notInWith(Criteria<?> criteria, String column, Collection<Object> values, Logic logic) {
        return DirectNotIn.create().criteria(criteria).column(column).values(values).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Between between(Criteria<T> criteria, Property<T, V> property, V begin, V end) {
        return between(criteria, property, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Between between(Criteria<T> criteria, Property<T, V> property, V begin, V end, Logic logic) {
        return Between.create().criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件对象
     */
    public static Between between(Criteria<?> criteria, String property, Object begin, Object end) {
        return between(criteria, property, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Between between(Criteria<?> criteria, String property, Object begin, Object end, Logic logic) {
        return Between.create().criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return 条件对象
     */
    public static DirectBetween betweenWith(String column, Object begin, Object end) {
        return betweenWith(column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectBetween betweenWith(String column, Object begin, Object end, Logic logic) {
        return DirectBetween.create().column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return 条件对象
     */
    public static DirectBetween betweenWith(String tableAlias, String column, Object begin, Object end) {
        return betweenWith(tableAlias, column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectBetween betweenWith(String tableAlias, String column, Object begin, Object end, Logic logic) {
        return DirectBetween.create().alias(tableAlias).column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @return 条件对象
     */
    public static DirectBetween betweenWith(Criteria<?> criteria, String column, Object begin, Object end) {
        return betweenWith(criteria, column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectBetween betweenWith(Criteria<?> criteria, String column,
                                            Object begin, Object end, Logic logic) {
        return DirectBetween.create().criteria(criteria).column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotBetween notBetween(Criteria<T> criteria, Property<T, V> property, V begin, V end) {
        return notBetween(criteria, property, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotBetween notBetween(Criteria<T> criteria, Property<T, V> property,
                                               V begin, V end, Logic logic) {
        return NotBetween.create().criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件对象
     */
    public static NotBetween notBetween(Criteria<?> criteria, String property, Object begin, Object end) {
        return notBetween(criteria, property, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotBetween notBetween(Criteria<?> criteria, String property, Object begin, Object end, Logic logic) {
        return NotBetween.create().criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(String column, Object begin, Object end) {
        return notBetweenWith(column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(String column, Object begin, Object end, Logic logic) {
        return DirectNotBetween.create().column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(String tableAlias, String column, Object begin, Object end) {
        return notBetweenWith(tableAlias, column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(String tableAlias, String column,
                                                  Object begin, Object end, Logic logic) {
        return DirectNotBetween.create().alias(tableAlias).column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(Criteria<?> criteria, String column, Object begin, Object end) {
        return notBetweenWith(criteria, column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotBetween notBetweenWith(Criteria<?> criteria, String column,
                                                  Object begin, Object end, Logic logic) {
        return DirectNotBetween.create().criteria(criteria).column(column).begin(begin).end(end).logic(logic).build();
    }

    // endregion

    // region fuzzy expression

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property, String value) {
        return like(criteria, property, value, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, String property, String value) {
        return like(criteria, property, value, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property, String value, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property,
                                String value, Character escape) {
        return like(criteria, property, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value, Character escape) {
        return like(criteria, property, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property, String value,
                                Character escape, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value,
                            Character escape, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property, String value, Match match) {
        return like(criteria, property, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value, Match match) {
        return like(criteria, property, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property,
                                String value, Match match, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value,
                            Match match, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property,
                                String value, Match match, Character escape) {
        return like(criteria, property, value, match, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value, Match match, Character escape) {
        return like(criteria, property, value, match, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Like like(Criteria<T> criteria, Property<T, String> property, String value,
                                Match match, Character escape, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Like like(Criteria<?> criteria, String property, String value,
                            Match match, Character escape, Logic logic) {
        return Like.create().criteria(criteria).property(property).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property, String value) {
        return notLike(criteria, property, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value) {
        return notLike(criteria, property, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property, String value, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property,
                                      String value, Character escape) {
        return notLike(criteria, property, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Character escape) {
        return notLike(criteria, property, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property, String value,
                                      Character escape, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Character escape, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property, String value, Match match) {
        return notLike(criteria, property, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Match match) {
        return notLike(criteria, property, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property,
                                      String value, Match match, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Match match, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property, String value,
                                      Match match, Character escape) {
        return notLike(criteria, property, value, match, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value, Match match, Character escape) {
        return notLike(criteria, property, value, match, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike notLike(Criteria<T> criteria, Property<T, String> property,
                                      String value, Match match, Character escape, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static NotLike notLike(Criteria<?> criteria, String property, String value,
                                  Match match, Character escape, Logic logic) {
        return NotLike.create().criteria(criteria).property(property).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value) {
        return likeWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Logic logic) {
        return DirectLike.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Match match) {
        return likeWith(tableAlias, column, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Match match, Logic logic) {
        return DirectLike.create().alias(tableAlias).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Character escape) {
        return likeWith(tableAlias, column, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Character escape, Logic logic) {
        return DirectLike.create().alias(tableAlias).column(column).value(value).escape(escape).logic(logic).build();
    }


    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value, Match match, Character escape) {
        return likeWith(tableAlias, column, value, match, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(String tableAlias, String column, String value,
                                      Match match, Character escape, Logic logic) {
        return DirectLike.create().alias(tableAlias).column(column).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value) {
        return likeWith(criteria, column, value, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value, Logic logic) {
        return DirectLike.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value, Match match) {
        return likeWith(criteria, column, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value, Match match, Logic logic) {
        return DirectLike.create().criteria(criteria).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value, Character escape) {
        return likeWith(criteria, column, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column,
                                      String value, Character escape, Logic logic) {
        return DirectLike.create().criteria(criteria).column(column).value(value).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column,
                                      String value, Match match, Character escape) {
        return likeWith(criteria, column, value, match, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectLike likeWith(Criteria<?> criteria, String column, String value,
                                      Match match, Character escape, Logic logic) {
        return DirectLike.create().criteria(criteria).column(column).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value) {
        return notLikeWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value, Logic logic) {
        return DirectNotLike.create().alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value, Match match) {
        return notLikeWith(tableAlias, column, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value,
                                            Match match, Logic logic) {
        return DirectNotLike.create().alias(tableAlias).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value, Character escape) {
        return notLikeWith(tableAlias, column, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column,
                                            String value, Character escape, Logic logic) {
        return DirectNotLike.create().alias(tableAlias).column(column).value(value)
                .escape(escape).logic(logic).build();
    }


    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value,
                                            Match match, Character escape) {
        return notLikeWith(tableAlias, column, value, match, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(String tableAlias, String column, String value,
                                            Match match, Character escape, Logic logic) {
        return DirectNotLike.create().alias(tableAlias).column(column).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value) {
        return notLikeWith(criteria, column, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value, Logic logic) {
        return DirectNotLike.create().criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value, Match match) {
        return notLikeWith(criteria, column, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column,
                                            String value, Match match, Logic logic) {
        return DirectNotLike.create().criteria(criteria).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value, Character escape) {
        return notLikeWith(criteria, column, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value,
                                            Character escape, Logic logic) {
        return DirectNotLike.create().criteria(criteria).column(column).value(value)
                .escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value,
                                            Match match, Character escape) {
        return notLikeWith(criteria, column, value, match, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectNotLike notLikeWith(Criteria<?> criteria, String column, String value,
                                            Match match, Character escape, Logic logic) {
        return DirectNotLike.create().criteria(criteria).column(column).value(value)
                .match(match).escape(escape).logic(logic).build();
    }

    // endregion

    // region template expression

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Object value, String template) {
        return template(criteria, property, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Object value, String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template)
                .value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property, Object value, String template) {
        return template(criteria, property, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property, Object value,
                                    String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template)
                .value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Collection<Object> values, String template) {
        return template(criteria, property, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Collection<Object> values, String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template)
                .values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property,
                                    Collection<Object> values, String template) {
        return template(criteria, property, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property, Collection<Object> values,
                                    String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template)
                .values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Map<String, Object> values, String template) {
        return template(criteria, property, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> Template template(Criteria<T> criteria, Property<T, V> property,
                                           Map<String, Object> values, String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template).map(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property,
                                    Map<String, Object> values, String template) {
        return template(criteria, property, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static Template template(Criteria<?> criteria, String property,
                                    Map<String, Object> values, String template, Logic logic) {
        return Template.create().criteria(criteria).property(property).template(template)
                .map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Object value) {
        return templateWith(template, value, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Object value, Logic logic) {
        return DirectTemplate.create().template(template).value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Collection<Object> values) {
        return templateWith(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Collection<Object> values, Logic logic) {
        return DirectTemplate.create().template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Map<String, Object> values) {
        return templateWith(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String template, Map<String, Object> values, Logic logic) {
        return DirectTemplate.create().template(template).map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param template   模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column, Object value, String template) {
        return templateWith(tableAlias, column, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param template   模板
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column,
                                              Object value, String template, Logic logic) {
        return DirectTemplate.create().alias(tableAlias).column(column).template(template)
                .value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column,
                                              Collection<Object> values, String template) {
        return templateWith(tableAlias, column, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column,
                                              Collection<Object> values, String template, Logic logic) {
        return DirectTemplate.create().alias(tableAlias).column(column).template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column,
                                              Map<String, Object> values, String template) {
        return templateWith(tableAlias, column, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(String tableAlias, String column,
                                              Map<String, Object> values, String template, Logic logic) {
        return DirectTemplate.create().alias(tableAlias).column(column).template(template)
                .map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param template 模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column, Object value, String template) {
        return templateWith(criteria, column, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column,
                                              Object value, String template, Logic logic) {
        return DirectTemplate.create().criteria(criteria).column(column).template(template)
                .value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column,
                                              Collection<Object> values, String template) {
        return templateWith(criteria, column, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column,
                                              Collection<Object> values, String template, Logic logic) {
        return DirectTemplate.create().criteria(criteria).column(column).template(template)
                .values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column,
                                              Map<String, Object> values, String template) {
        return templateWith(criteria, column, values, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectTemplate templateWith(Criteria<?> criteria, String column,
                                              Map<String, Object> values, String template, Logic logic) {
        return DirectTemplate.create().criteria(criteria).column(column).template(template)
                .map(values).logic(logic).build();
    }

    // endregion

    // region nested

    /**
     * NESTED
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     * @return 条件对象
     */
    public static Nested nested(Criteria<?> criteria, Logic logic, Criterion... conditions) {
        return nested(criteria, logic, ArrayUtil.toList(conditions));
    }

    /**
     * NESTED
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     * @return 条件对象
     */
    public static Nested nested(Criteria<?> criteria, Logic logic, Collection<Criterion> conditions) {
        return Nested.create().criteria(criteria).conditions(conditions).logic(logic).build();
    }
    // endregion

    // region normal equal

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <V1>          属性1值类型
     * @param <E>           实体类型
     * @param <V2>          属性2值类型
     * @return 条件对象
     */
    public static <T, V1, E, V2> NormalEqual nq(Criteria<T> criteria, Property<T, V1> property,
                                                Criteria<E> otherCriteria, Property<E, V2> otherProperty) {
        return nq(criteria, property, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <V1>          属性1值类型
     * @param <E>           实体类型
     * @param <V2>          属性2值类型
     * @return 条件对象
     */
    public static <T, V1, E, V2> NormalEqual nq(Criteria<T> criteria, Property<T, V1> property,
                                                Criteria<E> otherCriteria, Property<E, V2> otherProperty,
                                                Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @return 条件对象
     */
    public static NormalEqual nq(Criteria<?> criteria, String property,
                                 Criteria<?> otherCriteria, String otherProperty) {
        return nq(criteria, property, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nq(Criteria<?> criteria, String property,
                                 Criteria<?> otherCriteria, String otherProperty, Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @return 条件对象
     */
    public static NormalEqual nq(Criteria<?> criteria, ColumnWrapper column,
                                 Criteria<?> otherCriteria, ColumnWrapper otherColumn) {
        return nq(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nq(Criteria<?> criteria, ColumnWrapper column,
                                 Criteria<?> otherCriteria, ColumnWrapper otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();

    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <T>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, V> NormalEqual nqWith(Criteria<T> criteria, Property<T, V> property,
                                            Criteria<?> otherCriteria, String otherColumn) {
        return nqWith(criteria, property, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, V> NormalEqual nqWith(Criteria<T> criteria, Property<T, V> property,
                                            Criteria<?> otherCriteria, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, String property,
                                     Criteria<?> otherCriteria, String otherColumn) {
        return nqWith(criteria, property, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, String property,
                                     Criteria<?> otherCriteria, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param <T>             实体类型
     * @param <V>             属性值类型
     * @return 条件对象
     */
    public static <T, V> NormalEqual nqWith(Criteria<T> criteria, Property<T, V> property,
                                            String otherTableAlias, String otherColumn) {
        return nqWith(criteria, property, otherTableAlias, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param logic           逻辑符号
     * @param <T>             实体类型
     * @param <V>             属性值类型
     * @return 条件对象
     */
    public static <T, V> NormalEqual nqWith(Criteria<T> criteria, Property<T, V> property,
                                            String otherTableAlias, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, String property,
                                     String otherTableAlias, String otherColumn) {
        return nqWith(criteria, property, otherTableAlias, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param logic           逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, String property,
                                     String otherTableAlias, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).property(property).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, ColumnWrapper column,
                                     Criteria<?> otherCriteria, String otherColumn) {
        return nqWith(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, ColumnWrapper column,
                                     Criteria<?> otherCriteria, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param column          字段包装对象
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, ColumnWrapper column,
                                     String otherTableAlias, String otherColumn) {
        return nqWith(criteria, column, otherTableAlias, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param column          字段包装对象
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param logic           逻辑符号
     * @return 条件对象
     */
    public static NormalEqual nqWith(Criteria<?> criteria, ColumnWrapper column,
                                     String otherTableAlias, String otherColumn, Logic logic) {
        return NormalEqual.create().criteria(criteria).column(column).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(Criteria<?> criteria, String column,
                                          Criteria<?> otherCriteria, ColumnWrapper otherColumn) {
        return drtNq(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(Criteria<?> criteria, String column, Criteria<?> otherCriteria,
                                          ColumnWrapper otherColumn, Logic logic) {
        return DirectNormalEqual.create().criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <E, V> DirectNormalEqual drtNq(String tableAlias, String column,
                                                 Criteria<E> otherCriteria, Property<E, V> otherProperty) {
        return drtNq(tableAlias, column, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <E, V> DirectNormalEqual drtNq(String tableAlias, String column, Criteria<E> otherCriteria,
                                                 Property<E, V> otherProperty, Logic logic) {
        return DirectNormalEqual.create().alias(tableAlias).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(String tableAlias, String column,
                                          Criteria<?> otherCriteria, String otherProperty) {
        return drtNq(tableAlias, column, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(String tableAlias, String column,
                                          Criteria<?> otherCriteria, String otherProperty, Logic logic) {
        return DirectNormalEqual.create().alias(tableAlias).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <E, V> DirectNormalEqual drtNq(Criteria<?> criteria, String column,
                                                 Criteria<E> otherCriteria, Property<E, V> otherProperty) {
        return drtNq(criteria, column, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <E, V> DirectNormalEqual drtNq(Criteria<?> criteria, String column, Criteria<E> otherCriteria,
                                                 Property<E, V> otherProperty, Logic logic) {
        return DirectNormalEqual.create().criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(Criteria<?> criteria, String column,
                                          Criteria<?> otherCriteria, String otherProperty) {
        return drtNq(criteria, column, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @return 条件对象
     */
    public static DirectNormalEqual drtNq(Criteria<?> criteria, String column,
                                          Criteria<?> otherCriteria, String otherProperty, Logic logic) {
        return DirectNormalEqual.create().criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }
    // endregion

    // region sub query

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery sq(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc, Logic logic) {
        return SubQuery.create().criteria(criteria).column(column).sc(sc).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> SubQuery sq(Criteria<T> criteria, Property<T, V> property, SubCriteria<?> sc) {
        return sq(criteria, property, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @return 条件对象
     */
    public static SubQuery sq(Criteria<?> criteria, String property, SubCriteria<?> sc) {
        return sq(criteria, property, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> SubQuery sq(Criteria<T> criteria, Property<T, V> property, SubCriteria<?> sc, Logic logic) {
        return sq(criteria, property, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static SubQuery sq(Criteria<?> criteria, String property, SubCriteria<?> sc, Logic logic) {
        return sq(criteria, property, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> SubQuery sq(Criteria<T> criteria, Property<T, V> property, SubCriteria<?> sc, Symbol symbol) {
        return sq(criteria, property, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @return 条件对象
     */
    public static SubQuery sq(Criteria<?> criteria, String property, SubCriteria<?> sc, Symbol symbol) {
        return sq(criteria, property, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> SubQuery sq(Criteria<T> criteria, Property<T, V> property,
                                     SubCriteria<?> sc, Symbol symbol, Logic logic) {
        return SubQuery.create().criteria(criteria).property(property).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static SubQuery sq(Criteria<?> criteria, String property, SubCriteria<?> sc, Symbol symbol, Logic logic) {
        return SubQuery.create().criteria(criteria).property(property).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(String tableAlias, String column, SubCriteria<?> sc) {
        return sqWith(tableAlias, column, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param symbol     条件符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(String tableAlias, String column, SubCriteria<?> sc, Symbol symbol) {
        return sqWith(tableAlias, column, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(String tableAlias, String column, SubCriteria<?> sc, Logic logic) {
        return sqWith(tableAlias, column, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param symbol     条件符号
     * @param logic      逻辑符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(String tableAlias, String column,
                                        SubCriteria<?> sc, Symbol symbol, Logic logic) {
        return DirectSubQuery.create().alias(tableAlias).column(column).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(Criteria<?> criteria, String column, SubCriteria<?> sc) {
        return sqWith(criteria, column, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(Criteria<?> criteria, String column, SubCriteria<?> sc, Symbol symbol) {
        return sqWith(criteria, column, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(Criteria<?> criteria, String column, SubCriteria<?> sc, Logic logic) {
        return sqWith(criteria, column, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @return 条件对象
     */
    public static DirectSubQuery sqWith(Criteria<?> criteria, String column,
                                        SubCriteria<?> sc, Symbol symbol, Logic logic) {
        return DirectSubQuery.create().criteria(criteria).column(column).sc(sc).symbol(symbol).logic(logic).build();
    }
    // endregion

    // region EXISTS/NOT_EXISTS

    /**
     * EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <E> SubQuery exists(Criteria<?> criteria, SubCriteria<E> sc) {
        return exists(criteria, sc, Logic.AND);
    }

    /**
     * EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param logic    逻辑符号
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <E> SubQuery exists(Criteria<?> criteria, SubCriteria<E> sc, Logic logic) {
        return SubQuery.create().criteria(criteria).sc(sc).symbol(Symbol.EXISTS).logic(logic).build();
    }

    /**
     * NOT EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <E> SubQuery notExists(Criteria<?> criteria, SubCriteria<E> sc) {
        return notExists(criteria, sc, Logic.AND);
    }

    /**
     * NOT EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <E> SubQuery notExists(Criteria<?> criteria, SubCriteria<E> sc, Logic logic) {
        return SubQuery.create().criteria(criteria).sc(sc).symbol(Symbol.NOT_EXISTS).logic(logic).build();
    }
    // region
}
