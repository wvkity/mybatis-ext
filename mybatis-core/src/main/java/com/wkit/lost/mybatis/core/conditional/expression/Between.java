package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * Between范围条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class Between<T> extends AbstractBetweenExpression<T> {

    private static final long serialVersionUID = 3061778492037309959L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     */
    Between( Criteria<T> criteria, ColumnWrapper column, Object begin, Object end, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.symbol = Symbol.BETWEEN;
    }


    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, Property<T, ?> property, Object begin,
                                         Object end ) {
        return create( criteria, property, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, Property<T, ?> property, Object begin,
                                         Object end, Logic logic ) {
        if ( criteria != null && property != null ) {
            return create( criteria, criteria.searchColumn( property ), begin, end, logic );
        }
        return null;
    }

    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, String property, Object begin,
                                         Object end ) {
        return create( criteria, property, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, String property, Object begin,
                                         Object end, Logic logic ) {
        if ( criteria != null && hasText( property ) ) {
            return create( criteria, criteria.searchColumn( property ), begin, end, logic );
        }
        return null;
    }

    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, ColumnWrapper column, Object begin, Object end ) {
        return create( criteria, column, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN范围条件对象
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> Between<T> create( Criteria<T> criteria, ColumnWrapper column, Object begin,
                                         Object end, Logic logic ) {
        if ( criteria != null && column != null ) {
            return new Between<>( criteria, column, begin, end, logic );
        }
        return null;
    }
}
