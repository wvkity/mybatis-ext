package com.wkit.lost.mybatis.handler;

import com.wkit.lost.mybatis.core.metadata.Field;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.metadata.DefaultFieldResolver;
import com.wkit.lost.mybatis.resolver.FieldResolver;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 表字段映射处理器
 * @author wvkity
 */
public class ColumnHandler {

    /**
     * 默认属性解析器
     */
    public static final FieldResolver DEFAULT_RESOLVER = new DefaultFieldResolver();

    /**
     * 获取指定类的所有属性
     * @param entity   实体类
     * @param resolver 属性解析器
     * @return 属性集合
     */
    public static List<Field> getAllAttributes( final Class<?> entity, FieldResolver resolver ) {
        if ( resolver == null ) {
            resolver = DEFAULT_RESOLVER;
        }
        return resolver.getAllAttributes( entity );
    }

    /**
     * 根据指定类的BeanInfo获取所有属性
     * @param entity   实体类
     * @param resolver 属性解析器
     * @return 属性集合
     */
    public static List<Field> getAttributesFromBeanInfo( final Class<?> entity, FieldResolver resolver ) {
        if ( resolver == null ) {
    resolver = DEFAULT_RESOLVER;
    }
    return resolver.getAttributeFromBeanInfo( entity );
    }

    /**
     * 合并属性
     * @param entity   实体类
     * @param resolver 属性解析器
     * @return 属性集合
     */
    public static List<Field> merge( final Class<?> entity, FieldResolver resolver ) {
        List<Field> fromFieldAttrs = getAllAttributes( entity, resolver );
        List<Field> fromBeanInfoAttrs = getAttributesFromBeanInfo( entity, resolver );
        Set<Field> usedAttrs = new HashSet<>();
        Set<Field> allAttrs = fromFieldAttrs.stream()
                .peek( fieldAttr -> {
                    Field field = fromBeanInfoAttrs.stream()
                            .filter( beanInfoAttr -> !usedAttrs.contains( beanInfoAttr ) && StringUtil.equals( beanInfoAttr.getName(), fieldAttr.getName() ) )
                            .findFirst()
                            .orElse( null );
                    if ( field != null ) {
                        fieldAttr.copyFromOther( field );
                        usedAttrs.add( field );
                    }
                } ).collect( Collectors.toCollection( LinkedHashSet::new ) );
        if ( !fromBeanInfoAttrs.isEmpty() ) {
            allAttrs.addAll( fromBeanInfoAttrs.stream().filter( attribute -> !usedAttrs.contains( attribute ) ).collect( Collectors.toList() ) );
        }
        return new ArrayList<>( allAttrs );
    }
}
