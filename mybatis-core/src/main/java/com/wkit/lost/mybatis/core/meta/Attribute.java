package com.wkit.lost.mybatis.core.meta;

import com.wkit.lost.mybatis.annotation.Worker;
import com.wkit.lost.mybatis.utils.AnnotationUtil;
import com.wkit.lost.mybatis.annotation.Id;
import com.wkit.lost.mybatis.javax.JavaxPersistence;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 实体映射属性信息
 * @author DT
 */
@Accessors( chain = true )
@ToString
public class Attribute {

    /**
     * 属性
     */
    @Getter
    @Setter
    private Field field;

    /**
     * 属性名
     */
    @Getter
    @Setter
    private String name;

    /**
     * Java类型
     */
    @Getter
    @Setter
    private Class<?> javaType;

    /**
     * getter方法
     */
    @Getter
    @Setter
    private Method getter;

    /**
     * setter方法
     */
    @Getter
    @Setter
    private Method setter;

    /**
     * 构造方法
     * @param field 属性
     */
    public Attribute( Field field ) {
        this( field, null );
    }

    /**
     * 构造方法
     * @param field      属性
     * @param descriptor 属性描述
     */
    public Attribute( Field field, PropertyDescriptor descriptor ) {
        if ( field != null ) {
            this.name = field.getName();
            this.javaType = field.getType();
            this.field = field;
        }
        if ( descriptor != null ) {
            this.name = descriptor.getName();
            this.javaType = descriptor.getPropertyType();
            this.getter = descriptor.getReadMethod();
            this.setter = descriptor.getWriteMethod();
        }
    }

    /**
     * 复制属性值
     * @param other 其他属性对象
     */
    public void copyFromOther( Attribute other ) {
        if ( other != null ) {
            this.name = other.getName();
            this.javaType = other.getJavaType();
            this.getter = other.getGetter();
            this.setter = other.getSetter();
        }
    }

    /**
     * 检查是否存在主键注解
     * @return true: 存在 | false: 不存在
     */
    public boolean isAnnotationPresentOfId() {
        return isAnnotationPresent( Id.class, JavaxPersistence.ID ) || isAnnotationPresent( Worker.class );
    }

    /**
     * 检查是否存在指定注解
     * @param annotationClassName 注解类名
     * @return true: 存在 | false: 不存在
     */
    public boolean isAnnotationPresent( final String annotationClassName ) {
        return isAnnotationPresent( null, annotationClassName );
    }

    /**
     * 检查是否存在指定注解
     * @param annotationClass 注解类
     * @return true: 存在 | false: 不存在
     */
    public boolean isAnnotationPresent( final Class<? extends Annotation> annotationClass ) {
        return isAnnotationPresent( annotationClass, null );
    }

    /**
     * 检查是否存在指定注解
     * @param annotationClass     注解类
     * @param annotationClassName 注解类名
     * @return true: 存在 | false: 不存在
     */
    public boolean isAnnotationPresent( final Class<? extends Annotation> annotationClass, final String annotationClassName ) {
        boolean result = false;
        if ( field != null ) {
            result = AnnotationUtil.isAnnotationPresent( field, annotationClass, annotationClassName );
        }
        if ( !result && setter != null ) {
            result = AnnotationUtil.isAnnotationPresent( setter, annotationClass, annotationClassName );
        }
        if ( !result && getter != null ) {
            result = AnnotationUtil.isAnnotationPresent( getter, annotationClass, annotationClassName );
        }
        return result;
    }

    /**
     * 获取指定注解实例
     * @param annotationClass 注解类
     * @param <T>             注解类型
     * @return 注解实例
     */
    public <T extends Annotation> T getAnnotation( final Class<T> annotationClass ) {
        T result = null;
        if ( this.field != null ) {
            result = field.getAnnotation( annotationClass );
        }
        if ( result == null && this.setter != null ) {
            result = setter.getAnnotation( annotationClass );
        }
        if ( result == null && this.getter != null ) {
            result = getter.getAnnotation( annotationClass );
        }
        return result;
    }

    @Override
    public int hashCode() {
        return name == null ? 0 : name.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null || getClass() != obj.getClass() ) {
            return false;
        }
        Attribute that = ( Attribute ) obj;
        return !( name != null ? name.equals( that.getName() ) : that.name != null );
    }
    
}
