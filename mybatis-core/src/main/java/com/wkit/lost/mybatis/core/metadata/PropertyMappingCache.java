package com.wkit.lost.mybatis.core.metadata;

import com.wkit.lost.mybatis.core.lambda.Property;
import com.wkit.lost.mybatis.invoke.SerializedLambda;
import com.wkit.lost.mybatis.utils.Ascii;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性缓存
 * @author wvkity
 */
public final class PropertyMappingCache {

    private PropertyMappingCache() {
    }

    /**
     * 字段缓存
     */
    private static final Map<String, Map<String, ColumnWrapper>> COLUMN_CACHE = new ConcurrentHashMap<>( 64 );

    /**
     * Lambda缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNCTION_CACHE = new WeakHashMap<>();

    /**
     * 构建实体-表字段映射缓存
     * @param table 表映射对象
     */
    public static void build( final TableWrapper table ) {
        Optional.ofNullable( table )
                .filter( it -> Objects.nonNull( it.getEntity() ) )
                .ifPresent( it -> COLUMN_CACHE.putIfAbsent( it.getEntity().toString(), it.columnMappings() ) );
    }

    /**
     * Lambda对象转属性
     * @param property Lambda对象
     * @param <T>      实体类型
     * @return 属性名
     */
    public static <T> String lambdaToProperty( final Property<T, ?> property ) {
        return methodToProperty( Optional.ofNullable( parse( property ) )
                .map( SerializedLambda::getImplMethodName ).orElse( null ) );
    }

    /**
     * 方法名转属性
     * @param methodName 方法名
     * @return 属性
     */
    public static String methodToProperty( final String methodName ) {
        if ( Ascii.hasText( methodName ) ) {
            return PropertyNamer.methodToProperty( methodName );
        }
        return null;
    }

    /**
     * 解析Lambda表达式
     * @param property Lambda表达式
     * @param <T>      泛型类型
     * @return Lambda对象
     */
    public static <T> SerializedLambda parse( final Property<T, ?> property ) {
        Class<?> clazz = property.getClass();
        return Optional.ofNullable( FUNCTION_CACHE.getOrDefault( clazz, null ) )
                .map( WeakReference::get ).orElseGet( () -> {
                    SerializedLambda func = SerializedLambda.convert( property );
                    FUNCTION_CACHE.putIfAbsent( clazz, new WeakReference<>( func ) );
                    return FUNCTION_CACHE.get( clazz ).get();
                } );
    }

    /**
     * 根据类名获取字段映射
     * @param klass 实体类
     * @return 字段映射集合
     */
    public static Map<String, ColumnWrapper> columnCache( final Class<?> klass ) {
        return klass == null ? null : COLUMN_CACHE.getOrDefault( klass.toString(), null );
    }
}
