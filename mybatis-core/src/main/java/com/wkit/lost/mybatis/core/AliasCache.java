package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 别名缓存类
 * @author DT
 */
public abstract class AliasCache {

    /**
     * 缓存
     */
    private static final Map<Class<?>, String> ALIAS_CACHE = new ConcurrentHashMap<>( 128 );

    /**
     * 根据指定类获取别名
     * @param target 指定类
     * @return 别名
     */
    public static String getAlias( final Class<?> target ) {
        if ( target == null ) {
            return null;
        }
        String alias = ALIAS_CACHE.get( target );
        if ( alias == null ) {
            ALIAS_CACHE.putIfAbsent( target, StringUtil.getSimpleNameOfSplitFirstUpper( target ) );
            return ALIAS_CACHE.get( target );
        }
        return alias;
    }
}
