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
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotIn;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNotLike;
import com.wkit.lost.mybatis.core.conditional.expression.DirectNull;
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
import com.wkit.lost.mybatis.core.conditional.expression.NotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.NotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.NotIn;
import com.wkit.lost.mybatis.core.conditional.expression.NotLike;
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
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Equal<T> eq(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return Equal.create(criteria, property, value, logic);
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
        return Equal.create(criteria, property, value, logic);
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> directEq(String column, Object value) {
        return directEq(column, value, Logic.AND);
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> directEq(String column, Object value, Logic logic) {
        return DirectEqual.create(column, value, logic);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> directEq(String tableAlias, String column, Object value) {
        return directEq(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectEqual<T> directEq(String tableAlias, String column, Object value, Logic logic) {
        return DirectEqual.create(tableAlias, column, value, logic);
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectEqual<T> directEq(Criteria<T> criteria, String column, Object value) {
        return directEq(criteria, column, value, Logic.AND);
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
    public static <T> DirectEqual<T> directEq(Criteria<T> criteria, String column, Object value, Logic logic) {
        return DirectEqual.create(criteria, column, value, logic);
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> NotEqual<T> ne(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return NotEqual.create(criteria, property, value, logic);
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
        return NotEqual.create(criteria, property, value, logic);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> directNe(String column, Object value) {
        return directNe(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> directNe(String column, Object value, Logic logic) {
        return DirectNotEqual.create(column, value, logic);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> directNe(String tableAlias, String column, Object value) {
        return directNe(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectNotEqual<T> directNe(String tableAlias, String column, Object value, Logic logic) {
        return DirectNotEqual.create(tableAlias, column, value, logic);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> directNe(Criteria<T> criteria, String column, Object value) {
        return directNe(criteria, column, value, Logic.AND);
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
    public static <T> DirectNotEqual<T> directNe(Criteria<T> criteria, String column, Object value, Logic logic) {
        return DirectNotEqual.create(criteria, column, value, logic);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> LessThan<T> lt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return LessThan.create(criteria, property, value, logic);
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
        return LessThan.create(criteria, property, value, logic);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> directLt(String column, Object value) {
        return directLt(column, value, Logic.AND);
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> directLt(String column, Object value, Logic logic) {
        return DirectLessThan.create(column, value, logic);
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> directLt(String tableAlias, String column, Object value) {
        return directLt(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectLessThan<T> directLt(String tableAlias, String column, Object value, Logic logic) {
        return DirectLessThan.create(tableAlias, column, value, logic);
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThan<T> directLt(Criteria<T> criteria, String column, Object value) {
        return directLt(criteria, column, value, Logic.AND);
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
    public static <T> DirectLessThan<T> directLt(Criteria<T> criteria, String column, Object value, Logic logic) {
        return DirectLessThan.create(criteria, column, value, logic);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> LessThanOrEqual<T> le(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return LessThanOrEqual.create(criteria, property, value, logic);
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
        return LessThanOrEqual.create(criteria, property, value, logic);
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> directLe(String column, Object value) {
        return directLe(column, value, Logic.AND);
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> directLe(String column, Object value, Logic logic) {
        return DirectLessThanOrEqual.create(column, value, logic);
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> directLe(String tableAlias, String column, Object value) {
        return directLe(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectLessThanOrEqual<T> directLe(String tableAlias, String column, Object value, Logic logic) {
        return DirectLessThanOrEqual.create(tableAlias, column, value, logic);
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectLessThanOrEqual<T> directLe(Criteria<T> criteria, String column, Object value) {
        return directLe(criteria, column, value, Logic.AND);
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
    public static <T> DirectLessThanOrEqual<T> directLe(Criteria<T> criteria, String column, 
                                                        Object value, Logic logic) {
        return DirectLessThanOrEqual.create(criteria, column, value, logic);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThan<T> gt(Criteria<T> criteria, Property<T, V> property, V value, Logic logic) {
        return GreaterThan.create(criteria, property, value, logic);
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
        return GreaterThan.create(criteria, property, value, logic);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> directGt(String column, Object value) {
        return directGt(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> directGt(String column, Object value, Logic logic) {
        return DirectGreaterThan.create(column, value, logic);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> directGt(String tableAlias, String column, Object value) {
        return directGt(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectGreaterThan<T> directGt(String tableAlias, String column, Object value, Logic logic) {
        return DirectGreaterThan.create(tableAlias, column, value, logic);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThan<T> directGt(Criteria<T> criteria, String column, Object value) {
        return directGt(criteria, column, value, Logic.AND);
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
    public static <T> DirectGreaterThan<T> directGt(Criteria<T> criteria, String column, Object value, Logic logic) {
        return DirectGreaterThan.create(criteria, column, value, logic);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThanOrEqual<T> ge(Criteria<T> criteria, Property<T, V> property, 
                                                  V value, Logic logic) {
        return GreaterThanOrEqual.create(criteria, property, value, logic);
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
        return GreaterThanOrEqual.create(criteria, property, value, logic);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> directGe(String column, Object value) {
        return directGe(column, value, Logic.AND);
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> directGe(String column, Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create(column, value, logic);
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> directGe(String tableAlias, String column, Object value) {
        return directGe(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectGreaterThanOrEqual<T> directGe(String tableAlias, String column, 
                                                           Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create(tableAlias, column, value, logic);
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectGreaterThanOrEqual<T> directGe(Criteria<T> criteria, String column, Object value) {
        return directGe(criteria, column, value, Logic.AND);
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
    public static <T> DirectGreaterThanOrEqual<T> directGe(Criteria<T> criteria, String column,
                                                           Object value, Logic logic) {
        return DirectGreaterThanOrEqual.create(criteria, column, value, logic);
    }

    // endregion

    // region empty expression

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Null<T> isNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        return Null.create(criteria, property, logic);
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
        return Null.create(criteria, property, logic);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directIsNull(String tableAlias, String column) {
        return directIsNull(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directIsNull(String tableAlias, String column, Logic logic) {
        return DirectNull.create(tableAlias, column, logic);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directIsNull(Criteria<T> criteria, String column) {
        return directIsNull(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directIsNull(Criteria<T> criteria, String column, Logic logic) {
        return DirectNull.create(criteria, column, logic);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Null<T> notNull(Criteria<T> criteria, Property<T, V> property) {
        return notNull(criteria, property, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Null<T> notNull(Criteria<T> criteria, Property<T, V> property, Logic logic) {
        return Null.create(criteria, property, logic);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull(Criteria<T> criteria, String property) {
        return notNull(criteria, property, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull(Criteria<T> criteria, String property, Logic logic) {
        return Null.create(criteria, property, logic);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directNotNull(String tableAlias, String column) {
        return directNotNull(tableAlias, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directNotNull(String tableAlias, String column, Logic logic) {
        return DirectNull.create(tableAlias, column, logic);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directNotNull(Criteria<T> criteria, String column) {
        return directNotNull(criteria, column, Logic.AND);
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> directNotNull(Criteria<T> criteria, String column, Logic logic) {
        return DirectNull.create(criteria, column, logic);
    }

    // endregion

    // region range expression

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
        return In.create(criteria, property, values, logic);
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> directIn(String column, Collection<Object> values) {
        return directIn(column, values, Logic.AND);
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> directIn(String column, Collection<Object> values, Logic logic) {
        return DirectIn.create(column, values, logic);
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> directIn(String tableAlias, String column, Collection<Object> values) {
        return directIn(tableAlias, column, values, Logic.AND);
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
    public static <T> DirectIn<T> directIn(String tableAlias, String column, Collection<Object> values, Logic logic) {
        return DirectIn.create(tableAlias, column, values, logic);
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectIn<T> directIn(Criteria<T> criteria, String column, Collection<Object> values) {
        return directIn(criteria, column, values, Logic.AND);
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
    public static <T> DirectIn<T> directIn(Criteria<T> criteria, String column, 
                                           Collection<Object> values, Logic logic) {
        return DirectIn.create(criteria, column, values, logic);
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
        return NotIn.create(criteria, property, values, logic);
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> directNotIn(String column, Collection<Object> values) {
        return directNotIn(column, values, Logic.AND);
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> directNotIn(String column, Collection<Object> values, Logic logic) {
        return DirectNotIn.create(column, values, logic);
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> directNotIn(String tableAlias, String column, Collection<Object> values) {
        return directNotIn(tableAlias, column, values, Logic.AND);
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
    public static <T> DirectNotIn<T> directNotIn(String tableAlias, String column,
                                                 Collection<Object> values, Logic logic) {
        return DirectNotIn.create(tableAlias, column, values, logic);
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotIn<T> directNotIn(Criteria<T> criteria, String column,
                                                 Collection<Object> values) {
        return directNotIn(criteria, column, values, Logic.AND);
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
    public static <T> DirectNotIn<T> directNotIn(Criteria<T> criteria, String column,
                                                 Collection<Object> values, Logic logic) {
        return DirectNotIn.create(criteria, column, values, logic);
    }

    /**
     * BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Between<T> between(Criteria<T> criteria, Property<T, V> property, 
                                            V begin, V end, Logic logic) {
        return Between.create(criteria, property, begin, end, logic);
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
        return Between.create(criteria, property, begin, end, logic);
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectBetween<T> directBetween(String column, Object begin, Object end) {
        return directBetween(column, begin, end, Logic.AND);
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
    public static <T> DirectBetween<T> directBetween(String column, Object begin, Object end, Logic logic) {
        return DirectBetween.create(column, begin, end, logic);
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
    public static <T> DirectBetween<T> directBetween(String tableAlias, String column, Object begin, Object end) {
        return directBetween(tableAlias, column, begin, end, Logic.AND);
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
    public static <T> DirectBetween<T> directBetween(String tableAlias, String column, Object begin,
                                                     Object end, Logic logic) {
        return DirectBetween.create(tableAlias, column, begin, end, logic);
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
    public static <T> DirectBetween<T> directBetween(Criteria<T> criteria, String column, Object begin, Object end) {
        return directBetween(criteria, column, begin, end, Logic.AND);
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
    public static <T> DirectBetween<T> directBetween(Criteria<T> criteria, String column, Object begin,
                                                     Object end, Logic logic) {
        return DirectBetween.create(criteria, column, begin, end, logic);
    }

    /**
     * NOT BETWEEN AND
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> NotBetween<T> notBetween(Criteria<T> criteria, Property<T, V> property, 
                                                  V begin, V end, Logic logic) {
        return NotBetween.create(criteria, property, begin, end, logic);
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
        return NotBetween.create(criteria, property, begin, end, logic);
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNotBetween<T> directNotBetween(String column, Object begin, Object end) {
        return directNotBetween(column, begin, end, Logic.AND);
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
    public static <T> DirectNotBetween<T> directNotBetween(String column, Object begin, Object end, Logic logic) {
        return DirectNotBetween.create(column, begin, end, logic);
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
    public static <T> DirectNotBetween<T> directNotBetween(String tableAlias, String column, 
                                                           Object begin, Object end) {
        return directNotBetween(tableAlias, column, begin, end, Logic.AND);
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
    public static <T> DirectNotBetween<T> directNotBetween(String tableAlias, String column, Object begin,
                                                           Object end, Logic logic) {
        return DirectNotBetween.create(tableAlias, column, begin, end, logic);
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
    public static <T> DirectNotBetween<T> directNotBetween(Criteria<T> criteria, String column,
                                                           Object begin, Object end) {
        return directNotBetween(criteria, column, begin, end, Logic.AND);
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
    public static <T> DirectNotBetween<T> directNotBetween(Criteria<T> criteria, String column, Object begin,
                                                           Object end, Logic logic) {
        return DirectNotBetween.create(criteria, column, begin, end, logic);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value, Logic logic) {
        return Like.create(criteria, property, value, logic);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Character escape, Logic logic) {
        return Like.create(criteria, property, value, escape, logic);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Match match, Logic logic) {
        return Like.create(criteria, property, value, match, logic);
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
    public static <T> Like<T> like(Criteria<T> criteria, String property, String value,
                                   Match match, Character escape, Logic logic) {
        return Like.create(criteria, property, value, match, escape, logic);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value, Logic logic) {
        return NotLike.create(criteria, property, value, logic);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Character escape, Logic logic) {
        return NotLike.create(criteria, property, value, escape, logic);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Match match, Logic logic) {
        return NotLike.create(criteria, property, value, match, logic);
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
    public static <T> NotLike<T> notLike(Criteria<T> criteria, String property, String value,
                                         Match match, Character escape, Logic logic) {
        return NotLike.create(criteria, property, value, match, escape, logic);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value) {
        return directLike(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column,
                                               String value, Logic logic) {
        return DirectLike.create(tableAlias, column, value, logic);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Match match) {
        return directLike(tableAlias, column, value, match, Logic.AND);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Match match, Logic logic) {
        return DirectLike.create(tableAlias, column, value, match, logic);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Character escape) {
        return directLike(tableAlias, column, value, escape, Logic.AND);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Character escape, Logic logic) {
        return DirectLike.create(tableAlias, column, value, escape, logic);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Match match, Character escape) {
        return directLike(tableAlias, column, value, match, escape, Logic.AND);
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
    public static <T> DirectLike<T> directLike(String tableAlias, String column, String value,
                                               Match match, Character escape, Logic logic) {
        return DirectLike.create(tableAlias, column, value, match, escape, logic);
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value) {
        return directLike(criteria, column, value, Logic.AND);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column,
                                               String value, Logic logic) {
        return DirectLike.create(criteria, column, value, logic);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Match match) {
        return directLike(criteria, column, value, match, Logic.AND);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Match match, Logic logic) {
        return DirectLike.create(criteria, column, value, match, logic);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Character escape) {
        return directLike(criteria, column, value, escape, Logic.AND);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Character escape, Logic logic) {
        return DirectLike.create(criteria, column, value, escape, logic);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Match match, Character escape) {
        return directLike(criteria, column, value, match, escape, Logic.AND);
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
    public static <T> DirectLike<T> directLike(Criteria<T> criteria, String column, String value,
                                               Match match, Character escape, Logic logic) {
        return DirectLike.create(criteria, column, value, match, escape, logic);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value) {
        return directNotLike(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column,
                                                     String value, Logic logic) {
        return DirectNotLike.create(tableAlias, column, value, logic);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Match match) {
        return directNotLike(tableAlias, column, value, match, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Match match, Logic logic) {
        return DirectNotLike.create(tableAlias, column, value, match, logic);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Character escape) {
        return directNotLike(tableAlias, column, value, escape, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Character escape, Logic logic) {
        return DirectNotLike.create(tableAlias, column, value, escape, logic);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Match match, Character escape) {
        return directNotLike(tableAlias, column, value, match, escape, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(String tableAlias, String column, String value,
                                                     Match match, Character escape, Logic logic) {
        return DirectNotLike.create(tableAlias, column, value, match, escape, logic);
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, String value) {
        return directNotLike(criteria, column, value, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column,
                                                     String value, Logic logic) {
        return DirectNotLike.create(criteria, column, value, logic);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column,
                                                     String value, Match match) {
        return directNotLike(criteria, column, value, match, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, String value,
                                                     Match match, Logic logic) {
        return DirectNotLike.create(criteria, column, value, match, logic);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, 
                                                     String value, Character escape) {
        return directNotLike(criteria, column, value, escape, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, String value,
                                                     Character escape, Logic logic) {
        return DirectNotLike.create(criteria, column, value, escape, logic);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, String value,
                                                     Match match, Character escape) {
        return directNotLike(criteria, column, value, match, escape, Logic.AND);
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
    public static <T> DirectNotLike<T> directNotLike(Criteria<T> criteria, String column, String value,
                                                     Match match, Character escape, Logic logic) {
        return DirectNotLike.create(criteria, column, value, match, escape, logic);
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
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Object value,
                                              String template, Logic logic) {
        return Template.create(criteria, property, value, template, logic);
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
        return Template.create(criteria, property, value, template, logic);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Collection<Object> values,
                                              String template, Logic logic) {
        return Template.create(criteria, property, values, template, logic);
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
        return Template.create(criteria, property, values, template, logic);
    }

    /**
     * TEMPLATE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param template 模板
     * @param <T>      实体类型
     * @param <V>      值类型
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
     * @param <V>      值类型
     * @return 条件对象
     */
    public static <T, V> Template<T> template(Criteria<T> criteria, Property<T, V> property, Map<String, Object> values,
                                              String template, Logic logic) {
        return Template.create(criteria, property, values, template, logic);
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
        return Template.create(criteria, property, values, template, logic);
    }


    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Object value) {
        return directTemplate(template, value, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Object value, Logic logic) {
        return DirectTemplate.create(template, value, logic);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Collection<Object> values) {
        return directTemplate(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Collection<Object> values,
                                                       Logic logic) {
        return DirectTemplate.create(template, values, logic);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Map<String, Object> values) {
        return directTemplate(template, values, Logic.AND);
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectTemplate<T> directTemplate(String template, Map<String, Object> values,
                                                       Logic logic) {
        return DirectTemplate.create(template, values, logic);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Object value, String template) {
        return directTemplate(tableAlias, column, value, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Object value, String template, Logic logic) {
        return DirectTemplate.create(tableAlias, column, value, template, logic);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Collection<Object> values, String template) {
        return directTemplate(tableAlias, column, values, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Collection<Object> values, String template,
                                                       Logic logic) {
        return DirectTemplate.create(tableAlias, column, values, template, logic);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Map<String, Object> values, String template) {
        return directTemplate(tableAlias, column, values, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(String tableAlias, String column,
                                                       Map<String, Object> values, String template,
                                                       Logic logic) {
        return DirectTemplate.create(tableAlias, column, values, template, logic);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Object value, String template) {
        return directTemplate(criteria, column, value, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Object value, String template, Logic logic) {
        return DirectTemplate.create(criteria, column, value, template, logic);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Collection<Object> values, String template) {
        return directTemplate(criteria, column, values, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Collection<Object> values, String template,
                                                       Logic logic) {
        return DirectTemplate.create(criteria, column, values, template, logic);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Map<String, Object> values, String template) {
        return directTemplate(criteria, column, values, template, Logic.AND);
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
    public static <T> DirectTemplate<T> directTemplate(Criteria<T> criteria, String column,
                                                       Map<String, Object> values, String template,
                                                       Logic logic) {
        return DirectTemplate.create(criteria, column, values, template, logic);
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

    // region sub query

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> subQuery(Criteria<T> criteria, ColumnWrapper column, 
                                           SubCriteria<?> sc, Logic logic) {
        return SubQuery.create(criteria, column, sc, logic);
    }

    /**
     * SUB QUERY
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> subQuery(Criteria<T> criteria, String property, SubCriteria<?> sc) {
        return subQuery(criteria, property, sc, Symbol.EQ, Logic.AND);
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
    public static <T> SubQuery<T> subQuery(Criteria<T> criteria, String property, SubCriteria<?> sc, Logic logic) {
        return subQuery(criteria, property, sc, Symbol.EQ, logic);
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
    public static <T> SubQuery<T> subQuery(Criteria<T> criteria, String property, SubCriteria<?> sc, Symbol symbol) {
        return subQuery(criteria, property, sc, symbol, Logic.AND);
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
    public static <T> SubQuery<T> subQuery(Criteria<T> criteria, String property, SubCriteria<?> sc,
                                           Symbol symbol, Logic logic) {
        return SubQuery.create(criteria, property, sc, symbol, logic);
    }
    // endregion
}
