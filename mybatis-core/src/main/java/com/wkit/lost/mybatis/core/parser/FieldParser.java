package com.wkit.lost.mybatis.core.parser;

import com.wkit.lost.mybatis.core.metadata.FieldWrapper;

import java.util.List;

/**
 * 实体属性解析器
 * @author wvkity
 */
public interface FieldParser {

    /**
     * 解析实体属性
     * @param entity 实体类
     * @return 属性列表
     */
    List<FieldWrapper> parse( final Class<?> entity );

    /**
     * 从BeanInfo中解析实体属性
     * @param entity 实体类
     * @return 属性列表
     */
    List<FieldWrapper> parseFromBeanInfo(final Class<?> entity);
}
