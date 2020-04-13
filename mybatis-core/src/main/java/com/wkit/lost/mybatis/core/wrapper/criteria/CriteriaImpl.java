package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 通用条件包装器
 * @param <T> 实体类
 * @author wvkity
 */
public class CriteriaImpl<T> extends AbstractQueryCriteriaWrapper<T> implements UpdateWrapper<T, CriteriaImpl<T>> {

    private static final long serialVersionUID = -1502576053192302964L;

    /**
     * 更新条件包装器代理对象
     */
    private UpdateCriteria<T> delegate;
    
    @Override
    public CriteriaImpl<T> updateVersion(Object version) {
        return this;
    }
}
