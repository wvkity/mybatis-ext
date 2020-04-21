package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

import java.util.Collection;

/**
 * NOT IN范围条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class NotIn<T> extends AbstractRangeExpression<T> {

    private static final long serialVersionUID = 2452544078164431337L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param logic    逻辑符号
     */
    NotIn(Criteria<T> criteria, ColumnWrapper column, Collection<Object> values, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.values = values;
        this.logic = logic;
        this.symbol = Symbol.NOT_IN;
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> create(Criteria<T> criteria, String property,
                                      Collection<Object> values) {
        return create(criteria, property, values, Logic.AND);
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> create(Criteria<T> criteria, String property,
                                      Collection<Object> values, Logic logic) {
        return create(criteria, criteria.searchColumn(property), values, logic);
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotIn<T> create(Criteria<T> criteria, Property<T, V> property, Collection<Object> values) {
        return create(criteria, property, values, Logic.AND);
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> NotIn<T> create(Criteria<T> criteria, Property<T, V> property,
                                         Collection<Object> values, Logic logic) {
        return create(criteria, criteria.searchColumn(property), values, logic);
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> create(Criteria<T> criteria, ColumnWrapper column,
                                      Collection<Object> values) {
        return create(criteria, column, values, Logic.AND);
    }

    /**
     * 创建NOT IN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param values   值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> NotIn<T> create(Criteria<T> criteria, ColumnWrapper column,
                                      Collection<Object> values, Logic logic) {
        if (criteria != null && column != null) {
            return new NotIn<>(criteria, column, values, logic);
        }
        return null;
    }
}
