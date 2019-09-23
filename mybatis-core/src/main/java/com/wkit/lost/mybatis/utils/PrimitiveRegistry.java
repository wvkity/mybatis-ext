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
}
