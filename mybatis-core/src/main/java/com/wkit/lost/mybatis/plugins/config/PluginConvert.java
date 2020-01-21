package com.wkit.lost.mybatis.plugins.config;

import com.wkit.lost.mybatis.config.Plugin;
import com.wkit.lost.mybatis.plugins.data.auditing.MetadataAuditingInterceptor;
import com.wkit.lost.mybatis.plugins.paging.LimitInterceptor;
import com.wkit.lost.mybatis.plugins.locker.OptimisticLockerInterceptor;
import com.wkit.lost.mybatis.plugins.paging.PageableInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 插件转换器
 * @author wvkity
 */
public final class PluginConvert {

    /**
     * 插件类缓存
     */
    private static final Map<Plugin, Class<? extends Interceptor>> PLUGIN_MAPPING_CACHE =
            new ConcurrentHashMap<>( 8 );

    static {
        PLUGIN_MAPPING_CACHE.put( Plugin.PAGEABLE, PageableInterceptor.class );
        PLUGIN_MAPPING_CACHE.put( Plugin.LIMIT, LimitInterceptor.class );
        PLUGIN_MAPPING_CACHE.put( Plugin.META_DATA_AUDIT, MetadataAuditingInterceptor.class );
        PLUGIN_MAPPING_CACHE.put( Plugin.OPTIMISTIC_LOCKER, OptimisticLockerInterceptor.class );
    }

    /**
     * 根据插件枚举类获取对应的插件类
     * @param plugin 插件枚举类
     * @return 插件类
     */
    public static Class<? extends Interceptor> getInterceptorClass( Plugin plugin ) {
        return PLUGIN_MAPPING_CACHE.get( plugin );
    }

    /**
     * 根据插件枚举类型创建插件对象
     * @param plugin 插件枚举类
     * @return 插件对象
     */
    public static Interceptor getInterceptor( Plugin plugin ) {
        return Optional.ofNullable( plugin ).map( value ->
                newInstance( getInterceptorClass( value ) ) ).orElse( null );
    }

    /**
     * 创建插件对象
     * @param clazz 插件类
     * @param <T>   泛型类型
     * @return 插件对象
     */
    public static <T extends Interceptor> T newInstance( Class<T> clazz ) {
        if ( clazz != null ) {
            try {
                return clazz.getDeclaredConstructor().newInstance();
            } catch ( Exception e ) {
                // ignore
            }
        }
        return null;
    }
}
