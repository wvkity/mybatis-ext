package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Join;
import com.wvkity.mybatis.core.constant.Range;
import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wvkity.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wvkity.mybatis.core.wrapper.basic.DirectGroup;
import com.wvkity.mybatis.core.wrapper.basic.DirectOrder;
import com.wvkity.mybatis.core.wrapper.basic.DirectQuery;
import com.wvkity.mybatis.core.wrapper.basic.Group;
import com.wvkity.mybatis.core.wrapper.basic.Order;
import com.wvkity.mybatis.core.wrapper.basic.Query;
import com.wvkity.mybatis.core.wrapper.basic.QueryManager;
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
import java.util.stream.Collectors;


/**
 * 抽象查询条件包装器
 * @param <T> 实体类
 * @author wvkity
 */
@Log4j2
@SuppressWarnings({"serial"})
public abstract class AbstractQueryCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements EmbeddedResult, RangeFetch, QueryWrapper<T, AbstractQueryCriteriaWrapper<T>> {

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
     * 连表SQL片段
     */
    private String foreignSegment;

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
    protected final Set<ForeignCriteria<?>> FOREIGN_CRITERIA_SET = new LinkedHashSet<>(8);

    /**
     * 连表对象缓存
     */
    protected final Map<String, ForeignCriteria<?>> FOREIGN_CRITERIA_CACHE = new ConcurrentHashMap<>(8);

    @Override
    protected void inits() {
        super.inits();
        this.queryManager = new QueryManager(this);
    }

    // endregion

    // region query column

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
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> sc, String property) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> sc, String property, String alias) {
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
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> sc, Collection<String> properties) {
        return this;
    }

    @Override
    public <E> AbstractQueryCriteriaWrapper<T> subSelect(SubCriteria<E> sc, Map<String, String> properties) {
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

    protected <E> AbstractQueryCriteriaWrapper<T> ifPresent(Criteria<E> criteria,
                                                            Consumer<? super Criteria<E>> consumer) {
        Optional.ofNullable(criteria).ifPresent(consumer);
        return this;
    }

    @Override
    public String getQuerySegment() {
        if (CollectionUtil.hasElement(this.FOREIGN_CRITERIA_SET)) {
            List<String> segments = new ArrayList<>(this.FOREIGN_CRITERIA_SET.size() + 1);
            String segment = this.queryManager.getSegment();
            if (StringUtil.hasText(segment)) {
                segments.add(segment);
            }
            for (ForeignCriteria<?> foreign : this.FOREIGN_CRITERIA_SET) {
                if (foreign instanceof ForeignSubCriteria && foreign.isFetch()) {
                    Set<String> columns = foreign.getQueryColumns();
                    String realAlias = StringUtil.hasText(foreign.as()) ?
                            (foreign.as().trim() + Constants.DOT) : Constants.EMPTY;
                    if (CollectionUtil.hasElement(columns)) {
                        segments.add(columns.stream().map(it -> realAlias + it)
                                .collect(Collectors.joining(Constants.COMMA_SPACE)));
                    }
                } else if (foreign.isFetch() || foreign.queryManager.hasQueries()) {
                    String temp = foreign.queryManager.getSegment();
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
        return this.queryManager.getSegment();
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
        return order(Order.asc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> ascWithAlias(String alias, List<String> columns) {
        return order(DirectOrder.ascWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> asc(Function... functions) {
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
        return order(Order.desc(this, properties));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> descWithAlias(String alias, List<String> columns) {
        return order(DirectOrder.descWithAlias(alias, columns));
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> desc(Function... functions) {
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
    public AbstractQueryCriteriaWrapper<T> order(AbstractOrderWrapper<?> order) {
        this.segmentManager.order(order);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> orders(List<AbstractOrderWrapper<?>> orders) {
        this.segmentManager.orders(orders);
        return this;
    }

    // endregion

    // region group by

    @Override
    public AbstractQueryCriteriaWrapper<T> group(AbstractGroupWrapper<?> group) {
        this.segmentManager.group(group);
        return this;
    }

    @Override
    public AbstractQueryCriteriaWrapper<T> group(List<String> properties) {
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
        return "";
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
