package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.constant.Join;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * 抽象联表条件包装类
 * @param <T> 实体类型
 * @author wvkity
 */
@SuppressWarnings("serial")
public abstract class AbstractForeignCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    /**
     * 主表条件包装对象
     */
    protected AbstractQueryCriteriaWrapper<?> master;

    /**
     * 连接模式
     */
    @Getter(AccessLevel.MODULE)
    protected Join join;

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
     * 添加多个条件
     * @param conditions 条件
     * @return {@code this}
     */
    public AbstractForeignCriteria<T> on(Criterion... conditions) {
        return on(ArrayUtil.toList(conditions));
    }

    /**
     * 添加多个条件
     * @param conditions 条件
     * @return {@code this}
     */
    public AbstractForeignCriteria<T> on(Collection<Criterion> conditions) {
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
    public AbstractForeignCriteria<T> on(Consumer<AbstractCriteriaWrapper<T>> consumer) {
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
    public AbstractForeignCriteria<T> fetch() {
        this.fetch = true;
        return this;
    }
}
