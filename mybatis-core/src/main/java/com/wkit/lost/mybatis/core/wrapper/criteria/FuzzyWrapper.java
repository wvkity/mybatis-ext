package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.converter.PropertyConverter;

/**
 * 模糊条件接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface FuzzyWrapper<T, Chain extends FuzzyWrapper<T, Chain>> extends PropertyConverter<T> {

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain like(Property<T, String> property, String value) {
        return like(convert(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain like(String property, String value) {
        return like(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain like(Property<T, String> property, String value, Character escape) {
        return like(convert(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain like(String property, String value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike(Property<T, String> property, String value) {
        return orLike(convert(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLike(String property, String value) {
        return orLike(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLike(Property<T, String> property, String value, Character escape) {
        return orLike(convert(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLike(String property, String value, Character escape);

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft(Property<T, String> property, String value) {
        return likeLeft(convert(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeLeft(String property, String value) {
        return likeLeft(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeLeft(Property<T, String> property, String value, Character escape) {
        return likeLeft(convert(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeLeft(String property, String value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft(Property<T, String> property, String value) {
        return orLikeLeft(convert(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeLeft(String property, String value) {
        return orLikeLeft(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeLeft(Property<T, String> property, String value, Character escape) {
        return orLike(convert(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeLeft(String property, String value, Character escape);

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight(Property<T, String> property, String value) {
        return likeRight(convert(property), value);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain likeRight(String property, String value) {
        return likeRight(property, value, null);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain likeRight(Property<T, String> property, String value, Character escape) {
        return likeRight(convert(property), value, escape);
    }

    /**
     * LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain likeRight(String property, String value, Character escape);

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight(Property<T, String> property, String value) {
        return orLikeRight(convert(property), value);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLikeRight(String property, String value) {
        return orLikeRight(property, value, null);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orLikeRight(Property<T, String> property, String value, Character escape) {
        return orLikeRight(convert(property), value, escape);
    }

    /**
     * 或LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orLikeRight(String property, String value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike(Property<T, String> property, String value) {
        return notLike(convert(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLike(String property, String value) {
        return notLike(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLike(Property<T, String> property, String value, Character escape) {
        return notLike(convert(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLike(String property, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike(Property<T, String> property, String value) {
        return orNotLike(convert(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLike(String property, String value) {
        return orNotLike(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLike(Property<T, String> property, String value, Character escape) {
        return orNotLike(convert(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLike(String property, String value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft(Property<T, String> property, String value) {
        return notLikeLeft(convert(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeLeft(String property, String value) {
        return notLikeLeft(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeLeft(Property<T, String> property, String value, Character escape) {
        return notLikeLeft(convert(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeLeft(String property, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft(Property<T, String> property, String value) {
        return orNotLikeLeft(convert(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeLeft(String property, String value) {
        return orNotLikeLeft(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeLeft(Property<T, String> property, String value, Character escape) {
        return orNotLike(convert(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeLeft(String property, String value, Character escape);

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight(Property<T, String> property, String value) {
        return notLikeRight(convert(property), value);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain notLikeRight(String property, String value) {
        return notLikeRight(property, value, null);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain notLikeRight(Property<T, String> property, String value, Character escape) {
        return notLikeRight(convert(property), value, escape);
    }

    /**
     * NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain notLikeRight(String property, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight(Property<T, String> property, String value) {
        return orNotLikeRight(convert(property), value);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNotLikeRight(String property, String value) {
        return orNotLikeRight(property, value, null);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    default Chain orNotLikeRight(Property<T, String> property, String value, Character escape) {
        return orNotLikeRight(convert(property), value, escape);
    }

    /**
     * 或NOT LIKE
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @return {@code this}
     */
    Chain orNotLikeRight(String property, String value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain likeWith(String column, String value) {
        return likeWith(column, value, (Character) null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain likeWith(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain likeWith(String tableAlias, String column, String value) {
        return likeWith(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain likeWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orLikeWith(String column, String value) {
        return orLikeWith(column, value, (Character) null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orLikeWith(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orLikeWith(String tableAlias, String column, String value) {
        return orLikeWith(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orLikeWith(String tableAlias, String column, String value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain likeLeftWith(String column, String value) {
        return likeLeftWith(column, value, (Character) null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain likeLeftWith(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain likeLeftWith(String tableAlias, String column, String value) {
        return likeLeftWith(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain likeLeftWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orLikeLeftWith(String column, String value) {
        return orLikeLeftWith(column, value, (Character) null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orLikeLeftWith(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orLikeLeftWith(String tableAlias, String column, String value) {
        return orLikeLeftWith(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orLikeLeftWith(String tableAlias, String column, String value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain likeRightWith(String column, String value) {
        return likeRightWith(column, value, (Character) null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain likeRightWith(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain likeRightWith(String tableAlias, String column, String value) {
        return likeRightWith(tableAlias, column, value, null);
    }

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain likeRightWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orLikeRightWith(String column, String value) {
        return orLikeRightWith(column, value, (Character) null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orLikeRightWith(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orLikeRightWith(String tableAlias, String column, String value) {
        return orLikeRightWith(tableAlias, column, value, null);
    }

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orLikeRightWith(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain notLikeWith(String column, String value) {
        return notLikeWith(column, value, (Character) null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain notLikeWith(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain notLikeWith(String tableAlias, String column, String value) {
        return notLikeWith(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain notLikeWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orNotLikeWith(String column, String value) {
        return orNotLikeWith(column, value, (Character) null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orNotLikeWith(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orNotLikeWith(String tableAlias, String column, String value) {
        return orNotLikeWith(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orNotLikeWith(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain notLikeLeftWith(String column, String value) {
        return notLikeLeftWith(column, value, (Character) null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain notLikeLeftWith(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain notLikeLeftWith(String tableAlias, String column, String value) {
        return notLikeLeftWith(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain notLikeLeftWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orNotLikeLeftWith(String column, String value) {
        return orNotLikeLeftWith(column, value, (Character) null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orNotLikeLeftWith(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orNotLikeLeftWith(String tableAlias, String column, String value) {
        return orNotLikeLeftWith(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orNotLikeLeftWith(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain notLikeRightWith(String column, String value) {
        return notLikeRightWith(column, value, (Character) null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain notLikeRightWith(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain notLikeRightWith(String tableAlias, String column, String value) {
        return notLikeRightWith(tableAlias, column, value, null);
    }

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain notLikeRightWith(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orNotLikeRightWith(String column, String value) {
        return orNotLikeRightWith(column, value, (Character) null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orNotLikeRightWith(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orNotLikeRightWith(String tableAlias, String column, String value) {
        return orNotLikeRightWith(tableAlias, column, value, null);
    }

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @param escape     转义字符
     * @return {@code this}
     */
    Chain orNotLikeRightWith(String tableAlias, String column, String value, Character escape);

}
