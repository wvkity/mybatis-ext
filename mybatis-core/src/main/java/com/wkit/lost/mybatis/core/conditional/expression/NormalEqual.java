package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;

/**
 * 字段相等条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class NormalEqual<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = -2314270986996270968L;

    /**
     * 其他条件包装对象
     */
    private final Criteria<?> otherCriteria;

    /**
     * 其他字段包装对象
     */
    private final ColumnWrapper otherColumn;

    /**
     * 构造方法
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param logic         逻辑符号
     * @param <E>           实体类型
     */
    <E> NormalEqual(Criteria<T> criteria, ColumnWrapper column,
                    Criteria<E> otherCriteria, ColumnWrapper otherColumn, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.otherCriteria = otherCriteria;
        this.otherColumn = otherColumn;
        this.logic = logic;
    }

    @Override
    public String getSegment() {
        String alias = getAlias();
        String otherAlias = this.otherCriteria.as();
        return this.logic.getSegment() + Constants.SPACE + (StringUtil.hasText(alias) ? (getAlias() +
                Constants.DOT + this.column.getColumn()) : this.column.getColumn()) + Constants.SPACE +
                this.symbol.getSegment() + Constants.SPACE + (StringUtil.hasText(otherAlias) ? (otherAlias.trim() +
                Constants.DOT + this.otherColumn.getColumn()) : this.otherColumn.getColumn());
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <V1>          属性1值类型
     * @param <E>           实体类型
     * @param <V2>          属性2值类型
     * @return 条件对象
     */
    public static <T, V1, E, V2> NormalEqual<T> create(Criteria<T> criteria, Property<T, V1> property,
                                                       Criteria<E> otherCriteria, Property<E, V2> otherProperty) {
        return create(criteria, property, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <V1>          属性1值类型
     * @param <E>           实体类型
     * @param <V2>          属性2值类型
     * @return 条件对象
     */
    public static <T, V1, E, V2> NormalEqual<T> create(Criteria<T> criteria, Property<T, V1> property,
                                                       Criteria<E> otherCriteria, Property<E, V2> otherProperty,
                                                       Logic logic) {
        if (criteria != null && property != null && otherCriteria != null && otherProperty != null) {
            return create(criteria, criteria.searchColumn(property), otherCriteria,
                    otherCriteria.searchColumn(otherProperty), logic);
        }
        return null;
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> create(Criteria<T> criteria, String property,
                                               Criteria<E> otherCriteria, String otherProperty) {
        return create(criteria, property, otherCriteria, otherProperty, Logic.AND);
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> create(Criteria<T> criteria, String property,
                                               Criteria<E> otherCriteria, String otherProperty, Logic logic) {
        if (criteria != null && hasText(property) && otherCriteria != null && hasText(otherProperty)) {
            return create(criteria, criteria.searchColumn(property), otherCriteria,
                    otherCriteria.searchColumn(otherProperty), logic);
        }
        return null;
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> create(Criteria<T> criteria, ColumnWrapper column,
                                               Criteria<E> otherCriteria, ColumnWrapper otherColumn) {
        return create(criteria, column, otherCriteria, otherColumn, Logic.AND);
    }

    /**
     * 创建NORMAL EQUAL条件对象
     * @param criteria      条件包装对象
     * @param column        字段包装对象
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他字段包装对象
     * @param logic         逻辑符号
     * @param <T>           实体类型
     * @param <E>           实体类型
     * @return 条件对象
     */
    public static <T, E> NormalEqual<T> create(Criteria<T> criteria, ColumnWrapper column,
                                               Criteria<E> otherCriteria, ColumnWrapper otherColumn, Logic logic) {
        if (criteria != null && column != null && otherCriteria != null && otherColumn != null) {
            return new NormalEqual<>(criteria, column, otherCriteria, otherColumn, logic);
        }
        return null;
    }
}
