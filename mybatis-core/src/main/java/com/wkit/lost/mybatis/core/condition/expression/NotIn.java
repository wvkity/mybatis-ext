package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;

import java.util.Collection;

/**
 * NOT IN范围条件
 * @param <T> 泛型类型
 * @author wvkity
 */
public class NotIn<T> extends Range<T> {

    private static final long serialVersionUID = -5657463987007050669L;

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     */
    public NotIn( String property, Collection<Object> values ) {
        super( com.wkit.lost.mybatis.core.condition.Range.NOT_IN, property, values );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     */
    public NotIn( Criteria<T> criteria, String property, Collection<Object> values ) {
        super( criteria, com.wkit.lost.mybatis.core.condition.Range.NOT_IN, property, values );
    }

    /**
     * 构造方法
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public NotIn( String property, Collection<Object> values, Logic logic ) {
        super( com.wkit.lost.mybatis.core.condition.Range.NOT_IN, property, values, logic );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param property 属性
     * @param values   值
     * @param logic    逻辑操作
     */
    public NotIn( Criteria<T> criteria, String property, Collection<Object> values, Logic logic ) {
        super( criteria, com.wkit.lost.mybatis.core.condition.Range.NOT_IN, property, values, logic );
    }
}
