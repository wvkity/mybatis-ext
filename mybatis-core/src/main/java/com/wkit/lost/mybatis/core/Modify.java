package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.lambda.Property;

import java.util.Map;

public interface Modify<T, Context> {

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context modify( Property<T, ?> property, Object value );

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context modify( String property, Object value );

    /**
     * 添加多个更新列
     * @param map 列集合
     * @return 当前对象
     */
    Context modify( Map<String, Object> map );

    /**
     * 获取更新字段片段
     * @return SQL字符串
     */
    String getUpdateSegment();
}
