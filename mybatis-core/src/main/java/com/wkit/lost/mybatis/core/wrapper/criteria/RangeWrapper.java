package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 范围条件接口
 * @param <Chain> 子类
 * @param <P>     Lambda类
 * @author wvkity
 */
public interface RangeWrapper<Chain extends RangeWrapper<Chain, P>, P> extends LambdaConverter<P> {

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain in( P property, Object... values ) {
        return in( property, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain in( String property, Object... values ) {
        return in( property, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain in( P property, Collection<Object> values ) {
        return in( lambdaToProperty( property ), values );
    }

    /**
     * IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain in( String property, Collection<Object> values );

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orIn( P property, Object... values ) {
        return orIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orIn( String property, Object... values ) {
        return orIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orIn( P property, Collection<Object> values ) {
        return orIn( lambdaToProperty( property ), values );
    }

    /**
     * 或IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain orIn( String property, Collection<Object> values );

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain immediateIn( String column, Object... values ) {
        return immediateIn( column, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain immediateIn( String column, Collection<Object> values );


    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain immediateIn( String tableAlias, String column, Object... values ) {
        return immediateIn( tableAlias, column, ArrayUtil.toList( values ) );
    }

    /**
     * IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain immediateIn( String tableAlias, String column, Collection<Object> values );

    /**
     * 或IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain orImmediateIn( String column, Object... values ) {
        return orImmediateIn( column, ArrayUtil.toList( values ) );
    }

    /**
     * 或IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain orImmediateIn( String column, Collection<Object> values );

    /**
     * 或IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain orImmediateIn( String tableAlias, String column, Object... values ) {
        return orImmediateIn( tableAlias, column, ArrayUtil.toList( values ) );
    }

    /**
     * 或IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain orImmediateIn( String tableAlias, String column, Collection<Object> values );

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain notIn( P property, Object... values ) {
        return notIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain notIn( String property, Object... values ) {
        return notIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain notIn( P property, Collection<Object> values ) {
        return notIn( lambdaToProperty( property ), values );
    }

    /**
     * NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain notIn( String property, Collection<Object> values );

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orNotIn( P property, Object... values ) {
        return orNotIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orNotIn( String property, Object... values ) {
        return orNotIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    default Chain orNotIn( P property, Collection<Object> values ) {
        return orNotIn( lambdaToProperty( property ), values );
    }

    /**
     * 或NOT IN
     * @param property 属性
     * @param values   值
     * @return {@code this}
     */
    Chain orNotIn( String property, Collection<Object> values );

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain immediateNotIn( String column, Object... values ) {
        return immediateNotIn( column, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain immediateNotIn( String column, Collection<Object> values );


    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain immediateNotIn( String tableAlias, String column, Object... values ) {
        return immediateNotIn( tableAlias, column, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain immediateNotIn( String tableAlias, String column, Collection<Object> values );

    /**
     * 或NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    default Chain orImmediateNotIn( String column, Object... values ) {
        return orImmediateNotIn( column, ArrayUtil.toList( values ) );
    }

    /**
     * 或NOT IN
     * @param column 字段
     * @param values 值
     * @return {@code this}
     */
    Chain orImmediateNotIn( String column, Collection<Object> values );

    /**
     * 或NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    default Chain orImmediateNotIn( String tableAlias, String column, Object... values ) {
        return orImmediateNotIn( tableAlias, column, ArrayUtil.toList( values ) );
    }

    /**
     * 或NOT IN
     * @param tableAlias 表别名
     * @param column     字段
     * @param values     值
     * @return {@code this}
     */
    Chain orImmediateNotIn( String tableAlias, String column, Collection<Object> values );

    /**
     * BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default Chain between( P property, Object begin, Object end ) {
        return between( lambdaToProperty( property ), begin, end );
    }

    /**
     * BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain between( String property, Object begin, Object end );

    /**
     * 或BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default Chain orBetween( P property, Object begin, Object end ) {
        return orBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * 或BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain orBetween( String property, Object begin, Object end );

    /**
     * BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain immediateBetween( String column, Object begin, Object end );

    /**
     * BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain immediateBetween( String tableAlias, String column, Object begin, Object end );

    /**
     * BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain orImmediateBetween( String column, Object begin, Object end );

    /**
     * BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain orImmediateBetween( String tableAlias, String column, Object begin, Object end );

    /**
     * NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default Chain notBetween( P property, Object begin, Object end ) {
        return notBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain notBetween( String property, Object begin, Object end );

    /**
     * 或NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    default Chain orNotBetween( P property, Object begin, Object end ) {
        return orNotBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * 或NOT BETWEEN
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return {@code this}
     */
    Chain orNotBetween( String property, Object begin, Object end );

    /**
     * NOT BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain immediateNotBetween( String column, Object begin, Object end );

    /**
     * NOT BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain immediateNotBetween( String tableAlias, String column, Object begin, Object end );

    /**
     * NOT BETWEEN
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @return {@code this}
     */
    Chain orImmediateNotBetween( String column, Object begin, Object end );

    /**
     * NOT BETWEEN
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @return {@code this}
     */
    Chain orImmediateNotBetween( String tableAlias, String column, Object begin, Object end );
    
}
