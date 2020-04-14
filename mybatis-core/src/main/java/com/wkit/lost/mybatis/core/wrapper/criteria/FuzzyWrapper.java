package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.converter.PropertyConverter;

/**
 * 模糊条件接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface FuzzyWrapper<T, Chain extends CompareWrapper<T, Chain>> extends PropertyConverter<T> {

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
    default Chain directLike(String column, String value) {
        return directLike(column, value, (Character) null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLike(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLike(String tableAlias, String column, String value) {
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
    Chain directLike(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLike(String column, String value) {
        return orDirectLike(column, value, (Character) null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLike(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLike(String tableAlias, String column, String value) {
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
    Chain orDirectLike(String tableAlias, String column, String value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directLikeLeft(String column, String value) {
        return directLikeLeft(column, value, (Character)null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLikeLeft(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLikeLeft(String tableAlias, String column, String value) {
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
    Chain directLikeLeft(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLikeLeft(String column, String value) {
        return orDirectLikeLeft(column, value, (Character)null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLikeLeft(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLikeLeft(String tableAlias, String column, String value) {
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
    Chain orDirectLikeLeft(String tableAlias, String column, String value, Character escape);

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directLikeRight(String column, String value) {
        return directLikeRight(column, value, (Character)null);
    }

    /**
     * LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directLikeRight(String column, String value, Character escape);

    /**
     * LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directLikeRight(String tableAlias, String column, String value) {
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
    Chain directLikeRight(String tableAlias, String column, String value, Character escape);

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectLikeRight(String column, String value) {
        return orDirectLikeRight(column, value, (Character)null);
    }

    /**
     * 或LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectLikeRight(String column, String value, Character escape);

    /**
     * 或LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectLikeRight(String tableAlias, String column, String value) {
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
    Chain orDirectLikeRight(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLike(String column, String value) {
        return directNotLike(column, value, (Character)null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLike(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLike(String tableAlias, String column, String value) {
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
    Chain directNotLike(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLike(String column, String value) {
        return orDirectNotLike(column, value, (Character)null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLike(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLike(String tableAlias, String column, String value) {
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
    Chain orDirectNotLike(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLikeLeft(String column, String value) {
        return directNotLikeLeft(column, value, (Character)null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLikeLeft(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLikeLeft(String tableAlias, String column, String value) {
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
    Chain directNotLikeLeft(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLikeLeft(String column, String value) {
        return orDirectNotLikeLeft(column, value, (Character)null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeLeft(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLikeLeft(String tableAlias, String column, String value) {
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
    Chain orDirectNotLikeLeft(String tableAlias, String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain directNotLikeRight(String column, String value) {
        return directNotLikeRight(column, value, (Character)null);
    }

    /**
     * NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain directNotLikeRight(String column, String value, Character escape);

    /**
     * NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain directNotLikeRight(String tableAlias, String column, String value) {
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
    Chain directNotLikeRight(String tableAlias, String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    default Chain orDirectNotLikeRight(String column, String value) {
        return orDirectNotLikeRight(column, value, (Character) null);
    }

    /**
     * 或NOT LIKE
     * @param column 字段
     * @param value  值
     * @param escape 转义字符
     * @return {@code this}
     */
    Chain orDirectNotLikeRight(String column, String value, Character escape);

    /**
     * 或NOT LIKE
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    default Chain orDirectNotLikeRight(String tableAlias, String column, String value) {
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
    Chain orDirectNotLikeRight(String tableAlias, String column, String value, Character escape);

}
