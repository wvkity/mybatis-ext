package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 不等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class NotEqual<T> extends Simple<T> {

    private static final long serialVersionUID = -5367724512624788968L;

    /**
     * 构造方法
     * @param column 字段包装对象
     * @param value  值
     * @param logic  逻辑符号
     */
    NotEqual( ColumnWrapper column, Object value, Logic logic ) {
        super( column, value, Symbol.NE, logic );
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    NotEqual( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        super( criteria, column, value, Symbol.NE, logic );
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        if ( criteria != null && property != null ) {
            return create( criteria, criteria.searchColumn( property ), value, logic );
        }
        return null;
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, String property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, String property, Object value, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, logic );
        }
        return null;
    }

    /**
     * 创建不等于条件对象
     * @param column 字段包装对象
     * @param value  值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( ColumnWrapper column, Object value ) {
        return create( column, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param column 字段包装对象
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( ColumnWrapper column, Object value, Logic logic ) {
        if ( column != null ) {
            return new NotEqual<>( column, value, logic );
        }
        return null;
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, ColumnWrapper column, Object value ) {
        return create( criteria, column, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotEqual<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new NotEqual<>( criteria, column, value, logic );
        }
        return null;
    }
}
