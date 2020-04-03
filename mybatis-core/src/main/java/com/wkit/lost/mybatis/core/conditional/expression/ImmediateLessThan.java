package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 小于条件(字符串字段)
 * @param <T> 泛型类型
 * @author wvkity
 */
public class ImmediateLessThan<T> extends ImmediateSimple<T> {

    private static final long serialVersionUID = -3489226776472857369L;

    /**
     * 构造方法
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     */
    ImmediateLessThan( String column, Object value, Logic logic ) {
        super( column, value, Symbol.LT, logic );
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     */
    ImmediateLessThan( String tableAlias, String column, Object value, Logic logic ) {
        super( tableAlias, column, value, Symbol.LT, logic );
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     */
    ImmediateLessThan( Criteria<T> criteria, String column, Object value, Logic logic ) {
        super( criteria, column, value, Symbol.LT, logic );
    }

    /**
     * 创建小于条件对象
     * @param column 列名
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( String column, Object value ) {
        return create( column, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateLessThan<>( column, value, logic );
        }
        return null;
    }

    /**
     * 创建小于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( String tableAlias, String column, Object value ) {
        return create( tableAlias, column, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( String tableAlias, String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateLessThan<>( tableAlias, column, value, logic );
        }
        return null;
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( Criteria<T> criteria, String column, Object value ) {
        return create( criteria, column, value, Logic.AND );
    }

    /**
     * 创建小于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateLessThan<T> create( Criteria<T> criteria, String column, Object value, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateLessThan<>( criteria, column, value, logic );
        }
        return null;
    }
}
