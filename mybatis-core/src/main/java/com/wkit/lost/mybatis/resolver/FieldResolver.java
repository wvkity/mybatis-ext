package com.wkit.lost.mybatis.resolver;

import com.wkit.lost.mybatis.core.metadata.Field;

import java.util.List;

/**
 * 属性解析接口
 * @author wvkity
 */
@Deprecated
public interface FieldResolver {

    /**
     * 获取所有属性信息
     * @param entity 实体类
     * @return 属性映射集合
     */
    List<Field> getAllAttributes( final Class<?> entity );

    /**
     * 从BeanInfo获取所有属性信息
     * @param entity 实体类
     * @return 属性映射集合
     */
    List<Field> getAttributeFromBeanInfo( final Class<?> entity );
}
