package com.wvkity.mybatis.core.immutable;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.function.UnaryOperator;

public abstract class AbstractImmutableList<E> extends AbstractImmutableCollection<E>
        implements List<E>, RandomAccess {

    @Override
    public void add(int index, E element) {
        throw UOE;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw UOE;
    }

    @Override
    public E remove(int index) {
        throw UOE;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        throw UOE;
    }

    @Override
    public E set(int index, E element) {
        throw UOE;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        throw UOE;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        int size = this.size();
        subListRangeCheck(fromIndex, toIndex, size);
        return ImmutableSubList.fromList(this, fromIndex, toIndex);
    }

    @Override
    public Iterator<E> iterator() {
        return new ImmutableListIterator<>(this, size());
    }

    @Override
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        int size = size();
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size());
        }
        return new ImmutableListIterator<>(this, size, index);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof List)) {
            return false;
        }

        Iterator<?> oit = ((List<?>) o).iterator();
        for (int i = 0, s = size(); i < s; i++) {
            if (!oit.hasNext() || !get(i).equals(oit.next())) {
                return false;
            }
        }
        return !oit.hasNext();
    }

    @Override
    public int indexOf(Object o) {
        Objects.requireNonNull(o);
        for (int i = 0, s = size(); i < s; i++) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        Objects.requireNonNull(o);
        for (int i = size() - 1; i >= 0; i--) {
            if (o.equals(get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        for (int i = 0, s = size(); i < s; i++) {
            hash = 31 * hash + get(i).hashCode();
        }
        return hash;
    }

    static void subListRangeCheck(int fromIndex, int toIndex, int size) {
        if (fromIndex < 0)
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        if (toIndex > size)
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        if (fromIndex > toIndex)
            throw new IllegalArgumentException("fromIndex(" + fromIndex +
                    ") > toIndex(" + toIndex + ")");
    }

    static final class ImmutableSubList<E> extends AbstractImmutableList<E>
            implements RandomAccess {

        private final List<E> root;
        private final int offset;
        private final int size;

        private ImmutableSubList(List<E> root, int offset, int size) {
            this.root = root;
            this.offset = offset;
            this.size = size;
        }

        /**
         * Constructs a sublist of another SubList.
         */
        static <E> ImmutableSubList<E> fromSubList(ImmutableSubList<E> parent, int fromIndex, int toIndex) {
            return new ImmutableSubList<>(parent.root, parent.offset + fromIndex, toIndex - fromIndex);
        }

        /**
         * Constructs a sublist of an arbitrary AbstractImmutableList, which is
         * not a SubList itself.
         */
        static <E> ImmutableSubList<E> fromList(List<E> list, int fromIndex, int toIndex) {
            return new ImmutableSubList<>(list, fromIndex, toIndex - fromIndex);
        }

        public E get(int index) {
            if (index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size());
            }
            return root.get(offset + index);
        }

        public int size() {
            return size;
        }

        public Iterator<E> iterator() {
            return new ImmutableListIterator<>(this, size());
        }

        public ListIterator<E> listIterator(int index) {
            rangeCheck(index);
            return new ImmutableListIterator<>(this, size(), index);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            subListRangeCheck(fromIndex, toIndex, size);
            return ImmutableSubList.fromSubList(this, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("Index: " + index + " Size: " + size());
            }
        }
    }

    static final class ImmutableListIterator<E> implements ListIterator<E> {

        private final List<E> list;
        private final int size;
        private final boolean isListIterator;
        private int cursor;

        ImmutableListIterator(List<E> list, int size) {
            this.list = list;
            this.size = size;
            this.cursor = 0;
            isListIterator = false;
        }

        ImmutableListIterator(List<E> list, int size, int index) {
            this.list = list;
            this.size = size;
            this.cursor = index;
            isListIterator = true;
        }

        public boolean hasNext() {
            return cursor != size;
        }

        public E next() {
            try {
                int i = cursor;
                E next = list.get(i);
                cursor = i + 1;
                return next;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            throw UOE;
        }

        public boolean hasPrevious() {
            if (!isListIterator) {
                throw UOE;
            }
            return cursor != 0;
        }

        public E previous() {
            if (!isListIterator) {
                throw UOE;
            }
            try {
                int i = cursor - 1;
                E previous = list.get(i);
                cursor = i;
                return previous;
            } catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        public int nextIndex() {
            if (!isListIterator) {
                throw UOE;
            }
            return cursor;
        }

        public int previousIndex() {
            if (!isListIterator) {
                throw UOE;
            }
            return cursor - 1;
        }

        public void set(E e) {
            throw UOE;
        }

        public void add(E e) {
            throw UOE;
        }
    }
}

