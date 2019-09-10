package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.io.Serializable;

/**
 * 值比较条件封装
 * @param <Context> 当前对象
 * @param <R>       lambda属性对象
 */
public interface Compare<Context, R> extends LambdaResolver<R>, Segment, Serializable {
    
    /**
     * 主键等于
     * @param value 主键值
     * @return 当前对象
     */
    Context idEq( Object value );
    
    /**
     * 或主键等于
     * @param value 主键值
     * @return 当前对象
     */
    Context orIdEq( Object value );
    
    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context eq( R property, Object value ) {
        return eq( lambdaToProperty( property ), value );
    }
    
    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context eq( String property, Object value );
    
    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orEq( R property, Object value ) {
        return orEq( lambdaToProperty( property ), value );
    }
    
    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orEq( String property, Object value );
    
    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context ne( R property, Object value ) {
        return ne( lambdaToProperty( property ), value );
    }
    
    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context ne( String property, Object value );
    
    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orNe( R property, Object value ) {
        return orNe( lambdaToProperty( property ), value );
    }
    
    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orNe( String property, Object value );
    
    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context lt( R property, Object value ) {
        return lt( lambdaToProperty( property ), value );
    }
    
    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context lt( String property, Object value );
    
    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLt( R property, Object value ) {
        return orLt( lambdaToProperty( property ), value );
    }
    
    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orLt( String property, Object value );
    
    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context le( R property, Object value ) {
        return le( lambdaToProperty( property ), value );
    }
    
    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context le( String property, Object value );
    
    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orLe( R property, Object value ) {
        return orLe( lambdaToProperty( property ), value );
    }
    
    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orLe( String property, Object value );
    
    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context gt( R property, Object value ) {
        return gt( lambdaToProperty( property ), value );
    }
    
    /**
     * 大于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context gt( String property, Object value );
    
    /**
     * 或大于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orGt( R property, Object value ) {
        return orGt( lambdaToProperty( property ), value );
    }
    
    /**
     * 或大于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orGt( String property, Object value );
    
    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context ge( R property, Object value ) {
        return ge( lambdaToProperty( property ), value );
    }
    
    /**
     * 大于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context ge( String property, Object value );
    
    /**
     * 或大于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context orGe( R property, Object value ) {
        return orGe( lambdaToProperty( property ), value );
    }
    
    /**
     * 或大于等于
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context orGe( String property, Object value );

}
