package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 大于或等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class GreaterThanOrEqual<T> extends Simple<T> {

    private static final long serialVersionUID = 4392850257599241817L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    GreaterThanOrEqual(Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic) {
        super(criteria, column, value, Symbol.GE, logic);
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThanOrEqual<T> create(Criteria<T> criteria, Property<T, V> property, V value) {
        return create(criteria, property, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @param <V>      属性值类型
     * @return 条件对象
     */
    public static <T, V> GreaterThanOrEqual<T> create(Criteria<T> criteria, Property<T, V> property,
                                                      V value, Logic logic) {
        if (criteria != null && property != null) {
            return create(criteria, criteria.searchColumn(property), value, logic);
        }
        return null;
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> create(Criteria<T> criteria, String property, Object value) {
        return create(criteria, property, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> create(Criteria<T> criteria, String property, Object value, Logic logic) {
        if (criteria != null && hasText(property)) {
            return create(criteria, criteria.searchColumn(property), value, logic);
        }
        return null;
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> create(Criteria<T> criteria, ColumnWrapper column, Object value) {
        return create(criteria, column, value, Logic.AND);
    }

    /**
     * 创建大于或等于条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> GreaterThanOrEqual<T> create(Criteria<T> criteria, ColumnWrapper column,
                                                   Object value, Logic logic) {
        if (criteria != null && column != null) {
            return new GreaterThanOrEqual<>(criteria, column, value, logic);
        }
        return null;
    }
}
