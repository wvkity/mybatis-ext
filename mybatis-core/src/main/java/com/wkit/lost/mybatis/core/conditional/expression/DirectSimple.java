package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 简单条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectSimple<T> extends DirectExpressionWrapper<T> {

    private static final long serialVersionUID = 5549482113181211099L;

    /**
     * 构造方法
     * @param column 列名
     * @param value  值
     * @param symbol 条件符号
     * @param logic  逻辑符号
     */
    DirectSimple(String column, Object value, Symbol symbol, Logic logic) {
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param symbol     条件符号
     * @param logic      逻辑符号
     */
    DirectSimple(String tableAlias, String column, Object value, Symbol symbol, Logic logic) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param column   列名
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     */
    DirectSimple(Criteria<T> criteria, String column, Object value, Symbol symbol, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.symbol = symbol;
        this.logic = logic;
    }

    /**
     * 创建简单条件对象
     * @param column 列名
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String column, Object value) {
        return create(column, value, Symbol.EQ);
    }

    /**
     * 创建简单条件对象
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String column, Object value, Logic logic) {
        return create(column, value, Symbol.EQ, logic);
    }

    /**
     * 创建简单条件对象
     * @param column 列名
     * @param value  值
     * @param symbol 条件符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String column, Object value, Symbol symbol) {
        return create(column, value, symbol, Logic.AND);
    }

    /**
     * 创建简单条件对象
     * @param column 列名
     * @param value  值
     * @param symbol 条件符号
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String column, Object value, Symbol symbol, Logic logic) {
        if (hasText(column)) {
            return new DirectSimple<>(column, value, symbol, logic);
        }
        return null;
    }

    /**
     * 创建简单条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String tableAlias, String column, Object value) {
        return create(tableAlias, column, value, Symbol.EQ);
    }

    /**
     * 创建简单条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String tableAlias, String column, Object value, Logic logic) {
        return create(tableAlias, column, value, Symbol.EQ, logic);
    }

    /**
     * 创建简单条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param symbol     条件符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String tableAlias, String column, Object value, Symbol symbol) {
        return create(tableAlias, column, value, symbol, Logic.AND);
    }

    /**
     * 创建简单条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param symbol     条件符号
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(String tableAlias, String column, Object value,
                                             Symbol symbol, Logic logic) {
        if (hasText(column)) {
            return new DirectSimple<>(tableAlias, column, value, symbol, logic);
        }
        return null;
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列名
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(Criteria<T> criteria, String column, Object value) {
        return create(criteria, column, value, Symbol.EQ);
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(Criteria<T> criteria, String column, Object value, Logic logic) {
        return create(criteria, column, value, Symbol.EQ, logic);
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列名
     * @param value    值
     * @param symbol   条件符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(Criteria<T> criteria, String column, Object value,
                                             Symbol symbol) {
        return create(criteria, column, value, symbol, Logic.AND);
    }

    /**
     * 创建简单条件对象
     * @param criteria 条件对象
     * @param column   列名
     * @param value    值
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> DirectSimple<T> create(Criteria<T> criteria, String column, Object value,
                                             Symbol symbol, Logic logic) {
        if (hasText(column)) {
            return new DirectSimple<>(criteria, column, value, symbol, logic);
        }
        return null;
    }
}
