package com.wkit.lost.mybatis.core.meta;

import com.wkit.lost.mybatis.resolver.FieldResolver;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.AnnotationUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.annotation.Entity;
import com.wkit.lost.mybatis.annotation.Transient;
import com.wkit.lost.mybatis.core.meta.Attribute;
import com.wkit.lost.mybatis.exception.MapperResolverException;
import com.wkit.lost.mybatis.javax.JavaxPersistence;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 默认属性解析器
 * @author wvkity
 */
public class DefaultFieldResolver implements FieldResolver {

    @Override
    public List<Attribute> getAllAttributes( Class<?> entity ) {
        List<Attribute> fromFieldAttrs = getAttributesFromFieldOfRecursively( entity, new ArrayList<>(), 0 );
        List<Attribute> fromBeanInfoAttrs = getAttributeFromBeanInfo( entity );
        Set<Attribute> usedAttrs = new HashSet<>();
        return fromFieldAttrs.isEmpty() ? fromFieldAttrs : new ArrayList<>( fromFieldAttrs.stream().peek( fieldAttr -> {
            Attribute attribute = fromBeanInfoAttrs.stream()
                    .filter( beanInfoAttr -> !usedAttrs.contains( beanInfoAttr ) && StringUtil.equals( beanInfoAttr.getName(), fieldAttr.getName() ) )
                    .findFirst().orElse( null );
            if ( attribute != null ) {
                usedAttrs.add( attribute );
                fieldAttr.setJavaType( attribute.getJavaType() );
            }
        } ).collect( Collectors.toCollection( LinkedHashSet::new ) ) );
    }

    @Override
    public List<Attribute> getAttributeFromBeanInfo( Class<?> entity ) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo( entity );
        } catch ( IntrospectionException e ) {
            throw new MapperResolverException( StringUtil.format( "Failed to get bean information of `{}` class: `{}`", entity.getCanonicalName(), e.getMessage() ), e );
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        return ArrayUtil.isEmpty( descriptors ) ? new ArrayList<>() : new ArrayList<>( Stream.of( descriptors )
                .filter( this::filter )
                .map( this::valueOf )
                .collect( Collectors.toCollection( LinkedHashSet::new ) ) );
    }

    /**
     * 递归获取指定类的所有属性
     * @param entity     指定类
     * @param attributes 属性集合
     * @param level      是否为父级(1+: true)
     * @return 属性集合
     */
    protected List<Attribute> getAttributesFromFieldOfRecursively( final Class<?> entity, List<Attribute> attributes, int level ) {
        Field[] fields = entity.getDeclaredFields();
        if ( !ArrayUtil.isEmpty( fields ) ) {
            List<Field> attrs = Stream.of( fields )
                    .filter( field -> !Modifier.isStatic( field.getModifiers() ) && !Modifier.isTransient( field.getModifiers() ) )
                    .filter( this::filter )
                    .collect( Collectors.toCollection( ArrayList::new ) );
            int size = attrs.size();
            for ( int i = 0; i < size; i++ ) {
                addTo( attributes, attrs.get( i ), level, i );
            }
        }
        // 父类
        Class<?> superEntity = entity.getSuperclass();
        if ( superEntity != null && !Object.class.equals( superEntity )
                && ( AnnotationUtil.isAnnotationPresent( superEntity, Entity.class, JavaxPersistence.ENTITY )
                || ( !Map.class.isAssignableFrom( superEntity ) && !Collection.class.isAssignableFrom( superEntity ) ) ) ) {
            return getAttributesFromFieldOfRecursively( superEntity, attributes, ++level );
        }
        return attributes;
    }

    /**
     * 给指定属性集合添加属性信息
     * @param attributes 指定属性集合
     * @param field      属性
     * @param level      级别
     * @param index      索引
     */
    protected void addTo( List<Attribute> attributes, final Field field, int level, int index ) {
        if ( level == 0 ) {
            // self
            attributes.add( transform( field ) );
        } else {
            // super
            attributes.add( index, transform( field ) );
        }
    }

    /**
     * 过滤@Transient注解修饰属性
     * @param field 属性对象
     * @return true | false
     */
    protected boolean filter( final Field field ) {
        return field != null && !AnnotationUtil.isAnnotationPresent( field, Transient.class, JavaxPersistence.TRANSIENT );
    }

    /**
     * 过滤Class
     * @param descriptor 属性描述
     * @return true | false
     */
    protected boolean filter( final PropertyDescriptor descriptor ) {
        return descriptor != null && !descriptor.getName().equals( "class" );
    }

    /**
     * {@link PropertyDescriptor}转{@link Attribute}对象
     * @param descriptor 属性描述
     * @return {@link Attribute}(属性信息)
     */
    protected Attribute valueOf( final PropertyDescriptor descriptor ) {
        return new Attribute( null, descriptor );
    }

    /**
     * {@link Field}转{@link Attribute}对象
     * @param field {@link Field}(属性对象)
     * @return {@link Attribute}对象
     */
    protected Attribute transform( final Field field ) {
        return new Attribute( field );
    }
}
