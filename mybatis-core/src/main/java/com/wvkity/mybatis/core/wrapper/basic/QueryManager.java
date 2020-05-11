package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.criteria.AbstractQueryCriteriaWrapper;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 查询管理器
 * @author wvkity
 */
public class QueryManager implements Segment {

    private static final long serialVersionUID = -3275773488142492015L;

    /**
     * 排序缓存
     */
    private final Map<String, Function> FUNCTION_CACHE = new ConcurrentHashMap<>(8);

    /**
     * 条件对象
     */
    private final AbstractQueryCriteriaWrapper<?> criteria;

    /**
     * 查询列容器
     */
    private final List<AbstractQueryWrapper<?>> WRAPPERS = new CopyOnWriteArrayList<>();

    /**
     * 查询聚合函数
     */
    private final List<AbstractQueryWrapper<?>> FUNCTION_WRAPPERS = new CopyOnWriteArrayList<>();

    /**
     * 排除查询属性
     */
    private final Set<String> EXCLUDE_PROPERTIES = new HashSet<>(8);

    /**
     * 排除查询列
     */
    private final Set<String> EXCLUDE_COLUMNS = new HashSet<>(8);

    /**
     * 标记SQL片段已生成
     */
    private volatile boolean cached = false;

    /**
     * SQL片段
     */
    private String sqlSegment = "";

    /**
     * 临时
     */
    private volatile List<AbstractQueryWrapper<?>> _wrappers;

    /**
     * 构造方法
     * @param criteria 条件对象
     */
    public QueryManager(AbstractQueryCriteriaWrapper<?> criteria) {
        this.criteria = criteria;
    }

