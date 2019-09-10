package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.condition.Range;

import java.util.Collection;

/**
 * IN范围条件
 * @param <T> 泛型类型
 * @author DT
 */
public class InExpression<T> extends RangeExpression<T> {

    private static final long serialVersionUID = -900860959640244563L;

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     */
    public InExpression( String property, Collection<Object> values ) {
        super( Range.IN, property, values );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     */
    public InExpression( Criteria<T> criteria, String property, Collection<Object> values ) {
        super( criteria, Range.IN, property, values );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public InExpression( String property, Collection<Object> values, Logic logic ) {
        super( Range.IN, property, values, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public InExpression( Criteria<T> criteria, String property, Collection<Object> values, Logic logic ) {
        super( criteria, Range.IN, property, values, logic );
    }
}
