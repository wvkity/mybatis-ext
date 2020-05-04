package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.annotation.Id;
import com.wvkity.mybatis.annotation.SnowflakeSequence;
import com.wvkity.mybatis.javax.JavaxPersistence;
import com.wvkity.mybatis.utils.AnnotationUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * 属性包装器
 * @author wvkity
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class FieldWrapper {

    /**
     * 属性
     */
    private final Field field;

    /**
     * 属性名
     */
    private String name;

    /**
     * Java类型
     */
    private Class<?> javaType;

    /**
     * getter方法
     */
    private Method getter;

    /**
     * setter方法
     */
    private Method setter;

    /**
     * 构造方法
     * @param field 属性
     */
    public FieldWrapper(Field field) {
        this.field = field;
        this.name = field.getName();
        this.javaType = field.getType();
    }

    /**
     * 构造方法
     * @param field      属性
     * @param descriptor 属性描述
     */
    public FieldWrapper(Field field, PropertyDescriptor descriptor) {
        if (field != null) {
            this.field = field;
            this.name = field.getName();
            this.javaType = field.getType();
        } else {
            this.field = null;
        }
        if (descriptor != null) {
            this.name = descriptor.getName();
            this.javaType = descriptor.getPropertyType();
            this.getter = descriptor.getReadMethod();
            this.setter = descriptor.getWriteMethod();
        }
    }

    /**
     * 复制部分属性信息
     * @param other 其他属性对象
     */
    public void copy(final FieldWrapper other) {
        if (other != null) {
            this.name = other.getName();
            this.javaType = other.getJavaType();
            this.getter = other.getGetter();
            this.setter = other.getSetter();
        }
    }

    /**
     * 检查属性是否存在指定注解
     * @param annotationClass      注解类
     * @param otherAnnotationClass 注解全类名
     * @return true: 是, false: 否
     */
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass,
                                       final String otherAnnotationClass) {
        return (this.field != null && AnnotationUtil.isAnnotationPresent(field, annotationClass,
                otherAnnotationClass))
                || (this.setter != null && AnnotationUtil.isAnnotationPresent(this.setter, annotationClass,
                otherAnnotationClass))
                || (this.getter != null && AnnotationUtil.isAnnotationPresent(this.getter, annotationClass,
                otherAnnotationClass));
    }

    /**
     * 检查属性是否存在指定注解
     * @param annotationClass 注解类
     * @return true: 是, false: 否
     */
    public boolean isAnnotationPresent(final Class<? extends Annotation> annotationClass) {
        return isAnnotationPresent(annotationClass, null);
    }

    /**
     * 检查属性是否存在指定注解
     * @param annotationClass 注解全类名
     * @return true: 是, false: 否
     */
    public boolean isAnnotationPresent(final String annotationClass) {
        return isAnnotationPresent(null, annotationClass);
    }

    /**
     * 检查属性是否存在主键注解
     * @return true: 是, false: 否
     */
    public boolean isAnnotationPresentOfPrimaryKey() {
        return isAnnotationPresent(Id.class, JavaxPersistence.ID) || isAnnotationPresent(SnowflakeSequence.class);
    }

    /**
     * 获取指定注解对象
     * @param annotationClass 注解类
     * @param <T>             注解泛型
     * @return 注解实例
     */
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        if (this.field != null) {
            return Optional.ofNullable(field.getAnnotation(annotationClass))
                    .orElse(Optional.ofNullable(this.setter != null ? setter.getAnnotation(annotationClass) : null)
                            .orElse(Optional.ofNullable(this.getter != null ? getter.getAnnotation(annotationClass) : null)
                                    .orElse(null)));
        }
        return null;
    }
}
