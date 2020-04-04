package com.wkit.lost.mybatis.core.conditional;

import com.wkit.lost.mybatis.core.conditional.expression.Between;
import com.wkit.lost.mybatis.core.conditional.expression.Equal;
import com.wkit.lost.mybatis.core.conditional.expression.GreaterThan;
import com.wkit.lost.mybatis.core.conditional.expression.GreaterThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.IdEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateBetween;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateGreaterThan;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateGreaterThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateIn;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateLessThan;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateLessThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateLike;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateNotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateNotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateNotIn;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateNotLike;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateNull;
import com.wkit.lost.mybatis.core.conditional.expression.ImmediateTemplate;
import com.wkit.lost.mybatis.core.conditional.expression.In;
import com.wkit.lost.mybatis.core.conditional.expression.LessThan;
import com.wkit.lost.mybatis.core.conditional.expression.LessThanOrEqual;
import com.wkit.lost.mybatis.core.conditional.expression.Like;
import com.wkit.lost.mybatis.core.conditional.expression.NotBetween;
import com.wkit.lost.mybatis.core.conditional.expression.NotEqual;
import com.wkit.lost.mybatis.core.conditional.expression.NotIn;
import com.wkit.lost.mybatis.core.conditional.expression.NotLike;
import com.wkit.lost.mybatis.core.conditional.expression.Null;
import com.wkit.lost.mybatis.core.conditional.expression.Template;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

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
    public static <T> IdEqual<T> idEq( Criteria<T> criteria, Object value ) {
        return IdEqual.create( criteria, value );
    }

    /**
     * ID等于
     * @param criteria 条件包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> IdEqual<T> idEq( Criteria<T> criteria, Object value, Logic logic ) {
        return IdEqual.create( criteria, value, logic );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
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
    public static <T> Equal<T> eq( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return Equal.create( criteria, property, value, logic );
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
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return Equal.create( criteria, property, value, logic );
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String column, Object value ) {
        return immediateEq( column, value, Logic.AND );
    }

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String column, Object value, Logic logic ) {
        return ImmediateEqual.create( column, value, logic );
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( String tableAlias, String column, Object value ) {
        return immediateEq( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateEqual<T> immediateEq( String tableAlias, String column,
                                                     Object value, Logic logic ) {
        return ImmediateEqual.create( tableAlias, column, value, logic );
    }

    /**
     * 等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateEqual<T> immediateEq( Criteria<T> criteria, String column, Object value ) {
        return immediateEq( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateEqual<T> immediateEq( Criteria<T> criteria, String column,
                                                     Object value, Logic logic ) {
        return ImmediateEqual.create( criteria, column, value, logic );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
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
    public static <T> NotEqual<T> ne( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return NotEqual.create( criteria, property, value, logic );
    }

    /**
     * 不等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
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
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return NotEqual.create( criteria, property, value, logic );
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> immediateNe( String column, Object value ) {
        return immediateNe( column, value, Logic.AND );
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> immediateNe( String column, Object value, Logic logic ) {
        return ImmediateNotEqual.create( column, value, logic );
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> immediateNe( String tableAlias, String column, Object value ) {
        return immediateNe( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateNotEqual<T> immediateNe( String tableAlias, String column,
                                                        Object value, Logic logic ) {
        return ImmediateNotEqual.create( tableAlias, column, value, logic );
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> immediateNe( Criteria<T> criteria, String column, Object value ) {
        return immediateNe( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateNotEqual<T> immediateNe( Criteria<T> criteria, String column,
                                                        Object value, Logic logic ) {
        return ImmediateNotEqual.create( criteria, column, value, logic );
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return lt( criteria, property, value, Logic.AND );
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
    public static <T> LessThan<T> lt( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return LessThan.create( criteria, property, value, logic );
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( Criteria<T> criteria, String property, Object value ) {
        return lt( criteria, property, value, Logic.AND );
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
    public static <T> LessThan<T> lt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return LessThan.create( criteria, property, value, logic );
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> immediateLt( String column, Object value ) {
        return immediateLt( column, value, Logic.AND );
    }

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> immediateLt( String column, Object value, Logic logic ) {
        return ImmediateLessThan.create( column, value, logic );
    }

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> immediateLt( String tableAlias, String column, Object value ) {
        return immediateLt( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateLessThan<T> immediateLt( String tableAlias, String column,
                                                        Object value, Logic logic ) {
        return ImmediateLessThan.create( tableAlias, column, value, logic );
    }

    /**
     * 小于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> immediateLt( Criteria<T> criteria, String column, Object value ) {
        return immediateLt( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateLessThan<T> immediateLt( Criteria<T> criteria, String column,
                                                        Object value, Logic logic ) {
        return ImmediateLessThan.create( criteria, column, value, logic );
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return le( criteria, property, value, Logic.AND );
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
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, Property<T, ?> property,
                                             Object value, Logic logic ) {
        return LessThanOrEqual.create( criteria, property, value, logic );
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, String property, Object value ) {
        return le( criteria, property, value, Logic.AND );
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
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return LessThanOrEqual.create( criteria, property, value, logic );
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( String column, Object value ) {
        return immediateLe( column, value, Logic.AND );
    }

    /**
     * 小于或等于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( String column, Object value, Logic logic ) {
        return ImmediateLessThanOrEqual.create( column, value, logic );
    }

    /**
     * 小于或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( String tableAlias, String column, Object value ) {
        return immediateLe( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( String tableAlias, String column,
                                                               Object value, Logic logic ) {
        return ImmediateLessThanOrEqual.create( tableAlias, column, value, logic );
    }

    /**
     * 小于或等于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( Criteria<T> criteria, String column, Object value ) {
        return immediateLe( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateLessThanOrEqual<T> immediateLe( Criteria<T> criteria, String column,
                                                               Object value, Logic logic ) {
        return ImmediateLessThanOrEqual.create( criteria, column, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return gt( criteria, property, value, Logic.AND );
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
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return GreaterThan.create( criteria, property, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, String property, Object value ) {
        return gt( criteria, property, value, Logic.AND );
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
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return GreaterThan.create( criteria, property, value, logic );
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThan<T> immediateGt( String column, Object value ) {
        return immediateGt( column, value, Logic.AND );
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThan<T> immediateGt( String column, Object value, Logic logic ) {
        return ImmediateGreaterThan.create( column, value, logic );
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThan<T> immediateGt( String tableAlias, String column, Object value ) {
        return immediateGt( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateGreaterThan<T> immediateGt( String tableAlias, String column,
                                                           Object value, Logic logic ) {
        return ImmediateGreaterThan.create( tableAlias, column, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThan<T> immediateGt( Criteria<T> criteria, String column, Object value ) {
        return immediateGt( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateGreaterThan<T> immediateGt( Criteria<T> criteria, String column,
                                                           Object value, Logic logic ) {
        return ImmediateGreaterThan.create( criteria, column, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return ge( criteria, property, value, Logic.AND );
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
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return GreaterThanOrEqual.create( criteria, property, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, String property, Object value ) {
        return ge( criteria, property, value, Logic.AND );
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
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return GreaterThanOrEqual.create( criteria, property, value, logic );
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( String column, Object value ) {
        return immediateGe( column, value, Logic.AND );
    }

    /**
     * 大于
     * @param column 字段
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( String column, Object value, Logic logic ) {
        return ImmediateGreaterThanOrEqual.create( column, value, logic );
    }

    /**
     * 大于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( String tableAlias, String column, Object value ) {
        return immediateGe( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( String tableAlias, String column,
                                                                  Object value, Logic logic ) {
        return ImmediateGreaterThanOrEqual.create( tableAlias, column, value, logic );
    }

    /**
     * 大于
     * @param criteria 条件包装对象
     * @param column   字段
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( Criteria<T> criteria, String column, Object value ) {
        return immediateGe( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateGreaterThanOrEqual<T> immediateGe( Criteria<T> criteria, String column,
                                                                  Object value, Logic logic ) {
        return ImmediateGreaterThanOrEqual.create( criteria, column, value, logic );
    }

    // endregion

    // region empty expression

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, Property<T, ?> property ) {
        return isNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, Property<T, ?> property, Logic logic ) {
        return Null.create( criteria, property, logic );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, String property ) {
        return isNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, String property, Logic logic ) {
        return Null.create( criteria, property, logic );
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateIsNull( String tableAlias, String column ) {
        return immediateIsNull( tableAlias, column, Logic.AND );
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateIsNull( String tableAlias, String column, Logic logic ) {
        return ImmediateNull.create( tableAlias, column, logic );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateIsNull( Criteria<T> criteria, String column ) {
        return immediateIsNull( criteria, column, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateIsNull( Criteria<T> criteria, String column, Logic logic ) {
        return ImmediateNull.create( criteria, column, logic );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull( Criteria<T> criteria, Property<T, ?> property ) {
        return notNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull( Criteria<T> criteria, Property<T, ?> property, Logic logic ) {
        return Null.create( criteria, property, logic );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull( Criteria<T> criteria, String property ) {
        return notNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param property 属性
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Null<T> notNull( Criteria<T> criteria, String property, Logic logic ) {
        return Null.create( criteria, property, logic );
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateNotNull( String tableAlias, String column ) {
        return immediateNotNull( tableAlias, column, Logic.AND );
    }

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateNotNull( String tableAlias, String column, Logic logic ) {
        return ImmediateNull.create( tableAlias, column, logic );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateNotNull( Criteria<T> criteria, String column ) {
        return immediateNotNull( criteria, column, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNull<T> immediateNotNull( Criteria<T> criteria, String column, Logic logic ) {
        return ImmediateNull.create( criteria, column, logic );
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
    public static <T> In<T> in( Criteria<T> criteria, String property, Collection<Object> values ) {
        return in( criteria, property, values, Logic.AND );
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
    public static <T> In<T> in( Criteria<T> criteria, String property, Collection<Object> values, Logic logic ) {
        return In.create( criteria, property, values, logic );
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateIn<T> immediateIn( String column, Collection<Object> values ) {
        return immediateIn( column, values, Logic.AND );
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateIn<T> immediateIn( String column, Collection<Object> values, Logic logic ) {
        return ImmediateIn.create( column, values, logic );
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateIn<T> immediateIn( String tableAlias, String column, Collection<Object> values ) {
        return immediateIn( tableAlias, column, values, Logic.AND );
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
    public static <T> ImmediateIn<T> immediateIn( String tableAlias, String column,
                                                  Collection<Object> values, Logic logic ) {
        return ImmediateIn.create( tableAlias, column, values, logic );
    }

    /**
     * IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateIn<T> immediateIn( Criteria<T> criteria, String column,
                                                  Collection<Object> values ) {
        return immediateIn( criteria, column, values, Logic.AND );
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
    public static <T> ImmediateIn<T> immediateIn( Criteria<T> criteria, String column,
                                                  Collection<Object> values, Logic logic ) {
        return ImmediateIn.create( criteria, column, values, logic );
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property, Collection<Object> values ) {
        return notIn( criteria, property, values, Logic.AND );
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
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property,
                                      Collection<Object> values, Logic logic ) {
        return NotIn.create( criteria, property, values, logic );
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotIn<T> immediateNotIn( String column, Collection<Object> values ) {
        return immediateNotIn( column, values, Logic.AND );
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotIn<T> immediateNotIn( String column, Collection<Object> values, Logic logic ) {
        return ImmediateNotIn.create( column, values, logic );
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotIn<T> immediateNotIn( String tableAlias, String column,
                                                        Collection<Object> values ) {
        return immediateNotIn( tableAlias, column, values, Logic.AND );
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
    public static <T> ImmediateNotIn<T> immediateNotIn( String tableAlias, String column,
                                                        Collection<Object> values, Logic logic ) {
        return ImmediateNotIn.create( tableAlias, column, values, logic );
    }

    /**
     * NOT IN
     * @param criteria 条件包装对象
     * @param column   字段
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotIn<T> immediateNotIn( Criteria<T> criteria, String column,
                                                        Collection<Object> values ) {
        return immediateNotIn( criteria, column, values, Logic.AND );
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
    public static <T> ImmediateNotIn<T> immediateNotIn( Criteria<T> criteria, String column,
                                                        Collection<Object> values, Logic logic ) {
        return ImmediateNotIn.create( criteria, column, values, logic );
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
    public static <T> Between<T> between( Criteria<T> criteria, Property<T, ?> property, Object begin, Object end ) {
        return between( criteria, property, begin, end, Logic.AND );
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
    public static <T> Between<T> between( Criteria<T> criteria, Property<T, ?> property, Object begin,
                                          Object end, Logic logic ) {
        return Between.create( criteria, property, begin, end, logic );
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
    public static <T> Between<T> between( Criteria<T> criteria, String property, Object begin, Object end ) {
        return between( criteria, property, begin, end, Logic.AND );
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
    public static <T> Between<T> between( Criteria<T> criteria, String property, Object begin,
                                          Object end, Logic logic ) {
        return Between.create( criteria, property, begin, end, logic );
    }

    /**
     * BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateBetween<T> immediateBetween( String column, Object begin, Object end ) {
        return immediateBetween( column, begin, end, Logic.AND );
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
    public static <T> ImmediateBetween<T> immediateBetween( String column, Object begin,
                                                            Object end, Logic logic ) {
        return ImmediateBetween.create( column, begin, end, logic );
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
    public static <T> ImmediateBetween<T> immediateBetween( String tableAlias, String column,
                                                            Object begin, Object end ) {
        return immediateBetween( tableAlias, column, begin, end, Logic.AND );
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
    public static <T> ImmediateBetween<T> immediateBetween( String tableAlias, String column, Object begin,
                                                            Object end, Logic logic ) {
        return ImmediateBetween.create( tableAlias, column, begin, end, logic );
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
    public static <T> ImmediateBetween<T> immediateBetween( Criteria<T> criteria, String column, Object begin, Object end ) {
        return immediateBetween( criteria, column, begin, end, Logic.AND );
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
    public static <T> ImmediateBetween<T> immediateBetween( Criteria<T> criteria, String column, Object begin,
                                                            Object end, Logic logic ) {
        return ImmediateBetween.create( criteria, column, begin, end, logic );
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
    public static <T> NotBetween<T> notBetween( Criteria<T> criteria, Property<T, ?> property, Object begin,
                                                Object end, Logic logic ) {
        return NotBetween.create( criteria, property, begin, end, logic );
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
    public static <T> NotBetween<T> notBetween( Criteria<T> criteria, String property, Object begin, Object end ) {
        return notBetween( criteria, property, begin, end, Logic.AND );
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
    public static <T> NotBetween<T> notBetween( Criteria<T> criteria, String property, Object begin,
                                                Object end, Logic logic ) {
        return NotBetween.create( criteria, property, begin, end, logic );
    }

    /**
     * NOT BETWEEN AND
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> immediateNotBetween( String column, Object begin, Object end ) {
        return immediateNotBetween( column, begin, end, Logic.AND );
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
    public static <T> ImmediateNotBetween<T> immediateNotBetween( String column, Object begin,
                                                                  Object end, Logic logic ) {
        return ImmediateNotBetween.create( column, begin, end, logic );
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
    public static <T> ImmediateNotBetween<T> immediateNotBetween( String tableAlias, String column,
                                                                  Object begin, Object end ) {
        return immediateNotBetween( tableAlias, column, begin, end, Logic.AND );
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
    public static <T> ImmediateNotBetween<T> immediateNotBetween( String tableAlias, String column, Object begin,
                                                                  Object end, Logic logic ) {
        return ImmediateNotBetween.create( tableAlias, column, begin, end, logic );
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
    public static <T> ImmediateNotBetween<T> immediateNotBetween( Criteria<T> criteria, String column,
                                                                  Object begin, Object end ) {
        return immediateNotBetween( criteria, column, begin, end, Logic.AND );
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
    public static <T> ImmediateNotBetween<T> immediateNotBetween( Criteria<T> criteria, String column, Object begin,
                                                                  Object end, Logic logic ) {
        return ImmediateNotBetween.create( criteria, column, begin, end, logic );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value ) {
        return like( criteria, property, value, Logic.AND );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return Like.create( criteria, property, value, logic );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value, Character escape ) {
        return like( criteria, property, value, escape, Logic.AND );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value,
                                    Character escape, Logic logic ) {
        return Like.create( criteria, property, value, escape, logic );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value, Match match ) {
        return like( criteria, property, value, match, Logic.AND );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value,
                                    Match match, Logic logic ) {
        return Like.create( criteria, property, value, match, logic );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value,
                                    Match match, Character escape ) {
        return like( criteria, property, value, match, escape, Logic.AND );
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
    public static <T> Like<T> like( Criteria<T> criteria, String property, Object value,
                                    Match match, Character escape, Logic logic ) {
        return Like.create( criteria, property, value, match, escape, logic );
    }


    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value ) {
        return notLike( criteria, property, value, Logic.AND );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return NotLike.create( criteria, property, value, logic );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value, Character escape ) {
        return notLike( criteria, property, value, escape, Logic.AND );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value,
                                          Character escape, Logic logic ) {
        return NotLike.create( criteria, property, value, escape, logic );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value, Match match ) {
        return notLike( criteria, property, value, match, Logic.AND );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value,
                                          Match match, Logic logic ) {
        return NotLike.create( criteria, property, value, match, logic );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value,
                                          Match match, Character escape ) {
        return notLike( criteria, property, value, match, escape, Logic.AND );
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
    public static <T> NotLike<T> notLike( Criteria<T> criteria, String property, Object value,
                                          Match match, Character escape, Logic logic ) {
        return NotLike.create( criteria, property, value, match, escape, logic );
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value ) {
        return immediateLike( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column,
                                                      Object value, Logic logic ) {
        return ImmediateLike.create( tableAlias, column, value, logic );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Match match ) {
        return immediateLike( tableAlias, column, value, match, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Match match, Logic logic ) {
        return ImmediateLike.create( tableAlias, column, value, match, logic );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Character escape ) {
        return immediateLike( tableAlias, column, value, escape, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Character escape, Logic logic ) {
        return ImmediateLike.create( tableAlias, column, value, escape, logic );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Match match, Character escape ) {
        return immediateLike( tableAlias, column, value, match, escape, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( String tableAlias, String column, Object value,
                                                      Match match, Character escape, Logic logic ) {
        return ImmediateLike.create( tableAlias, column, value, match, escape, logic );
    }

    /**
     * LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value ) {
        return immediateLike( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column,
                                                      Object value, Logic logic ) {
        return ImmediateLike.create( criteria, column, value, logic );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Match match ) {
        return immediateLike( criteria, column, value, match, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Match match, Logic logic ) {
        return ImmediateLike.create( criteria, column, value, match, logic );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Character escape ) {
        return immediateLike( criteria, column, value, escape, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Character escape, Logic logic ) {
        return ImmediateLike.create( criteria, column, value, escape, logic );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Match match, Character escape ) {
        return immediateLike( criteria, column, value, match, escape, Logic.AND );
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
    public static <T> ImmediateLike<T> immediateLike( Criteria<T> criteria, String column, Object value,
                                                      Match match, Character escape, Logic logic ) {
        return ImmediateLike.create( criteria, column, value, match, escape, logic );
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value ) {
        return immediateNotLike( tableAlias, column, value, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column,
                                                            Object value, Logic logic ) {
        return ImmediateNotLike.create( tableAlias, column, value, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Match match ) {
        return immediateNotLike( tableAlias, column, value, match, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Match match, Logic logic ) {
        return ImmediateNotLike.create( tableAlias, column, value, match, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Character escape ) {
        return immediateNotLike( tableAlias, column, value, escape, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Character escape, Logic logic ) {
        return ImmediateNotLike.create( tableAlias, column, value, escape, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Match match, Character escape ) {
        return immediateNotLike( tableAlias, column, value, match, escape, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( String tableAlias, String column, Object value,
                                                            Match match, Character escape, Logic logic ) {
        return ImmediateNotLike.create( tableAlias, column, value, match, escape, logic );
    }

    /**
     * NOT LIKE
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value ) {
        return immediateNotLike( criteria, column, value, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column,
                                                            Object value, Logic logic ) {
        return ImmediateNotLike.create( criteria, column, value, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Match match ) {
        return immediateNotLike( criteria, column, value, match, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Match match, Logic logic ) {
        return ImmediateNotLike.create( criteria, column, value, match, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Character escape ) {
        return immediateNotLike( criteria, column, value, escape, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Character escape, Logic logic ) {
        return ImmediateNotLike.create( criteria, column, value, escape, logic );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Match match, Character escape ) {
        return immediateNotLike( criteria, column, value, match, escape, Logic.AND );
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
    public static <T> ImmediateNotLike<T> immediateNotLike( Criteria<T> criteria, String column, Object value,
                                                            Match match, Character escape, Logic logic ) {
        return ImmediateNotLike.create( criteria, column, value, match, escape, logic );
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
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property,
                                            Object value, String template ) {
        return template( criteria, property, value, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property, Object value,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, value, template, logic );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property, Object value, String template ) {
        return template( criteria, property, value, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property, Object value,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, value, template, logic );
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
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property,
                                            Collection<Object> values, String template ) {
        return template( criteria, property, values, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property, Collection<Object> values,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, values, template, logic );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property,
                                            Collection<Object> values, String template ) {
        return template( criteria, property, values, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property, Collection<Object> values,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, values, template, logic );
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
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property,
                                            Map<String, Object> values, String template ) {
        return template( criteria, property, values, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, Property<T, ?> property, Map<String, Object> values,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, values, template, logic );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property,
                                            Map<String, Object> values, String template ) {
        return template( criteria, property, values, template, Logic.AND );
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
    public static <T> Template<T> template( Criteria<T> criteria, String property, Map<String, Object> values,
                                            String template, Logic logic ) {
        return Template.create( criteria, property, values, template, logic );
    }


    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Object value ) {
        return immediateTemplate( template, value, Logic.AND );
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Object value, Logic logic ) {
        return ImmediateTemplate.create( template, value, logic );
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Collection<Object> values ) {
        return immediateTemplate( template, values, Logic.AND );
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Collection<Object> values,
                                                              Logic logic ) {
        return ImmediateTemplate.create( template, values, logic );
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Map<String, Object> values ) {
        return immediateTemplate( template, values, Logic.AND );
    }

    /**
     * TEMPLATE
     * @param template 模板
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateTemplate<T> immediateTemplate( String template, Map<String, Object> values,
                                                              Logic logic ) {
        return ImmediateTemplate.create( template, values, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Object value, String template ) {
        return immediateTemplate( tableAlias, column, value, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Object value, String template, Logic logic ) {
        return ImmediateTemplate.create( tableAlias, column, value, template, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Collection<Object> values, String template ) {
        return immediateTemplate( tableAlias, column, values, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Collection<Object> values, String template,
                                                              Logic logic ) {
        return ImmediateTemplate.create( tableAlias, column, values, template, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Map<String, Object> values, String template ) {
        return immediateTemplate( tableAlias, column, values, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( String tableAlias, String column,
                                                              Map<String, Object> values, String template,
                                                              Logic logic ) {
        return ImmediateTemplate.create( tableAlias, column, values, template, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Object value, String template ) {
        return immediateTemplate( criteria, column, value, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Object value, String template, Logic logic ) {
        return ImmediateTemplate.create( criteria, column, value, template, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Collection<Object> values, String template ) {
        return immediateTemplate( criteria, column, values, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Collection<Object> values, String template,
                                                              Logic logic ) {
        return ImmediateTemplate.create( criteria, column, values, template, logic );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Map<String, Object> values, String template ) {
        return immediateTemplate( criteria, column, values, template, Logic.AND );
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
    public static <T> ImmediateTemplate<T> immediateTemplate( Criteria<T> criteria, String column,
                                                              Map<String, Object> values, String template,
                                                              Logic logic ) {
        return ImmediateTemplate.create( criteria, column, values, template, logic );
    }

    // endregion

}
