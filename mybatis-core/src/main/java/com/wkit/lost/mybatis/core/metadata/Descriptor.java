package com.wkit.lost.mybatis.core.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * JAVA信息
 * @author wvkity
 */
@Getter
@ToString
@EqualsAndHashCode
public final class Descriptor {

    /**
     * 字段信息对象
     */
    private final Field field;

    /**
     * JAVA类型
     */
    private final Class<?> javaType;

    /**
     * 属性名
     */
    private final String name;

    /**
     * GET方法
     */
    private final Method getter;

    /**
     * SET方法
     */
    private final Method setter;

    /**
     * 构造方法
     * @param field    字段
     * @param javaType JAVA类型
     * @param name     属性
     * @param getter   GET方法
     * @param setter   SET方法
     */
    public Descriptor(Field field, Class<?> javaType, String name, Method getter, Method setter) {
        this.field = field;
        this.javaType = javaType;
        this.name = name;
        this.getter = getter;
        this.setter = setter;
    }
}
