package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.utils.ClassUtil;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
public final class ProviderCache {

    private ProviderCache() {
    }

    private static final Map<String, Class<? extends Provider>> PROVIDER_CACHE = new ConcurrentHashMap<>( 32 );

    @SuppressWarnings( { "unchecked" } )
    public static Class<? extends Provider> getTarget( final Class<?> klass ) {
        return Optional.ofNullable( klass ).map( it -> {
            Class<? extends Provider> target = PROVIDER_CACHE.getOrDefault( it.toString(), null );
            if ( target == null ) {
                Class<?> objectClass = ClassUtil.getGenericType( it, 0 );
                if ( Provider.class.isAssignableFrom( objectClass ) ) {
                    PROVIDER_CACHE.putIfAbsent( it.toString(), ( Class<? extends Provider> ) objectClass );
                    return PROVIDER_CACHE.getOrDefault( it.toString(), null );
                }
                return null;
            }
            return target;
        } ).orElse( null );
    }
    
    public static Provider newInstance( final Class<?> klass ) {
        return Optional.ofNullable( getTarget( klass ) ).map( it -> {
            try {
                return it.getDeclaredConstructor().newInstance();
            } catch ( Exception e ) {
                // ignore
            }
            return null;
        } ).orElse( null );
    }
}
