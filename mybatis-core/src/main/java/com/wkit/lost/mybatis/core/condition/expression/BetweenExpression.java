package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * BETWEEN范围条件
 * @param <T> 泛型类型
 * @author DT
 */
public class BetweenExpression<T> extends AbstractBetweenExpression<T> {

    private static final long serialVersionUID = 1099869217427380136L;

    /**
     * 构造方法
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     */
    public BetweenExpression( String property, Object begin, Object end ) {
        this( property, begin, end, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     */
    public BetweenExpression( Criteria<T> criteria, String property, Object begin, Object end ) {
        this( criteria, property, begin, end, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     */
    public BetweenExpression( String property, Object begin, Object end, Logic logic ) {
        this.property = property;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.operator = Operator.BETWEEN;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑操作
     */
    public BetweenExpression( Criteria<T> criteria, String property, Object begin, Object end, Logic logic ) {
        this.criteria = criteria;
        this.property = property;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.operator = Operator.BETWEEN;
    }

}
