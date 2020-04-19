package com.wkit.lost.mybatis.core.wrapper.criteria;

/**
 * 更新条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
public class UpdateCriteria<T> extends AbstractUpdateCriteriaWrapper<T> {

    private static final long serialVersionUID = 3553689733240459052L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public UpdateCriteria(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.inits();
    }

    @Override
    protected UpdateCriteria<T> newInstance() {
        UpdateCriteria<T> instance = new UpdateCriteria<>(this.entityClass);
        copy(instance, this);
        return instance;
    }
}
