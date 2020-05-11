package com.wvkity.mybatis.core.mapping.sql;

import com.wvkity.mybatis.utils.ClassUtil;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

/**
 * SQL创建器缓存工具
 * @author wvkity
 */
public final class CreatorCache {

    private CreatorCache() {
    }

    private static final Map<String, Class<? extends Creator>> CREATOR_CACHE =
            Collections.synchronizedMap(new WeakHashMap<>(48));

    @SuppressWarnings({"unchecked"})
    public static Class<? extends Creator> getTarget(final Class<?> klass) {
        return Optional.ofNullable(klass).map(it -> {
            Class<? extends Creator> target = CREATOR_CACHE.getOrDefault(it.toString(), null);
            if (target == null) {
                Class<?> objectClass = ClassUtil.getGenericType(it, 0);
                if (Creator.class.isAssignableFrom(objectClass)) {
                    CREATOR_CACHE.putIfAbsent(it.toString(), (Class<? extends Creator>) objectClass);
                    return CREATOR_CACHE.getOrDefault(it.toString(), null);
                }
                return null;
            }
            return target;
        }).orElse(null);
    }

    public static Creator newInstance(final Class<?> klass) {
        return Optional.ofNullable(getTarget(klass)).map(it -> {
            try {
                return it.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                // ignore
            }
            return null;
        }).orElse(null);
    }
}
