package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.utils.AnnotationUtil;
import lombok.AllArgsConstructor;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 注解元数据
 * @author wvkity
 */
@AllArgsConstructor
public class AnnotationMetadata {

    /**
     * 注解参数值
     */
    private Map<String, Object> values;

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public byte byteValue(String property, byte defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == byte.class) {
                return (byte) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public char charValue(String property, char defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == char.class) {
                return (char) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: false)
     * @param property 属性名称
     * @return 值
     */
    public boolean booleanValue(String property) {
        return booleanValue(property, false);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public boolean booleanValue(String property, boolean defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == boolean.class) {
                return (boolean) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: 0)
     * @param property 属性名称
     * @return 值
     */
    public short shortValue(String property) {
        return shortValue(property, (short) 0);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public short shortValue(String property, short defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == short.class) {
                return (short) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: 0)
     * @param property 属性名称
     * @return 值
     */
    public int intValue(String property) {
        return intValue(property, 0);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public int intValue(String property, int defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == int.class) {
                return (int) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: 0f)
     * @param property 属性名称
     * @return 值
     */
    public float floatValue(String property) {
        return floatValue(property, 0f);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public float floatValue(String property, float defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == float.class) {
                return (float) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: 0d)
     * @param property 属性名称
     * @return 值
     */
    public double doubleValue(String property) {
        return doubleValue(property, 0d);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public double doubleValue(String property, double defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == double.class) {
                return (double) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: 0)
     * @param property 属性名称
     * @return 值
     */
    public long longValue(String property) {
        return longValue(property, 0L);
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public long longValue(String property, long defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value.getClass() == long.class) {
                return (long) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值(默认值: "")
     * @return 值
     */
    public String stringValue() {
        return stringValue("value", "");
    }

    /**
     * 获取值(默认值: "")
     * @param property 属性名称
     * @return 值
     */
    public String stringValue(String property) {
        return stringValue(property, "");
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public String stringValue(String property, String defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value instanceof String) {
                return (String) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public Class<?> classValue(String property, Class<?> defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value instanceof Class) {
                return (Class<?>) value;
            }
        }
        return defaultValue;
    }

    /**
     * 获取值
     * @param property     属性名称
     * @param defaultValue 默认值
     * @return 值
     */
    public Enum<? extends Enum<?>> enumValue(String property, Enum<? extends Enum<?>> defaultValue) {
        if (values.containsKey(property)) {
            Object value = values.get(property);
            if (value instanceof Enum) {
                return (Enum<? extends Enum<?>>) value;
            }
        }
        return defaultValue;
    }

    public static AnnotationMetadata forObject(Class<?> clazz, String annotationClass) {
        return AnnotationUtil.getAnnotationMetaObject(clazz, annotationClass);
    }

    public static AnnotationMetadata forObject(Field field, String annotationClass) {
        return AnnotationUtil.getAnnotationMetaObject(field, annotationClass);
    }

}

