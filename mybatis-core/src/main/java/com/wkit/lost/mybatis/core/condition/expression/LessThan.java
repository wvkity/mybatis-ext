package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.criteria.Operator;

/**
 * 小于
 * @param <T> 泛型类型
 * @author wvkity
 */
public class LessThan<T> extends Simple<T> {

    private static final long serialVersionUID = -642459924223944256L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public LessThan( String property, Object value ) {
        super( property, value, Operator.LT, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public LessThan( String property, Object value, Logic logic ) {
        super( property, value, Operator.LT, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     */
    public LessThan( Criteria<T> criteria, String property, Object value ) {
        super( criteria, property, value, Operator.LT, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public LessThan( Criteria<T> criteria, String property, Object value, Logic logic ) {
        super( criteria, property, value, Operator.LT, logic );
    }
}
