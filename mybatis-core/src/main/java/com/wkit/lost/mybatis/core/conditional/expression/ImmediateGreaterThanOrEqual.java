package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 大于或等于条件(字符串字段)
 * @param <T> 泛型类型
 * @author wvkity
 */
public class ImmediateGreaterThanOrEqual<T> extends ImmediateSimple<T> {

    private static final long serialVersionUID = -2478288775983917149L;

    /**
     * 构造方法
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     */
    ImmediateGreaterThanOrEqual(String column, Object value, Logic logic) {
        super(column, value, Symbol.GE, logic);
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     */
    ImmediateGreaterThanOrEqual(String tableAlias, String column, Object value, Logic logic) {
        super(tableAlias, column, value, Symbol.GE, logic);
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     */
    ImmediateGreaterThanOrEqual(Criteria<T> criteria, String column, Object value, Logic logic) {
        super(criteria, column, value, Symbol.GE, logic);
    }

    /**
     * 创建大于或等于条件对象
     * @param column 列名
     * @param value  值
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(String column, Object value) {
        return create(column, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param column 列名
     * @param value  值
     * @param logic  逻辑符号
     * @param <T>    泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new ImmediateGreaterThanOrEqual<>(column, value, logic);
        }
        return null;
    }

    /**
     * 创建大于或等于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(String tableAlias, String column, Object value) {
        return create(tableAlias, column, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param tableAlias 表别名
     * @param column     列名
     * @param value      值
     * @param logic      逻辑符号
     * @param <T>        泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(String tableAlias, String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new ImmediateGreaterThanOrEqual<>(tableAlias, column, value, logic);
        }
        return null;
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(Criteria<T> criteria, String column, Object value) {
        return create(criteria, column, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param column   列名
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      泛型类型
     * @return 条件对象
     */
    public static <T> ImmediateGreaterThanOrEqual<T> create(Criteria<T> criteria, String column, Object value, Logic logic) {
        if (hasText(column)) {
            return new ImmediateGreaterThanOrEqual<>(criteria, column, value, logic);
        }
        return null;
    }
}
