package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.segment.SegmentManager;

/**
 * 基本条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
public class BasicCriteria<T> extends AbstractCriteriaWrapper<T> {

    private static final long serialVersionUID = -6095556771822358933L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public BasicCriteria(Class<T> entityClass) {
        this(entityClass, null);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     */
    public BasicCriteria(Class<T> entityClass, String alias) {
        this.entityClass = entityClass;
        this.parameterSequence = master.parameterSequence;
        this.aliasSequence = master.aliasSequence;
        this.paramValueMappings = master.paramValueMappings;
        this.segmentManager = new SegmentManager();
        this.tableAlias = alias;
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
    }

    @Override
    protected BasicCriteria<T> newInstance() {
        BasicCriteria<T> instance = new BasicCriteria<>(this.entityClass, this.tableAlias);
        copy(instance, this);
        return instance;
    }

    /**
     * 创建条件包装对象
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 条件包装对象
     */
    public static <T> BasicCriteria<T> from(final Class<T> entity) {
        return new BasicCriteria<>(entity);
    }
}
