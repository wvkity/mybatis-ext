package com.wvkity.mybatis.core.immutable;

import com.wvkity.mybatis.utils.ArrayUtil;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

/**
 * 不可变Set集合
 * @param <E> 元素类型
 * @author wvkity
 */
public final class ImmutableLinkedSet<E> extends AbstractImmutableSet<E> {

    final E[] elements;
    final E[] source;
    final int size;
    static final int SALT;
    private static final Set<?> EMPTY_SET = new ImmutableLinkedSet<>();

    static {
        long nt = System.nanoTime();
        SALT = (int) ((nt >>> 32) ^ nt);
    }

    @SuppressWarnings("unchecked")
    private ImmutableLinkedSet() {
        this.elements = (E[]) new Object[0];
        this.size = 0;
        this.source = (E[]) new Object[0];
    }

    @SuppressWarnings("unchecked")
    ImmutableLinkedSet(E... elements) {
        Set<E> tmp = new LinkedHashSet<>(ArrayUtil.toList(elements));
        this.size = tmp.size();
        if (this.size > 0) {
            this.elements = (E[]) new Object[(((this.size << 2) + 1) & ~1)];
            this.source = (E[]) tmp.toArray();
            this.init(elements);
        } else {
            this.elements = (E[]) new Object[0];
            this.source = (E[]) new Object[0];
        }
    }

    @SafeVarargs
    private final void init(E... args) {
        for (E element : args) {
            E v = Objects.requireNonNull(element);
            int idx = probe(v);
            if (idx >= 0) {
                // 覆盖
                this.elements[idx] = v;
            } else {
                this.elements[-(idx + 1)] = v;
            }
        }
    }

    private int probe(Object pe) {
        int idx = Math.floorMod(pe.hashCode(), elements.length);
        while (true) {
            E ee = elements[idx];
            if (ee == null) {
                return -idx - 1;
            } else if (pe.equals(ee)) {
                return idx;
            } else if (++idx == elements.length) {
                idx = 0;
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new ImmutableSetIterator();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for (E e : elements) {
            if (e != null) {
                h += e.hashCode();
            }
        }
        return h;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("not serial proxy");
    }

    private Object writeReplace() {
        Object[] array = new Object[size];
        int dest = 0;
        for (Object o : elements) {
            if (o != null) {
                array[dest++] = o;
            }
        }
        return new CollSer(CollSer.IMM_SET, array);
    }

    final class ImmutableSetIterator implements Iterator<E> {

        private int remaining;

        private int index;

        ImmutableSetIterator() {
            remaining = size();
            if (remaining > 0) {
                this.index = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return remaining > 0;
        }

        @Override
        public E next() {
            if (hasNext()) {
                E element = source[this.index];
                remaining--;
                this.index++;
                return element;
            } else {
                throw new NoSuchElementException();
            }
        }
    }

    /**
     * 创建不可变Set集合
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    @SuppressWarnings("unchecked")
    static <E> Set<E> emptySet() {
        return (Set<E>) ImmutableLinkedSet.EMPTY_SET;
    }

    /**
     * 创建不可变Set集合
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of() {
        return emptySet();
    }

    /**
     * 创建不可变Set集合
     * @param e1  元素1
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(E e1) {
        return construct(e1);
    }

    /**
     * 创建不可变Set集合
     * @param e1  元素1
     * @param e2  元素2
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(E e1, E e2) {
        return construct(e1, e2);
    }

    /**
     * 创建不可变Set集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(E e1, E e2, E e3) {
        return construct(e1, e2, e3);
    }

    /**
     * 创建不可变Set集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param e4  元素4
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(E e1, E e2, E e3, E e4) {
        return construct(e1, e2, e3, e4);
    }

    /**
     * 创建不可变Set集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param e4  元素4
     * @param e5  元素5
     * @param <E> 元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(E e1, E e2, E e3, E e4, E e5) {
        return construct(e1, e2, e3, e4, e5);
    }

    /**
     * 创建不可变Set集合
     * @param iterable {@link Iterable}
     * @param <E>      元素类型
     * @return {@link ImmutableLinkedSet}
     */
    @SuppressWarnings("unchecked")
    public static <E> Set<E> of(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return of((Collection<E>) iterable);
        } else {
            return of(iterable.iterator());
        }
    }

    /**
     * 创建不可变Set集合
     * @param iterator {@link Iterator}
     * @param <E>      元素类型
     * @return {@link ImmutableLinkedSet}
     */
    public static <E> Set<E> of(Iterator<? extends E> iterator) {
        if (iterator.hasNext()) {
            E first = iterator.next();
            if (iterator.hasNext()) {
                Set<E> elements = new LinkedHashSet<>();
                elements.add(first);
                while (iterator.hasNext()) {
                    elements.add(iterator.next());
                }
                return of(elements);
            }
            return of(first);
        }
        return of();
    }

    /**
     * 创建不可变List集合
     * @param elements 元素集合
     * @param <E>      元素类型
     * @return {@link ImmutableLinkedSet}
     */
    @SuppressWarnings("unchecked")
    public static <E> Set<E> of(Collection<? extends E> elements) {
        if (elements instanceof AbstractImmutableSet) {
            return (Set<E>) elements;
        } else if (elements instanceof Set) {
            return (Set<E>) construct((elements).toArray());
        }
        return (Set<E>) construct(new LinkedHashSet<>(elements).toArray());
    }

    /**
     * 创建不可变Set集合
     * @param elements 元素数组
     * @param <E>      元素类型
     * @return {@link ImmutableLinkedSet}
     */
    @SafeVarargs
    public static <E> Set<E> construct(E... elements) {
        if (elements.length == 0) {
            return of();
        }
        return new ImmutableLinkedSet<>(elements);
    }
    
}
