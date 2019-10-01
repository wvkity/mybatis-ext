package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.util.Collection;

/**
 * 查询列接口
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 * @author DT
 */
public interface Query<T, Context> {

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();

    /**
     * 添加查询列
     * @param property 属性
     * @return 当前对象
     */
    Context query( Property<T, ?> property );

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
    Context query( Property<T, ?>... properties );

    /**
     * 添加多个查询列
     * @param properties 属性数组
     * @return 当前对象
     */
    Context query( String... properties );

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
    Context exclude( Property<T, ?> property );

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
    Context exclude( Property<T, ?>... properties );

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
