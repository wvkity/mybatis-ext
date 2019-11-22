package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;

/**
 * 等于
 * @param <T> 泛型类型
 * @author DT
 */
public class Equal<T> extends Simple<T> {

    private static final long serialVersionUID = 6766253424375291700L;

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     */
    public Equal( String property, Object value ) {
        super( property, value, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public Equal( String property, Object value, Logic logic ) {
        super( property, value, Operator.EQ, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     */
    public Equal( Criteria<T> criteria, String property, Object value ) {
        super( criteria, property, value, Operator.EQ, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询条件对象
     * @param property 属性
     * @param value    值
     * @param logic    逻辑操作
     */
    public Equal( Criteria<T> criteria, String property, Object value, Logic logic ) {
        super( criteria, property, value, Operator.EQ, logic );
    }
}
