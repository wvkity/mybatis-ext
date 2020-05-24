package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Comparator;
import com.wvkity.mybatis.core.constant.Join;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Range;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.aggreate.AbstractFunction;
import com.wvkity.mybatis.core.wrapper.aggreate.Aggregator;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.aggreate.FunctionBuilder;
import com.wvkity.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wvkity.mybatis.core.wrapper.basic.AbstractQueryWrapper;
import com.wvkity.mybatis.core.wrapper.basic.AbstractSortWrapper;
import com.wvkity.mybatis.core.wrapper.basic.Case;
import com.wvkity.mybatis.core.wrapper.basic.CaseQuery;
import com.wvkity.mybatis.core.wrapper.basic.DirectGroup;
import com.wvkity.mybatis.core.wrapper.basic.DirectQuery;
import com.wvkity.mybatis.core.wrapper.basic.DirectSort;
import com.wvkity.mybatis.core.wrapper.basic.FunctionQuery;
import com.wvkity.mybatis.core.wrapper.basic.FunctionSort;
import com.wvkity.mybatis.core.wrapper.basic.Group;
import com.wvkity.mybatis.core.wrapper.basic.Query;
import com.wvkity.mybatis.core.wrapper.basic.QueryManager;
import com.wvkity.mybatis.core.wrapper.basic.Sort;
import com.wvkity.mybatis.executor.resultset.EmbeddedResult;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;


/**
 * 抽象查询条件包装器
 * @param <T> 实体类
 * @author wvkity
 */
@Log4j2
@SuppressWarnings({"serial", "unchecked"})
public abstract class AbstractQueryCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements EmbeddedResult, RangeFetch, QueryWrapper<T, AbstractQueryCriteriaWrapper<T>> {

    // region fields

    /**
     * 主表查询条件对象
     */
    protected AbstractQueryCriteriaWrapper<?> master;

    /**
     * 是否开启属性名自动映射成字段别名
     */
    @Getter
    protected boolean propertyAutoMappingAlias = false;

    /**
     * 查询是否包含聚合函数
     */
    protected boolean includeFunctionForQuery = true;

    /**
     * 仅仅只查询聚合函数
     */
    protected boolean onlyFunctionForQuery = false;
    
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

    /**
     * 连表对象集合
     */
    protected final Set<AbstractForeignCriteria<?>> FOREIGN_CRITERIA_SET = new LinkedHashSet<>(8);

    /**
     * 连表对象缓存
     */
    protected final Map<String, AbstractForeignCriteria<?>> FOREIGN_CRITERIA_CACHE = new ConcurrentHashMap<>(8);

    @Override
    protected void inits() {
        super.inits();
        this.queryManager = new QueryManager(this);
    }

    // endregion

    // region query column


