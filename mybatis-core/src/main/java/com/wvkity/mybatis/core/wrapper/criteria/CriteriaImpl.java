package com.wvkity.mybatis.core.wrapper.criteria;

import java.util.Map;

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
    private final UpdateCriteria<T> delegate;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public CriteriaImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
        this.inits();
        this.delegate = new UpdateCriteria<>(this.entityClass);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     */
    public CriteriaImpl(Class<T> entityClass, String alias) {
        this.entityClass = entityClass;
        this.tableAlias = alias;
        this.inits();
        this.delegate = new UpdateCriteria<>(this.entityClass);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     * @param delegate    更新操作代理对象
     */
    private CriteriaImpl(Class<T> entityClass, String alias, UpdateCriteria<T> delegate) {
        this.entityClass = entityClass;
        this.tableAlias = alias;
        this.delegate = delegate;
    }

    @Override
    public CriteriaImpl<T> set(String property, Object value) {
        this.delegate.set(property, value);
        return this;
    }

    @Override
    public CriteriaImpl<T> set(Map<String, Object> properties) {
        this.delegate.set(properties);
        return this;
    }

    @Override
    public CriteriaImpl<T> setWith(String column, Object value) {
        this.delegate.setWith(column, value);
        return this;
    }

    @Override
    public CriteriaImpl<T> setWith(Map<String, Object> columns) {
        this.delegate.setWith(columns);
        return this;
    }

    @Override
    public CriteriaImpl<T> version(Object version) {
        this.delegate.version(version);
        return this;
    }

    @Override
    public String getUpdateSegment() {
        return this.delegate.getUpdateSegment();
    }

    @Override
    protected CriteriaImpl<T> newInstance() {
        CriteriaImpl<T> instance = new CriteriaImpl<>(this.entityClass, this.tableAlias, this.delegate);
        copy(instance, this);
        return instance;
    }

    /**
     * 创建条件包装对象
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 条件包装对象
     */
    public static <T> CriteriaImpl<T> from(final Class<T> entity) {
        return new CriteriaImpl<>(entity);
    }
}
