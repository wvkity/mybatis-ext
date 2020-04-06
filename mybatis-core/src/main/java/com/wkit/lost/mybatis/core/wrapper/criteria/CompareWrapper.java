package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.LambdaConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 比较条件包装接口
 * @param <Chain> 子类
 * @param <P>     Lambda类
 */
public interface CompareWrapper<Chain extends CompareWrapper<Chain, P>, P> extends LambdaConverter<P> {

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
     * @param p1 属性1
     * @param v1 值1
     * @param p2 属性2
     * @param v2 值2
     * @return {@code this}
     */
    default Chain eq( P p1, Object v1, P p2, Object v2 ) {
        Map<String, Object> props = new HashMap<>( 2 );
        props.put( lambdaToProperty( p1 ), v1 );
        props.put( lambdaToProperty( p2 ), v2 );
        return eq( props );
    }

    /**
     * 等于
     * @param p1 属性1
     * @param v1 值1
     * @param p2 属性2
     * @param v2 值2
     * @param p3 属性3
     * @param v3 值3
     * @return {@code this}
     */
    default Chain eq( P p1, Object v1, P p2, Object v2, P p3, Object v3 ) {
        Map<String, Object> props = new HashMap<>( 3 );
        props.put( lambdaToProperty( p1 ), v1 );
        props.put( lambdaToProperty( p2 ), v2 );
        props.put( lambdaToProperty( p3 ), v3 );
        return eq( props );
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain eq( String property, Object value );

    /**
     * 等于
     * @param p1 属性1
     * @param v1 值1
     * @param p2 属性2
     * @param v2 值2
     * @return {@code this}
     */
    default Chain eq( String p1, Object v1, String p2, Object v2 ) {
        Map<String, Object> props = new HashMap<>( 2 );
        props.put( p1, v1 );
        props.put( p2, v2 );
        return eq( props );
    }

    /**
     * 等于
     * @param p1 属性1
     * @param v1 值1
     * @param p2 属性2
     * @param v2 值2
     * @param p3 属性3
     * @param v3 值3
     * @return {@code this}
     */
    default Chain eq( String p1, Object v1, String p2, Object v2, String p3, Object v3 ) {
        Map<String, Object> props = new HashMap<>( 3 );
        props.put( p1, v1 );
        props.put( p2, v2 );
        props.put( p3, v3 );
        return eq( props );
    }

    /**
     * 等于
     * @param properties 属性-值集合
     * @return {@code this}
     */
    Chain eq( Map<String, Object> properties );

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
    Chain immediateEq( String column, Object value );

    /**
     * 等于
     * @param c1 字段1
     * @param v1 值1
     * @param c2 字段2
     * @param v2 值2
     * @return {@code this}
     */
    default Chain immediateEq( String c1, Object v1, String c2, Object v2 ) {
        Map<String, Object> columns = new HashMap<>( 2 );
        columns.put( c1, v1 );
        columns.put( c2, v2 );
        return immediateEq( columns );
    }

    /**
     * 等于
     * @param c1 字段1
     * @param v1 值1
     * @param c2 字段2
     * @param v2 值2
     * @param c3 字段3
     * @param v3 值3
     * @return {@code this}
     */
    default Chain immediateEq( String c1, Object v1, String c2, Object v2, String c3, Object v3 ) {
        Map<String, Object> columns = new HashMap<>( 3 );
        columns.put( c1, v1 );
        columns.put( c2, v2 );
        columns.put( c3, v3 );
        return immediateEq( columns );
    }

    /**
     * 等于
     * @param columns 字段-值集合
     * @return {@code this}
     */
    Chain immediateEq( Map<String, Object> columns );

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateEq( String tableAlias, String column, Object value );

    /**
     * 等于
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param v1         值1
     * @param c2         字段2
     * @param v2         值2
     * @return {@code this}
     */
    default Chain immediateEq( String tableAlias, String c1, Object v1, String c2, Object v2 ) {
        Map<String, Object> columns = new HashMap<>( 2 );
        columns.put( c1, v1 );
        columns.put( c2, v2 );
        return immediateEq( tableAlias, columns );
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param v1         值1
     * @param c2         字段2
     * @param v2         值2
     * @param c3         字段3
     * @param v3         值3
     * @return {@code this}
     */
    default Chain immediateEq( String tableAlias, String c1, Object v1, String c2, Object v2, String c3, Object v3 ) {
        Map<String, Object> columns = new HashMap<>( 3 );
        columns.put( c1, v1 );
        columns.put( c2, v2 );
        columns.put( c3, v3 );
        return immediateEq( tableAlias, columns );
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param columns    字段-值集合
     * @return {@code this}
     */
    Chain immediateEq( String tableAlias, Map<String, Object> columns );

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

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain ne( P property, Object value ) {
        return ne( lambdaToProperty( property ), value );
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain ne( String property, Object value );

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orNe( P property, Object value ) {
        return orNe( lambdaToProperty( property ), value );
    }

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orNe( String property, Object value );

    /**
     * 不等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateNe( String column, Object value );

    /**
     * 不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateNe( String tableAlias, String column, Object value );

    /**
     * 或不等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateNe( String column, Object value );

    /**
     * 或不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateNe( String tableAlias, String column, Object value );

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain lt( P property, Object value ) {
        return lt( lambdaToProperty( property ), value );
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain lt( String property, Object value );

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLt( P property, Object value ) {
        return orLt( lambdaToProperty( property ), value );
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orLt( String property, Object value );

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateLt( String column, Object value );

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateLt( String tableAlias, String column, Object value );

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateLt( String column, Object value );

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateLt( String tableAlias, String column, Object value );

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain le( P property, Object value ) {
        return le( lambdaToProperty( property ), value );
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain le( String property, Object value );

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orLe( P property, Object value ) {
        return orLe( lambdaToProperty( property ), value );
    }

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orLe( String property, Object value );

    /**
     * 小于等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateLe( String column, Object value );

    /**
     * 小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateLe( String tableAlias, String column, Object value );

    /**
     * 或小于等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateLe( String column, Object value );

    /**
     * 或小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateLe( String tableAlias, String column, Object value );

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain gt( P property, Object value ) {
        return gt( lambdaToProperty( property ), value );
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain gt( String property, Object value );

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orGt( P property, Object value ) {
        return orGt( lambdaToProperty( property ), value );
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orGt( String property, Object value );

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateGt( String column, Object value );

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateGt( String tableAlias, String column, Object value );

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateGt( String column, Object value );

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateGt( String tableAlias, String column, Object value );

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain ge( P property, Object value ) {
        return ge( lambdaToProperty( property ), value );
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain ge( String property, Object value );

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    default Chain orGe( P property, Object value ) {
        return orGe( lambdaToProperty( property ), value );
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orGe( String property, Object value );

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain immediateGe( String column, Object value );

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain immediateGe( String tableAlias, String column, Object value );

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orImmediateGe( String column, Object value );

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orImmediateGe( String tableAlias, String column, Object value );

}

