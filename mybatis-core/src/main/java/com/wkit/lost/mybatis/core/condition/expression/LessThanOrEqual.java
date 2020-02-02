package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import com.wkit.lost.mybatis.core.criteria.Operator;

/**
 * 小于或等于
 * @param <T> 泛型类型
 * @author wvkity
 */
public class LessThanOrEqual<T> extends Simple<T> {

    private static final long serialVersionUID = -929131251679870343L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public LessThanOrEqual( String property, Object value ) {
        super( property, value, Operator.LE, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public LessThanOrEqual( String property, Object value, Logic logic ) {
        super( property, value, Operator.LE, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     */
    public LessThanOrEqual( Criteria<T> criteria, String property, Object value ) {
        super( criteria, property, value, Operator.LE, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public LessThanOrEqual( Criteria<T> criteria, String property, Object value, Logic logic ) {
        super( criteria, property, value, Operator.LE, logic );
    }
}
