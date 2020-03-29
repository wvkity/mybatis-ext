package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 简单条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class Simple<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = 431175395571986016L;

    /**
     * 构造方法
     * @param column 列包装对象
     * @param value  值
     * @param symbol 条件符号
     * @param logic  逻辑符号
     */
    Simple( ColumnWrapper column, Object value, Symbol symbol, Logic logic ) {
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     */
    Simple( Criteria<T> criteria, ColumnWrapper column, Object value, Symbol symbol, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性Lambda对象
     * @param value    值
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return create( criteria, property, value, Symbol.EQ );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性Lambda对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, Property<T, ?> property,
                                        Object value, Logic logic ) {
        return create( criteria, criteria.searchColumn( property ), value, Symbol.EQ, logic );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性Lambda对象
     * @param value    值
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, Property<T, ?> property,
                                        Object value, Symbol symbol ) {
        return create( criteria, property, value, symbol, Logic.AND );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性Lambda对象
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, Property<T, ?> property, Object value,
                                        Symbol symbol, Logic logic ) {
        if ( criteria != null && property != null ) {
            return create( criteria, criteria.searchColumn( property ), value, symbol, logic );
        }
        return null;
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, String property, Object value ) {
        return create( criteria, property, value, Symbol.EQ );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, String property, Object value, Logic logic ) {
        return create( criteria, property, value, Symbol.EQ, logic );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, String property, Object value, Symbol symbol ) {
        return create( criteria, property, value, symbol, Logic.AND );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, String property, Object value,
                                        Symbol symbol, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, symbol, logic );
        }
        return null;
    }

    /**
     * 创建简单条件对象
     * @param column 列包装对象
     * @param value  值
     * @param <T>    实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( ColumnWrapper column, Object value ) {
        return create( column, value, Symbol.EQ );
    }

    /**
     * 创建简单条件对象
     * @param column 列包装对象
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( ColumnWrapper column, Object value, Logic logic ) {
        return create( column, value, Symbol.EQ, logic );
    }

    /**
     * 创建简单条件对象
     * @param column 列包装对象
     * @param value  值
     * @param symbol 条件符号
     * @param <T>    实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( ColumnWrapper column, Object value, Symbol symbol ) {
        return create( column, value, symbol, Logic.AND );
    }

    /**
     * 创建简单条件对象
     * @param column 列包装对象
     * @param value  值
     * @param symbol 条件符号
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( ColumnWrapper column, Object value, Symbol symbol, Logic logic ) {
        if ( column != null ) {
            return new Simple<>( column, value, symbol, logic );
        }
        return null;
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, ColumnWrapper column, Object value ) {
        return create( criteria, column, value, Symbol.EQ );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        return create( criteria, column, value, Symbol.EQ, logic );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Symbol symbol ) {
        return create( criteria, column, value, symbol, Logic.AND );
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列包装对象
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 简单条件对象
     */
    public static <T> Simple<T> create( Criteria<T> criteria, ColumnWrapper column, Object value,
                                        Symbol symbol, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new Simple<>( criteria, column, value, symbol, logic );
        }
        return null;
    }
}
