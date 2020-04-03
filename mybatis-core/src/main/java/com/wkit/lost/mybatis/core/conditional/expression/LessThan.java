package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 小于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class LessThan<T> extends Simple<T> {

    private static final long serialVersionUID = 771632679424527146L;
    
    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    LessThan( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        super( criteria, column, value, Symbol.LT, logic );
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, Property<T, ?> property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, Property<T, ?> property, Object value, Logic logic ) {
        if ( criteria != null && property != null ) {
            return create( criteria, criteria.searchColumn( property ), value, logic );
        }
        return null;
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, String property, Object value ) {
        return create( criteria, property, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, String property, Object value, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), value, logic );
        }
        return null;
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, ColumnWrapper column, Object value ) {
        return create( criteria, column, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> LessThan<T> create( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new LessThan<>( criteria, column, value, logic );
        }
        return null;
    }
}
