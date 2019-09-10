package com.wkit.lost.mybatis.core.condition.criterion;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.MatchMode;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.condition.expression.BetweenExpression;
import com.wkit.lost.mybatis.core.condition.expression.IdentifierExpression;
import com.wkit.lost.mybatis.core.condition.expression.InExpression;
import com.wkit.lost.mybatis.core.condition.expression.LikeExpression;
import com.wkit.lost.mybatis.core.condition.expression.NativeExpression;
import com.wkit.lost.mybatis.core.condition.expression.NestedExpression;
import com.wkit.lost.mybatis.core.condition.expression.NotBetweenExpression;
import com.wkit.lost.mybatis.core.condition.expression.NotInExpression;
import com.wkit.lost.mybatis.core.condition.expression.NotNullExpression;
import com.wkit.lost.mybatis.core.condition.expression.NullExpression;
import com.wkit.lost.mybatis.core.condition.expression.SimpleExpression;
import com.wkit.lost.mybatis.core.condition.expression.TemplateExpression;

import java.util.Collection;

/**
 * 条件工具类
 * @author DT
 */
public abstract class Restrictions {

    /**
     * 主键等于
     * @param value 值
     * @param <T>   泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierExpression<T> idEq( Object value ) {
        return idEq( value, Logic.AND );
    }

    /**
     * 主键等于
     * @param value 值
     * @param logic 逻辑操作
     * @param <T>   泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierExpression<T> idEq( Object value, Logic logic ) {
        return new IdentifierExpression<>( value, logic );
    }

    /**
     * 主键等于
     * @param criteria 查询对象
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierExpression<T> idEq( Criteria<T> criteria, Object value ) {
        return idEq( criteria, value, Logic.AND );
    }

    /**
     * 主键等于
     * @param criteria 查询对象
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> IdentifierExpression<T> idEq( Criteria<T> criteria, Object value, Logic logic ) {
        return new IdentifierExpression<>( criteria, value, logic );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> eq( String property, Object value ) {
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
    public static <T> SimpleExpression<T> eq( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, logic );
    }

    /**
     * 等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> eq( Criteria<T> criteria, String property, Object value ) {
        return eq( criteria, property, value, Logic.AND );
    }

    /**
     * 等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> eq( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, logic );
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ne( String property, Object value ) {
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
    public static <T> SimpleExpression<T> ne( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, Operator.NE, logic );
    }

    /**
     * 不等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ne( Criteria<T> criteria, String property, Object value ) {
        return ne( criteria, property, value, Logic.AND );
    }

    /**
     * 不等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ne( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, Operator.NE, logic );
    }

    /**
     * 小于条件
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> lt( String property, Object value ) {
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
    public static <T> SimpleExpression<T> lt( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, Operator.LT, logic );
    }

    /**
     * 小于条件
     * @param criteria 查询条件
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> lt( Criteria<T> criteria, String property, Object value ) {
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
    public static <T> SimpleExpression<T> lt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, Operator.LT, logic );
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> le( String property, Object value ) {
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
    public static <T> SimpleExpression<T> le( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, Operator.LE, logic );
    }

    /**
     * 小于等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> le( Criteria<T> criteria, String property, Object value ) {
        return le( criteria, property, value, Logic.AND );
    }

    /**
     * 小于等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> le( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, Operator.LE, logic );
    }

    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> gt( String property, Object value ) {
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
    public static <T> SimpleExpression<T> gt( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, Operator.GT, logic );
    }

    /**
     * 大于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> gt( Criteria<T> criteria, String property, Object value ) {
        return gt( criteria, property, value, Logic.AND );
    }

    /**
     * 大于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> gt( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, Operator.GT, logic );
    }

    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ge( String property, Object value ) {
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
    public static <T> SimpleExpression<T> ge( String property, Object value, Logic logic ) {
        return new SimpleExpression<>( property, value, Operator.GE, logic );
    }

    /**
     * 大于等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ge( Criteria<T> criteria, String property, Object value ) {
        return ge( criteria, property, value, Logic.AND );
    }

    /**
     * 大于等于
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> SimpleExpression<T> ge( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return new SimpleExpression<>( criteria, property, value, Operator.GE, logic );
    }

    /**
     * IS NULL
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NullExpression<T> isNull( String property ) {
        return isNull( property, Logic.AND );
    }

    /**
     * IS NULL
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NullExpression<T> isNull( String property, Logic logic ) {
        return new NullExpression<>( property, logic );
    }

    /**
     * IS NULL
     * @param criteria 查询对象
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NullExpression<T> isNull( Criteria<T> criteria, String property ) {
        return isNull( criteria, property, Logic.AND );
    }

    /**
     * IS NULL
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NullExpression<T> isNull( Criteria<T> criteria, String property, Logic logic ) {
        return new NullExpression<>( criteria, property, logic );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNullExpression<T> notNull( String property ) {
        return new NotNullExpression<>( property, Logic.AND );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNullExpression<T> notNull( String property, Logic logic ) {
        return new NotNullExpression<>( property, logic );
    }

    /**
     * IS NOT NULL
     * @param criteria 查询对象
     * @param property 属性
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNullExpression<T> notNull( Criteria<T> criteria, String property ) {
        return notNull( criteria, property, Logic.AND );
    }

    /**
     * IS NOT NULL
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotNullExpression<T> notNull( Criteria<T> criteria, String property, Logic logic ) {
        return new NotNullExpression<>( criteria, property, logic );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( String property, Object... values ) {
        return in( property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( String property, Collection<Object> values ) {
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
    public static <T> InExpression<T> in( String property, Logic logic, Object... values ) {
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
    public static <T> InExpression<T> in( String property, Logic logic, Collection<Object> values ) {
        return new InExpression<>( property, values, logic );
    }

    /**
     * IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( Criteria<T> criteria, String property, Object... values ) {
        return in( criteria, property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( Criteria<T> criteria, String property, Collection<Object> values ) {
        return in( criteria, property, Logic.AND, values );
    }

    /**
     * IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( Criteria<T> criteria, String property, Logic logic, Object... values ) {
        return in( criteria, property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> InExpression<T> in( Criteria<T> criteria, String property, Logic logic, Collection<Object> values ) {
        return new InExpression<>( criteria, property, values, logic );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( String property, Object... values ) {
        return notIn( property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( String property, Collection<Object> values ) {
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
    public static <T> NotInExpression<T> notIn( String property, Logic logic, Object... values ) {
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
    public static <T> NotInExpression<T> notIn( String property, Logic logic, Collection<Object> values ) {
        return new NotInExpression<>( property, values, logic );
    }

    /**
     * NOT IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( Criteria<T> criteria, String property, Object... values ) {
        return notIn( criteria, property, Logic.AND, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( Criteria<T> criteria, String property, Collection<Object> values ) {
        return notIn( criteria, property, Logic.AND, values );
    }

    /**
     * NOT IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( Criteria<T> criteria, String property, Logic logic, Object... values ) {
        return notIn( criteria, property, logic, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotInExpression<T> notIn( Criteria<T> criteria, String property, Logic logic, Collection<Object> values ) {
        return new NotInExpression<>( criteria, property, values, logic );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( String property, String value ) {
        return like( property, value, MatchMode.ANYWHERE );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( String property, String value, Logic logic ) {
        return like( property, value, MatchMode.ANYWHERE, logic );
    }

    /**
     * LIKE条件
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( String property, String value, Character escape ) {
        return like( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * LIKE条件
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( String property, String value, MatchMode matchMode ) {
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
    public static <T> LikeExpression<T> like( String property, String value, Character escape, Logic logic ) {
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
    public static <T> LikeExpression<T> like( String property, String value, MatchMode matchMode, Logic logic ) {
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
    public static <T> LikeExpression<T> like( String property, String value, MatchMode matchMode, Character escape ) {
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
    public static <T> LikeExpression<T> like( String property, String value, MatchMode matchMode, Character escape, Logic logic ) {
        return new LikeExpression<>( property, value, matchMode, escape, logic );
    }

    /**
     * LIKE条件
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value ) {
        return like( criteria, property, value, MatchMode.ANYWHERE );
    }

    /**
     * LIKE条件
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, Logic logic ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, logic );
    }

    /**
     * LIKE条件
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, Character escape ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * LIKE条件
     * @param criteria  查询对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode ) {
        return like( criteria, property, value, matchMode, null, Logic.AND );
    }

    /**
     * LIKE条件
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param escape   转移字符
     * @param logic    连接方式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, Character escape, Logic logic ) {
        return like( criteria, property, value, MatchMode.ANYWHERE, escape, logic );
    }

    /**
     * LIKE条件
     * @param criteria  查询对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Logic logic ) {
        return like( criteria, property, value, matchMode, null, logic );
    }

    /**
     * LIKE条件
     * @param criteria  查询对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Character escape ) {
        return like( criteria, property, value, matchMode, escape, Logic.AND );
    }

    /**
     * LIKE条件
     * @param criteria  查询对象
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转移字符
     * @param logic     连接方式
     * @return 条件对象
     */
    public static <T> LikeExpression<T> like( Criteria<T> criteria, String property, String value, MatchMode matchMode, Character escape, Logic logic ) {
        return new LikeExpression<>( criteria, property, value, matchMode, escape, logic );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> BetweenExpression<T> between( String property, Object begin, Object end ) {
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
    public static <T> BetweenExpression<T> between( String property, Object begin, Object end, Logic logic ) {
        return new BetweenExpression<>( property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> BetweenExpression<T> between( Criteria<T> criteria, String property, Object begin, Object end ) {
        return between( criteria, property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> BetweenExpression<T> between( Criteria<T> criteria, String property, Object begin, Object end, Logic logic ) {
        return new BetweenExpression<>( criteria, property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetweenExpression<T> notBetween( String property, Object begin, Object end ) {
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
    public static <T> NotBetweenExpression<T> notBetween( String property, Object begin, Object end, Logic logic ) {
        return new NotBetweenExpression<>( property, begin, end, logic );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetweenExpression<T> notBetween( Criteria<T> criteria, String property, Object begin, Object end ) {
        return notBetween( criteria, property, begin, end, Logic.AND );
    }

    /**
     * BETWEEN条件
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> NotBetweenExpression<T> notBetween( Criteria<T> criteria, String property, Object begin, Object end, Logic logic ) {
        return new NotBetweenExpression<>( criteria, property, begin, end, logic );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( String template, String property, Object value ) {
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
    public static <T> TemplateExpression<T> template( String template, Logic logic, String property, Object value ) {
        return new TemplateExpression<>( template, logic, property, value );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, String template, String property, Object value ) {
        return template( criteria, template, Logic.AND, property, value );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, Logic logic, String template, String property, Object value ) {
        return new TemplateExpression<>( criteria, template, logic, property, value );
    }

    /**
     * 自定义模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( String template, String property, Object... values ) {
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
    public static <T> TemplateExpression<T> template( String template, Logic logic, String property, Object... values ) {
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
    public static <T> TemplateExpression<T> template( String template, String property, Collection<Object> values ) {
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
    public static <T> TemplateExpression<T> template( String template, Logic logic, String property, Collection<Object> values ) {
        return new TemplateExpression<>( template, logic, property, values );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, String template, String property, Object... values ) {
        return template( criteria, template, Logic.AND, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, String template, Logic logic, String property, Object... values ) {
        return template( criteria, template, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, String template, String property, Collection<Object> values ) {
        return template( criteria, template, Logic.AND, property, values );
    }

    /**
     * 自定义模板条件
     * @param criteria 查询对象
     * @param template 模板
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> TemplateExpression<T> template( Criteria<T> criteria, String template, Logic logic, String property, Collection<Object> values ) {
        return new TemplateExpression<>( criteria, template, logic, property, values );
    }

    /**
     * 嵌套条件
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Criterion<?>... conditions ) {
        return nested( ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Collection<Criterion<?>> conditions ) {
        return nested( Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param logic      逻辑操作
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Logic logic, Criterion<?>... conditions ) {
        return nested( logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param logic      逻辑操作
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Logic logic, Collection<Criterion<?>> conditions ) {
        return new NestedExpression<>( logic, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Criteria<T> criteria, Criterion<?>... conditions ) {
        return nested( criteria, Logic.AND, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        return nested( criteria, Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param logic      逻辑操作
     * @param conditions 条件对象数组
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Criteria<T> criteria, Logic logic, Criterion<?>... conditions ) {
        return nested( criteria, logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param logic      逻辑操作
     * @param conditions 条件对象集合
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> NestedExpression<T> nested( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions ) {
        return new NestedExpression<>( criteria, logic, conditions );
    }

    /**
     * 纯SQL条件
     * @param sql SQL片段
     * @param <T> 泛型类型
     * @return 条件对象
     */
    public static <T> NativeExpression<T> nativeSql( String sql ) {
        return new NativeExpression<>( sql );
    }
}
