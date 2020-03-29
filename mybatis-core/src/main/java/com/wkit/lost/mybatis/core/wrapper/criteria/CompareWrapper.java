package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.lambda.LambdaConverter;

/**
 * 比较条件包装接口
 * @param <Chain> 当前对象
 * @param <P>     Lambda类
 */
public interface CompareWrapper<Chain, P> extends LambdaConverter<P> {

    /**
     * 主键等于
     * @param value 值
     * @return {@code this}
     */
    Chain idEq( Object value );

    /**
     * 或主键等于
     * @param value 值
     * @return {@code this}
     */
    Chain orIdEq( Object value );

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain eq( P property, Object value ) {
        return eq( lambdaToProperty( property ), value );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain eq( String property, Object value );

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orEq( P property, Object value ) {
        return orEq( lambdaToProperty( property ), value );
    }

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orEq( String property, Object value );

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediatePureEq( String column, Object value );

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateEq( String column, Object value );

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateEq( String tableAlias, String column, Object value );

    /**
     * 或等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediatePureEq( String column, Object value );

    /**
     * 或等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateEq( String column, Object value );

    /**
     * 或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateEq( String tableAlias, String column, Object value );
}
