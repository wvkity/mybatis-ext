package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.lambda.Property;

import java.util.Map;

public interface Modify<T, Context> {

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context update( Property<T, ?> property, Object value );

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context update( String property, Object value );

    /**
     * 添加多个更新列
     * @param map 列集合
     * @return 当前对象
     */
    Context update( Map<String, Object> map );

    /**
     * 修改版本
     * @param version 版本号
     * @return 当前对象
     */
    Context updateVersion( Object version );

    /**
     * 获取更新字段片段
     * @return SQL字符串
     */
    String getUpdateSegment();
}
