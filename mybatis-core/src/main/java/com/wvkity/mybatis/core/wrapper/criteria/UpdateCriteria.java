package com.wvkity.mybatis.core.wrapper.criteria;

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

    /**
     * 创建条件包装对象
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 条件包装对象
     */
    public static <T> UpdateCriteria<T> from(final Class<T> entity) {
        return new UpdateCriteria<>(entity);
    }

    /**
     * 创建条件包装对象
     * @param instance 实体对象
     * @param <T>      泛型类型
     * @return 条件包装对象
     */
    @SuppressWarnings("unchecked")
    public static <T> UpdateCriteria<T> from(T instance) {
        return from((Class<T>) instance.getClass());
    }
}
