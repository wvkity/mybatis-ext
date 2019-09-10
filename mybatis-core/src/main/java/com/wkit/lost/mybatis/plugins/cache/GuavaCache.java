package com.wkit.lost.mybatis.plugins.cache;

import com.google.common.cache.CacheBuilder;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Guava缓存
 * @author DT
 */
public class GuavaCache<K, V> implements Cache<K, V> {

    /**
     * 缓存对象
     */
    private final com.google.common.cache.Cache<K, V> CACHE;

    /**
     * 构造方法
     * @param properties 配置
     * @param prefix     前缀
     */
    public GuavaCache( Properties properties, String prefix ) {
        CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
        // JDK9+
        /*Optional.ofNullable( properties.getProperty( prefix + ".maximumSize" ) )
                .ifPresentOrElse( value -> cacheBuilder.maximumSize( Long.parseLong( value ) ), () -> cacheBuilder.maximumSize( 1000 ) );*/
        cacheBuilder.maximumSize( Long.parseLong( Optional.ofNullable( properties.getProperty( prefix + ".maximumSize" ) ).orElse( "1000" ) ) );
        Optional.ofNullable( properties.getProperty( prefix + ".expireAfterAccess" ) )
                .ifPresent( value -> cacheBuilder.expireAfterAccess( Long.parseLong( value ), TimeUnit.MILLISECONDS ) );
        Optional.ofNullable( properties.getProperty( prefix + ".expireAfterWrite" ) )
                .ifPresent( value -> cacheBuilder.expireAfterWrite( Long.parseLong( value ), TimeUnit.MILLISECONDS ) );
        Optional.ofNullable( properties.getProperty( prefix + ".initialCapacity" ) )
                .ifPresent( value -> cacheBuilder.initialCapacity( Integer.parseInt( value ) ) );
        CACHE = cacheBuilder.build();
    }

    @Override
    public V get( K key ) {
        return CACHE.getIfPresent( key );
    }

    @Override
    public void put( K key, V value ) {
        CACHE.put( key, value );
    }
}
