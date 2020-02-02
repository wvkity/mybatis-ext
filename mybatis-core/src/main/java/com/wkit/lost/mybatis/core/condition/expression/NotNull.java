package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.criteria.Operator;

/**
 * NOT NULL
 * @param <T> 泛型类型
 * @author wvkity
 */
public class NotNull<T> extends AbstractNull<T> {

    private static final long serialVersionUID = 4172306808770157321L;

    /**
     * 构造方法
     * @param property 属性
     */
    public NotNull( String property ) {
        this( property, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param logic    逻辑操作
     */
    public NotNull( String property, Logic logic ) {
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NOT_NULL;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     */
    public NotNull( Criteria<T> criteria, String property ) {
        this( criteria, property, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     */
    public NotNull( Criteria<T> criteria, String property, Logic logic ) {
        this.criteria = criteria;
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NOT_NULL;
    }
}
