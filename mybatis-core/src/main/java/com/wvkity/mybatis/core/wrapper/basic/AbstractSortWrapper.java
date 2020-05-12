package com.wvkity.mybatis.core.wrapper.basic;

import lombok.Getter;

/**
 * 抽象排序包装器
 * @param <E> 字段类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractSortWrapper<E> extends AbstractWrapper<E> implements SortWrapper<E> {

    /**
     * 排序方式
     */
    @Getter
    protected boolean ascending;

}
