package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * 大于
 * @param <T> 泛型类型
 * @author wvkity
 */
public class GreaterThan<T> extends Simple<T> {

    private static final long serialVersionUID = -2829337435315273148L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public GreaterThan( String property, Object value ) {
        super( property, value, Operator.GT, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public GreaterThan( String property, Object value, Logic logic ) {
        super( property, value, Operator.GT, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     */
    public GreaterThan( Criteria<T> criteria, String property, Object value ) {
        super( criteria, property, value, Operator.GT, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public GreaterThan( Criteria<T> criteria, String property, Object value, Logic logic ) {
        super( criteria, property, value, Operator.GT, logic );
    }
}
