package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.wrapper.basic.QueryManager;
import com.wvkity.mybatis.core.wrapper.basic.SegmentManager;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.Collection;

/**
 * 子查询条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
public class SubCriteria<T> extends AbstractSubCriteria<T> {

    private static final long serialVersionUID = 4851693309633974454L;

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param clauses     条件
     * @param <E>         泛型类型
     */
    public <E> SubCriteria(Class<T> entityClass, AbstractQueryCriteriaWrapper<E> master,
                           Collection<Criterion> clauses) {
        this(entityClass, null, master, clauses);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     * @param master      主表条件包装对象
     * @param <E>         泛型类型
     */
    public <E> SubCriteria(Class<T> entityClass, String alias, AbstractQueryCriteriaWrapper<E> master) {
        this(entityClass, alias, master, null);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     * @param master      主表条件包装对象
     * @param clauses     条件
     * @param <E>         泛型类型
     */
    public <E> SubCriteria(Class<T> entityClass, String alias, AbstractQueryCriteriaWrapper<E> master,
                           Collection<Criterion> clauses) {
        this.entityClass = entityClass;
        this.master = master;
        this.parameterSequence = master.parameterSequence;
        this.aliasSequence = master.aliasSequence;
        this.paramValueMappings = master.paramValueMappings;
        this.segmentManager = new SegmentManager();
        this.queryManager = new QueryManager(this);
        this.tableAlias = alias;
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
        if (CollectionUtil.hasElement(clauses)) {
            this.where(clauses);
        }
    }

    @Override
    protected AbstractCriteriaWrapper<T> newInstance() {
        SubCriteria<T> instance = new SubCriteria<>(this.entityClass, this.tableAlias, this.master);
        copy(instance, this);
        return instance;
    }

    @Override
    public boolean isRoot() {
        return false;
    }
}
