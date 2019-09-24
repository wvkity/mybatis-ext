package com.wkit.lost.mybatis.resolver;

import com.wkit.lost.mybatis.core.meta.Attribute;

import java.util.List;

/**
 * 属性解析接口
 * @author DT
 */
public interface FieldResolver {

    /**
     * 获取所有属性信息
     * @param entity 实体类
     * @return 属性映射集合
     */
    List<Attribute> getAllAttributes( final Class<?> entity );

    /**
     * 从beaninfo获取所有属性信息
     * @param entity 实体类
     * @return 属性映射集合
     */
    List<Attribute> getAttributeFromBeanInfo( final Class<?> entity );
}
