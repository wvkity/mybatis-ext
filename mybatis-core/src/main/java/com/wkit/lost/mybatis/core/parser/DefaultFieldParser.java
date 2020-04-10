package com.wkit.lost.mybatis.core.parser;

import com.wkit.lost.mybatis.annotation.Entity;
import com.wkit.lost.mybatis.annotation.Transient;
import com.wkit.lost.mybatis.core.metadata.FieldWrapper;
import com.wkit.lost.mybatis.exception.MapperParserException;
import com.wkit.lost.mybatis.javax.JavaxPersistence;
import com.wkit.lost.mybatis.utils.AnnotationUtil;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 默认实体属性解析器
 * @author wvkity
 */
public class DefaultFieldParser implements FieldParser {

    @Override
    public List<FieldWrapper> parse(Class<?> entity) {
        return recursivelyRead(new ArrayList<>(), entity, 0);
    }

    @Override
    public List<FieldWrapper> parseFromBeanInfo(Class<?> entity) {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(entity);
        } catch (IntrospectionException e) {
            throw new MapperParserException(StringUtil.format("Failed to get bean information of `{}` " +
                    "class: `{}`", entity.getCanonicalName(), e.getMessage()), e);
        }
        PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
        return ArrayUtil.isEmpty(descriptors) ? new ArrayList<>(0) : new ArrayList<>(
                Stream.of(descriptors).filter(this::filter).map(this::convert)
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
    }

    /**
     * 递归读取指定实体类所有属性
     * @param wrappers 属性集合
     * @param entity   实体类
     * @param level    级别(标识是否为父类: [1+: true])
     * @return 属性集合
     */
    protected List<FieldWrapper> recursivelyRead(List<FieldWrapper> wrappers, final Class<?> entity, int level) {
        Field[] array = entity.getDeclaredFields();
        if (!ArrayUtil.isEmpty(array)) {
            List<Field> fields = Stream.of(array).filter(this::filter)
                    .collect(Collectors.toCollection(ArrayList::new));
            for (int i = 0, size = fields.size(); i < size; i++) {
                addTo(wrappers, fields.get(i), level, i);
            }
        }
        // 基类
        Class<?> superClass = entity.getSuperclass();
        if (superClass != null && !Object.class.equals(superClass)
                && (AnnotationUtil.isAnnotationPresent(superClass, Entity.class, JavaxPersistence.ENTITY)
                || (!Map.class.isAssignableFrom(superClass) && !Collection.class.isAssignableFrom(superClass)))) {
            return recursivelyRead(wrappers, superClass, ++level);
        }
        return wrappers;
    }

    /**
     * 过滤属性
     * @param field 属性
     * @return true/false
     */
    protected boolean filter(final Field field) {
        return field != null
                && !Modifier.isStatic(field.getModifiers())
                && !Modifier.isTransient(field.getModifiers())
                && !AnnotationUtil.isAnnotationPresent(field, Transient.class, JavaxPersistence.TRANSIENT);
    }

    /**
     * 过滤属性
     * @param descriptor 属性描述
     * @return true/false
     */
    protected boolean filter(final PropertyDescriptor descriptor) {
        return descriptor != null && !"class".equals(descriptor.getName());
    }

    /**
     * 将指定属性对象添加到属性集合
     * @param wrappers 属性集合
     * @param field    属性对象
     * @param level    级别
     * @param index    索引
     */
    protected void addTo(final List<FieldWrapper> wrappers, final Field field, final int level, final int index) {
        if (level == 0) {
            wrappers.add(convert(field));
        } else {
            wrappers.add(index, convert(field));
        }
    }

    /**
     * {@link Field}转{@link FieldWrapper}对象
     * @param field 属性对象
     * @return {@link FieldWrapper}对象
     */
    protected FieldWrapper convert(final Field field) {
        return new FieldWrapper(field);
    }

    /**
     * {@link PropertyDescriptor}转{@link FieldWrapper}对象
     * @param descriptor 属性描述
     * @return 属性信息
     */
    protected FieldWrapper convert(final PropertyDescriptor descriptor) {
        return new FieldWrapper(null, descriptor);
    }
}
