package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.criteria.AbstractCriteriaWrapper;

/**
 * 条件管理器
 * @author wvkity
 */
public class ConditionManager<T> extends AbstractConditionManager<T> {
    
    private static final long serialVersionUID = 4261564506488337105L;
    
    /**
     * 构造方法
     * @param criteria 查询对象
     */
    public ConditionManager( AbstractCriteriaWrapper<T> criteria ) {
        this.criteria = criteria;
    }
    
    @Override
    protected AbstractConditionManager<T> instance( AbstractCriteriaWrapper<T> criteria ) {
        return new ConditionManager<>( criteria );
    }
}
