package com.wkit.lost.mybatis.core.criteria;

import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.function.Function;

/**
 * 条件容器
 * @param <T> 泛型类型
 * @author wvkity
 */
@Log4j2
@Accessors( chain = true )
@SuppressWarnings( { "serial", "unchecked" } )
public abstract class AbstractCriteriaWrapper<T> extends AbstractChainCriteriaWrapper<T, AbstractCriteriaWrapper<T>> {

    @Override
    public <E> AbstractCriteriaWrapper<E> getMaster() {
        return this.master != null ? ( AbstractCriteriaWrapper<E> ) this.master : null;
    }

    @Override
    public <E> AbstractCriteriaWrapper<E> getRootMaster() {
        AbstractCriteriaWrapper<E> rootMaster;
        AbstractCriteriaWrapper<E> root = ( AbstractCriteriaWrapper<E> ) this;
        while ( ( rootMaster = root.getMaster() ) != null ) {
            root = rootMaster;
        }
        return root;
    }

    public AbstractCriteriaWrapper<T> bridge( Function<AbstractQueryCriteria<T>, AbstractCriteriaWrapper<T>> function ) {
        if ( this instanceof AbstractQueryCriteria ) {
            function.apply( ( AbstractQueryCriteria<T> ) this );
        }
        return this;
    }
}
