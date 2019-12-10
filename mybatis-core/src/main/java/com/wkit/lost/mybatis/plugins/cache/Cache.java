package com.wkit.lost.mybatis.plugins.cache;

/**
 * 缓存接口
 * @param <K> 键
 * @param <V> 值
 * @author wvkity
 */
public interface Cache<K, V> {

    /**
     * 获取值
     * @param key 键
     * @return 值
     */
    V get( K key );

    /**
     * 保存值
     * @param key 键
     * @param value 值
     */
    void put( K key, V value );
}
