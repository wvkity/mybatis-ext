package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * NOT LIKE模糊匹配
 * @param <T> 实体类型
 * @author wvkity
 */
public class NotLike<T> extends AbstractFuzzyExpression<T> {

    private static final long serialVersionUID = 6927519566015548400L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    NotLike( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.logic = logic;
        this.symbol = Symbol.NOT_LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     */
    NotLike( Criteria<T> criteria, ColumnWrapper column, Object value, Match match, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.match = match;
        this.logic = logic;
        this.symbol = Symbol.NOT_LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     */
    NotLike( Criteria<T> criteria, ColumnWrapper column, Object value, Character escape, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.NOT_LIKE;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     */
    NotLike( Criteria<T> criteria, ColumnWrapper column, Object value, Match match, Character escape, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.match = match;
        this.escape = escape;
        this.logic = logic;
        this.symbol = Symbol.NOT_LIKE;
    }


    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        return create( criteria, criteria.searchColumn( property ), value, logic );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value ) {
        return create( criteria, column, value, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new NotLike<>( criteria, column, value, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value, Match match ) {
        return create( criteria, property, value, match, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value,
                                         Match match, Logic logic ) {
        return create( criteria, criteria.searchColumn( property ), value, match, logic );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value, Match match ) {
        return create( criteria, property, value, match, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value,
                                         Match match, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, match, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                         Match match ) {
        return create( criteria, column, value, match, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                         Match match, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new NotLike<>( criteria, column, value, match, logic );
        }
        return null;
    }


    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value, Character escape ) {
        return create( criteria, property, value, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value,
                                         Character escape, Logic logic ) {
        return create( criteria, criteria.searchColumn( property ), value, escape, logic );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value, Character escape ) {
        return create( criteria, property, value, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value,
                                         Character escape, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, escape, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param escape   转义字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Character escape ) {
        return create( criteria, column, value, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param escape   转义字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                         Character escape, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new NotLike<>( criteria, column, value, escape, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value,
                                         Match match, Character escape ) {
        return create( criteria, property, value, match, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, Property<T, ?> property, Object value,
                                         Match match, Character escape, Logic logic ) {
        return create( criteria, criteria.searchColumn( property ), value, match, escape, logic );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value,
                                         Match match, Character escape ) {
        return create( criteria, property, value, match, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字符
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, String property, Object value,
                                         Match match, Character escape, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, match, escape, logic );
        }
        return null;
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                         Match match, Character escape ) {
        return create( criteria, column, value, match, escape, Logic.AND );
    }

    /**
     * 创建NOT LIKE条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param match    匹配模式
     * @param escape   转义字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotLike<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                         Match match, Character escape, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new NotLike<>( criteria, column, value, match, escape, logic );
        }
        return null;
    }
}
