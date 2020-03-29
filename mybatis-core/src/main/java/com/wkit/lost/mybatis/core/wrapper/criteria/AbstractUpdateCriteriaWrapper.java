package com.wkit.lost.mybatis.core.wrapper.criteria;

@SuppressWarnings( { "serial" } )
public abstract class AbstractUpdateCriteriaWrapper<T> extends AbstractCriteriaWrapper<T> 
        implements Modify<T, AbstractUpdateCriteriaWrapper<T>> {

    @Override
    public AbstractUpdateCriteriaWrapper<T> updateVersion( Object version ) {
        return null;
    }
}
