package com.wvkity.mybatis.core.wrapper.criteria;

import java.util.function.Function;

/**
 * 抽象条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractCriteriaWrapper<T> extends AbstractChainCriteriaWrapper<T, AbstractCriteriaWrapper<T>> {
    
    /**
     * 桥接
     * @param function function对象
     * @return 当前对象
     */
    public AbstractCriteriaWrapper<T> bridge(Function<AbstractQueryCriteriaWrapper<T>, AbstractCriteriaWrapper<T>> function) {
        if (this instanceof AbstractQueryCriteriaWrapper) {
            function.apply((AbstractQueryCriteriaWrapper<T>) this);
        }
        return this;
    }
}
