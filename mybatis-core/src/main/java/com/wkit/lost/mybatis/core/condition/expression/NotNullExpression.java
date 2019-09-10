package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * NOT NULL
 * @param <T> 泛型类型
 * @author DT
 */
public class NotNullExpression<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = 4172306808770157321L;

    /**
     * 构造方法
     * @param property 属性
     */
    public NotNullExpression( String property ) {
        this( property, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param logic    逻辑操作
     */
    public NotNullExpression( String property, Logic logic ) {
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NOT_NULL;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     */
    public NotNullExpression( Criteria<T> criteria, String property ) {
        this( criteria, property, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     */
    public NotNullExpression( Criteria<T> criteria, String property, Logic logic ) {
        this.criteria = criteria;
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NOT_NULL;
    }
}
