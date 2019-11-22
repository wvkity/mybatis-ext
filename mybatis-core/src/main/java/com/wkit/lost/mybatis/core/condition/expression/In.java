package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;

import java.util.Collection;

/**
 * IN范围条件
 * @param <T> 泛型类型
 * @author DT
 */
public class In<T> extends Range<T> {

    private static final long serialVersionUID = -900860959640244563L;

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     */
    public In( String property, Collection<Object> values ) {
        super( com.wkit.lost.mybatis.core.condition.Range.IN, property, values );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     */
    public In( Criteria<T> criteria, String property, Collection<Object> values ) {
        super( criteria, com.wkit.lost.mybatis.core.condition.Range.IN, property, values );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public In( String property, Collection<Object> values, Logic logic ) {
        super( com.wkit.lost.mybatis.core.condition.Range.IN, property, values, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public In( Criteria<T> criteria, String property, Collection<Object> values, Logic logic ) {
        super( criteria, com.wkit.lost.mybatis.core.condition.Range.IN, property, values, logic );
    }
}
