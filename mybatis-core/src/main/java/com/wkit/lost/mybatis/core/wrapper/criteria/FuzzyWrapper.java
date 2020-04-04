package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.lambda.LambdaConverter;

/**
 * 模糊条件接口
 * @param <Chain> 子类
 * @param <P>     Lambda类型
 * @author wvkity
 */
public interface FuzzyWrapper<Chain extends FuzzyWrapper<Chain, P>, P> extends LambdaConverter<P> {

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain like( P property, Object value ) {
        return like( lambdaToProperty( property ), value );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain like( String property, Object value ) {
        return like( property, value, null );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain like( P property, Object value, Character escape ) {
        return like( lambdaToProperty( property ), value, escape );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain like( String property, Object value, Character escape );

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike( P property, Object value ) {
        return orLike( lambdaToProperty( property ), value );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike( String property, Object value ) {
        return orLike( property, value, null );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLike( P property, Object value, Character escape ) {
        return orLike( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLike( String property, Object value, Character escape );

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft( P property, Object value ) {
        return likeLeft( lambdaToProperty( property ), value );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft( String property, Object value ) {
        return likeLeft( property, value, null );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeLeft( P property, Object value, Character escape ) {
        return likeLeft( lambdaToProperty( property ), value, escape );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeLeft( String property, Object value, Character escape );

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft( P property, Object value ) {
        return orLikeLeft( lambdaToProperty( property ), value );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft( String property, Object value ) {
        return orLikeLeft( property, value, null );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeLeft( P property, Object value, Character escape ) {
        return orLike( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeLeft( String property, Object value, Character escape );

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight( P property, Object value ) {
        return likeRight( lambdaToProperty( property ), value );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight( String property, Object value ) {
        return likeRight( property, value, null );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeRight( P property, Object value, Character escape ) {
        return likeRight( lambdaToProperty( property ), value, escape );
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeRight( String property, Object value, Character escape );

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight( P property, Object value ) {
        return orLikeRight( lambdaToProperty( property ), value );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight( String property, Object value ) {
        return orLikeRight( property, value, null );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeRight( P property, Object value, Character escape ) {
        return orLikeRight( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeRight( String property, Object value, Character escape );

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike( P property, Object value ) {
        return notLike( lambdaToProperty( property ), value );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike( String property, Object value ) {
        return notLike( property, value, null );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLike( P property, Object value, Character escape ) {
        return notLike( lambdaToProperty( property ), value, escape );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLike( String property, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike( P property, Object value ) {
        return orNotLike( lambdaToProperty( property ), value );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike( String property, Object value ) {
        return orNotLike( property, value, null );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLike( P property, Object value, Character escape ) {
        return orNotLike( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLike( String property, Object value, Character escape );

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft( P property, Object value ) {
        return notLikeLeft( lambdaToProperty( property ), value );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft( String property, Object value ) {
        return notLikeLeft( property, value, null );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeLeft( P property, Object value, Character escape ) {
        return notLikeLeft( lambdaToProperty( property ), value, escape );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeLeft( String property, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft( P property, Object value ) {
        return orNotLikeLeft( lambdaToProperty( property ), value );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft( String property, Object value ) {
        return orNotLikeLeft( property, value, null );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeLeft( P property, Object value, Character escape ) {
        return orNotLike( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeLeft( String property, Object value, Character escape );

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight( P property, Object value ) {
        return notLikeRight( lambdaToProperty( property ), value );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight( String property, Object value ) {
        return notLikeRight( property, value, null );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeRight( P property, Object value, Character escape ) {
        return notLikeRight( lambdaToProperty( property ), value, escape );
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeRight( String property, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight( P property, Object value ) {
        return orNotLikeRight( lambdaToProperty( property ), value );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight( String property, Object value ) {
        return orNotLikeRight( property, value, null );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeRight( P property, Object value, Character escape ) {
        return orNotLikeRight( lambdaToProperty( property ), value, escape );
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeRight( String property, Object value, Character escape );

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateLike( String column, Object value ) {
        return immediateLike( column, value, null );
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateLike( String column, Object value, Character escape );

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateLike( String tableAlias, String column, Object value ) {
        return immediateLike( tableAlias, column, value, null );
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateLike( String tableAlias, String column, Object value, Character escape );

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateLike( String column, Object value ) {
        return orImmediateLike( column, value, null );
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateLike( String column, Object value, Character escape );
    
    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateLike( String tableAlias, String column, Object value ) {
        return orImmediateLike( tableAlias, column, value, null );
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateLike( String tableAlias, String column, Object value, Character escape );

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateLikeLeft( String column, Object value ) {
        return immediateLikeLeft( column, value, null );
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateLikeLeft( String column, Object value, Character escape );

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateLikeLeft( String tableAlias, String column, Object value ) {
        return immediateLikeLeft( tableAlias, column, value, null );
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateLikeLeft( String tableAlias, String column, Object value, Character escape );

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateLikeLeft( String column, Object value ) {
        return orImmediateLikeLeft( column, value, null );
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateLikeLeft( String column, Object value, Character escape );

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateLikeLeft( String tableAlias, String column, Object value ) {
        return orImmediateLikeLeft( tableAlias, column, value, null );
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateLikeLeft( String tableAlias, String column, Object value, Character escape );

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateLikeRight( String column, Object value ) {
        return immediateLikeRight( column, value, null );
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateLikeRight( String column, Object value, Character escape );

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateLikeRight( String tableAlias, String column, Object value ) {
        return immediateLikeRight( tableAlias, column, value, null );
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateLikeRight( String tableAlias, String column, Object value, Character escape );

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateLikeRight( String column, Object value ) {
        return orImmediateLikeRight( column, value, null );
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateLikeRight( String column, Object value, Character escape );
    
    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateLikeRight( String tableAlias, String column, Object value ) {
        return orImmediateLikeRight( tableAlias, column, value, null );
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateLikeRight( String tableAlias, String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateNotLike( String column, Object value ) {
        return immediateNotLike( column, value, null );
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateNotLike( String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateNotLike( String tableAlias, String column, Object value ) {
        return immediateNotLike( tableAlias, column, value, null );
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateNotLike( String tableAlias, String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateNotLike( String column, Object value ) {
        return orImmediateNotLike( column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLike( String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateNotLike( String tableAlias, String column, Object value ) {
        return orImmediateNotLike( tableAlias, column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLike( String tableAlias, String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateNotLikeLeft( String column, Object value ) {
        return immediateNotLikeLeft( column, value, null );
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateNotLikeLeft( String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateNotLikeLeft( String tableAlias, String column, Object value ) {
        return immediateNotLikeLeft( tableAlias, column, value, null );
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateNotLikeLeft( String tableAlias, String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateNotLikeLeft( String column, Object value ) {
        return orImmediateNotLikeLeft( column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLikeLeft( String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateNotLikeLeft( String tableAlias, String column, Object value ) {
        return orImmediateNotLikeLeft( tableAlias, column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLikeLeft( String tableAlias, String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain immediateNotLikeRight( String column, Object value ) {
        return immediateNotLikeRight( column, value, null );
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain immediateNotLikeRight( String column, Object value, Character escape );

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain immediateNotLikeRight( String tableAlias, String column, Object value ) {
        return immediateNotLikeRight( tableAlias, column, value, null );
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain immediateNotLikeRight( String tableAlias, String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orImmediateNotLikeRight( String column, Object value ) {
        return orImmediateNotLikeRight( column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLikeRight( String column, Object value, Character escape );

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orImmediateNotLikeRight( String tableAlias, String column, Object value ) {
        return orImmediateNotLikeRight( tableAlias, column, value, null );
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orImmediateNotLikeRight( String tableAlias, String column, Object value, Character escape );
   
}
