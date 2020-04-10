package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

import java.util.Collection;

/**
 * IN范围条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class In<T> extends AbstractRangeExpression<T> {

    private static final long serialVersionUID = 1408575628333900120L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param logic    逻辑符号
     */
    In(Criteria<T> criteria, ColumnWrapper column, Collection<Object> values, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.values = values;
        this.logic = logic;
        this.symbol = Symbol.IN;
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, String property,
                                   Collection<Object> values) {
        return create(criteria, property, values, Logic.AND);
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, String property,
                                   Collection<Object> values, Logic logic) {
        return create(criteria, criteria.searchColumn(property), values, logic);
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, Property<T, ?> property,
                                   Collection<Object> values) {
        return create(criteria, property, values, Logic.AND);
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, Property<T, ?> property,
                                   Collection<Object> values, Logic logic) {
        return create(criteria, criteria.searchColumn(property), values, logic);
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, ColumnWrapper column,
                                   Collection<Object> values) {
        return create(criteria, column, values, Logic.AND);
    }

    /**
     * 创建IN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> In<T> create(Criteria<T> criteria, ColumnWrapper column,
                                   Collection<Object> values, Logic logic) {
        if (criteria != null && column != null) {
            return new In<>(criteria, column, values, logic);
        }
        return null;
    }
}