    @Override
    public AbstractQueryCriteriaWrapper<T> filtrate(Predicate<ColumnWrapper> predicate) {
        if (predicate != null) {
            List<ColumnWrapper> its = this.filtration(predicate);
            if (CollectionUtil.hasElement(its)) {
                this.queryManager.queries(Query.Multi.query(this, its));
            }
        }
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(String property) {
        this.queryManager.query(Query.Single.query(this, property));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(String property, String alias) {
        this.queryManager.query(Query.Single.query(this, property, alias));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(String column) {
        this.queryManager.query(DirectQuery.Single.query(this, column, null));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(String column, String alias) {
        this.queryManager.query(DirectQuery.Single.query(this, column, alias));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(String tableAlias, String column, String alias) {
        this.queryManager.query(DirectQuery.Single.query(tableAlias, column, alias));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(Collection<String> properties) {
        this.queryManager.queries(Query.Multi.query(this, properties));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(Map<String, String> properties) {
        this.queryManager.queries(Query.Multi.query(this, properties));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(Collection<String> columns) {
        this.queryManager.queries(DirectQuery.Multi.query(this, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(Map<String, String> columns) {
        this.queryManager.queries(DirectQuery.Multi.query(this, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(String tableAlias, Map<String, String> columns) {
        this.queryManager.queries(DirectQuery.Multi.query(tableAlias, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selectWith(String tableAlias, Collection<String> columns) {
        this.queryManager.queries(DirectQuery.Multi.query(tableAlias, columns));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> exclude(String property) {
        this.queryManager.exclude(property);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> excludes(Collection<String> properties) {
        this.queryManager.excludes(properties);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> excludeWith(String column) {
        this.queryManager.excludeWith(column);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> excludesWith(Collection<String> columns) {
        this.queryManager.excludes(columns);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> select(Case _case) {
        this.queryManager.query(CaseQuery.Single.query(_case));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> selects(Collection<Case> cases) {
        this.queryManager.queries(CaseQuery.Multi.queries(cases));
        return this;
    }

    protected <E> AbstractQueryCriteriaWrapper<T> ifPresent(Criteria<E> criteria,
                                                            Consumer<? super Criteria<E>> consumer) {
        Optional.ofNullable(criteria).ifPresent(consumer);
        return this;
    }

    // endregion

    // region foreign criteria

    @Override
    public <E> ForeignCriteria<E> join(Class<E> entity, Join join) {
        return cacheForeign(new ForeignCriteria<>(entity, this, join));
    }

    @Override
    public <E> ForeignCriteria<E> join(Class<E> entity, Join join, Consumer<ForeignCriteria<E>> consumer) {
        ForeignCriteria<E> instance = join(entity, join);
        Optional.ofNullable(consumer).ifPresent(it -> it.accept(instance));
        return cacheForeign(instance);
    }

    @Override
    public <E> ForeignCriteria<E> join(Class<E> entity, Join join, BiConsumer<AbstractQueryCriteriaWrapper<T>,
            ForeignCriteria<E>> consumer) {
        ForeignCriteria<E> instance = join(entity, join);
        Optional.ofNullable(consumer).ifPresent(it -> it.accept(this, instance));
        return cacheForeign(instance);
    }

    @Override
    public ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join) {
        ForeignSubCriteria<?> instance = new ForeignSubCriteria<>(sc, this, join);
        cacheForeign(instance);
        return instance;
    }

    @Override
    public ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join,
                                      Consumer<ForeignSubCriteria<?>> consumer) {
        ForeignSubCriteria<?> instance = join(sc, join);
        Optional.ofNullable(consumer).ifPresent(it -> it.accept(instance));
        cacheForeign(instance);
        return instance;
    }

    @Override
    public ForeignSubCriteria<?> join(SubCriteria<?> sc, Join join,
                                      BiConsumer<AbstractQueryCriteriaWrapper<T>,
                                              ForeignSubCriteria<?>> consumer) {
        ForeignSubCriteria<?> instance = join(sc, join);
        Optional.ofNullable(consumer).ifPresent(it -> it.accept(this, instance));
        cacheForeign(instance);
        return instance;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> add(List<ForeignCriteria<?>> list) {
        if (CollectionUtil.hasElement(list)) {
            list.stream().filter(Objects::nonNull).forEach(this::cacheForeign);
        }
        return this;
    }

    /**
     * 缓存连表条件包装对象
     * @param foreign 连表条件包装对象
     * @param <E>     实体类型
     * @return {@link ForeignCriteria}
     */
    private <E> ForeignCriteria<E> cacheForeign(final ForeignCriteria<E> foreign) {
        Optional.ofNullable(foreign).ifPresent(it -> {
            it.useAs();
            it.master.useAs();
            final String alias = foreign.alias();
            FOREIGN_CRITERIA_SET.add(foreign);
            if (StringUtil.hasText(alias)) {
                // 缓存
                FOREIGN_CRITERIA_CACHE.putIfAbsent(alias, foreign);
            }
        });
        return foreign;
    }

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
        return sort(Sort.asc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> ascWithAlias(String alias, List<String> columns) {
        return sort(DirectSort.ascWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> asc(Function... functions) {
        return sort(FunctionSort.asc(functions));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> funcAsc(List<String> aliases) {
        return sort(FunctionSort.asc(this.searchFunctions(aliases)));
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
        return sort(Sort.desc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> descWithAlias(String alias, List<String> columns) {
        return sort(DirectSort.descWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> desc(Function... functions) {
        return sort(FunctionSort.desc(functions));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> funcDesc(List<String> aliases) {
        return sort(FunctionSort.desc(this.searchFunctions(aliases)));
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
    public AbstractQueryCriteriaWrapper<T> sort(AbstractSortWrapper<?> sort) {
        this.segmentManager.sort(sort);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sorts(List<AbstractSortWrapper<?>> sorts) {
        this.segmentManager.sorts(sorts);
        return this;
    }

    // endregion

    // region group by

    @Override
    public AbstractQueryCriteriaWrapper<T> group(String property) {
        return group(Group.group(this, property));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> group(AbstractGroupWrapper<?> group) {
        this.segmentManager.group(group);
        return this;
    }

    public AbstractQueryCriteriaWrapper<T> groups(Collection<String> properties) {
        return group(Group.group(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> groupWithAlias(String alias, List<String> columns) {
        return group(DirectGroup.groupWithAlias(alias, columns));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> AbstractQueryCriteriaWrapper<T> foreignGroup(String foreignAlias, Property<T, V>... properties) {
        return null;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> foreignGroup(String foreignAlias, List<String> properties) {
        return null;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> groups(List<AbstractGroupWrapper<?>> groups) {
        this.segmentManager.groups(groups);
        return this;
    }

    // endregion

    // region function

    // region count

    @Override
    public AbstractQueryCriteriaWrapper<T> count() {
        return function(FunctionBuilder.create().criteria(this)
                .column(TableHandler.getPrimaryKey(this.entityClass)).count());
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> count(Case _case) {
        return function(Aggregator.count(this, _case));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> count(String property, boolean distinct, String alias) {
        return function(Aggregator.count(this, property, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> count(String property, boolean distinct, String alias,
                                                 Comparator comparator, Object value) {
        return function(Aggregator.count(this, property, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> count(String property, boolean distinct, String alias,
                                                 Comparator comparator, Object min, Object max) {
        return function(Aggregator.count(this, property, distinct, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> countWith(String column, boolean distinct, String alias) {
        return function(Aggregator.countWith(this, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> countWith(String tableAlias, String column, boolean distinct, String alias) {
        return function(Aggregator.countWith(tableAlias, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> countWith(String column, boolean distinct, String alias,
                                                     Comparator comparator, Object value) {
        return function(Aggregator.countWith(this, column, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> countWith(String column, boolean distinct, String alias,
                                                     Comparator comparator, Object min, Object max) {
        return function(Aggregator.countWith(this, column, distinct, alias, comparator, min, max, Logic.AND));
    }

    // endregion

    // region sum

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(Case _case) {
        return function(Aggregator.sum(this, _case));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct, String alias) {
        return function(Aggregator.sum(this, property, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct,
                                               String alias, Comparator comparator, Object value) {
        return function(Aggregator.sum(this, property, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct, String alias,
                                               Comparator comparator, Object min, Object max) {
        return function(Aggregator.sum(this, property, distinct, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct, String alias, Integer scale) {
        return peek(Aggregator.sum(this, property, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct, String alias,
                                               Integer scale, Comparator comparator, Object value) {
        return peek(Aggregator.sum(this, property, distinct, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sum(String property, boolean distinct, String alias,
                                               Integer scale, Comparator comparator, Object min, Object max) {
        return peek(Aggregator.sum(this, property, distinct, alias, comparator, min, max, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias) {
        return function(Aggregator.sumWith(this, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String tableAlias, String column, boolean distinct, String alias) {
        return function(Aggregator.sumWith(tableAlias, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias,
                                                   Comparator comparator, Object value) {
        return function(Aggregator.sumWith(this, column, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias,
                                                   Comparator comparator, Object min, Object max) {
        return function(Aggregator.sumWith(this, column, distinct, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias, Integer scale) {
        return peek(Aggregator.sumWith(this, column, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String tableAlias, String column, boolean distinct,
                                                   String alias, Integer scale) {
        return peek(Aggregator.sumWith(tableAlias, column, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias, Integer scale,
                                                   Comparator comparator, Object value) {
        return peek(Aggregator.sumWith(this, column, distinct, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> sumWith(String column, boolean distinct, String alias, Integer scale,
                                                   Comparator comparator, Object min, Object max) {
        return peek(Aggregator.sumWith(this, column, distinct, alias, comparator, min, max, Logic.AND), scale);
    }

    // endregion

    // region avg

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(Case _case) {
        return function(Aggregator.avg(this, _case));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias) {
        return function(Aggregator.avg(this, property, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias,
                                               Comparator comparator, Object value) {
        return function(Aggregator.avg(this, property, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias,
                                               Comparator comparator, Object min, Object max) {
        return function(Aggregator.avg(this, property, distinct, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias, Integer scale) {
        return peek(Aggregator.avg(this, property, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias,
                                               Integer scale, Comparator comparator, Object value) {
        return peek(Aggregator.avg(this, property, distinct, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avg(String property, boolean distinct, String alias,
                                               Integer scale, Comparator comparator, Object min, Object max) {
        return peek(Aggregator.avg(this, property, distinct, alias, comparator, min, max, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias) {
        return function(Aggregator.avgWith(this, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String tableAlias, String column, boolean distinct, String alias) {
        return function(Aggregator.avgWith(tableAlias, column, distinct, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias,
                                                   Comparator comparator, Object value) {
        return function(Aggregator.avgWith(this, column, distinct, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias,
                                                   Comparator comparator, Object min, Object max) {
        return function(Aggregator.avgWith(this, column, distinct, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias, Integer scale) {
        return peek(Aggregator.avgWith(this, column, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String tableAlias, String column, boolean distinct,
                                                   String alias, Integer scale) {
        return peek(Aggregator.avgWith(tableAlias, column, distinct, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias, Integer scale,
                                                   Comparator comparator, Object value) {
        return peek(Aggregator.avgWith(this, column, distinct, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> avgWith(String column, boolean distinct, String alias, Integer scale,
                                                   Comparator comparator, Object min, Object max) {
        return peek(Aggregator.avgWith(this, column, distinct, alias, comparator, min, max, Logic.AND), scale);
    }

    // endregion

    // region min

    @Override
    public AbstractQueryCriteriaWrapper<T> min(Case _case) {
        return function(Aggregator.min(this, _case));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> min(String property, String alias) {
        return function(Aggregator.min(this, property, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> min(String property, String alias,
                                               Comparator comparator, Object value) {
        return function(Aggregator.min(this, property, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> min(String property, String alias,
                                               Comparator comparator, Object min, Object max) {
        return function(Aggregator.min(this, property, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> min(String property, String alias, Integer scale) {
        return peek(Aggregator.min(this, property, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> min(String property, String alias, Integer scale,
                                               Comparator comparator, Object min, Object max) {
        return peek(Aggregator.min(this, property, alias, comparator, min, max, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias) {
        return function(Aggregator.minWith(this, column, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String tableAlias, String column, String alias) {
        return function(Aggregator.minWith(tableAlias, column, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias, Comparator comparator, Object value) {
        return function(Aggregator.minWith(this, column, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias, 
                                                   Comparator comparator, Object min, Object max) {
        return function(Aggregator.minWith(this, column, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias, Integer scale) {
        return peek(Aggregator.minWith(this, column, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String tableAlias, String column, String alias, Integer scale) {
        return peek(Aggregator.minWith(tableAlias, column, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias, Integer scale, 
                                                   Comparator comparator, Object value) {
        return peek(Aggregator.minWith(this, column, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> minWith(String column, String alias, Integer scale, 
                                                   Comparator comparator, Object min, Object max) {
        return peek(Aggregator.minWith(this, column, alias, comparator, min, max, Logic.AND), scale);
    }

    // endregion

    // region max

    @Override
    public AbstractQueryCriteriaWrapper<T> max(Case _case) {
        return function(Aggregator.max(this, _case));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> max(String property, String alias) {
        return function(Aggregator.max(this, property, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> max(String property, String alias,
                                               Comparator comparator, Object value) {
        return function(Aggregator.max(this, property, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> max(String property, String alias,
                                               Comparator comparator, Object min, Object max) {
        return function(Aggregator.max(this, property, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> max(String property, String alias, Integer scale) {
        return peek(Aggregator.max(this, property, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> max(String property, String alias, Integer scale,
                                               Comparator comparator, Object min, Object max) {
        return peek(Aggregator.max(this, property, alias, comparator, min, max, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias) {
        return function(Aggregator.maxWith(this, column, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String tableAlias, String column, String alias) {
        return function(Aggregator.maxWith(tableAlias, column, alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias, Comparator comparator, Object value) {
        return function(Aggregator.maxWith(this, column, alias, comparator, value, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias,
                                                   Comparator comparator, Object min, Object max) {
        return function(Aggregator.maxWith(this, column, alias, comparator, min, max, Logic.AND));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias, Integer scale) {
        return peek(Aggregator.maxWith(this, column, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String tableAlias, String column, String alias, Integer scale) {
        return peek(Aggregator.maxWith(tableAlias, column, alias), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias, Integer scale,
                                                   Comparator comparator, Object value) {
        return peek(Aggregator.maxWith(this, column, alias, comparator, value, Logic.AND), scale);
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> maxWith(String column, String alias, Integer scale,
                                                   Comparator comparator, Object min, Object max) {
        return peek(Aggregator.maxWith(this, column, alias, comparator, min, max, Logic.AND), scale);
    }

    // endregion

    private AbstractQueryCriteriaWrapper<T> peek(AbstractFunction<?> function, Integer scale) {
        Optional.ofNullable(function).ifPresent(it -> {
            it.scale(scale);
            function(it);
        });
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> function(Function function) {
        this.queryManager.query(FunctionQuery.Single.query(function));
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> functions(Collection<Function> functions) {
        this.queryManager.queries(FunctionQuery.Multi.queries(functions));
        return this;
    }

    // endregion


    // region having

    @Override
    public AbstractQueryCriteriaWrapper<T> having(String alias) {
        return this.having(this.queryManager.searchFunction(alias));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> havings(List<String> aliases) {
        return this.havings(this.queryManager.searchFunctions(aliases));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> having(Function function) {
        this.segmentManager.having(function);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> havings(Collection<Function> functions) {
        this.segmentManager.havings(functions);
        return this;
    }


    // endregion

    // region get/set methods

    @Override
    public <E> AbstractQueryCriteriaWrapper<E> getMaster() {
        return this.master != null ? (AbstractQueryCriteriaWrapper<E>) this.master : null;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<E> getRootMaster() {
        AbstractQueryCriteriaWrapper<E> rootMaster;
        if (this.isRoot()) {
            return (AbstractQueryCriteriaWrapper<E>) this;
        } else {
            AbstractQueryCriteriaWrapper<E> root = getMaster();
            while (!root.isRoot()) {
                root = root.getMaster();
            }
            return root;
        }
    }

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
        return Range.NONE;
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
    public AbstractQueryCriteriaWrapper<T> excludeFunc() {
        this.includeFunctionForQuery = false;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> onlyQueryFunc() {
        this.onlyFunctionForQuery = true;
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> groups() {
        this.groupAll = true;
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

    @Override
    public Function searchFunction(String alias) {
        return Optional.ofNullable(this.queryManager.searchFunction(alias)).orElse(null);
    }

    @Override
    public List<Function> searchFunctions(List<String> aliases) {
        return Optional.ofNullable(this.queryManager.searchFunctions(aliases)).orElse(null);
    }

    @Override
    public String getQuerySegment() {
        if (CollectionUtil.hasElement(this.FOREIGN_CRITERIA_SET)) {
            List<String> segments = new ArrayList<>(this.FOREIGN_CRITERIA_SET.size() + 1);
            String segment = this.isOnly() ? this.queryManager.getFuncSegment() : this.queryManager.getSegment();
            if (StringUtil.hasText(segment)) {
                segments.add(segment);
            }
            for (AbstractForeignCriteria<?> it : this.FOREIGN_CRITERIA_SET) {
                if (it.isFetch() && it instanceof ForeignSubCriteria) {
                    Set<String> columns = it.getQueryColumns();
                    String realAlias = StringUtil.hasText(it.as()) ?
                            (it.as().trim() + Constants.DOT) : Constants.EMPTY;
                    if (CollectionUtil.hasElement(columns)) {
                        segments.add(columns.stream().map(c -> realAlias + c)
                                .collect(Collectors.joining(Constants.COMMA_SPACE)));
                    }
                } else if (it.isFetch() || it.queryManager.hasQueries()) {
                    String temp = it.queryManager.getSegment();
                    if (StringUtil.hasText(temp)) {
                        segments.add(temp);
                    }
                }
            }
            if (CollectionUtil.hasElement(segments)) {
                return String.join(Constants.COMMA_SPACE, segments);
            } else {
                return Constants.EMPTY;
            }
        }
        return this.isOnly() ? this.queryManager.getFuncSegment() : this.queryManager.getSegment();
    }

    /**
     * 获取查询字段集合
     * @return 字段集合
     */
    protected Set<String> getQueryColumns() {
        return null;
    }

    @Override
    public String getSegment() {
        return this.segmentManager.getSegment(isGroupAll() ? getGroupSegment() : null);
    }

    public String getGroupSegment() {
        List<String> columns = new ArrayList<>();
        List<AbstractQueryWrapper<?>> queries = this.queryManager.getQueries();
        if (CollectionUtil.hasElement(queries)) {
            String realAlias = StringUtil.hasText(this.as()) ?
                    (this.as().trim() + Constants.DOT) : Constants.EMPTY;
            for (AbstractQueryWrapper<?> it : queries) {
                if (it instanceof Query || it instanceof DirectQuery) {
                    columns.add(realAlias + it.columnName());
                }
            }
        }
        if (CollectionUtil.hasElement(this.FOREIGN_CRITERIA_SET)) {
            for (AbstractForeignCriteria<?> it : this.FOREIGN_CRITERIA_SET) {
                if (it.isFetch() && it instanceof ForeignSubCriteria) {
                    String realAlias = StringUtil.hasText(it.as()) ?
                            (it.as().trim() + Constants.DOT) : Constants.EMPTY;
                    Set<String> its = it.getQueryColumns();
                    if (CollectionUtil.hasElement(its)) {
                        for (String c : its) {
                            columns.add(realAlias + c);
                        }
                    }
                } else if (it.isFetch() || it.queryManager.hasQueries()) {
                    List<AbstractQueryWrapper<?>> its = it.queryManager.getQueries();
                    String realAlias = StringUtil.hasText(it.as()) ?
                            (it.as().trim() + Constants.DOT) : Constants.EMPTY;
                    for (AbstractQueryWrapper<?> query : its) {
                        if (query instanceof Query || query instanceof DirectQuery) {
                            columns.add(realAlias + query.columnName());
                        }
                    }
                }
            }
        }
        return columns.isEmpty() ? Constants.EMPTY : String.join(Constants.COMMA_SPACE, columns);
    }

    /**
     * 获取连表SQL片段
     * @return SQL片段
     */
    public String getForeignSegment() {
        if (!FOREIGN_CRITERIA_SET.isEmpty()) {
            return this.FOREIGN_CRITERIA_SET.stream().map(it -> {
                StringBuilder builder = new StringBuilder(60);
                builder.append(it.getJoin().getSegment()).append(Constants.SPACE);
                if (it instanceof ForeignSubCriteria) {
                    builder.append(((ForeignSubCriteria<?>) it).getSubCriteria().getSegmentForCondition());
                    builder.append(" AS ").append(it.as());
                } else {
                    builder.append(it.getTableName());
                }
                builder.append(" ON ");
                String condition = it.getSegment();
                if (AND_OR_PATTERN.matcher(condition).matches()) {
                    builder.append(condition.replaceFirst(AND_OR_REGEX, "$2"));
                } else {
                    builder.append(condition);
                }
                return builder.append(Constants.SPACE).toString();
            }).collect(Collectors.joining(Constants.NEW_LINE));
        }
        return Constants.EMPTY;
    }

    // endregion
}
