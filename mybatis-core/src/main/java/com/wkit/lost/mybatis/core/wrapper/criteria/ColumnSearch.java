package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

/**
 * 字段搜索接口
 * @param <T> 实体类型
 * @author wvkity
 */
public interface ColumnSearch<T> {

    /**
     * 搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    ColumnWrapper searchColumn( Property<T, ?> property );

    /**
     * 根据属性搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    ColumnWrapper searchColumn( String property );
}
