package com.wvkity.mybatis.utils;

import com.wvkity.mybatis.core.metadata.AnnotationMetadata;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注解工具类
 * @author wvkity
 */
@Log4j2
public abstract class AnnotationUtil {

    /**
     * 检查对象是否存在注解
     * @param target 待检查对象
     * @return boolean
     */
    public static boolean hasAnnotation(final Class<?> target) {
        return target != null && !ArrayUtil.isEmpty(target.getDeclaredAnnotations());
    }

    /**
     * 检查对象是否存在指定注解
     * @param target          待检查对象
     * @param annotationClass 注解类
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Class<?> target, final Class<? extends Annotation> annotationClass) {
        return annotationClass != null && hasAnnotation(target) && target.isAnnotationPresent(annotationClass);
    }

    /**
     * 检查接口是否存在指定注解
     * @param target          待检查接口
     * @param annotationClass 注解类
     * @return boolean
     */
    public static boolean isAnnotationPresentForInterface(final Class<?> target, final Class<? extends Annotation> annotationClass) {
        return isAnnotationPresent(target, annotationClass) && target.isInterface();
    }

    /**
     * 检查类中是否存在指定注解
     * @param target              待检查类
     * @param annotationClassName 注解类名
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Class<?> target, final String annotationClassName) {
        if (StringUtil.isBlank(annotationClassName)) {
            return false;
        }
        return getAnnotations(target).stream().anyMatch(annotationClassName::equals);
    }

    /**
     * 检查类中是否存在指定注解
     * @param target              待检查类
     * @param annotationClass     注解类
     * @param annotationClassName 注解类名
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Class<?> target, final Class<? extends Annotation> annotationClass, final String annotationClassName) {
        return isAnnotationPresent(target, annotationClass) || wrapAnnotation(getAnnotations(target), annotationClassName);
    }

    /**
     * 检查属性上是否存在指定注解
     * @param field           {@link Field}
     * @param annotationClass 注解类
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Field field, final Class<? extends Annotation> annotationClass) {
        return annotationClass != null && field.isAnnotationPresent(annotationClass);
    }

    /**
     * 检查属性上是否存在指定注解
     * @param field               {@link Field}
     * @param annotationClassName 注解类名
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Field field, final String annotationClassName) {
        return wrapAnnotation(getAnnotations(field), annotationClassName);
    }

    /**
     * 检查属性上是否存在指定注解
     * @param field               {@link Field}
     * @param annotationClass     注解类
     * @param annotationClassName 注解类名
     * @return boolean
     * @see #isAnnotationPresent(Field, Class)
     * @see #isAnnotationPresent(Field, String)
     * @see #getAnnotations(Field)
     * @see #wrapAnnotation(Set, String)
     */
    public static boolean isAnnotationPresent(final Field field, final Class<? extends Annotation> annotationClass, final String annotationClassName) {
        return isAnnotationPresent(field, annotationClass) || wrapAnnotation(getAnnotations(field), annotationClassName);
    }

    /**
     * 检查方法上是否存在指定注解
     * @param method          {@link Method}
     * @param annotationClass 注解类名
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Method method, final Class<? extends Annotation> annotationClass) {
        return annotationClass != null && method.isAnnotationPresent(annotationClass);
    }

    /**
     * 检查方法上是否存在指定注解
     * @param method              {@link Method}
     * @param annotationClassName 注解类名
     * @return boolean
     */
    public static boolean isAnnotationPresent(final Method method, final String annotationClassName) {
        return wrapAnnotation(getAnnotations(method), annotationClassName);
    }

    /**
     * 检查方法上是否存在指定注解
     * @param method              {@link Method}
     * @param annotationClass     注解类
     * @param annotationClassName 注解类名
     * @return boolean
     * @see #isAnnotationPresent(Method, Class)
     * @see #isAnnotationPresent(Method, String)
     * @see #getAnnotations(Method)
     * @see #wrapAnnotation(Set, String)
     */
    public static boolean isAnnotationPresent(final Method method, final Class<? extends Annotation> annotationClass, final String annotationClassName) {
        return isAnnotationPresent(method, annotationClass) || wrapAnnotation(getAnnotations(method), annotationClassName);
    }

    /**
     * 检查是否存在指定注解
     * @param annotations         注解类名集合
     * @param annotationClassName 注解类名
     * @return boolean
     */
    public static boolean wrapAnnotation(final Set<String> annotations, final String annotationClassName) {
        return annotations != null && !annotations.isEmpty() && annotations.contains(annotationClassName);
    }

    /**
     * 获取属性对象所有注解类名
     * @param field {@link Field}
     * @return 注解类名列表
     */
    public static Set<String> getAnnotations(final Field field) {
        return field == null ? null : toSetString(field.getDeclaredAnnotations());
    }

    /**
     * 获取方法对象所有注解类名
     * @param method {@link Method}
     * @return boolean
     */
    public static Set<String> getAnnotations(final Method method) {
        return method == null ? null : toSetString(method.getDeclaredAnnotations());
    }

    /**
     * 获取指定类的所有注解
     * @param target 指定的类
     * @return 注解类名
     */
    public static Set<String> getAnnotations(final Class<?> target) {
        return hasAnnotation(target) ? toSetString(target.getAnnotations()) : new HashSet<>(0);
    }

    /**
     * 将注解数组对象转注解类名集合
     * @param annotations 注解数组
     * @return 类名集合
     */
    private static Set<String> toSetString(final Annotation... annotations) {
        return ArrayUtil.isEmpty(annotations) ? new HashSet<>(0) : Arrays.stream(annotations)
                .filter(Objects::nonNull)
                .map(Annotation::annotationType)
                .map(Class::getCanonicalName)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @SuppressWarnings("unchecked")
    public static AnnotationMetadata getAnnotationMetaObject(Class<?> target, String annotationClass) {
        if (target != null && !Ascii.isNullOrEmpty(annotationClass)) {
            try {
                Class<? extends Annotation> clazz = (Class<? extends Annotation>) Class.forName(annotationClass);
                return getAnnotationMetaObject(target.getAnnotation(clazz));
            } catch (Exception e) {
                log.warn("Cannot resolve entity class `{}` annotation `{}`: {}", target.getName(), annotationClass, e);
            }
        }
        return new AnnotationMetadata(new HashMap<>(0));
    }

    @SuppressWarnings("unchecked")
    public static AnnotationMetadata getAnnotationMetaObject(Field target, String annotationClass) {
        if (target != null && StringUtil.hasText(annotationClass)) {
            try {
                Class<? extends Annotation> clazz = (Class<? extends Annotation>) Class.forName(annotationClass);
                return getAnnotationMetaObject(target.getAnnotation(clazz));
            } catch (Exception e) {
                log.warn("Unable to parse attributes `{}` annotation `{}`: {}", target.getName(), annotationClass, e);
            }
        }
        return new AnnotationMetadata(new HashMap<>(0));
    }

    @SuppressWarnings("unchecked")
    public static AnnotationMetadata getAnnotationMetaObject(Annotation annotation) {
        if (annotation != null) {
            MetaObject metaObject = SystemMetaObject.forObject(annotation);
            Object value = metaObject.getValue("h.memberValues");
            if (value instanceof Map) {
                return new AnnotationMetadata((Map<String, Object>) value);
            }
        }
        return new AnnotationMetadata(new HashMap<>(0));
    }
}
