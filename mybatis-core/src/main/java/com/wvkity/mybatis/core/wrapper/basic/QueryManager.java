package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.handler.TableHandler;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.metadata.TableWrapper;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.criteria.AbstractQueryCriteriaWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 查询管理器
 * @author wvkity
 */
public class QueryManager implements Segment {

    private static final long serialVersionUID = -3275773488142492015L;

    /**
     * 条件对象
     */
    private final AbstractQueryCriteriaWrapper<?> criteria;

    /**
     * 查询列容器
     */
    private final List<AbstractQueryWrapper<?, ?>> wrappers = new CopyOnWriteArrayList<>();

    /**
     * 排除查询属性
     */
    private final Set<String> excludeProperties = new HashSet<>(8);

    /**
     * 排除查询列
     */
    private final Set<String> excludeColumns = new HashSet<>(8);

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
    private volatile List<AbstractQueryWrapper<?, ?>> _wrappers;

    /**
     * 构造方法
     * @param criteria 条件对象
     */
    public QueryManager(AbstractQueryCriteriaWrapper<?> criteria) {
        this.criteria = criteria;
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象数组
     * @return 当前对象
     */
    public QueryManager add(AbstractQueryWrapper<?, ?>... wrappers) {
        return add(ArrayUtil.toList(wrappers));
    }

    /**
     * 添加查询列
     * @param wrappers 查询列包装对象集合
     * @return 当前对象
     */
    public QueryManager add(Collection<? extends AbstractQueryWrapper<?, ?>> wrappers) {
        if (CollectionUtil.hasElement(wrappers)) {
            List<? extends AbstractQueryWrapper<?, ?>> its = wrappers.stream().filter(Objects::nonNull)
                    .collect(Collectors.toList());
            if (!its.isEmpty()) {
                this.wrappers.addAll(its);
                this.cached = false;
            }
        }
        return this;
    }

    /**
     * 排除查询属性
     * @param properties 属性数组
     * @return 当前对象
     */
    public QueryManager excludes(String... properties) {
        return excludes(ArrayUtil.toList(properties));
    }

    /**
     * 排除查询属性
     * @param properties 属性集合
     * @return 当前对象
     */
    public QueryManager excludes(Collection<String> properties) {
        Set<String> its = distinct(properties);
        if (!its.isEmpty()) {
            this.excludeProperties.addAll(its);
            this.cached = false;
        }
        return this;
    }

    /**
     * 排除查询列
     * @param columns 列名数组
     * @return 当前对象
     */
    public QueryManager directExcludes(String... columns) {
        return directExcludes(ArrayUtil.toList(columns));
    }

    /**
     * 排除查询列
     * @param columns 列名集合
     * @return 当前对象
     */
    public QueryManager directExcludes(Collection<String> columns) {
        Set<String> its = distinct(columns);
        if (!its.isEmpty()) {
            this.excludeColumns.addAll(its);
            this.cached = false;
        }
        return this;
    }

    /**
     * 获取所有查询列
     * @return 查询列集合
     */
    public List<AbstractQueryWrapper<?, ?>> getQueries() {
        if (cached) {
            return new ArrayList<>(this._wrappers);
        }
        List<AbstractQueryWrapper<?, ?>> queries;
        if (CollectionUtil.hasElement(wrappers)) {
            queries = wrappers.stream().filter(it -> {
                if (it instanceof Query) {
                    return accept(((Query<?>) it).getProperty(), this.excludeProperties, false);
                } else if (it instanceof DirectQuery) {
                    return accept(((DirectQuery<?>) it).getColumn(), this.excludeColumns, true);
                }
                return true;
            }).collect(Collectors.toList());
        } else {
            // 获取所有列
            Set<ColumnWrapper> columnWrappers = Optional.ofNullable(TableHandler.getTable(criteria.getEntityClass()))
                    .map(TableWrapper::columns).orElse(null);
            if (columnWrappers != null) {
                queries = columnWrappers.stream().filter(it ->
                        accept(it.getProperty(), this.excludeProperties, false)
                                && accept(it.getColumn(), this.excludeColumns, true)
                ).map(it -> Query.Single.query(criteria, it)).collect(Collectors.toList());
            } else {
                return new ArrayList<>(0);
            }
        }
        //queries = loop( criteria );
        this._wrappers = queries;
        this.cached = true;
        return new ArrayList<>(this._wrappers);
    }

    private List<AbstractQueryWrapper<?, ?>> build(Criteria<?> criteria) {
        // 
        Set<ColumnWrapper> columnWrappers = TableHandler.getTable(criteria.getEntityClass()).columns();
        return columnWrappers.stream().filter(it ->
                accept(it.getProperty(), this.excludeProperties, false)
                        && accept(it.getColumn(), this.excludeColumns, true)
        ).map(it -> Query.Single.query(criteria, it)).collect(Collectors.toList());
    }

    /**
     * 检查是否存在查询列对象
     * @return true: 是, false: 否
     */
    public boolean hasQueries() {
        return this.wrappers != null && !this.wrappers.isEmpty();
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
     * 获取SQL片段
     * @param applyQuery 是否为查询语句
     * @return SQL片段
     */
    public String getSegment(boolean applyQuery) {
        return getQueries().stream().map(it -> it.getSegment(applyQuery))
                .filter(StringUtil::hasText).collect(Collectors.joining(", "));
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
