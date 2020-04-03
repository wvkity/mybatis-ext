package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 不等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class ImmediateNotEqual<T> extends ImmediateSimple<T> {

    private static final long serialVersionUID = -1800609049494847517L;

    /**
     * 构造方法
     * @param column 字段名
     * @param value  值
     * @param logic  逻辑符号
     */
    ImmediateNotEqual( String column, Object value, Logic logic ) {
        super( column, value, Symbol.NE, logic );
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段名
     * @param value      值
     * @param logic      逻辑符号
     */
    ImmediateNotEqual( String tableAlias, String column, Object value, Logic logic ) {
        super( tableAlias, column, value, Symbol.NE, logic );
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段名
     * @param value    值
     * @param logic    逻辑符号
     */
    ImmediateNotEqual( Criteria<T> criteria, String column, Object value, Logic logic ) {
        super( criteria, column, value, Symbol.NE, logic );
    }

    /**
     * 创建不等于条件对象
     * @param column 列名
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( String column, Object value ) {
        return create( column, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateNotEqual<>( column, value, logic );
        }
        return null;
    }

    /**
     * 创建不等于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( String tableAlias, String column, Object value ) {
        return create( tableAlias, column, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( String tableAlias, String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateNotEqual<>( tableAlias, column, value, logic );
        }
        return null;
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( Criteria<T> criteria, String column, Object value ) {
        return create( criteria, column, value, Logic.AND );
    }

    /**
     * 创建不等于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateNotEqual<T> create( Criteria<T> criteria, String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateNotEqual<>( criteria, column, value, logic );
        }
        return null;
    }
}
