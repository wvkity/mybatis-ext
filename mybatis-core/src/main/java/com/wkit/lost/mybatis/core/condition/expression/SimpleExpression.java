package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * 简单条件
 * @param <T> 泛型类型
 * @author DT
 */
public class SimpleExpression<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = 1464702377691845445L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public SimpleExpression( String property, Object value ) {
        this( property, value, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public SimpleExpression( String property, Object value, Logic logic ) {
        this( property, value, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param operator 操作类型
     */
    public SimpleExpression( String property, Object value, Operator operator ) {
        this( property, value, operator, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param operator 操作类型
     * @param logic    逻辑操作
     */
    public SimpleExpression( String property, Object value, Operator operator, Logic logic ) {
        this.operator = operator;
        this.logic = logic;
        this.property = property;
        this.value = value;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     */
    public SimpleExpression( Criteria<T> criteria, String property, Object value ) {
        this( criteria, property, value, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public SimpleExpression( Criteria<T> criteria, String property, Object value, Logic logic ) {
        this( criteria, property, value, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param operator 操作类型
     */
    public SimpleExpression( Criteria<T> criteria, String property, Object value, Operator operator ) {
        this( criteria, property, value, operator, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param value    值
     * @param operator 操作类型
     * @param logic    逻辑操作
     */
    public SimpleExpression( Criteria<T> criteria, String property, Object value, Operator operator, Logic logic ) {
        this.operator = operator;
        this.logic = logic;
        this.property = property;
        this.value = value;
        this.setCriteria( criteria );
    }
}