    /**
     * 添加查询列
     * @param wrapper 查询列包装对象
     * @return {@code this}
     */
    public QueryManager query(AbstractQueryWrapper<?> wrapper) {
        if (wrapper != null) {
            if (wrapper instanceof FunctionQuery) {
                this.FUNCTION_WRAPPERS.add(wrapper);
                cacheFunction(wrapper);
            } else {
                this.WRAPPERS.add(wrapper);
            }
            this.cached = false;
        }
        return this;
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象数组
     * @return {@code this}
     */
    public QueryManager queries(AbstractQueryWrapper<?>... wrappers) {
        return queries(ArrayUtil.toList(wrappers));
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象集合
     * @return {@code this}
     */
    public QueryManager queries(Collection<? extends AbstractQueryWrapper<?>> wrappers) {
        if (CollectionUtil.hasElement(wrappers)) {
            List<? extends AbstractQueryWrapper<?>> its = wrappers.stream().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!its.isEmpty()) {
                for (AbstractQueryWrapper<?> it : its) {
                    if (it instanceof FunctionQuery) {
                        this.FUNCTION_WRAPPERS.add(it);
                        cacheFunction(it);
                    } else {
                        this.WRAPPERS.add(it);
                    }
                }
                this.cached = false;
            }
        }
        return this;
    }

    private void cacheFunction(AbstractQueryWrapper<?> wrapper) {
        if (StringUtil.hasText(wrapper.alias())) {
            this.FUNCTION_CACHE.put(wrapper.alias(), ((FunctionQuery) wrapper).getFunction());
        }
    }

    /**
     * 排除查询属性
     * @param property 属性
     * @return {@code this}
     */
    public QueryManager exclude(String property) {
        if (StringUtil.hasText(property)) {
            this.EXCLUDE_PROPERTIES.add(property);
        }
        return this;
    }

    /**
     * 排除查询属性
     * @param properties 属性数组
     * @return {@code this}
     */
    public QueryManager excludes(String... properties) {
        return excludes(ArrayUtil.toList(properties));
    }

    /**
     * 排除查询属性
     * @param properties 属性集合
     * @return {@code this}
     */
    public QueryManager excludes(Collection<String> properties) {
        Set<String> its = distinct(properties);
        if (!its.isEmpty()) {
            this.EXCLUDE_PROPERTIES.addAll(its);
            this.cached = false;
        }
        return this;
    }

    /**
     * 排除查询列
     * @param column 字段
     * @return {@code this}
     */
    public QueryManager excludeWith(String column) {
        if (StringUtil.hasText(column)) {
            this.EXCLUDE_COLUMNS.add(column);
        }
        return this;
    }

    /**
     * 排除查询列
     * @param columns 字段数组
     * @return {@code this}
     */
    public QueryManager excludesWith(String... columns) {
        return excludesWith(ArrayUtil.toList(columns));
    }

    /**
     * 排除查询列
     * @param columns 字段集合
     * @return {@code this}
     */
    public QueryManager excludesWith(Collection<String> columns) {
        Set<String> its = distinct(columns);
        if (!its.isEmpty()) {
            this.EXCLUDE_COLUMNS.addAll(its);
            this.cached = false;
        }
        return this;
    }

    /**
     * 获取所有查询列
     * @return 查询列集合
     */
    public List<AbstractQueryWrapper<?>> getQueries() {
        if (cached) {
            return new ArrayList<>(this._wrappers);
        }
        List<AbstractQueryWrapper<?>> queries;
        if (CollectionUtil.hasElement(WRAPPERS)) {
            queries = WRAPPERS.stream().filter(it -> {
                if (it instanceof Query) {
                    return accept(((Query) it).getProperty(), this.EXCLUDE_PROPERTIES, false);
                } else if (it instanceof DirectQuery) {
                    return accept(((DirectQuery) it).getColumn(), this.EXCLUDE_COLUMNS, true);
                }
                return true;
            }).collect(Collectors.toList());
            if (this.criteria.isInclude() && !this.FUNCTION_WRAPPERS.isEmpty()) {
                queries.addAll(this.FUNCTION_WRAPPERS);
            }
        } else {
            // 获取所有列
            Set<ColumnWrapper> columnWrappers = Optional.ofNullable(TableHandler.getTable(criteria.getEntityClass()))
                    .map(TableWrapper::columns).orElse(null);
            if (columnWrappers != null) {
                queries = columnWrappers.stream().filter(it ->
                        accept(it.getProperty(), this.EXCLUDE_PROPERTIES, false)
                                && accept(it.getColumn(), this.EXCLUDE_COLUMNS, true)
                ).map(it -> Query.Single.query(this.criteria, it)).collect(Collectors.toList());
                if (this.criteria.isInclude() && !this.FUNCTION_WRAPPERS.isEmpty()) {
                    queries.addAll(this.FUNCTION_WRAPPERS);
                }
            } else {
                return new ArrayList<>(0);
            }
        }
        this._wrappers = queries;
        this.cached = true;
        return new ArrayList<>(this._wrappers);
    }

    /**
     * 搜索聚合函数对象
     * @param alias 聚合函数别名
     * @return 聚合函数对象
     */
    public Function searchFunction(final String alias) {
        return this.FUNCTION_CACHE.get(alias);
    }

    /**
     * 搜索聚合函数对象
     * @param aliases 聚合函数别名数组
     * @return 聚合函数对象集合
     */
    public List<Function> searchFunctions(final String... aliases) {
        return searchFunctions(ArrayUtil.toList(aliases));
    }

    /**
     * 搜索聚合函数对象
     * @param aliases 聚合函数别名集合
     * @return 聚合函数对象集合
     */
    public List<Function> searchFunctions(final List<String> aliases) {
        if (CollectionUtil.hasElement(aliases)) {
            return aliases.stream().filter(StringUtil::hasText).map(this::searchFunction)
                    .filter(Objects::nonNull).collect(Collectors.toList());
        }
        return null;
    }

    /**
     * 检查是否存在查询列对象
     * @return true: 是, false: 否
     */
    public boolean hasQueries() {
        return this.WRAPPERS != null && !this.WRAPPERS.isEmpty();
    }

    @Override
    public String getSegment() {
        if (cached && StringUtil.hasText(this.sqlSegment)) {
            return this.sqlSegment;
        }
        this.sqlSegment = getSegment(true);
        return this.sqlSegment;
    }

    /**
     * 获取聚合函数SQL片段
     * @return SQL片段
     */
    public String getFuncSegment() {
        return this.FUNCTION_WRAPPERS.isEmpty() ? Constants.EMPTY : this.FUNCTION_WRAPPERS.stream()
                .map(it -> it.getSegment(true)).filter(StringUtil::hasText)
                .collect(Collectors.joining(Constants.COMMA_SPACE));
    }

    /**
     * 获取SQL片段
     * @param applyQuery 是否为查询语句
     * @return SQL片段
     */
    public String getSegment(boolean applyQuery) {
        return getQueries().stream().map(it -> it.getSegment(applyQuery))
                .filter(StringUtil::hasText).collect(Collectors.joining(Constants.COMMA_SPACE));
    }

    /**
     * 检查字符串是否在容器中出现
     * @param target     目标字符串
     * @param wrappers   目标容器
     * @param ignoreCase 是否忽略大小写比较
     * @return true: 否, false: 是
     */
    private boolean accept(String target, Collection<String> wrappers, boolean ignoreCase) {
        if (CollectionUtil.isEmpty(wrappers)) {
            return true;
        }
        if (ignoreCase) {
            return wrappers.parallelStream().noneMatch(it -> it.equalsIgnoreCase(target));
        } else {
            return wrappers.parallelStream().noneMatch(it -> it.equals(target));
        }
    }

    /**
     * 字符串集合去重
     * @param values 字符串集合
     * @return 新的字符串集合
     */
    private Set<String> distinct(Collection<String> values) {
        return CollectionUtil.isEmpty(values) ? new HashSet<>(0) :
                values.stream().filter(StringUtil::hasText).collect(Collectors.toSet());
    }

}
