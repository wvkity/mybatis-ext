package com.wvkity.mybatis.core.immutable;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

    ImmutableLinkedMap(Object... args) {
        int length = args.length;
        if ((length & 1) != 0) {
            throw new IllegalArgumentException("The parameter length must be even.");
        }
        Set<Object> tmp = new HashSet<>();
        for (int i = 0; i < length; i += 2) {
            tmp.add(args[i]);
        }
        this.size = tmp.size();
        if (this.size > 0) {
            this.table = new Object[(((this.size << 2) + 1) & ~1)];
            this.keys = new Object[this.size];
            this.init(args);
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

    private void init(Object... args) {
        int kk = 0;
        for (int i = 0, j = args.length; i < j; i += 2) {
            @SuppressWarnings("unchecked")
            K k = (K) Objects.requireNonNull(args[i]);
            @SuppressWarnings("unchecked")
            V v = (V) Objects.requireNonNull(args[i + 1]);
            int index = probe(k);
            if (index >= 0) {
                // 覆盖
                this.table[index + 1] = v;
            } else {
                int dest = -(index + 1);
                this.table[dest] = k;
                this.table[dest + 1] = v;
                this.keys[kk++] = k;
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
     * 创建不可变Map集合
     * @param args 多个参数
     * @param <K>  键
     * @param <V>  值
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> construct(Object... args) {
        return new ImmutableLinkedMap<>(args);
    }

    /**
     * 创建空Map集合
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of() {
        return emptyMap();
    }

    /**
     * 创建不可变Map集合
     * @param k   键
     * @param v   值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of(K k, V v) {
        return construct(k, v);
    }

    /**
     * 创建不可变Map集合
     * @param k1  键1
     * @param v1  键1对应映射值
     * @param k2  键2
     * @param v2  键2对应映射值
     * @param <K> 键类型
     * @param <V> 值类型
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2) {
        return construct(k1, v1, k2, v2);
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
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3) {
        return construct(k1, v1, k2, v2, k3, v3);
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
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        return construct(k1, v1, k2, v2, k3, v3, k4, v4);
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
     * @return {@link ImmutableLinkedMap}
     */
    public static <K, V> Map<K, V> of(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return construct(k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    /**
     * 创建不可改变Map集合
     * @param original 源数据
     * @param <K>      键
     * @param <V>      值
     * @return {@link ImmutableLinkedMap}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Map<K, V> original) {
        if (original == null) {
            throw new NullPointerException();
        }
        if (original instanceof AbstractImmutableMap) {
            return original;
        }
        if (original.isEmpty()) {
            return emptyMap();
        }
        return of(original.entrySet().toArray(new Entry[0]));
    }

    /**
     * 创建不可变Map集合
     * @param entries {@link Entry}
     * @param <K>     键类型
     * @param <V>     值类型
     * @return {@link ImmutableLinkedMap}
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> of(Entry<? extends K, ? extends V>... entries) {
        int size = entries.length;
        if (size == 0) {
            return ImmutableLinkedMap.emptyMap();
        } else if (size == 1) {
            return construct(entries[0].getKey(), entries[0].getValue());
        } else {
            Object[] args = new Object[size << 1];
            int i = 0;
            for (Entry<? extends K, ? extends V> entry : entries) {
                args[i++] = entry.getKey();
                args[i++] = entry.getValue();
            }
            return construct(args);
        }
    }

    /**
     * 创建不可变Map集合
     * @param entries {@link Iterable}
     * @param <K>     键类型
     * @param <V>     值类型
     * @return {@link ImmutableLinkedMap}
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
     * @return {@link ImmutableLinkedMap}
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
