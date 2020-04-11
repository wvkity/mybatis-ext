package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * IS NULL条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectNull<T> extends AbstractDirectEmptyExpression<T> {

    private static final long serialVersionUID = -4663604933639152009L;

    /**
     * 构造方法
     * @param column 字段
     * @param logic  逻辑符号
     */
    DirectNull(String column, Logic logic) {
        this.column = column;
        this.logic = logic;
        this.symbol = Symbol.NULL;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     */
    DirectNull(String tableAlias, String column, Logic logic) {
        this.column = column;
        this.logic = logic;
        this.tableAlias = tableAlias;
        this.symbol = Symbol.NULL;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     */
    DirectNull(Criteria<T> criteria, String column, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.logic = logic;
        this.symbol = Symbol.NULL;
    }

    /**
     * 创建IS NULL条件对象
     * @param column 字段
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(String column) {
        return create(column, Logic.AND);
    }

    /**
     * 创建IS NULL条件对象
     * @param column 字段
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(String column, Logic logic) {
        if (hasText(column)) {
            return new DirectNull<>(column, logic);
        }
        return null;
    }

    /**
     * 创建IS NULL条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(String tableAlias, String column) {
        return create(tableAlias, column, Logic.AND);
    }

    /**
     * 创建IS NULL条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(String tableAlias, String column, Logic logic) {
        if (hasText(column)) {
            return new DirectNull<>(tableAlias, column, logic);
        }
        return null;
    }

    /**
     * 创建IS NULL条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(Criteria<T> criteria, String column) {
        return create(criteria, column, Logic.AND);
    }

    /**
     * 创建IS NULL条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> DirectNull<T> create(Criteria<T> criteria, String column, Logic logic) {
        if (criteria != null && hasText(column)) {
            return new DirectNull<>(criteria, column, logic);
        }
        return null;
    }
}
