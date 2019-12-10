package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * 不等于
 * @param <T> 泛型类型
 * @author wvkity
 */
public class NotEqual<T> extends Simple<T> {

    private static final long serialVersionUID = 3636224606863688778L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public NotEqual( String property, Object value ) {
        super( property, value, Operator.NE, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public NotEqual( String property, Object value, Logic logic ) {
        super( property, value, Operator.NE, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     */
    public NotEqual( Criteria<T> criteria, String property, Object value ) {
        super( criteria, property, value, Operator.NE, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public NotEqual( Criteria<T> criteria, String property, Object value, Logic logic ) {
        super( criteria, property, value, Operator.NE, logic );
    }
}
