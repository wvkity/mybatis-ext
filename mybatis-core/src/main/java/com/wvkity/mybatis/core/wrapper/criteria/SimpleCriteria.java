package com.wvkity.mybatis.core.wrapper.criteria;

/**
 * 基本条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
public class SimpleCriteria<T> extends AbstractCriteriaWrapper<T> {

    private static final long serialVersionUID = -6095556771822358933L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public SimpleCriteria(Class<T> entityClass) {
        this(entityClass, null);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     */
    public SimpleCriteria(Class<T> entityClass, String alias) {
        this.entityClass = entityClass;
        this.inits();
        this.tableAlias = alias;
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
    }

    @Override
    protected SimpleCriteria<T> newInstance() {
        SimpleCriteria<T> instance = new SimpleCriteria<>(this.entityClass, this.tableAlias);
        copy(instance, this);
        return instance;
    }

    /**
     * 创建条件包装对象
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 条件包装对象
     */
    public static <T> SimpleCriteria<T> from(final Class<T> entity) {
        return new SimpleCriteria<>(entity);
    }

    /**
     * 创建条件包装对象
     * @param instance 实体对象
     * @param <T>      泛型类型
     * @return 条件包装对象
     */
    @SuppressWarnings("unchecked")
    public static <T> SimpleCriteria<T> from(T instance) {
        return from((Class<T>) instance.getClass());
    }
}
