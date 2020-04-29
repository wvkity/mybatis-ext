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
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> IdEqual<T> idEq(Criteria<T> criteria, Object value) {
        return IdEqual.create(criteria, value);
    }

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> IdEqual<T> idEq(Criteria<T> criteria, Object value, Logic logic) {
        return IdEqual.create(criteria, value, logic);
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
    public static <T, V> Equal<T> eq(Criteria<T> criteria, Property<T, V> property, V value) {
        return eq(criteria, property, value, Logic.AND);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq(Criteria<T> criteria, String property, Object value) {
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
    public static <T, V> Equal<T> eq(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        Equal.Builder<T> builder = Equal.create();
        return builder.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq(Criteria<T> criteria, String property, Object value, Logic logic) {
        Equal.Builder<T> it = Equal.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(String column, Object value) {
        return eqWith(column, value, Logic.AND);
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(String column, Object value, Logic logic) {
        DirectEqual.Builder<T> it = DirectEqual.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(String tableAlias, String column, Object value) {
        return eqWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(String tableAlias, String column, Object value, Logic logic) {
        DirectEqual.Builder<T> it = DirectEqual.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(Criteria<T> criteria, String column, Object value) {
        return eqWith(criteria, column, value, Logic.AND);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> eqWith(Criteria<T> criteria, String column, Object value, Logic logic) {
        DirectEqual.Builder<T> it = DirectEqual.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> NotEqual<T> ne(Criteria<T> criteria, Property<T, V> property, V value) {
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
    public static <T, V> NotEqual<T> ne(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        NotEqual.Builder<T> it = NotEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne(Criteria<T> criteria, String property, Object value) {
        return ne(criteria, property, value, Logic.AND);
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne(Criteria<T> criteria, String property, Object value, Logic logic) {
        NotEqual.Builder<T> it = NotEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(String column, Object value) {
        return neWith(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(String column, Object value, Logic logic) {
        DirectNotEqual.Builder<T> it = DirectNotEqual.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(String tableAlias, String column, Object value) {
        return neWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(String tableAlias, String column, Object value, Logic logic) {
        DirectNotEqual.Builder<T> it = DirectNotEqual.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(Criteria<T> criteria, String column, Object value) {
        return neWith(criteria, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> neWith(Criteria<T> criteria, String column, Object value, Logic logic) {
        DirectNotEqual.Builder<T> it = DirectNotEqual.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> LessThan<T> lt(Criteria<T> criteria, Property<T, V> property, V value) {
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
    public static <T, V> LessThan<T> lt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        LessThan.Builder<T> it = LessThan.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThan<T> lt(Criteria<T> criteria, String property, Object value) {
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
    public static <T> LessThan<T> lt(Criteria<T> criteria, String property, Object value, Logic logic) {
        LessThan.Builder<T> it = LessThan.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(String column, Object value) {
        return ltWith(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(String column, Object value, Logic logic) {
        DirectLessThan.Builder<T> it = DirectLessThan.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(String tableAlias, String column, Object value) {
        return ltWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(String tableAlias, String column, Object value, Logic logic) {
        DirectLessThan.Builder<T> it = DirectLessThan.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(Criteria<T> criteria, String column, Object value) {
        return ltWith(criteria, column, value, Logic.AND);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> ltWith(Criteria<T> criteria, String column, Object value, Logic logic) {
        DirectLessThan.Builder<T> it = DirectLessThan.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> LessThanOrEqual<T> le(Criteria<T> criteria, Property<T, V> property, V value) {
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
    public static <T, V> LessThanOrEqual<T> le(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        LessThanOrEqual.Builder<T> it = LessThanOrEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le(Criteria<T> criteria, String property, Object value) {
        return le(criteria, property, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le(Criteria<T> criteria, String property, Object value, Logic logic) {
        LessThanOrEqual.Builder<T> it = LessThanOrEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> leWith(String column, Object value) {
        return leWith(column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> leWith(String column, Object value, Logic logic) {
        DirectLessThanOrEqual.Builder<T> it = DirectLessThanOrEqual.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> leWith(String tableAlias, String column, Object value) {
        return leWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> leWith(String tableAlias, String column, Object value, Logic logic) {
        DirectLessThanOrEqual.Builder<T> it = DirectLessThanOrEqual.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> leWith(Criteria<T> criteria, String column, Object value) {
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
    public static <T> DirectLessThanOrEqual<T> leWith(Criteria<T> criteria, String column,
                                                      Object value, Logic logic) {
        DirectLessThanOrEqual.Builder<T> it = DirectLessThanOrEqual.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> GreaterThan<T> gt(Criteria<T> criteria, Property<T, V> property, V value) {
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
    public static <T, V> GreaterThan<T> gt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        GreaterThan.Builder<T> it = GreaterThan.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt(Criteria<T> criteria, String property, Object value) {
        return gt(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt(Criteria<T> criteria, String property, Object value, Logic logic) {
        GreaterThan.Builder<T> it = GreaterThan.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(String column, Object value) {
        return gtWith(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(String column, Object value, Logic logic) {
        DirectGreaterThan.Builder<T> it = DirectGreaterThan.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(String tableAlias, String column, Object value) {
        return gtWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(String tableAlias, String column, Object value, Logic logic) {
        DirectGreaterThan.Builder<T> it = DirectGreaterThan.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(Criteria<T> criteria, String column, Object value) {
        return gtWith(criteria, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> gtWith(Criteria<T> criteria, String column, Object value, Logic logic) {
        DirectGreaterThan.Builder<T> it = DirectGreaterThan.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> GreaterThanOrEqual<T> ge(Criteria<T> criteria, Property<T, V> property, V value) {
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
    public static <T, V> GreaterThanOrEqual<T> ge(Criteria<T> criteria, Property<T, V> property,
                                                  V value, Logic logic) {
        GreaterThanOrEqual.Builder<T> it = GreaterThanOrEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge(Criteria<T> criteria, String property, Object value) {
        return ge(criteria, property, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge(Criteria<T> criteria, String property, Object value, Logic logic) {
        GreaterThanOrEqual.Builder<T> it = GreaterThanOrEqual.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(String column, Object value) {
        return geWith(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(String column, Object value, Logic logic) {
        DirectGreaterThanOrEqual.Builder<T> it = DirectGreaterThanOrEqual.create();
        return it.column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(String tableAlias, String column, Object value) {
        return geWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(String tableAlias, String column,
                                                         Object value, Logic logic) {
        DirectGreaterThanOrEqual.Builder<T> it = DirectGreaterThanOrEqual.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(Criteria<T> criteria, String column, Object value) {
        return geWith(criteria, column, value, Logic.AND);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> geWith(Criteria<T> criteria, String column,
                                                         Object value, Logic logic) {
        DirectGreaterThanOrEqual.Builder<T> it = DirectGreaterThanOrEqual.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
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
    public static <T, V> Null<T> isNull(Criteria<T> criteria, Property<T, V> property) {
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
    public static <T, V> Null<T> isNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        Null.Builder<T> it = Null.create();
        return it.criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull(Criteria<T> criteria, String property) {
        return isNull(criteria, property, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull(Criteria<T> criteria, String property, Logic logic) {
        Null.Builder<T> it = Null.create();
        return it.criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> isNullWith(String tableAlias, String column) {
        return isNullWith(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> isNullWith(String tableAlias, String column, Logic logic) {
        DirectNull.Builder<T> it = DirectNull.create();
        return it.alias(tableAlias).column(column).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> isNullWith(Criteria<T> criteria, String column) {
        return isNullWith(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> isNullWith(Criteria<T> criteria, String column, Logic logic) {
        DirectNull.Builder<T> it = DirectNull.create();
        return it.criteria(criteria).column(column).logic(logic).build();
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotNull<T> notNull(Criteria<T> criteria, Property<T, V> property) {
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
    public static <T, V> NotNull<T> notNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        NotNull.Builder<T> it = NotNull.create();
        return it.criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull(Criteria<T> criteria, String property) {
        return notNull(criteria, property, Logic.AND);
    }

    /**
     * IS NOT NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull(Criteria<T> criteria, String property, Logic logic) {
        NotNull.Builder<T> it = NotNull.create();
        return it.criteria(criteria).property(property).logic(logic).build();
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotNull<T> notNullWith(String tableAlias, String column) {
        return notNullWith(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotNull<T> notNullWith(String tableAlias, String column, Logic logic) {
        DirectNotNull.Builder<T> it = DirectNotNull.create();
        return it.alias(tableAlias).column(column).logic(logic).build();
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotNull<T> notNullWith(Criteria<T> criteria, String column) {
        return notNullWith(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotNull<T> notNullWith(Criteria<T> criteria, String column, Logic logic) {
        DirectNotNull.Builder<T> it = DirectNotNull.create();
        return it.criteria(criteria).column(column).logic(logic).build();
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
    public static <T, V> In<T> in(Criteria<T> criteria, Property<T, V> property, Collection<Object> values) {
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
    public static <T, V> In<T> in(Criteria<T> criteria, Property<T, V> property, Collection<Object> values, Logic logic) {
        In.Builder<T> it = In.create();
        return it.criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> in(Criteria<T> criteria, String property, Collection<Object> values) {
        return in(criteria, property, values, Logic.AND);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> in(Criteria<T> criteria, String property, Collection<Object> values, Logic logic) {
        In.Builder<T> it = In.create();
        return it.criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(String column, Collection<Object> values) {
        return inWith(column, values, Logic.AND);
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(String column, Collection<Object> values, Logic logic) {
        DirectIn.Builder<T> it = DirectIn.create();
        return it.column(column).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(String tableAlias, String column, Collection<Object> values) {
        return inWith(tableAlias, column, values, Logic.AND);
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(String tableAlias, String column, Collection<Object> values, Logic logic) {
        DirectIn.Builder<T> it = DirectIn.create();
        return it.alias(tableAlias).column(column).values(values).logic(logic).build();
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(Criteria<T> criteria, String column, Collection<Object> values) {
        return inWith(criteria, column, values, Logic.AND);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> inWith(Criteria<T> criteria, String column,
                                         Collection<Object> values, Logic logic) {
        DirectIn.Builder<T> it = DirectIn.create();
        return it.criteria(criteria).column(column).values(values).logic(logic).build();
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
    public static <T, V> NotIn<T> notIn(Criteria<T> criteria, Property<T, V> property, Collection<Object> values) {
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
    public static <T, V> NotIn<T> notIn(Criteria<T> criteria, Property<T, V> property,
                                        Collection<Object> values, Logic logic) {
        NotIn.Builder<T> it = NotIn.create();
        return it.criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn(Criteria<T> criteria, String property, Collection<Object> values) {
        return notIn(criteria, property, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn(Criteria<T> criteria, String property,
                                     Collection<Object> values, Logic logic) {
        NotIn.Builder<T> it = NotIn.create();
        return it.criteria(criteria).property(property).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(String column, Collection<Object> values) {
        return notInWith(column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(String column, Collection<Object> values, Logic logic) {
        DirectNotIn.Builder<T> it = DirectNotIn.create();
        return it.column(column).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(String tableAlias, String column, Collection<Object> values) {
        return notInWith(tableAlias, column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(String tableAlias, String column,
                                               Collection<Object> values, Logic logic) {
        DirectNotIn.Builder<T> it = DirectNotIn.create();
        return it.alias(tableAlias).column(column).values(values).logic(logic).build();
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(Criteria<T> criteria, String column,
                                               Collection<Object> values) {
        return notInWith(criteria, column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> notInWith(Criteria<T> criteria, String column,
                                               Collection<Object> values, Logic logic) {
        DirectNotIn.Builder<T> it = DirectNotIn.create();
        return it.criteria(criteria).column(column).values(values).logic(logic).build();
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
    public static <T, V> Between<T> between(Criteria<T> criteria, Property<T, V> property, V begin, V end) {
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
    public static <T, V> Between<T> between(Criteria<T> criteria, Property<T, V> property,
                                            V begin, V end, Logic logic) {
        Between.Builder<T> it = Between.create();
        return it.criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> between(Criteria<T> criteria, String property, Object begin, Object end) {
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
     * @return 条件对象
     */
    public static <T> Between<T> between(Criteria<T> criteria, String property, Object begin,
                                         Object end, Logic logic) {
        Between.Builder<T> it = Between.create();
        return it.criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(String column, Object begin, Object end) {
        return betweenWith(column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(String column, Object begin, Object end, Logic logic) {
        DirectBetween.Builder<T> it = DirectBetween.create();
        return it.column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(String tableAlias, String column, Object begin, Object end) {
        return betweenWith(tableAlias, column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(String tableAlias, String column, Object begin,
                                                   Object end, Logic logic) {
        DirectBetween.Builder<T> it = DirectBetween.create();
        return it.alias(tableAlias).column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(Criteria<T> criteria, String column, Object begin, Object end) {
        return betweenWith(criteria, column, begin, end, Logic.AND);
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> betweenWith(Criteria<T> criteria, String column, Object begin,
                                                   Object end, Logic logic) {
        DirectBetween.Builder<T> it = DirectBetween.create();
        return it.criteria(criteria).column(column).begin(begin).end(end).logic(logic).build();
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
    public static <T, V> NotBetween<T> notBetween(Criteria<T> criteria, Property<T, V> property,
                                                  V begin, V end, Logic logic) {
        NotBetween.Builder<T> it = NotBetween.create();
        return it.criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween(Criteria<T> criteria, String property, Object begin, Object end) {
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
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween(Criteria<T> criteria, String property, Object begin,
                                               Object end, Logic logic) {
        NotBetween.Builder<T> it = NotBetween.create();
        return it.criteria(criteria).property(property).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(String column, Object begin, Object end) {
        return notBetweenWith(column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(String column, Object begin, Object end, Logic logic) {
        DirectNotBetween.Builder<T> it = DirectNotBetween.create();
        return it.column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(String tableAlias, String column,
                                                         Object begin, Object end) {
        return notBetweenWith(tableAlias, column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(String tableAlias, String column, Object begin,
                                                         Object end, Logic logic) {
        DirectNotBetween.Builder<T> it = DirectNotBetween.create();
        return it.alias(tableAlias).column(column).begin(begin).end(end).logic(logic).build();
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(Criteria<T> criteria, String column,
                                                         Object begin, Object end) {
        return notBetweenWith(criteria, column, begin, end, Logic.AND);
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> notBetweenWith(Criteria<T> criteria, String column, Object begin,
                                                         Object end, Logic logic) {
        DirectNotBetween.Builder<T> it = DirectNotBetween.create();
        return it.criteria(criteria).column(column).begin(begin).end(end).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value) {
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value) {
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property,
                                   String value, Character escape) {
        return like(criteria, property, value, escape, Logic.AND);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value, Character escape) {
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value,
                                   Character escape, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Character escape, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value, Match match) {
        return like(criteria, property, value, match, Logic.AND);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value, Match match) {
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value,
                                   Match match, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).match(match).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Match match, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).match(match).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value,
                                   Match match, Character escape) {
        return like(criteria, property, value, match, escape, Logic.AND);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Match match, Character escape) {
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
    public static <T> Like<T> like(Criteria<T> criteria, Property<T, String> property, String value,
                                   Match match, Character escape, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).match(match).escape(escape).logic(logic).build();
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Match match, Character escape, Logic logic) {
        Like.Builder<T> it = Like.create();
        return it.criteria(criteria).property(property).value(value).match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property, String value) {
        return notLike(criteria, property, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value) {
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property,
                                         String value, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property,
                                         String value, Character escape) {
        return notLike(criteria, property, value, escape, Logic.AND);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value, Character escape) {
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property, String value,
                                         Character escape, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Character escape, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).escape(escape).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property,
                                         String value, Match match) {
        return notLike(criteria, property, value, match, Logic.AND);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value, Match match) {
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property, String value,
                                         Match match, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).match(match).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Match match, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).match(match).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property, String value,
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
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
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
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> notLike(Criteria<T> criteria, Property<T, String> property, String value,
                                         Match match, Character escape, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).match(match).escape(escape).logic(logic).build();
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Match match, Character escape, Logic logic) {
        NotLike.Builder<T> it = NotLike.create();
        return it.criteria(criteria).property(property).value(value).match(match).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value) {
        return likeWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value, Match match) {
        return likeWith(tableAlias, column, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value,
                                             Match match, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.alias(tableAlias).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value,
                                             Character escape) {
        return likeWith(tableAlias, column, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value,
                                             Character escape, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.alias(tableAlias).column(column).value(value).escape(escape).logic(logic).build();
    }


    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value,
                                             Match match, Character escape) {
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
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(String tableAlias, String column, String value,
                                             Match match, Character escape, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.alias(tableAlias).column(column).value(value).match(match).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value) {
        return likeWith(criteria, column, value, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column,
                                             String value, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Match match) {
        return likeWith(criteria, column, value, match, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Match match, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.criteria(criteria).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Character escape) {
        return likeWith(criteria, column, value, escape, Logic.AND);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Character escape, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.criteria(criteria).column(column).value(value).escape(escape).logic(logic).build();
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Match match, Character escape) {
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
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> likeWith(Criteria<T> criteria, String column, String value,
                                             Match match, Character escape, Logic logic) {
        DirectLike.Builder<T> it = DirectLike.create();
        return it.criteria(criteria).column(column).value(value).match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value) {
        return notLikeWith(tableAlias, column, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column,
                                                   String value, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.alias(tableAlias).column(column).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value, Match match) {
        return notLikeWith(tableAlias, column, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value,
                                                   Match match, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.alias(tableAlias).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value,
                                                   Character escape) {
        return notLikeWith(tableAlias, column, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value,
                                                   Character escape, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.alias(tableAlias).column(column).value(value).escape(escape).logic(logic).build();
    }


    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param match      匹配模式
     * @param escape     转义字符
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value,
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
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(String tableAlias, String column, String value,
                                                   Match match, Character escape, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.alias(tableAlias).column(column).value(value).match(match).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column, String value) {
        return notLikeWith(criteria, column, value, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column,
                                                   String value, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.criteria(criteria).column(column).value(value).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column,
                                                   String value, Match match) {
        return notLikeWith(criteria, column, value, match, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column, String value,
                                                   Match match, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.criteria(criteria).column(column).value(value).match(match).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column,
                                                   String value, Character escape) {
        return notLikeWith(criteria, column, value, escape, Logic.AND);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column, String value,
                                                   Character escape, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.criteria(criteria).column(column).value(value).escape(escape).logic(logic).build();
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column, String value,
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
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> notLikeWith(Criteria<T> criteria, String column, String value,
                                                   Match match, Character escape, Logic logic) {
        DirectNotLike.Builder<T> it = DirectNotLike.create();
        return it.criteria(criteria).column(column).value(value).match(match).escape(escape).logic(logic).build();
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property,
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Object value,
                                              String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property, Object value, String template) {
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
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property, Object value,
                                           String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).value(value).logic(logic).build();
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property,
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Collection<Object> values,
                                              String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property,
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
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property, Collection<Object> values,
                                           String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).values(values).logic(logic).build();
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property,
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
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Map<String, Object> values,
                                              String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).map(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property,
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
     * @return 条件对象
     */
    public static <T> Template<T> template(Criteria<T> criteria, String property, Map<String, Object> values,
                                           String template, Logic logic) {
        Template.Builder<T> it = Template.create();
        return it.criteria(criteria).property(property).template(template).map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Object value) {
        return templateWith(template, value, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Object value, Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.template(template).value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Collection<Object> values) {
        return templateWith(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Collection<Object> values,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Map<String, Object> values) {
        return templateWith(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String template, Map<String, Object> values,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.template(template).map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param template   模板
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
                                                     Object value, String template) {
        return templateWith(tableAlias, column, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param template   模板
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
                                                     Object value, String template, Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.alias(tableAlias).column(column).template(template).value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
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
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
                                                     Collection<Object> values, String template,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.alias(tableAlias).column(column).template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param template   模板
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
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
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(String tableAlias, String column,
                                                     Map<String, Object> values, String template,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.alias(tableAlias).column(column).template(template).map(values).logic(logic).build();
    }


    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
                                                     Object value, String template) {
        return templateWith(criteria, column, value, template, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param template 模板
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
                                                     Object value, String template, Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.criteria(criteria).column(column).template(template).value(value).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
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
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
                                                     Collection<Object> values, String template,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.criteria(criteria).column(column).template(template).values(values).logic(logic).build();
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
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
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> templateWith(Criteria<T> criteria, String column,
                                                     Map<String, Object> values, String template,
                                                     Logic logic) {
        DirectTemplate.Builder<T> it = DirectTemplate.create();
        return it.criteria(criteria).column(column).template(template).map(values).logic(logic).build();
    }

    // endregion

    // region nested

    /**
     * NESTED
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested(Criteria<T> criteria, Logic logic, Criterion<?>... conditions) {
        return Nested.create(criteria, logic, conditions);
    }

    /**
     * NESTED
     * @param criteria   条件包装对象
     * @param logic      逻辑符号
     * @param conditions 条件
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested(Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions) {
        return Nested.create(criteria, logic, conditions);
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
    public static <T, V1, E, V2> NormalEqual<T> nq(Criteria<T> criteria, Property<T, V1> property,
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
    public static <T, V1, E, V2> NormalEqual<T> nq(Criteria<T> criteria, Property<T, V1> property,
                                                   Criteria<E> otherCriteria, Property<E, V2> otherProperty,
                                                   Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nq(Criteria<T> criteria, String property,
                                           Criteria<E> otherCriteria, String otherProperty) {
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
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nq(Criteria<T> criteria, String property,
                                           Criteria<E> otherCriteria, String otherProperty, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nq(Criteria<T> criteria, ColumnWrapper column,
                                           Criteria<E> otherCriteria, ColumnWrapper otherColumn) {
        return nq(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nq(Criteria<T> criteria, ColumnWrapper column,
                                           Criteria<E> otherCriteria, ColumnWrapper otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();

    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, E, V> NormalEqual<T> nqWith(Criteria<T> criteria, Property<T, V> property,
                                                  Criteria<E> otherCriteria, String otherColumn) {
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
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, E, V> NormalEqual<T> nqWith(Criteria<T> criteria, Property<T, V> property,
                                                  Criteria<E> otherCriteria, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, String property,
                                               Criteria<E> otherCriteria, String otherColumn) {
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
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, String property,
                                               Criteria<E> otherCriteria, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param <T>             实体类型
     * @param <E>             实体类型
     * @param <V>             属性值类型
     * @return 条件对象
     */
    public static <T, E, V> NormalEqual<T> nqWith(Criteria<T> criteria, Property<T, V> property,
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
     * @param <E>             实体类型
     * @param <V>             属性值类型
     * @return 条件对象
     */
    public static <T, E, V> NormalEqual<T> nqWith(Criteria<T> criteria, Property<T, V> property,
                                                  String otherTableAlias, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param <T>             实体类型
     * @param <E>             实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, String property,
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
     * @param <E>             实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, String property,
                                               String otherTableAlias, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).property(property).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, ColumnWrapper column,
                                               Criteria<E> otherCriteria, String otherColumn) {
        return nqWith(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, ColumnWrapper column,
                                               Criteria<E> otherCriteria, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria        条件包装对象
     * @param column          字段包装对象
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param <T>             实体类型
     * @param <E>             实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, ColumnWrapper column,
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
     * @param <T>             实体类型
     * @param <E>             实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> nqWith(Criteria<T> criteria, ColumnWrapper column,
                                               String otherTableAlias, String otherColumn, Logic logic) {
        NormalEqual.Builder<T> it = NormalEqual.create();
        return it.criteria(criteria).column(column).otherAlias(otherTableAlias)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column,
                                                    Criteria<E> otherCriteria, ColumnWrapper otherColumn) {
        return drtNq(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column, Criteria<E> otherCriteria,
                                                    ColumnWrapper otherColumn, Logic logic) {
        DirectNormalEqual.Builder<T> it = DirectNormalEqual.create();
        return it.criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherColumn(otherColumn).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, E, V> DirectNormalEqual<T> drtNq(String tableAlias, String column,
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
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, E, V> DirectNormalEqual<T> drtNq(String tableAlias, String column, Criteria<E> otherCriteria,
                                                       Property<E, V> otherProperty, Logic logic) {
        DirectNormalEqual.Builder<T> it = DirectNormalEqual.create();
        return it.alias(tableAlias).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(String tableAlias, String column,
                                                    Criteria<E> otherCriteria, String otherProperty) {
        return drtNq(tableAlias, column, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * NORMAL EQUAL
     * @param tableAlias    表别名
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(String tableAlias, String column,
                                                    Criteria<E> otherCriteria, String otherProperty, Logic logic) {
        DirectNormalEqual.Builder<T> it = DirectNormalEqual.create();
        return it.alias(tableAlias).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return 条件对象
     */
    public static <T, E, V> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column,
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
    public static <T, E, V> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column, Criteria<E> otherCriteria,
                                                       Property<E, V> otherProperty, Logic logic) {
        DirectNormalEqual.Builder<T> it = DirectNormalEqual.create();
        return it.criteria(criteria).column(column).otherCriteria(otherCriteria)
                .otherProperty(otherProperty).logic(logic).build();
    }

    /**
     * NORMAL EQUAL
     * @param criteria      条件包装对象
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column,
                                                    Criteria<E> otherCriteria, String otherProperty) {
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
     * @return 条件对象
     */
    public static <T, E> DirectNormalEqual<T> drtNq(Criteria<T> criteria, String column,
                                                    Criteria<E> otherCriteria, String otherProperty, Logic logic) {
        DirectNormalEqual.Builder<T> it = DirectNormalEqual.create();
        return it.criteria(criteria).column(column).otherCriteria(otherCriteria)
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
    public static <T> SubQuery<T> sq(Criteria<T> criteria, ColumnWrapper column,
                                     SubCriteria<?> sc, Logic logic) {
        SubQuery.Builder<T> it = SubQuery.create();
        return it.criteria(criteria).column(column).sc(sc).logic(logic).build();
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
    public static <T, V> SubQuery<T> sq(Criteria<T> criteria, Property<T, V> property, SubCriteria<?> sc) {
        return sq(criteria, property, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> sq(Criteria<T> criteria, String property, SubCriteria<?> sc) {
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
    public static <T, V> SubQuery<T> sq(Criteria<T> criteria, Property<T, V> property,
                                        SubCriteria<?> sc, Logic logic) {
        return sq(criteria, property, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> sq(Criteria<T> criteria, String property, SubCriteria<?> sc, Logic logic) {
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
    public static <T, V> SubQuery<T> sq(Criteria<T> criteria, Property<T, V> property,
                                        SubCriteria<?> sc, Symbol symbol) {
        return sq(criteria, property, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> sq(Criteria<T> criteria, String property, SubCriteria<?> sc, Symbol symbol) {
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
    public static <T, V> SubQuery<T> sq(Criteria<T> criteria, Property<T, V> property, SubCriteria<?> sc,
                                        Symbol symbol, Logic logic) {
        SubQuery.Builder<T> it = SubQuery.create();
        return it.criteria(criteria).property(property).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> sq(Criteria<T> criteria, String property, SubCriteria<?> sc,
                                     Symbol symbol, Logic logic) {
        SubQuery.Builder<T> it = SubQuery.create();
        return it.criteria(criteria).property(property).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(String tableAlias, String column, SubCriteria<?> sc) {
        return sqWith(tableAlias, column, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param symbol     条件符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(String tableAlias, String column, SubCriteria<?> sc,
                                               Symbol symbol) {
        return sqWith(tableAlias, column, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(String tableAlias, String column, SubCriteria<?> sc,
                                               Logic logic) {
        return sqWith(tableAlias, column, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param tableAlias 表别名
     * @param column     字段
     * @param sc         子查询条件包装对象
     * @param symbol     条件符号
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(String tableAlias, String column, SubCriteria<?> sc,
                                               Symbol symbol, Logic logic) {
        DirectSubQuery.Builder<T> it = DirectSubQuery.create();
        return it.alias(tableAlias).column(column).sc(sc).symbol(symbol).logic(logic).build();
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(Criteria<T> criteria, String column, SubCriteria<?> sc) {
        return sqWith(criteria, column, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(Criteria<T> criteria, String column, SubCriteria<?> sc, Symbol symbol) {
        return sqWith(criteria, column, sc, symbol, Logic.AND);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(Criteria<T> criteria, String column, SubCriteria<?> sc, Logic logic) {
        return sqWith(criteria, column, sc, Symbol.EQ, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectSubQuery<T> sqWith(Criteria<T> criteria, String column, SubCriteria<?> sc,
                                               Symbol symbol, Logic logic) {
        DirectSubQuery.Builder<T> it = DirectSubQuery.create();
        return it.criteria(criteria).column(column).sc(sc).symbol(symbol).logic(logic).build();
    }
    // endregion

    // region EXISTS/NOT_EXISTS

    /**
     * EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param <T>      实体类型
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists(Criteria<T> criteria, SubCriteria<E> sc) {
        return exists(criteria, sc, Logic.AND);
    }

    /**
     * EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists(Criteria<T> criteria, SubCriteria<E> sc, Logic logic) {
        SubQuery.Builder<T> it = SubQuery.create();
        return it.criteria(criteria).sc(sc).symbol(Symbol.EXISTS).logic(logic).build();
    }

    /**
     * NOT EXISTS
     * @param criteria 条件包装对象
     * @param sc       子查询条件对象
     * @param <T>      实体类型
     * @param <E>      子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notExists(Criteria<T> criteria, SubCriteria<E> sc) {
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
    public static <T, E> SubQuery<T> notExists(Criteria<T> criteria, SubCriteria<E> sc, Logic logic) {
        SubQuery.Builder<T> it = SubQuery.create();
        return it.criteria(criteria).sc(sc).symbol(Symbol.NOT_EXISTS).logic(logic).build();
    }
    // region
}
