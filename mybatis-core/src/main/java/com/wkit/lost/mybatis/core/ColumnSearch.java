package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 字段搜索接口
 * @param <T> 实体类型
 * @author DT
 */
public interface ColumnSearch<T> {

    /**
     * 搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    Column searchColumn( Property<T, ?> property );

    /**
     * 根据属性搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    Column searchColumn( String property );
}
