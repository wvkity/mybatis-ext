package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.io.Serializable;
import java.util.Collection;

/**
 * 范围条件接口
 * @param <Context> 当前对象
 * @param <R>       lambda属性对象
 * @author wvkity
 */
public interface Range<Context, R> extends LambdaResolver<R>, Segment, Serializable {

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context in( R property, Object... values ) {
        return in( lambdaToProperty( property ), values );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context in( String property, Object... values ) {
        return in( property, ArrayUtil.toList( values ) );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context in( R property, Collection<Object> values ) {
        return in( lambdaToProperty( property ), values );
    }

    /**
     * IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context in( String property, Collection<Object> values );

    /**
     * 或IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orIn( R property, Object... values ) {
        return orIn( lambdaToProperty( property ), values );
    }

    /**
     * 或IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orIn( String property, Object... values ) {
        return orIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orIn( R property, Collection<Object> values ) {
        return orIn( lambdaToProperty( property ), values );
    }

    /**
     * 或IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context orIn( String property, Collection<Object> values );

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context notIn( R property, Object... values ) {
        return notIn( lambdaToProperty( property ), values );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context notIn( String property, Object... values ) {
        return notIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context notIn( R property, Collection<Object> values ) {
        return notIn( lambdaToProperty( property ), values );
    }

    /**
     * NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context notIn( String property, Collection<Object> values );

    /**
     * 或NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orNotIn( R property, Object... values ) {
        return orNotIn( lambdaToProperty( property ), values );
    }

    /**
     * 或NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orNotIn( String property, Object... values ) {
        return orNotIn( property, ArrayUtil.toList( values ) );
    }

    /**
     * 或NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    default Context orNotIn( R property, Collection<Object> values ) {
        return orNotIn( lambdaToProperty( property ), values );
    }

    /**
     * 或NOT IN范围
     * @param property 属性
     * @param values   值
     * @return 当前对象
     */
    Context orNotIn( String property, Collection<Object> values );
}
