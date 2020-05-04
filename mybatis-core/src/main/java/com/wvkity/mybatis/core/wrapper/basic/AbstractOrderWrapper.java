package com.wvkity.mybatis.core.wrapper.basic;

import lombok.Getter;

/**
 * 抽象排序包装器
 * @param <T> 实体泛型类型
 * @param <E> 字段类型
 */
@SuppressWarnings({"serial"})
public abstract class AbstractOrderWrapper<E> extends AbstractWrapper<E> implements OrderWrapper<E> {

    /**
     * 排序方式
     */
    @Getter
    protected boolean ascending;

}
