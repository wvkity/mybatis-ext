package com.wvkity.mybatis.core.immutable;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.function.Predicate;

public abstract class AbstractImmutableCollection<E> extends AbstractCollection<E> {

    protected static final UnsupportedOperationException UOE = new UnsupportedOperationException();

    @Override
    public boolean add(E e) {
        throw UOE;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw UOE;
    }

    @Override
    public void clear() {
        throw UOE;
    }

    @Override
    public boolean remove(Object o) {
        throw UOE;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw UOE;
    }

    @Override
    public boolean removeIf(Predicate<? super E> filter) {
        throw UOE;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw UOE;
    }
}
