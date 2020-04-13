package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.core.wrapper.criteria.SubCriteria;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;

/**
 * 子查询条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class SubQuery<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = 6237749348714845224L;

    /**
     * 子查询条件包装对象
     */
    private final SubCriteria<?> subCriteria;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     */
    SubQuery(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc,
             Symbol symbol, Logic logic) {
        this.criteria = criteria;
        this.column = column;
        this.subCriteria = sc;
        this.symbol = symbol;
        this.logic = logic;
    }

    @Override
    public String getSegment() {
        if (this.symbol != Symbol.NULL && this.symbol != Symbol.NOT_NULL && this.symbol != Symbol.BETWEEN
                && this.symbol != Symbol.NOT_BETWEEN) {
            StringBuilder builder = new StringBuilder(60);
            if (this.logic != null) {
                builder.append(this.logic.getSegment()).append(Constants.SPACE);
            }
            String realSegment = this.subCriteria.getSegmentForCondition();
            if (this.column != null) {
                String realAlias = getAlias();
                if (StringUtil.hasText(realAlias)) {
                    builder.append(realAlias).append(Constants.DOT);
                }
                builder.append(this.column.getColumn());
            }
            builder.append(Constants.SPACE).append(this.symbol.getSegment()).append(Constants.SPACE);
            builder.append(realSegment);
            return builder.toString();
        }
        return "";
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, Property<T, ?> property, SubCriteria<?> sc) {
        return create(criteria, property, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, String property, SubCriteria<?> sc) {
        return create(criteria, property, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         SubCriteria<?> sc, Logic logic) {
        return create(criteria, property, sc, Symbol.EQ, logic);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, String property, SubCriteria<?> sc, Logic logic) {
        return create(criteria, property, sc, Symbol.EQ, logic);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, Property<T, ?> property,
                                         SubCriteria<?> sc, Symbol symbol) {
        return create(criteria, property, sc, symbol, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, String property, SubCriteria<?> sc, Symbol symbol) {
        return create(criteria, property, sc, symbol, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, Property<T, ?> property, SubCriteria<?> sc,
                                         Symbol symbol, Logic logic) {
        if (criteria != null && sc != null && property != null) {
            return create(criteria, criteria.searchColumn(property), sc, symbol, logic);
        }
        return null;
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, String property, SubCriteria<?> sc,
                                         Symbol symbol, Logic logic) {
        if (criteria != null && sc != null && hasText(property)) {
            return create(criteria, criteria.searchColumn(property), sc, symbol, logic);
        }
        return null;
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc) {
        return create(criteria, column, sc, Symbol.EQ, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc,
                                         Logic logic) {
        return create(criteria, column, sc, Symbol.EQ, logic);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc,
                                         Symbol symbol) {
        return create(criteria, column, sc, symbol, Logic.AND);
    }

    /**
     * 创建子查询条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param sc       子查询条件包装对象
     * @param symbol   条件符号
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> SubQuery<T> create(Criteria<T> criteria, ColumnWrapper column, SubCriteria<?> sc,
                                         Symbol symbol, Logic logic) {
        if (criteria != null && sc != null) {
            if (symbol == Symbol.EXISTS || symbol == Symbol.NOT_EXISTS) {
                return new SubQuery<>(criteria, column, sc, symbol, logic);
            } else if (column != null) {
                return new SubQuery<>(criteria, column, sc, symbol, logic);
            }
        }
        return null;
    }
}
