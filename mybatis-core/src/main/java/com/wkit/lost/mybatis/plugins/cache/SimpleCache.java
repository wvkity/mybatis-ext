package com.wkit.lost.mybatis.plugins.cache;

import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.cache.decorators.FifoCache;
import org.apache.ibatis.cache.impl.PerpetualCache;
import org.apache.ibatis.mapping.CacheBuilder;

import java.util.Optional;
import java.util.Properties;

/**
 * 简单缓存
 * <p>采用MyBatis缓存实现</p>
 * @author wvkity
 */
public class SimpleCache<K, V> implements Cache<K, V> {

    /**
     * 缓存对象
     */
    private final org.apache.ibatis.cache.Cache CACHE;

    /**
     * 构造方法
     * @param properties 配置
     * @param prefix     前缀
     */
    @SuppressWarnings( "unchecked" )
    public SimpleCache( Properties properties, String prefix ) {
        CacheBuilder cacheBuilder = new CacheBuilder( "SQL_CACHE" );
        String typeClass = properties.getProperty( prefix + ".typeClass" );
        if ( StringUtil.hasText( typeClass ) ) {
            try {
                cacheBuilder.implementation( ( Class<? extends org.apache.ibatis.cache.Cache> ) Class.forName( typeClass ) );
            } catch ( ClassNotFoundException e ) {
                cacheBuilder.implementation( PerpetualCache.class );
            }
        } else {
            cacheBuilder.implementation( PerpetualCache.class );
        }
        String evictionClass = properties.getProperty( prefix + ".evictionClass" );
        if ( StringUtil.hasText( evictionClass ) ) {
            try {
                cacheBuilder.addDecorator( ( Class<? extends org.apache.ibatis.cache.Cache> ) Class.forName( evictionClass ) );
            } catch ( ClassNotFoundException e ) {
                cacheBuilder.addDecorator( FifoCache.class );
            }
        } else {
            cacheBuilder.addDecorator( FifoCache.class );
        }
        Optional.ofNullable( properties.getProperty( prefix + ".flushInterval" ) ).ifPresent( value -> cacheBuilder.clearInterval( Long.parseLong( value ) ) );
        Optional.ofNullable( properties.getProperty( prefix + ".size" ) ).ifPresent( value -> cacheBuilder.size( Integer.parseInt( value ) ) );
        cacheBuilder.properties( properties );
        CACHE = cacheBuilder.build();
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public V get( K key ) {
        return ( V ) Optional.ofNullable( CACHE.getObject( key ) ).orElse( null );
    }

    @Override
    public void put( K key, V value ) {
        CACHE.putObject( key, value );
    }
}
