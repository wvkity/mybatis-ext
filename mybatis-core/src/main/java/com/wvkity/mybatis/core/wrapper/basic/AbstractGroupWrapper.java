package com.wvkity.mybatis.core.wrapper.basic;

/**
 * 抽象分组包装器
 * @param <T> 实体泛型类型
 * @param <E> 字段类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractGroupWrapper<T, E> extends AbstractWrapper<T, E> implements GroupWrapper<E> {

}
