package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 不等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectNotEqual<T> extends DirectSimple<T> {

    private static final long serialVersionUID = -1800609049494847517L;

    /**
     * 构造方法
     * @param column 字段名
     * @param value  值
     * @param logic  逻辑符号
     */
    DirectNotEqual(String column, Object value, Logic logic) {
        super(column, value, Symbol.NE, logic);
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段名
     * @param value      值
     * @param logic      逻辑符号
     */
    DirectNotEqual(String tableAlias, String column, Object value, Logic logic) {
        super(tableAlias, column, value, Symbol.NE, logic);
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段名
     * @param value    值
     * @param logic    逻辑符号
     */
    DirectNotEqual(Criteria<T> criteria, String column, Object value, Logic logic) {
        super(criteria, column, value, Symbol.NE, logic);
    }

    /**
     * 创建不等于条件对象
     * @param column 列名
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> create(String column, Object value) {
        return create(column, value, Logic.AND);
    }

    /**
     * 创建不等于条件对象
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> DirectNotEqual<T> create(String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new DirectNotEqual<>(column, value, logic);
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
    public static <T> DirectNotEqual<T> create(String tableAlias, String column, Object value) {
        return create(tableAlias, column, value, Logic.AND);
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
    public static <T> DirectNotEqual<T> create(String tableAlias, String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new DirectNotEqual<>(tableAlias, column, value, logic);
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
    public static <T> DirectNotEqual<T> create(Criteria<T> criteria, String column, Object value) {
        return create(criteria, column, value, Logic.AND);
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
    public static <T> DirectNotEqual<T> create(Criteria<T> criteria, String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new DirectNotEqual<>(criteria, column, value, logic);
        }
        return null;
    }
}
