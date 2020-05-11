package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Join;
import com.wvkity.mybatis.core.wrapper.basic.SegmentManager;
import com.wvkity.mybatis.core.wrapper.basic.QueryManager;

/**
 * 联表条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
public class ForeignCriteria<T> extends AbstractForeignCriteria<T> {

    private static final long serialVersionUID = 4721325414781275867L;

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param join        连接模式
     */
    public ForeignCriteria(Class<T> entityClass, AbstractQueryCriteriaWrapper<?> master, Join join) {
        this(entityClass, master, join, null);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param join        连接模式
     * @param alias       别名
     */
    public ForeignCriteria(Class<T> entityClass, AbstractQueryCriteriaWrapper<?> master,
                           Join join, String alias) {
        this.entityClass = entityClass;
        this.master = master;
        this.join = join;
        this.parameterSequence = master.parameterSequence;
        this.aliasSequence = master.aliasSequence;
        this.paramValueMappings = master.paramValueMappings;
        this.tableAlias = alias;
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
        this.segmentManager = new SegmentManager();
        this.queryManager = new QueryManager(this);
    }

    @Override
    protected ForeignCriteria<T> newInstance() {
        ForeignCriteria<T> instance = new ForeignCriteria<>(this.entityClass, this.master, this.join);
        copy(instance, this);
        return instance;
    }

    @Override
    public boolean isRoot() {
        return false;
    }
}
