package com.wvkity.mybatis.core.segment;

import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 抽象SQL片段
 * @param <T> 泛型类型
 */
@SuppressWarnings({"serial"})
public abstract class AbstractSegment<E> implements Segment {

    /**
     * 锁
     */
    private final Object LOCK = new Object();

    /**
     * SQL片段集合
     */
    protected volatile List<E> segments;

    /**
     * 添加多个片段对象
     * @param segments 片段对象集合
     */
    public void addAll(Collection<E> segments) {
        if (CollectionUtil.hasElement(segments)) {
            Set<E> its = segments.stream().filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
            if (CollectionUtil.hasElement(its)) {
                if (this.segments == null) {
                    synchronized (LOCK) {
                        if (this.segments == null) {
                            this.segments = new CopyOnWriteArrayList<>();
                        }
                    }
                }
                this.segments.addAll(its);
            }
        }
    }

    /**
     * 检查是否存在SQL片段
     * @return boolean
     */
    public boolean isNotEmpty() {
        return CollectionUtil.hasElement(this.segments);
    }
}
