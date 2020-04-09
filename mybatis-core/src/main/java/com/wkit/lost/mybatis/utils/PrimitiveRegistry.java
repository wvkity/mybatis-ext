package com.wkit.lost.mybatis.utils;

import java.util.IdentityHashMap;
import java.util.Map;

public final class PrimitiveRegistry {

    private PrimitiveRegistry() {
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVE_WRAPPER_TYPE_CACHE = new IdentityHashMap<>( 8 );

    static {
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Boolean.class, boolean.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Byte.class, byte.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Character.class, char.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Double.class, double.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Float.class, float.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Integer.class, int.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Long.class, long.class );
        PRIMITIVE_WRAPPER_TYPE_CACHE.put( Short.class, short.class );
    }

    /**
     * 检查指定对象的类型是否为基本类型或包装类型
     * @param target 指定对象
     * @return true: 是 false: 否
     */
    public static boolean isPrimitiveOrWrapper( Object target ) {
        return target != null && isPrimitiveOrWrapper( target.getClass() );
    }

    /**
     * 检查指定类是否为基本类型或包装类型
     * @param clazz 指定类
     * @return true: 是 false: 否
     */
    public static boolean isPrimitiveOrWrapper( Class<?> clazz ) {
        return clazz != null && ( clazz.isPrimitive() || PRIMITIVE_WRAPPER_TYPE_CACHE.containsKey( clazz ) );
    }

    /**
     * 字符串类型值转换成基本数据类型值
     * @param javaType Java类型
     * @param value    值
     * @return 转换后的值
     */
    public static Object convert( Class<?> javaType, String value ) {
        if ( javaType == null || value == null || javaType == String.class ) {
            return value;
        }
        if ( javaType == Long.class || javaType == long.class ) {
            return Long.valueOf( value );
        }
        if ( javaType == Integer.class || javaType == int.class ) {
            return Integer.valueOf( value );
        }
        if ( javaType == Short.class || javaType == short.class ) {
            return Short.valueOf( value );
        }
        if ( javaType == Character.class || javaType == char.class ) {
            return value.charAt( 0 );
        }
        if ( javaType == Boolean.class || javaType == boolean.class ) {
            return Boolean.valueOf( value );
        }
        return value;
    }
}
