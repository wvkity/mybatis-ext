package com.wkit.lost.mybatis.plugins.cache;

import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.Properties;

/**
 * 缓存工厂类
 * @author DT
 */
public abstract class CacheFactory {

    /**
     * 创建缓存
     * @param cacheClass 缓存类
     * @param prefix     配置前缀
     * @param properties 配置
     * @param <K>        键类型
     * @param <V>        值类型
     * @return 缓存实例
     */
    @SuppressWarnings( "unchecked" )
    public static <K, V> Cache<K, V> createCache( String cacheClass, String prefix, Properties properties ) {
        if ( StringUtil.isBlank( cacheClass ) || "false".equalsIgnoreCase( cacheClass ) ) {
            try {
                Class.forName( "com.github.benmanes.caffeine.cache.Cache" );
                return new CaffeineCache<>( properties, prefix );
            } catch ( Exception e ) {
                return new SimpleCache<>( properties, prefix );
            }
        } else {
            Class<? extends Cache> clazz;
            try {
                clazz = ( Class<? extends Cache> ) Class.forName( cacheClass );
                try {
                    return clazz.getDeclaredConstructor( Properties.class, String.class ).newInstance( properties, prefix );
                } catch ( Exception e ) {
                    return clazz.getDeclaredConstructor().newInstance();
                }
            } catch ( Exception e ) {
                throw new MyBatisPluginException( "SQL cache instance creation failed - `" + cacheClass + "`: " + e, e );
            }

        }
    }
}
