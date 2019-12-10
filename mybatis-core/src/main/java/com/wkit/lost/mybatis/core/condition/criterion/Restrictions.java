package com.wkit.lost.mybatis.core.condition.criterion;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.MatchMode;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.SubCriteria;
import com.wkit.lost.mybatis.core.condition.expression.Between;
import com.wkit.lost.mybatis.core.condition.expression.Equal;
import com.wkit.lost.mybatis.core.condition.expression.GreaterThan;
import com.wkit.lost.mybatis.core.condition.expression.GreaterThanOrEqual;
import com.wkit.lost.mybatis.core.condition.expression.IdentifierEqual;
import com.wkit.lost.mybatis.core.condition.expression.In;
import com.wkit.lost.mybatis.core.condition.expression.LessThan;
import com.wkit.lost.mybatis.core.condition.expression.LessThanOrEqual;
import com.wkit.lost.mybatis.core.condition.expression.Like;
import com.wkit.lost.mybatis.core.condition.expression.Native;
import com.wkit.lost.mybatis.core.condition.expression.Nested;
import com.wkit.lost.mybatis.core.condition.expression.NotBetween;
import com.wkit.lost.mybatis.core.condition.expression.NotEqual;
import com.wkit.lost.mybatis.core.condition.expression.NotIn;
import com.wkit.lost.mybatis.core.condition.expression.NotNull;
import com.wkit.lost.mybatis.core.condition.expression.Null;
import com.wkit.lost.mybatis.core.condition.expression.PropertyEqual;
import com.wkit.lost.mybatis.core.condition.expression.SubQuery;
import com.wkit.lost.mybatis.core.condition.expression.Template;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 条件工具类
 * @author wvkity
 */
public final class Restrictions {

    private Restrictions() {
    }

    // region simple

    /**
     * 主键等于
     * @param value 值
     * @param <T>   泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierEqual<T> idEq( Object value ) {
        return idEq( value, Logic.AND );
    }

    /**
     * 主键等于
     * @param value 值
     * @param logic 逻辑操作
     * @param <T>   泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierEqual<T> idEq( Object value, Logic logic ) {
        return new IdentifierEqual<>( value, logic );
    }

    /**
     * 主键等于
     * @param criteria 查询条件对象
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierEqual<T> idEq( Criteria<T> criteria, Object value ) {
        return idEq( criteria, value, Logic.AND );
    }

    /**
     * 主键等于
     * @param criteria 查询条件对象
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierEqual<T> idEq( Criteria<T> criteria, Object value, Logic logic ) {
        return new IdentifierEqual<>( criteria, value, logic );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( String property, Object value ) {
        return eq( property, value, Logic.AND );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( String property, Object value, Logic logic ) {
        return new Equal<>( property, value, logic );
    }

    /**
     * 等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Equal<T> eq( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new Equal<>( criteria, property, value, logic );
    }

    /**
     * 等于
     * @param property      属性
     * @param other         其他查询条件对象
     * @param otherProperty 其他查询条件对象属性
     * @param <T>           泛型类型
     * @return 条件对象
     */
    public static <T> PropertyEqual<T> eq( String property, Criteria<?> other, String otherProperty ) {
        return eq( property, other, otherProperty, Logic.AND );
    }

    /**
     * 等于
     * @param property      属性
     * @param other         其他查询条件对象
     * @param otherProperty 其他查询条件对象属性
     * @param logic         逻辑操作
     * @param <T>           泛型类型
     * @return 条件对象
     */
    public static <T> PropertyEqual<T> eq( String property, Criteria<?> other, String otherProperty, Logic logic ) {
        return new PropertyEqual<>( property, other, otherProperty, logic );
    }

    /**
     * 等于
     * @param criteria      查询条件对象
     * @param property      属性
     * @param other         其他查询条件对象
     * @param otherProperty 其他查询条件对象属性
     * @param <T>           泛型类型
     * @return 条件对象
     */
    public static <T> PropertyEqual<T> eq( Criteria<T> criteria, String property, Criteria<?> other, String otherProperty ) {
        return eq( criteria, property, other, otherProperty, Logic.AND );
    }

