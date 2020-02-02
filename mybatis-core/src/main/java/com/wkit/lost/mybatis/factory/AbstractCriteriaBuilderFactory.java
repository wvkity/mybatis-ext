package com.wkit.lost.mybatis.factory;

import com.wkit.lost.mybatis.core.criteria.CriteriaImpl;

/**
 * 抽象查询对象构建器工厂类
 * @param <T> 类型
 * @author wvkity
 */
public abstract class AbstractCriteriaBuilderFactory<T> {
    
    /**
     * 缓存实例
     */
    private CriteriaCache cache = CriteriaCache.getInstance();
    
    /**
     * 获取查询对象实例
     * @return 查询对象
     */
    @SuppressWarnings( "unchecked" )
    public CriteriaImpl<T> getCriteria() {
        return (CriteriaImpl<T>) cache.getCriteria( getClass() );
    }
    
    /**
     * 获取查询对象实例
     * @param alias 表别名
     * @return 查询对象
     */
    @SuppressWarnings( "unchecked" )
    public CriteriaImpl<T> getCriteria( String alias ) {
        return (CriteriaImpl<T>) cache.getCriteria( getClass(), alias );
    }
}
