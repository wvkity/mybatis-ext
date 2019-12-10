package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * IS NULL条件
 * @param <T> 泛型类型
 * @author wvkity
 */
public class Null<T> extends AbstractNull<T> {

    private static final long serialVersionUID = -4643959508433967421L;

    /**
     * 构造方法
     * @param property 属性
     */
    public Null( String property ) {
        this( property, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param logic    逻辑操作
     */
    public Null( String property, Logic logic ) {
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NULL;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     */
    public Null( Criteria<T> criteria, String property ) {
        this( criteria, property, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param logic    逻辑操作
     */
    public Null( Criteria<T> criteria, String property, Logic logic ) {
        this.criteria = criteria;
        this.property = property;
        this.logic = logic;
        this.operator = Operator.NULL;
    }
}
