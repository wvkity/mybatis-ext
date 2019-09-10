package com.wkit.lost.mybatis.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Class工具类
 */
public abstract class ClassUtil {

    public static final String CGLIB_PROXY = "net.sf.cglib.proxy.Factory";
    public static final String JAVASSIST_PROXY = "javassist.util.proxy.ProxyObject";
    public static final String SPRING_CGLIB_PROXY = "org.springframework.cglib.proxy.Factory";
    public static final String MYBATIS_PROXY = "org.apache.ibatis.javassist.util.proxy.ProxyObject";
    private static final Set<String> PROXY_CACHE = new HashSet<>();

    static {
        PROXY_CACHE.add( CGLIB_PROXY );
        PROXY_CACHE.add( JAVASSIST_PROXY );
        PROXY_CACHE.add( SPRING_CGLIB_PROXY );
        PROXY_CACHE.add( MYBATIS_PROXY );
    }

    /**
     * 检查是否为代理类
     * @param clazz 待检查类
     * @return true: 是 | false: 否
     */
    public static boolean isProxy( final Class<?> clazz ) {
        return clazz != null && Optional.ofNullable( clazz.getInterfaces() )
                .map( Arrays::stream )
                .map( Object::getClass )
                .map( Class::getName )
                .filter( PROXY_CACHE::contains )
                .isPresent();
    }

    /**
     * 获取指定类的具体类(排除代理对象)
     * @param clazz 指定类
     * @return 具体类
     */
    public static Class<?> getRealClass( Class<?> clazz ) {
        return isProxy( clazz ) ? clazz.getSuperclass() : clazz;
    }

    /**
     * 获取类全名
     * @param target 类
     * @return 类全名
     */
    public static String getCanonicalName( final Class<?> target ) {
        return target == null ? null : target.getCanonicalName();
    }

    /**
     * 比较类型是否一致
     * @param source 源类型
     * @param target 目标类型
     * @return boolean
     */
    public static boolean isAssignableFrom( final Class<?> source, final Class<?> target ) {
        return source != null && target != null && source.isAssignableFrom( target );
    }

    /**
     * 比较类型是否一致
     * @param target  源类型
     * @param objects 值
     * @return boolean
     */
    public static boolean isAssignableFrom( final Class<?> target, final Object... objects ) {
        if ( target == null || ArrayUtil.isEmpty( objects ) ) {
            return false;
        }
        for ( Object object : objects ) {
            if ( object != null && isAssignableFrom( target, object.getClass() ) ) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定类的泛型类型
     * @param target 指定类
     * @param index  索引
     * @return {@link Class}泛型
     */
    public static Class<?> getGenericType( final Class<?> target, final int index ) {
        return getGenericType( target.getGenericSuperclass(), index );
    }

    /**
     * 获取泛型
     * @param genericType 类型
     * @param index       索引
     * @return {@link Class}泛型
     */
    public static Class<?> getGenericType( final Type genericType, final int index ) {
        if ( !( genericType instanceof ParameterizedType ) ) {
            return Object.class;
        }
        Type[] types = ( ( ParameterizedType ) genericType ).getActualTypeArguments();
        if ( index > types.length || index < 0 ) {
            return Object.class;
        }
        Type type = types[ index ];
        if ( !( type instanceof Class ) ) {
            return Object.class;
        }
        return ( Class<?> ) type;
    }

    /**
     * 字符串类转实例
     * @param className 类名
     * @return 实例
     */
    public static Object newInstance( final String className ) {
        try {
            Class<?> clazz = Class.forName( className );
            return newInstance( clazz );
        } catch ( Exception e ) {
            // ignore
        }
        return null;
    }

    /**
     * 创建实例
     * @param clazz 类
     * @param <T>   类型
     * @return 实例
     * @throws Exception \n
     */
    public static <T> T newInstance( Class<T> clazz ) throws Exception {
        return clazz.getDeclaredConstructor().newInstance();
    }
}
