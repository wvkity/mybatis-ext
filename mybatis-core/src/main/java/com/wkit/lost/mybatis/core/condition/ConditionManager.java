package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.AbstractCriteria;

/**
 * 条件管理器
 * @author DT
 */
public class ConditionManager<T> extends AbstractConditionManager<T> {
    
    private static final long serialVersionUID = 4261564506488337105L;
    
    /**
     * 构造方法
     * @param criteria 查询对象
     */
    public ConditionManager( AbstractCriteria<T> criteria ) {
        this.criteria = criteria;
    }
    
    @Override
    protected AbstractConditionManager<T> instance( AbstractCriteria<T> criteria ) {
        return new ConditionManager<>( criteria );
    }
}
