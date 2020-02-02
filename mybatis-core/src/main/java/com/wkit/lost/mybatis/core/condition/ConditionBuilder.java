package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.criteria.MatchMode;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.util.Collection;
import java.util.function.Function;

/**
 * 条件构建器
 * @param <R> Lambda类型
 * @author wvkity
 */
public interface ConditionBuilder<T, Context, R> extends LambdaResolver<R> {

    /**
     * 主键值等于
     * @param value 值
     * @return 条件管理对象
     */
    Context idEqExp( Object value );

    /**
     * 或主键等于
     * @param value 值
     * @return 条件管理对象
     */
    Context orIdEqExp( Object value );

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context eqExp( R property, Object value ) {
        return eqExp( lambdaToProperty( property ), value );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context eqExp( String property, Object value );

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orEqExp( R property, Object value ) {
        return orEqExp( lambdaToProperty( property ), value );
    }

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orEqExp( String property, Object value );

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context neExp( R property, Object value ) {
        return neExp( lambdaToProperty( property ), value );
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context neExp( String property, Object value );

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orNeExp( R property, Object value ) {
        return orNeExp( lambdaToProperty( property ), value );
    }

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orNeExp( String property, Object value );

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context ltExp( R property, Object value ) {
        return ltExp( lambdaToProperty( property ), value );
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context ltExp( String property, Object value );

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLtExp( R property, Object value ) {
        return orLtExp( lambdaToProperty( property ), value );
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orLtExp( String property, Object value );

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context leExp( R property, Object value ) {
        return leExp( lambdaToProperty( property ), value );
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context leExp( String property, Object value );

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLeExp( R property, Object value ) {
        return orLeExp( lambdaToProperty( property ), value );
    }

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orLeExp( String property, Object value );

    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context gtExp( R property, Object value ) {
        return gtExp( lambdaToProperty( property ), value );
    }

    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context gtExp( String property, Object value );

    /**
     * 或大于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orGtExp( R property, Object value ) {
        return orGtExp( lambdaToProperty( property ), value );
    }

    /**
     * 或大于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orGtExp( String property, Object value );

    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context geExp( R property, Object value ) {
        return geExp( lambdaToProperty( property ), value );
    }

    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context geExp( String property, Object value );

    /**
     * 或大于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orGeExp( R property, Object value ) {
        return orGeExp( lambdaToProperty( property ), value );
    }

    /**
     * 或大于等于
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orGeExp( String property, Object value );

    /**
     * IS NULL
     * @param property 属性
     * @return 条件管理对象
     */
    default Context isNullExp( R property ) {
        return isNullExp( lambdaToProperty( property ) );
    }

    /**
     * IS NULL
     * @param property 属性
     * @return 条件管理对象
     */
    Context isNullExp( String property );

    /**
     * OR IS NULL
     * @param property 属性
     * @return 条件管理对象
     */
    default Context orIsNullExp( R property ) {
        return orIsNullExp( lambdaToProperty( property ) );
    }

    /**
     * OR IS NULL
     * @param property 属性
     * @return 条件管理对象
     */
    Context orIsNullExp( String property );

    /**
     * IS NOT NULL
     * @param property 属性
     * @return 条件管理对象
     */
    default Context notNullExp( R property ) {
        return notNullExp( lambdaToProperty( property ) );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @return 条件管理对象
     */
    Context notNullExp( String property );

    /**
     * OR IS NOT NULL
     * @param property 属性
     * @return 条件管理对象
     */
    default Context orNotNullExp( R property ) {
        return orNotNullExp( lambdaToProperty( property ) );
    }

    /**
     * OR IS NOT NULL
     * @param property 属性
     * @return 条件管理对象
     */
    Context orNotNullExp( String property );

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context inExp( R property, Object... values ) {
        return inExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context inExp( String property, Object... values ) {
        return inExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context inExp( R property, Collection<Object> values ) {
        return inExp( lambdaToProperty( property ), values );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context inExp( String property, Collection<Object> values );

    /**
     * OR IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orInExp( R property, Object... values ) {
        return orInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * OR IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orInExp( String property, Object... values ) {
        return orInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * OR IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orInExp( R property, Collection<Object> values ) {
        return orInExp( lambdaToProperty( property ), values );
    }

    /**
     * OR IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context orInExp( String property, Collection<Object> values );

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context notInExp( R property, Object... values ) {
        return notInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context notInExp( String property, Object... values ) {
        return notInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context notInExp( R property, Collection<Object> values ) {
        return notInExp( lambdaToProperty( property ), values );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context notInExp( String property, Collection<Object> values );

    /**
     * OR NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orNotInExp( R property, Object... values ) {
        return orNotInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * OR NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orNotInExp( String property, Object... values ) {
        return orNotInExp( property, ArrayUtil.toList( values ) );
    }

    /**
     * OR NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orNotInExp( R property, Collection<Object> values ) {
        return orNotInExp( lambdaToProperty( property ), values );
    }

    /**
     * OR NOT IN
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context orNotInExp( String property, Collection<Object> values );

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeExp( R property, String value ) {
        return likeExp( property, value, MatchMode.ANYWHERE );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeExp( String property, String value ) {
        return likeExp( property, value, MatchMode.ANYWHERE );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件管理对象
     */
    default Context likeExp( R property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件管理对象
     */
    default Context likeExp( String property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件管理对象
     */
    default Context likeExp( R property, String value, MatchMode matchMode ) {
        return likeExp( property, value, matchMode, null );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件管理对象
     */
    default Context likeExp( String property, String value, MatchMode matchMode ) {
        return likeExp( property, value, matchMode, null );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 条件管理对象
     */
    default Context likeExp( R property, String value, MatchMode matchMode, Character escape ) {
        return likeExp( lambdaToProperty( property ), value, matchMode, escape );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 条件管理对象
     */
    Context likeExp( String property, String value, MatchMode matchMode, Character escape );

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeLeftExp( R property, String value ) {
        return likeExp( property, value, MatchMode.END );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeLeftExp( String property, String value ) {
        return likeExp( property, value, MatchMode.END );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context likeLeftExp( R property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.END, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context likeLeftExp( String property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.END, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeRightExp( R property, String value ) {
        return likeExp( property, value, MatchMode.START );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context likeRightExp( String property, String value ) {
        return likeExp( property, value, MatchMode.START );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context likeRightExp( R property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.START, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context likeRightExp( String property, String value, Character escape ) {
        return likeExp( property, value, MatchMode.START, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeExp( R property, String value ) {
        return orLikeExp( property, value, MatchMode.ANYWHERE );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeExp( String property, String value ) {
        return orLikeExp( property, value, MatchMode.ANYWHERE );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件管理对象
     */
    default Context orLikeExp( R property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return 条件管理对象
     */
    default Context orLikeExp( String property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.ANYWHERE, escape );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件管理对象
     */
    default Context orLikeExp( R property, String value, MatchMode matchMode ) {
        return orLikeExp( property, value, matchMode, null );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @return 条件管理对象
     */
    default Context orLikeExp( String property, String value, MatchMode matchMode ) {
        return orLikeExp( property, value, matchMode, null );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 条件管理对象
     */
    default Context orLikeExp( R property, String value, MatchMode matchMode, Character escape ) {
        return orLikeExp( lambdaToProperty( property ), value, matchMode, escape );
    }

    /**
     * 模糊匹配
     * @param property  属性
     * @param value     值
     * @param matchMode 匹配模式
     * @param escape    转义字符
     * @return 条件管理对象
     */
    Context orLikeExp( String property, String value, MatchMode matchMode, Character escape );

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeLeftExp( R property, String value ) {
        return orLikeExp( property, value, MatchMode.END );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeLeftExp( String property, String value ) {
        return orLikeExp( property, value, MatchMode.END );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context orLikeLeftExp( R property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.END, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context orLikeLeftExp( String property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.END, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeRightExp( R property, String value ) {
        return orLikeExp( property, value, MatchMode.START );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orLikeRightExp( String property, String value ) {
        return orLikeExp( property, value, MatchMode.START );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context orLikeRightExp( R property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.START, escape );
    }

    /**
     * 模糊匹配
     * @param property 属性
     * @param value    值
     * @param escape   转义字符串
     * @return 条件管理对象
     */
    default Context orLikeRightExp( String property, String value, Character escape ) {
        return orLikeExp( property, value, MatchMode.START, escape );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    default Context betweenExp( R property, Object begin, Object end ) {
        return betweenExp( lambdaToProperty( property ), begin, end );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    Context betweenExp( String property, Object begin, Object end );

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    default Context orBetweenExp( R property, Object begin, Object end ) {
        return orBetweenExp( lambdaToProperty( property ), begin, end );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    Context orBetweenExp( String property, Object begin, Object end );

    /**
     * NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    default Context notBetweenExp( R property, Object begin, Object end ) {
        return notBetweenExp( lambdaToProperty( property ), begin, end );
    }

    /**
     * NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    Context notBetweenExp( String property, Object begin, Object end );

    /**
     * OR NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    default Context orNotBetweenExp( R property, Object begin, Object end ) {
        return orNotBetweenExp( lambdaToProperty( property ), begin, end );
    }

    /**
     * OR NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 条件管理对象
     */
    Context orNotBetweenExp( String property, Object begin, Object end );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context templateExp( String template, R property, Object value ) {
        return templateExp( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context templateExp( String template, String property, Object value );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context templateExp( String template, R property, Object... values ) {
        return templateExp( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context templateExp( String template, String property, Object... values ) {
        return templateExp( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context templateExp( String template, R property, Collection<Object> values ) {
        return templateExp( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context templateExp( String template, String property, Collection<Object> values );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context orTemplateExp( String template, R property, Object value ) {
        return orTemplateExp( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context orTemplateExp( String template, String property, Object value );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orTemplateExp( String template, R property, Object... values ) {
        return orTemplateExp( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orTemplateExp( String template, String property, Object... values ) {
        return orTemplateExp( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context orTemplateExp( String template, R property, Collection<Object> values ) {
        return orTemplateExp( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context orTemplateExp( String template, String property, Collection<Object> values );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    default Context exactTemplateExp( String template, R property, Object value ) {
        return exactTemplateExp( template, lambdaToProperty( property ), value );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param value    值
     * @return 条件管理对象
     */
    Context exactTemplateExp( String template, String property, Object value );

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context exactTemplateExp( String template, R property, Object... values ) {
        return exactTemplateExp( template, lambdaToProperty( property ), ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context exactTemplateExp( String template, String property, Object... values ) {
        return exactTemplateExp( template, property, ArrayUtil.toList( values ) );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    default Context exactTemplateExp( String template, R property, Collection<Object> values ) {
        return exactTemplateExp( template, lambdaToProperty( property ), values );
    }

    /**
     * 模板条件
     * @param template 模板
     * @param property 属性
     * @param values   值
     * @return 条件管理对象
     */
    Context exactTemplateExp( String template, String property, Collection<Object> values );

    /**
     * 嵌套条件
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    default Context nesting( Criterion<?>... conditions ) {
        return nesting( Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    default Context nesting( Collection<Criterion<?>> conditions ) {
        return nesting( Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param logic      连接方式
     * @param conditions 条件数组
     * @return 条件管理对象
     */
    default Context nesting( Logic logic, Criterion<?>... conditions ) {
        return nesting( logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param logic      连接方式
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    Context nesting( Logic logic, Collection<Criterion<?>> conditions );

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件数组
     * @return 条件管理对象
     */
    default Context nesting( Criteria<T> criteria, Criterion<?>... conditions ) {
        return nesting( criteria, Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    default Context nesting( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        return nesting( criteria, Logic.AND, conditions );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件数组
     * @param logic      连接方式
     * @return 条件管理对象
     */
    default Context nesting( Criteria<T> criteria, Logic logic, Criterion<?>... conditions ) {
        return nesting( criteria, logic, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件集合
     * @param logic      连接方式
     * @return 条件管理对象
     */
    Context nesting( Criteria<T> criteria, Logic logic, Collection<Criterion<?>> conditions );

    /**
     * 嵌套条件
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    default Context orNesting( Collection<Criterion<?>> conditions ) {
        return nesting( Logic.OR, conditions );
    }

    /**
     * 嵌套条件
     * @param conditions 条件数组
     * @return 条件管理对象
     */
    default Context orNesting( Criterion<?>... conditions ) {
        return nesting( Logic.OR, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件数组
     * @return 条件管理对象
     */
    default Context orNesting( Criteria<T> criteria, Criterion<?>... conditions ) {
        return nesting( criteria, Logic.OR, ArrayUtil.toList( conditions ) );
    }

    /**
     * 嵌套条件
     * @param criteria   查询对象
     * @param conditions 条件集合
     * @return 条件管理对象
     */
    default Context orNesting( Criteria<T> criteria, Collection<Criterion<?>> conditions ) {
        return nesting( criteria, Logic.OR, conditions );
    }

    /**
     * 嵌套条件
     * @param function Lambda对象
     * @return 条件管理对象
     */
    Context nesting( Function<Context, Context> function );

    /**
     * 嵌套条件
     * @param function Lambda对象
     * @return 条件管理对象
     */
    Context orNesting( Function<Context, Context> function );
}
