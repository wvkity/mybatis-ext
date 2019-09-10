package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.util.Collection;

/**
 * 查询列接口
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 * @param <R>       Lambda对象
 * @author DT
 */
public interface Query<T, Context, R> extends LambdaResolver<R> {

    /**
     * 添加查询列
     * @param property 属性
     * @return 当前对象
     */
    default Context query( R property ) {
        return query( lambdaToProperty( property ) );
    }

    /**
     * 添加查询列
     * @param property 属性
     * @return 当前对象
     */
    Context query( String property );

    /**
     * 添加多个查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    default Context query( R... properties ) {
        return query( lambdaToProperty( properties ) );
    }

    /**
     * 添加多个查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context query( String... properties ) {
        return query( ArrayUtil.toList( properties ) );
    }

    /**
     * 添加多个查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    Context query( Collection<String> properties );

    /**
     * 过滤查询列
     * @param property 属性
     * @return 当前对象
     */
    default Context exclude( R property ) {
        return exclude( lambdaToProperty( property ) );
    }

    /**
     * 过滤查询列
     * @param property 属性
     * @return 当前对象
     */
    Context exclude( String property );

    /**
     * 过滤查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    default Context exclude( R... properties ) {
        return exclude( lambdaToProperty( properties ) );
    }

    /**
     * 过滤查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context exclude( String... properties ) {
        return exclude( ArrayUtil.toList( properties ) );
    }

    /**
     * 过滤查询列
     * @param properties 属性集合
     * @return 当前对象
     */
    Context exclude( Collection<String> properties );

}
