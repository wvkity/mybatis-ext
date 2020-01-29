package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.core.metadata.Table;
import com.wkit.lost.mybatis.invoke.SerializedLambda;
import com.wkit.lost.mybatis.lambda.Property;
import org.apache.ibatis.reflection.property.PropertyNamer;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 属性缓存(用于lambda)
 * @author wvkity
 */
public final class PropertyMappingForLambda {

    private static final Map<String, Map<String, Column>> CACHE = new ConcurrentHashMap<>( 128 );
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNCTIONS = new WeakHashMap<>();

    /**
     * 解析lambda表达式
     * @param function lambda对象
     * @param <T>      类型
     * @return 解析结果
     */
    public static <T> SerializedLambda resolve( Property<T, ?> function ) {
        Class<?> clazz = function.getClass();
        return Optional.ofNullable( FUNCTIONS.get( clazz ) )
                .map( WeakReference::get )
                .orElseGet( () -> {
                    SerializedLambda func = SerializedLambda.convert( function );
                    FUNCTIONS.put( clazz, new WeakReference<>( func ) );
                    return func;
                } );
    }

    /**
     * lambda对象转属性
     * @param lambda Lambda对象
     * @return 属性名
     */
    public static <T> String lambdaToProperty( Property<T, ?> lambda ) {
        return getPropertyNameFromMethodName( resolve( lambda ).getImplMethodName() );
    }

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param methodName 方法名
     * @return 属性名
     */
    public static <T> String getPropertyNameFromMethodName( String methodName ) {
        if ( StringUtil.isBlank( methodName ) ) {
            return null;
        }
        return PropertyNamer.methodToProperty( methodName );
    }

    /**
     * 缓存实体-表映射信息
     * @param table 表映射信息
     */
    public static void createCache( Table table ) {
        createCache( table.getEntity().getName(), table );
    }

    /**
     * 缓存实体-表映射信息
     * @param className 实体类名
     * @param table     表映射信息
     */
    public static void createCache( String className, Table table ) {
        if ( !CACHE.containsKey( className ) ) {
            CACHE.putIfAbsent( className, createLambdaMap( table ) );
        }
    }

    /**
     * 获取实体字段映射缓存
     * @param entityName 实体类名
     * @return 字段映射缓存
     */
    public static Map<String, Column> getColumnCache( final String entityName ) {
        return CACHE.get( entityName );
    }

    /**
     * 缓存实体属性-表字段映射信息
     * @param table 表映射信息
     * @return 实体-字段缓存信息
     */
    private static Map<String, Column> createLambdaMap( Table table ) {
        return table.getColumns().stream().collect( Collectors.toConcurrentMap( Column::getProperty, Function.identity() ) );
    }
}
