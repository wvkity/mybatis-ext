package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.LambdaConverter;

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
    default Chain like(P property, Object value) {
        return like(lambdaToProperty(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain like(String property, Object value) {
        return like(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain like(P property, Object value, Character escape) {
        return like(lambdaToProperty(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain like(String property, Object value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike(P property, Object value) {
        return orLike(lambdaToProperty(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike(String property, Object value) {
        return orLike(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLike(P property, Object value, Character escape) {
        return orLike(lambdaToProperty(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLike(String property, Object value, Character escape);

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft(P property, Object value) {
        return likeLeft(lambdaToProperty(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft(String property, Object value) {
        return likeLeft(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeLeft(P property, Object value, Character escape) {
        return likeLeft(lambdaToProperty(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeLeft(String property, Object value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft(P property, Object value) {
        return orLikeLeft(lambdaToProperty(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft(String property, Object value) {
        return orLikeLeft(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeLeft(P property, Object value, Character escape) {
        return orLike(lambdaToProperty(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeLeft(String property, Object value, Character escape);

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight(P property, Object value) {
        return likeRight(lambdaToProperty(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight(String property, Object value) {
        return likeRight(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeRight(P property, Object value, Character escape) {
        return likeRight(lambdaToProperty(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeRight(String property, Object value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight(P property, Object value) {
        return orLikeRight(lambdaToProperty(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight(String property, Object value) {
        return orLikeRight(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeRight(P property, Object value, Character escape) {
        return orLikeRight(lambdaToProperty(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeRight(String property, Object value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike(P property, Object value) {
        return notLike(lambdaToProperty(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike(String property, Object value) {
        return notLike(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLike(P property, Object value, Character escape) {
        return notLike(lambdaToProperty(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLike(String property, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike(P property, Object value) {
        return orNotLike(lambdaToProperty(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike(String property, Object value) {
        return orNotLike(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLike(P property, Object value, Character escape) {
        return orNotLike(lambdaToProperty(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLike(String property, Object value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft(P property, Object value) {
        return notLikeLeft(lambdaToProperty(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft(String property, Object value) {
        return notLikeLeft(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeLeft(P property, Object value, Character escape) {
        return notLikeLeft(lambdaToProperty(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeLeft(String property, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft(P property, Object value) {
        return orNotLikeLeft(lambdaToProperty(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft(String property, Object value) {
        return orNotLikeLeft(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeLeft(P property, Object value, Character escape) {
        return orNotLike(lambdaToProperty(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeLeft(String property, Object value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight(P property, Object value) {
        return notLikeRight(lambdaToProperty(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight(String property, Object value) {
        return notLikeRight(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeRight(P property, Object value, Character escape) {
        return notLikeRight(lambdaToProperty(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeRight(String property, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight(P property, Object value) {
        return orNotLikeRight(lambdaToProperty(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight(String property, Object value) {
        return orNotLikeRight(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeRight(P property, Object value, Character escape) {
        return orNotLikeRight(lambdaToProperty(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeRight(String property, Object value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directLike(String column, Object value) {
        return directLike(column, value, null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLike(String column, Object value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLike(String tableAlias, String column, Object value) {
        return directLike(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directLike(String tableAlias, String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLike(String column, Object value) {
        return orDirectLike(column, value, null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLike(String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLike(String tableAlias, String column, Object value) {
        return orDirectLike(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectLike(String tableAlias, String column, Object value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directLikeLeft(String column, Object value) {
        return directLikeLeft(column, value, null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLikeLeft(String column, Object value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLikeLeft(String tableAlias, String column, Object value) {
        return directLikeLeft(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directLikeLeft(String tableAlias, String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLikeLeft(String column, Object value) {
        return orDirectLikeLeft(column, value, null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLikeLeft(String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLikeLeft(String tableAlias, String column, Object value) {
        return orDirectLikeLeft(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectLikeLeft(String tableAlias, String column, Object value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directLikeRight(String column, Object value) {
        return directLikeRight(column, value, null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLikeRight(String column, Object value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLikeRight(String tableAlias, String column, Object value) {
        return directLikeRight(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directLikeRight(String tableAlias, String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLikeRight(String column, Object value) {
        return orDirectLikeRight(column, value, null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLikeRight(String column, Object value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLikeRight(String tableAlias, String column, Object value) {
        return orDirectLikeRight(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectLikeRight(String tableAlias, String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLike(String column, Object value) {
        return directNotLike(column, value, null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLike(String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLike(String tableAlias, String column, Object value) {
        return directNotLike(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directNotLike(String tableAlias, String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLike(String column, Object value) {
        return orDirectNotLike(column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLike(String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLike(String tableAlias, String column, Object value) {
        return orDirectNotLike(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectNotLike(String tableAlias, String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLikeLeft(String column, Object value) {
        return directNotLikeLeft(column, value, null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLikeLeft(String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLikeLeft(String tableAlias, String column, Object value) {
        return directNotLikeLeft(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directNotLikeLeft(String tableAlias, String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLikeLeft(String column, Object value) {
        return orDirectNotLikeLeft(column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeLeft(String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLikeLeft(String tableAlias, String column, Object value) {
        return orDirectNotLikeLeft(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeLeft(String tableAlias, String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLikeRight(String column, Object value) {
        return directNotLikeRight(column, value, null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLikeRight(String column, Object value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLikeRight(String tableAlias, String column, Object value) {
        return directNotLikeRight(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain directNotLikeRight(String tableAlias, String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLikeRight(String column, Object value) {
        return orDirectNotLikeRight(column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeRight(String column, Object value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLikeRight(String tableAlias, String column, Object value) {
        return orDirectNotLikeRight(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeRight(String tableAlias, String column, Object value, Character escape);

}
