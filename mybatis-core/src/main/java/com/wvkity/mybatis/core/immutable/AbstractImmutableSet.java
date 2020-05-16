package com.wvkity.mybatis.core.immutable;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

@SuppressWarnings("serial")
public abstract class AbstractImmutableSet<E> extends AbstractImmutableCollection<E> implements Set<E>,  Serializable {

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Set)) {
            return false;
        }

        Collection<?> c = (Collection<?>) o;
        if (c.size() != size()) {
            return false;
        }
        for (Object e : c) {
            if (e == null || !contains(e)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public abstract int hashCode();
}
