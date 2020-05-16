package com.wvkity.mybatis.core.immutable;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("serial")
public abstract class AbstractImmutableMap<K, V> extends AbstractMap<K, V> implements Serializable {
    
    protected static final UnsupportedOperationException UOE = new UnsupportedOperationException();

    @Override
    public void clear() {
        throw UOE;
    }

    @Override
    public final V compute(K key, BiFunction<? super K, ? super V, ? extends V> rf) {
        throw UOE;
    }

    @Override
    public final V computeIfAbsent(K key, Function<? super K, ? extends V> mf) {
        throw UOE;
    }

    @Override
    public final V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> rf) {
        throw UOE;
    }

    @Override
    public final V merge(K key, V value, BiFunction<? super V, ? super V, ? extends V> rf) {
        throw UOE;
    }

    @Override
    public final V put(K key, V value) {
        throw UOE;
    }

    @Override
    public final V remove(Object key) {
        throw UOE;
    }

    @Override
    public final void putAll(Map<? extends K, ? extends V> m) {
        throw UOE;
    }

    @Override
    public final void replaceAll(BiFunction<? super K, ? super V, ? extends V> function) {
        throw UOE;
    }

    @Override
    public final V putIfAbsent(K key, V value) {
        throw UOE;
    }

    @Override
    public final boolean remove(Object key, Object value) {
        throw UOE;
    }

    @Override
    public final boolean replace(K key, V oldValue, V newValue) {
        throw UOE;
    }

    @Override
    public final V replace(K key, V value) {
        throw UOE;
    }
}
