package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.constant.Range;
import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.wrapper.aggreate.Aggregation;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wkit.lost.mybatis.core.wrapper.basic.DirectOrder;
import com.wkit.lost.mybatis.core.wrapper.basic.DirectQuery;
import com.wkit.lost.mybatis.core.wrapper.basic.Order;
import com.wkit.lost.mybatis.core.wrapper.basic.Query;
import com.wkit.lost.mybatis.core.wrapper.basic.QueryManager;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;


/**
 * 抽象查询条件包装器
 * @param <T> 实体类
 * @author wvkity
 */
@Log4j2
@SuppressWarnings({"serial"})
public abstract class AbstractQueryCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements QueryWrapper<T, AbstractQueryCriteriaWrapper<T>> {

    // region fields

    /**
     * 是否开启属性名自动映射成字段别名
     */
    @Getter
    protected boolean propertyAutoMappingAlias = false;

    /**
     * 查询SQL片段
     */
    private String querySegment;

    /**
     * 返回值映射Map
     */
    protected String resultMap;

    /**
     * 返回值类型
     */
    protected Class<?> resultType;

    /**
     * 开始行
     */
    @Getter
    protected long rowStart;

    /**
     * 结束行
     */
    @Getter
    protected long rowEnd;

    /**
     * 开始页
     */
    @Getter
    protected long pageStart;

    /**
     * 结束页
     */
    @Getter
    protected long pageEnd;

    /**
     * 每页数目
     */
    @Getter
    protected long pageSize;

    /**
     * 查询管理器
     */
    @Getter
    protected QueryManager queryManager;

    @Override
    protected void inits() {
        super.inits();
        this.queryManager = new QueryManager(this);
    }

    // endregion

    // region query column

    @Override
    public AbstractQueryCriteriaWrapper<T> select(String property) {
        this.queryManager.add(Query.Single.query(this, property));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(String property, String alias) {
        this.queryManager.add(Query.Single.query(this, property, alias));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelect(String column) {
        this.queryManager.add(DirectQuery.Single.query(this, column, null));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelect(String column, String alias) {
        this.queryManager.add(DirectQuery.Single.query(this, column, alias));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelect(String tableAlias, String column, String alias) {
        this.queryManager.add(DirectQuery.Single.query(tableAlias, column, alias));
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> criteria, String property) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> criteria, String property, String alias) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selects(Collection<String> properties) {
        this.queryManager.add(Query.Multi.query(this, properties));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selects(Map<String, String> properties) {
        this.queryManager.add(Query.Multi.query(this, properties));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelects(Collection<String> columns) {
        this.queryManager.add(DirectQuery.Multi.query(this, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelects(Map<String, String> columns) {
        this.queryManager.add(DirectQuery.Multi.query(this, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelects(String tableAlias, Map<String, String> columns) {
        this.queryManager.add(DirectQuery.Multi.query(tableAlias, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directSelects(String tableAlias, Collection<String> columns) {
        this.queryManager.add(DirectQuery.Multi.query(tableAlias, columns));
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelects(SubCriteria<E> criteria, Collection<String> properties) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelects(SubCriteria<E> criteria, Map<String, String> properties) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> excludes(Collection<String> properties) {
        this.queryManager.excludes(properties);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directExcludes(Collection<String> columns) {
        this.queryManager.excludes(columns);
        return this;
    }

    protected <E> AbstractQueryCriteriaWrapper<T> ifPresent(Criteria<E> criteria,
                                                            Consumer<? super Criteria<E>> consumer) {
        Optional.ofNullable(criteria).ifPresent(consumer);
        return this;
    }

    @Override
    public String getQuerySegment() {
        return this.queryManager.getSegment();
    }

    // endregion

    // region foreign criteria
    @Override
    public <E> ForeignCriteria<E> searchForeign(String alias) {
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign(Class<E> entity) {
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign(String alias, Class<E> entity) {
        return null;
    }
    // endregion

    // region order by

    @Override
    public AbstractQueryCriteriaWrapper<T> asc(List<String> properties) {
        return add(Order.asc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directAscWithAlias(String alias, List<String> columns) {
        return add(DirectOrder.ascWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> asc(Aggregation... aggregations) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> aggregateAsc(List<String> aliases) {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> AbstractQueryCriteriaWrapper<T> foreignAsc(String foreignAlias, Property<T, V>... properties) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> foreignAsc(String foreignAlias, List<String> properties) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> desc(List<String> properties) {
        return add(Order.desc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> directDescWithAlias(String alias, List<String> columns) {
        return add(DirectOrder.descWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> desc(Aggregation... aggregations) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> aggregateDesc(List<String> aliases) {
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> AbstractQueryCriteriaWrapper<T> foreignDesc(String foreignAlias, Property<T, V>... properties) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> foreignDesc(String foreignAlias, List<String> properties) {
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> addOrder(List<AbstractOrderWrapper<?, ?>> orders) {
        this.segmentManager.addOrder(orders);
        return this;
    }

    // endregion

    // region group by
    // endregion

    // region get/set methods

    @Override
    public boolean isEnableAlias() {
        return this.enableAlias;
    }

    @Override
    public AbstractCriteriaWrapper<T> as(boolean enabled) {
        this.enableAlias = enabled;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> propertyAutoMappingAlias(boolean enable) {
        this.propertyAutoMappingAlias = enable;
        return this;
    }

    @Override
    public boolean isRange() {
        return (rowStart >= 0 && rowEnd > 0) || (pageStart > 0 && pageEnd > 0);
    }

    @Override
    public Range range() {
        if (rowStart >= 0 && rowEnd > 0) {
            return Range.IMMEDIATE;
        } else if (pageStart > 0 && pageEnd > 0) {
            return Range.PAGEABLE;
        }
        return super.range();
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> range(long start, long end) {
        this.rowStart = start;
        this.rowEnd = end;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> range(long pageStart, long pageEnd, long size) {
        this.pageStart = pageStart;
        this.pageEnd = pageEnd;
        this.pageSize = size;
        return this;
    }

    @Override
    public boolean isOnly() {
        return this.onlyFunctionForQuery;
    }

    @Override
    public boolean isInclude() {
        return this.includeFunctionForQuery;
    }

    @Override
    public String resultMap() {
        return this.resultMap;
    }

    @Override
    public Class<?> resultType() {
        return this.resultType;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> resultMap(String resultMap) {
        this.resultMap = resultMap;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> resultType(Class<?> resultType) {
        this.resultType = resultType;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> useAs() {
        this.enableAlias = true;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> as(String alias) {
        this.enableAlias = true;
        this.tableAlias = alias;
        return this;
    }

    // endregion


    @Override
    public String getSegment() {
        return this.segmentManager.getSegment(isGroupAll() ? getGroupSegment() : null);
    }

    // region abstract methods
    public String getGroupSegment() {
        return "";
    }
    // endregion
}
