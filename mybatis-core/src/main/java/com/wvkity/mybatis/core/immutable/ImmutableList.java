package com.wvkity.mybatis.core.immutable;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 不可变List集合
 * @param <E> 元素类型
 * @author wvkity
 */
public final class ImmutableList<E> extends AbstractImmutableList<E> implements Serializable {

    /**
     * 元素
     */
    private final E[] elements;

    @SafeVarargs
    ImmutableList(E... elements) {
        int length = elements.length;
        @SuppressWarnings("unchecked")
        E[] tmp = (E[]) new Object[length];
        if (length > 0) {
            switch (length) {
                case 1:
                    tmp[0] = Objects.requireNonNull(elements[0]);
                    break;
                case 2:
                    tmp[0] = Objects.requireNonNull(elements[0]);
                    tmp[1] = Objects.requireNonNull(elements[1]);
                    break;
                default:
                    for (int i = 0; i < length; i++) {
                        tmp[i] = Objects.requireNonNull(elements[i]);
                    }
                    break;
            }
        }
        this.elements = tmp;
    }

    @Override
    public int size() {
        return this.elements.length;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public E get(int index) {
        return this.elements[index];
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new InvalidObjectException("not serial proxy");
    }

    private Object writeReplace() {
        return new CollSer(CollSer.IMM_LIST, elements);
    }

    /**
     * 创建空不可变List集合
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of() {
        return construct();
    }

    /**
     * 创建不可变List集合
     * @param e1  元素1
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of(E e1) {
        return construct(e1);
    }

    /**
     * 创建不可变List集合
     * @param e1  元素1
     * @param e2  元素2
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of(E e1, E e2) {
        return construct(e1, e2);
    }

    /**
     * 创建不可变List集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of(E e1, E e2, E e3) {
        return construct(e1, e2, e3);
    }

    /**
     * 创建不可变List集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param e4  元素4
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of(E e1, E e2, E e3, E e4) {
        return construct(e1, e2, e3, e4);
    }

    /**
     * 创建不可变List集合
     * @param e1  元素1
     * @param e2  元素2
     * @param e3  元素3
     * @param e4  元素4
     * @param e5  元素5
     * @param <E> 元素类型
     * @return {@link ImmutableList}
     */
    public static <E> List<E> of(E e1, E e2, E e3, E e4, E e5) {
        return construct(e1, e2, e3, e4, e5);
    }

    /**
     * 创建不可变List集合
     * @param iterable {@link Iterable}
     * @param <E>      元素类型
     * @return {@link ImmutableList}
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> of(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return of((Collection<? extends E>) iterable);
        } else {
            return of(iterable.iterator());
        }
    }

    /**
     * 创建不可变List集合
     * @param iterator {@link Iterator}
     * @param <E>      元素类型
     * @return {@link ImmutableList}
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> of(Iterator<? extends E> iterator) {
        if (iterator.hasNext()) {
            E first = iterator.next();
            if (iterator.hasNext()) {
                List<E> original = new ArrayList<>();
                original.add(first);
                while (iterator.hasNext()) {
                    original.add(iterator.next());
                }
                return (List<E>) construct(original.toArray());
            } else {
                return of(first);
            }
        } else {
            return of();
        }
    }

    /**
     * 创建不可变List集合
     * @param elements 元素集合
     * @param <E>      元素类型
     * @return {@link ImmutableList}
     */
    @SuppressWarnings("unchecked")
    public static <E> List<E> of(Collection<? extends E> elements) {
        if (elements instanceof AbstractImmutableList && elements.getClass() != ImmutableSubList.class) {
            return (List<E>) elements;
        } else {
            return (List<E>) construct(elements.toArray());
        }
    }

    /**
     * 创建不可变List集合
     * @param <E> 元素数组
     */
    @SafeVarargs
    public static <E> List<E> construct(E... args) {
        return new ImmutableList<>(args);
    }

}
