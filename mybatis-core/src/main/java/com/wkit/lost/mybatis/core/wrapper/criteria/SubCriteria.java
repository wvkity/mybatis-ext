package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.core.wrapper.basic.QueryManager;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Collection;

/**
 * 子查询条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
public class SubCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    private static final long serialVersionUID = 4851693309633974454L;

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param clauses     条件
     * @param <E>         泛型类型
     */
    public <E> SubCriteria(Class<T> entityClass, AbstractCriteriaWrapper<E> master, Collection<Criterion<?>> clauses) {
        this(entityClass, null, master, clauses);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     * @param master      主表条件包装对象
     * @param <E>         泛型类型
     */
    public <E> SubCriteria(Class<T> entityClass, String alias, AbstractCriteriaWrapper<E> master) {
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
    public <E> SubCriteria(Class<T> entityClass, String alias, AbstractCriteriaWrapper<E> master,
                           Collection<Criterion<?>> clauses) {
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

    /**
     * 获取条件
     * @return 条件片段
     */
    public String getSegmentForCondition() {
        StringBuilder builder = new StringBuilder(Constants.BRACKET_LEFT);
        builder.append("SELECT ").append(this.getQuerySegment()).append(" FROM ").append(this.getTableName());
        builder.append(getForeignSegment());
        if (this.isHasCondition()) {
            builder.append(Constants.SPACE).append(this.getWhereSegment());
        }
        builder.append(Constants.BRACKET_RIGHT);
        return builder.toString();
    }
}
