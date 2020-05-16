package com.wvkity.mybatis.core.immutable;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * 不可变Map集合
 * @param <K> 键
 * @param <V> 值
 * @author wvkity
 */
public final class ImmutableLinkedMap<K, V> extends AbstractImmutableMap<K, V> implements Serializable {

    private static final Map<?, ?> EMPTY_MAP = new ImmutableLinkedMap<>();
    static final int SALT;
    final Object[] table;
    final Object[] keys;
    final int size;

    static {
        long nt = System.nanoTime();
        SALT = (int) ((nt >>> 32) ^ nt);
    }

    private ImmutableLinkedMap() {
        this.table = new Object[0];
        this.size = 0;
        this.keys = new Object[0];
    }

    ImmutableLinkedMap(Map<K, V> map) {
        this.size = map.size();
        if (this.size > 0) {
            this.table = new Object[(((this.size << 2) + 1) & ~1)];
            this.keys = new Object[this.size];
            this.init(map);
        } else {
            this.table = new Object[0];
            this.keys = new Object[0];
        }
    }

    @Override
    public boolean containsKey(Object key) {
        return this.size > 0 && key != null && probe(key) >= 0;
    }

    @Override
    public boolean containsValue(Object value) {
        if (this.size == 0 || value == null) {
            return false;
        }
        for (int i = 1, j = table.length; i < j; i += 2) {
            Object v = table[i];
            if (value.equals(v)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(Object key) {
        if (this.size == 0 || key == null) {
            return null;
        }
        int idx = probe(key);
        return idx >= 0 ? (V) this.table[idx + 1] : null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int i = 0; i < table.length; i += 2) {
            Object k = table[i];
            if (k != null) {
                hash += k.hashCode() ^ table[i + 1].hashCode();
            }
        }
        return hash;
    }

    private void init(Map<K, V> map) {
        int j = -1;
        for (Entry<K, V> entry : map.entrySet()) {
            K k = Objects.requireNonNull(entry.getKey());
            V v = Objects.requireNonNull(entry.getValue());
            int idx = probe(k);
            if (idx >= 0) {
                // 覆盖
                this.table[idx + 1] = v;
            } else {
                int i = -(idx + 1);
                this.table[i] = k;
                this.table[i + 1] = v;
                this.keys[++j] = k;
            }
        }
    }

    private int probe(Object key) {
        int idx = Math.floorMod(key.hashCode(), table.length >> 1) << 1;
        while (true) {
            @SuppressWarnings("unchecked")
            K ek = (K) table[idx];
            if (ek == null) {
                return -idx - 1;
            } else if (key.equals(ek)) {
                return idx;
            } else if ((idx += 2) == table.length) {
                idx = 0;
            }
        }
    }

    final class ImmutableMapIterator implements Iterator<Entry<K, V>> {

        private int remaining;
        private int index;

        ImmutableMapIterator() {
            this.remaining = size();
            if (this.remaining > 0) {
                this.index = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return this.remaining > 0;
        }

        @Override
        public Entry<K, V> next() {
            if (hasNext()) {
                int idx = probe(keys[this.index]);
                @SuppressWarnings("unchecked")
                Entry<K, V> entry = new KeyValueHolder<>((K) table[idx], (V) table[idx + 1]);
                this.remaining--;
                this.index++;
                return entry;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new AbstractSet<Entry<K, V>>() {
            @Override
            public Iterator<Entry<K, V>> iterator() {
                return new ImmutableMapIterator();
            }

            @Override
            public int size() {
                return ImmutableLinkedMap.this.size;
            }
        };
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("not serial proxy");
    }

    private Object writeReplace() {
        Object[] array = new Object[2 * size];
        int len = table.length;
        int dest = 0;
        for (int i = 0; i < len; i += 2) {
            if (table[i] != null) {
                array[dest++] = table[i];
                array[dest++] = table[i + 1];
            }
        }
        return new CollSer(CollSer.IMM_MAP, array);
    }

    /**
     * 空集合
     * @param <K> 键
     * @param <V> 值
     * @return 空集合
     */
    @SuppressWarnings("unchecked")
    static <K, V> Map<K, V> emptyMap() {
        return (Map<K, V>) ImmutableLinkedMap.EMPTY_MAP;
    }

    /**
     * 创建不可改变Map集合
     * @param original 源数据
     * @param <K>      键
     * @param <V>      值
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> construct(Map<K, V> original) {
        if (original == null) {
            throw new NullPointerException();
        }
        if (original instanceof AbstractImmutableMap) {
            return original;
        }
        if (original.isEmpty()) {
            return emptyMap();
        }
        return new ImmutableLinkedMap<>(original);
    }

    /**
     * 创建不可变Map集合
     * @param args 多个参数
     * @param <K>  键
     * @param <V>  值
     * @return 不可变Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> from(Object... args) {
        int length = args.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("The parameter length must be even.");
        }
        Map<K, V> map = new LinkedHashMap<>(length << 1);
        if (length == 2) {
            K k = (K) Objects.requireNonNull(args[0]);
            V v = (V) Objects.requireNonNull(args[1]);
            map.put(k, v);
        } else {
            for (int i = 0; i < length; i += 2) {
                K k = (K) Objects.requireNonNull(args[i]);
                V v = (V) Objects.requireNonNull(args[i + 1]);
                map.put(k, v);
            }
        }
        return construct(map);
    }

    /**
     * 创建不可变Map集合
     * @param k   键
     * @param v   值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可变Map集合
     */
    public static <K, V> Map<K, V> of(K k, V v) {
        return from(k, v);
    }

    /**
     * 创建不可变Map集合
     * @param k1  键1
     * @param v1  键1对应映射值
     * @param k2  键2
     * @param v2  键2对应映射值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可变Map集合
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        return from(k1, v1, k2, v2);
    }

    /**
     * 创建不可变Map集合
     * @param k1  键1
     * @param v1  键1对应映射值
     * @param k2  键2
     * @param v2  键2对应映射值
     * @param k3  键3
     * @param v3  键3对应映射值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可变Map集合
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return from(k1, v1, k2, v2, k3, v3);
    }

    /**
     * 创建不可变Map集合
     * @param k1  键1
     * @param v1  键1对应映射值
     * @param k2  键2
     * @param v2  键2对应映射值
     * @param k3  键3
     * @param v3  键3对应映射值
     * @param k4  键4
     * @param v4  键4对应映射值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可变Map集合
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return from(k1, v1, k2, v2, k3, v3, k4, v4);
    }

    /**
     * 创建不可变Map集合
     * @param k1  键1
     * @param v1  键1对应映射值
     * @param k2  键2
     * @param v2  键2对应映射值
     * @param k3  键3
     * @param v3  键3对应映射值
     * @param k4  键4
     * @param v4  键4对应映射值
     * @param k5  键5
     * @param v5  键5对应映射值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return 不可变Map集合
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return from(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    /**
     * 创建不可变Map集合
     * @param entries {@link Entry}
     * @param <K>     键类型
     * @param <V>     值类型
     * @return 不可变Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Entry<? extends K, ? extends V>... entries) {
        int size = entries.length;
        if (size == 0) {
            return ImmutableLinkedMap.emptyMap();
        } else if (size == 1) {
            return from(entries[0].getKey(), entries[0].getValue());
        } else {
            Map<K, V> arg = new HashMap<>(size);
            for (Entry<? extends K, ? extends V> value : entries) {
                Entry<K, V> entry = (Entry<K, V>) value;
                arg.put(entry.getKey(), entry.getValue());
            }
            return construct(arg);
        }
    }

    /**
     * 创建不可变Map集合
     * @param entries {@link Iterable}
     * @param <K>     键类型
     * @param <V>     值类型
     * @return 不可变Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Iterable<? extends Entry<? extends K, ? extends V>> entries) {
        if (entries instanceof Collection) {
            return of(((Collection<Entry<K, V>>) entries).toArray(new Entry[0]));
        } else {
            return of(entries.iterator());
        }
    }

    /**
     * 创建不可变Map集合
     * @param iterator {@link Iterator}
     * @param <K>      键类型
     * @param <V>      值类型
     * @return 不可变Map集合
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Iterator<? extends Entry<? extends K, ? extends V>> iterator) {
        List<Entry<? extends K, ? extends V>> arg = new ArrayList<>();
        while (iterator.hasNext()) {
            arg.add(iterator.next());
        }
        return of(arg.toArray(new Entry[0]));
    }
    
}
