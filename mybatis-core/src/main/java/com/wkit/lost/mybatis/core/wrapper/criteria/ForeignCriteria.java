package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Join;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.core.wrapper.basic.QueryManager;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 联表条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
public class ForeignCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    private static final long serialVersionUID = 4721325414781275867L;

    /**
     * 主表条件包装对象
     */
    protected final AbstractQueryCriteriaWrapper<?> master;

    /**
     * 连接模式
     */
    @Getter(AccessLevel.MODULE)
    protected final Join join;

    /**
     * 主表属性
     */
    protected String reference;

    /**
     * 抓取连表对象所查询的字段
     */
    @Getter
    protected boolean fetch = false;

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param join        连接模式
     * @param <E>         实体类型
     */
    public <E> ForeignCriteria(Class<T> entityClass, AbstractQueryCriteriaWrapper<E> master, Join join) {
        this(entityClass, master, join, null);
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表条件包装对象
     * @param join        连接模式
     * @param alias       别名
     * @param <E>         实体类型
     */
    public <E> ForeignCriteria(Class<T> entityClass, AbstractQueryCriteriaWrapper<E> master,
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

    /**
     * 添加多个条件
     * @param conditions 条件
     * @return {@code this}
     */
    public ForeignCriteria<T> on(Criterion<?>... conditions) {
        return on(ArrayUtil.toList(conditions));
    }

    /**
     * 添加多个条件
     * @param conditions 条件
     * @return {@code this}
     */
    public ForeignCriteria<T> on(Collection<Criterion<?>> conditions) {
        if (CollectionUtil.hasElement(conditions)) {
            this.where(conditions);
        }
        return this;
    }

    /**
     * 添加条件
     * @param consumer {@link Consumer}
     * @return {@code this}
     */
    public ForeignCriteria<T> on(Consumer<AbstractCriteriaWrapper<T>> consumer) {
        Optional.ofNullable(consumer).ifPresent(it -> it.accept(this));
        return this;
    }

    /**
     * 获取别名(不包含系统内置生成的别名)
     * @return 别名
     */
    protected String alias() {
        return this.tableAlias;
    }

    /**
     * 设置副表引用属性
     * @param reference 引用属性
     */
    public void reference(String reference) {
        this.reference = reference;
    }

    /**
     * 设置抓取连表对象所查询的字段
     * @return {@code this}
     */
    public ForeignCriteria<T> fetch() {
        this.fetch = true;
        return this;
    }

    @Override
    protected ForeignCriteria<T> newInstance() {
        ForeignCriteria<T> instance = new ForeignCriteria<>(this.entityClass, this.master, this.join);
        copy(instance, this);
        return instance;
    }
}