    /**
     * 等于
     * @param criteria      查询条件对象
     * @param property      属性
     * @param other         其他查询条件对象
     * @param otherProperty 其他查询条件对象属性
     * @param logic         逻辑操作
     * @param <T>           泛型类型
     * @return 条件对象
     */
    public static <T> PropertyEqual<T> eq( Criteria<T> criteria, String property, Criteria<?> other, String otherProperty, Logic logic ) {
        return new PropertyEqual<>( criteria, property, other, otherProperty, logic );
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( String property, Object value ) {
        return ne( property, value, Logic.AND );
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( String property, Object value, Logic logic ) {
        return new NotEqual<>( property, value, logic );
    }

    /**
     * 不等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
    }

    /**
     * 不等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> ne( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new NotEqual<>( criteria, property, value, logic );
    }

    /**
     * 小于条件
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( String property, Object value ) {
        return lt( property, value, Logic.AND );
    }

    /**
     * 小于条件
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( String property, Object value, Logic logic ) {
        return new LessThan<>( property, value, logic );
    }

    /**
     * 小于条件
     * @param criteria 查询条件
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( Criteria<T> criteria, String property, Object value ) {
        return lt( criteria, property, value, Logic.AND );
    }

    /**
     * 小于条件
     * @param criteria 查询条件
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThan<T> lt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new LessThan<>( criteria, property, value, logic );
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( String property, Object value ) {
        return le( property, value, Logic.AND );
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( String property, Object value, Logic logic ) {
        return new LessThanOrEqual<>( property, value, logic );
    }

    /**
     * 小于等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, String property, Object value ) {
        return le( criteria, property, value, Logic.AND );
    }

    /**
     * 小于等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> LessThanOrEqual<T> le( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new LessThanOrEqual<>( criteria, property, value, logic );
    }

    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( String property, Object value ) {
        return gt( property, value, Logic.AND );
    }

    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( String property, Object value, Logic logic ) {
        return new GreaterThan<>( property, value, logic );
    }

    /**
     * 大于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, String property, Object value ) {
        return gt( criteria, property, value, Logic.AND );
    }

    /**
     * 大于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThan<T> gt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new GreaterThan<>( criteria, property, value, logic );
    }

    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( String property, Object value ) {
        return ge( property, value, Logic.AND );
    }

    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( String property, Object value, Logic logic ) {
        return new GreaterThanOrEqual<>( property, value, logic );
    }

    /**
     * 大于等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, String property, Object value ) {
        return ge( criteria, property, value, Logic.AND );
    }

    /**
     * 大于等于
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> ge( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new GreaterThanOrEqual<>( criteria, property, value, logic );
    }

    /**
     * IS NULL
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( String property ) {
        return isNull( property, Logic.AND );
    }

    /**
     * IS NULL
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( String property, Logic logic ) {
        return new Null<>( property, logic );
    }

    /**
     * IS NULL
     * @param criteria 查询条件对象
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, String property ) {
        return isNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Null<T> isNull( Criteria<T> criteria, String property, Logic logic ) {
        return new Null<>( criteria, property, logic );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull( String property ) {
        return new NotNull<>( property, Logic.AND );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull( String property, Logic logic ) {
        return new NotNull<>( property, logic );
    }

    /**
     * IS NOT NULL
     * @param criteria 查询条件对象
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull( Criteria<T> criteria, String property ) {
        return notNull( criteria, property, Logic.AND );
    }

    /**
     * IS NOT NULL
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNull<T> notNull( Criteria<T> criteria, String property, Logic logic ) {
        return new NotNull<>( criteria, property, logic );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( String property, Object... values ) {
        return in( property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( String property, Collection<Object> values ) {
        return in( property, Logic.AND, values );
    }

    /**
     * IN范围
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( String property, Logic logic, Object... values ) {
        return in( property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( String property, Logic logic, Collection<Object> values ) {
        return new In<>( property, values, logic );
    }

    /**
     * IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( Criteria<T> criteria, String property, Object... values ) {
        return in( criteria, property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( Criteria<T> criteria, String property, Collection<Object> values ) {
        return in( criteria, property, Logic.AND, values );
    }

    /**
     * IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( Criteria<T> criteria, String property, Logic logic, Object... values ) {
        return in( criteria, property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> In<T> in( Criteria<T> criteria, String property, Logic logic, Collection<Object> values ) {
        return new In<>( criteria, property, values, logic );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( String property, Object... values ) {
        return notIn( property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( String property, Collection<Object> values ) {
        return notIn( property, Logic.AND, values );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( String property, Logic logic, Object... values ) {
        return notIn( property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( String property, Logic logic, Collection<Object> values ) {
        return new NotIn<>( property, values, logic );
    }

    /**
     * NOT IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property, Object... values ) {
        return notIn( criteria, property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property, Collection<Object> values ) {
        return notIn( criteria, property, Logic.AND, values );
    }

    /**
     * NOT IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property, Logic logic, Object... values ) {
        return notIn( criteria, property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param criteria 查询条件对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotIn<T> notIn( Criteria<T> criteria, String property, Logic logic, Collection<Object> values ) {
        return new NotIn<>( criteria, property, values, logic );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value ) {
        return like( property, value, MatchMode.ANYWHERE );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, Logic logic ) {
        return like( property, value, MatchMode.ANYWHERE, logic );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, Character escape ) {
        return like( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * LIKE条件
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, MatchMode matchMode ) {
        return like( property, value, matchMode, null, Logic.AND );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, Character escape, Logic logic ) {
        return like( property, value, MatchMode.ANYWHERE, escape, logic );
    }

    /**
     * LIKE条件
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, MatchMode matchMode, Logic logic ) {
        return like( property, value, matchMode, null, logic );
    }

    /**
     * LIKE条件
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, MatchMode matchMode, Character escape ) {
        return like( property, value, matchMode, escape, Logic.AND );
    }

    /**
     * LIKE条件
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( String property, String value, MatchMode matchMode, Character escape, Logic logic ) {
        return new Like<>( property, value, matchMode, escape, logic );
    }

    /**
     * LIKE条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value ) {
        return like( criteria, property, value, MatchMode.ANYWHERE );
    }

    /**
     * LIKE条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, Logic logic ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, logic );
    }

    /**
     * LIKE条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, Character escape ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * LIKE条件
     * @param criteria  查询条件对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode ) {
        return like( criteria, property, value, matchMode, null, Logic.AND );
    }

    /**
     * LIKE条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, Character escape, Logic logic ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, escape, logic );
    }

    /**
     * LIKE条件
     * @param criteria  查询条件对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Logic logic ) {
        return like( criteria, property, value, matchMode, null, logic );
    }

    /**
     * LIKE条件
     * @param criteria  查询条件对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Character escape ) {
        return like( criteria, property, value, matchMode, escape, Logic.AND );
    }

    /**
     * LIKE条件
     * @param criteria  查询条件对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> Like<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Character escape, Logic logic ) {
        return new Like<>( criteria, property, value, matchMode, escape, logic );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Between<T> between( String property, Object begin, Object end ) {
        return between( property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Between<T> between( String property, Object begin, Object end, Logic logic ) {
        return new Between<>( property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Between<T> between( Criteria<T> criteria, String property, Object begin, Object end ) {
        return between( criteria, property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Between<T> between( Criteria<T> criteria, String property, Object begin, Object end, Logic logic ) {
        return new Between<>( criteria, property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween( String property, Object begin, Object end ) {
        return notBetween( property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween( String property, Object begin, Object end, Logic logic ) {
        return new NotBetween<>( property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween( Criteria<T> criteria, String property, Object begin, Object end ) {
        return notBetween( criteria, property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询条件对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetween<T> notBetween( Criteria<T> criteria, String property, Object begin, Object end, Logic logic ) {
        return new NotBetween<>( criteria, property, begin, end, logic );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, String property, Object value ) {
        return template( template, Logic.AND, property, value );
    }

    /**
     * 自定义模板条件
     * @param property 属性
     * @param template 模板
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, Logic logic, String property, Object value ) {
        return new Template<>( template, logic, property, value );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, String template, String property, Object value ) {
        return template( criteria, template, Logic.AND, property, value );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, Logic logic, String template, String property, Object value ) {
        return new Template<>( criteria, template, logic, property, value );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, String property, Object... values ) {
        return template( template, Logic.AND, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, Logic logic, String property, Object... values ) {
        return template( template, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, String property, Collection<Object> values ) {
        return template( template, Logic.AND, property, values );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( String template, Logic logic, String property, Collection<Object> values ) {
        return new Template<>( template, logic, property, values );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, String template, String property, Object... values ) {
        return template( criteria, template, Logic.AND, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, String template, Logic logic, String property, Object... values ) {
        return template( criteria, template, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, String template, String property, Collection<Object> values ) {
        return template( criteria, template, Logic.AND, property, values );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询条件对象
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> Template<T> template( Criteria<T> criteria, String template, Logic logic, String property, Collection<Object> values ) {
        return new Template<>( criteria, template, logic, property, values );
    }

    /**
     * 嵌套条件
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Criterion<?>... conditions ) {
        return nested( ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Collection<Criterion<?>> conditions ) {
        return nested( Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param logic      逻辑操作
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Logic logic, Criterion<?>... conditions ) {
        return nested( logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param logic      逻辑操作
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Logic logic, Collection<Criterion<?>> conditions ) {
        return new Nested<>( logic, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询条件对象
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Criteria<T> criteria, Criterion<?>... conditions ) {
        return nested( criteria, Logic.AND, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询条件对象
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        return nested( criteria, Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询条件对象
     * @param logic      逻辑操作
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Criteria<T> criteria, Logic logic, Criterion<?>... conditions ) {
        return nested( criteria, logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询条件对象
     * @param logic      逻辑操作
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> Nested<T> nested( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions ) {
        return new Nested<>( criteria, logic, conditions );
    }

    /**
     * 纯SQL条件
     * @param sql SQL片段
     * @param <T> 泛型类型
     * @return 条件对象
     */
    public static <T> Native<T> nativeSql( String sql ) {
        return new Native<>( sql );
    }

    // endregion

    // region sub query conditions

    /**
     * 主键等于
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> idEq( SubCriteria<E> subCriteria ) {
        return idEq( subCriteria, Logic.AND );
    }

    /**
     * 主键等于
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> idEq( SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( subCriteria, logic );
    }

    /**
     * 主键等于
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> idEq( Criteria<T> criteria, SubCriteria<E> subCriteria ) {
        return idEq( criteria, subCriteria, Logic.AND );
    }

    /**
     * 主键等于
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> idEq( Criteria<T> criteria, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, subCriteria, logic );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> eq( String property, SubCriteria<E> subCriteria ) {
        return eq( property, subCriteria, Logic.AND );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> eq( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, logic );
    }

    /**
     * 等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> eq( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return eq( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> eq( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, logic );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ne( String property, SubCriteria<E> subCriteria ) {
        return ne( property, subCriteria, Logic.AND );
    }

    /**
     * 等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ne( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.NE, logic );
    }

    /**
     * 等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ne( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return ne( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ne( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.NE, logic );
    }

    /**
     * 小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> lt( String property, SubCriteria<E> subCriteria ) {
        return lt( property, subCriteria, Logic.AND );
    }

    /**
     * 小于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> lt( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.LT, logic );
    }

    /**
     * 小于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> lt( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return lt( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 小于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> lt( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.LT, logic );
    }

    /**
     * 小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> le( String property, SubCriteria<E> subCriteria ) {
        return le( property, subCriteria, Logic.AND );
    }

    /**
     * 小于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> le( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.LE, logic );
    }

    /**
     * 小于等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> le( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return le( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 小于等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> le( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.LE, logic );
    }

    /**
     * 大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> gt( String property, SubCriteria<E> subCriteria ) {
        return gt( property, subCriteria, Logic.AND );
    }

    /**
     * 大于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> gt( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.GT, logic );
    }

    /**
     * 大于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> gt( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return gt( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 大于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> gt( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.GT, logic );
    }

    /**
     * 大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ge( String property, SubCriteria<E> subCriteria ) {
        return ge( property, subCriteria, Logic.AND );
    }

    /**
     * 大于等于
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ge( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.GE, logic );
    }

    /**
     * 大于等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ge( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return ge( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * 大于等于
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> ge( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.GE, logic );
    }

    /**
     * IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> in( String property, SubCriteria<E> subCriteria ) {
        return in( property, subCriteria, Logic.AND );
    }

    /**
     * IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> in( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.IN, logic );
    }

    /**
     * IN范围
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> in( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return in( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * IN范围
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> in( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.IN, logic );
    }

    /**
     * NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notIn( String property, SubCriteria<E> subCriteria ) {
        return notIn( property, subCriteria, Logic.AND );
    }

    /**
     * NOT IN范围
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notIn( String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( property, subCriteria, Operator.NOT_IN, logic );
    }

    /**
     * NOT IN范围
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notIn( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return notIn( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * NOT IN范围
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notIn( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.NOT_IN, logic );
    }

    /**
     * EXISTS
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists( Criteria<T> criteria, SubCriteria<E> subCriteria ) {
        return exists( criteria, null, subCriteria, Logic.AND );
    }

    /**
     * EXISTS
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists( Criteria<T> criteria, SubCriteria<E> subCriteria, Logic logic ) {
        return exists( criteria, null, subCriteria, logic );
    }

    /**
     * EXISTS
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return exists( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * EXISTS
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> exists( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.EXISTS, logic );
    }

    /**
     * NOT EXISTS
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notExists( Criteria<T> criteria, SubCriteria<E> subCriteria ) {
        return notExists( criteria, null, subCriteria, Logic.AND );
    }

    /**
     * NOT EXISTS
     * @param criteria    查询条件对象
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notExists( Criteria<T> criteria, SubCriteria<E> subCriteria, Logic logic ) {
        return notExists( criteria, null, subCriteria, logic );
    }

    /**
     * NOT EXISTS
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notExists( Criteria<T> criteria, String property, SubCriteria<E> subCriteria ) {
        return notExists( criteria, property, subCriteria, Logic.AND );
    }

    /**
     * NOT EXISTS
     * @param criteria    查询条件对象
     * @param property    属性
     * @param subCriteria 子查询条件对象
     * @param logic       逻辑操作
     * @param <T>         泛型类型
     * @param <E>         子查询实体类型
     * @return 条件对象
     */
    public static <T, E> SubQuery<T> notExists( Criteria<T> criteria, String property, SubCriteria<E> subCriteria, Logic logic ) {
        return new SubQuery<>( criteria, property, subCriteria, Operator.NOT_EXISTS, logic );
    }
    // endregion
}
