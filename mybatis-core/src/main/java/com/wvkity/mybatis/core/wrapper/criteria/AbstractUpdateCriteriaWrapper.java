package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 抽象更新条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractUpdateCriteriaWrapper<T> extends AbstractCriteriaWrapper<T>
        implements UpdateWrapper<T, AbstractUpdateCriteriaWrapper<T>> {

    /**
     * 更新字段
     */
    private final Map<String, Object> UPDATE_COLUMN_CACHE = new ConcurrentSkipListMap<>();

    /**
     * 更新字段包装对象
     */
    private final Map<ColumnWrapper, Object> UPDATE_COLUMN_WRAPPER_CACHE = new ConcurrentHashMap<>();

    /**
     * 更新代码片段
     */
    protected String updateSegment;

    @Override
    public AbstractUpdateCriteriaWrapper<T> set(String property, Object value) {
        Optional.ofNullable(this.searchColumn(property)).filter(ColumnWrapper::isUpdatable).ifPresent(it ->
                this.UPDATE_COLUMN_WRAPPER_CACHE.put(it, value));
        return this;
    }

    @Override
    public AbstractUpdateCriteriaWrapper<T> set(Map<String, Object> properties) {
        if (CollectionUtil.hasElement(properties)) {
            properties.forEach(this::set);
        }
        return this;
    }

    @Override
    public AbstractUpdateCriteriaWrapper<T> directSet(String column, Object value) {
        if (checkCanUpdatable(column)) {
            this.UPDATE_COLUMN_CACHE.put(column, value);
        }
        return this;
    }

    @Override
    public AbstractUpdateCriteriaWrapper<T> directSet(Map<String, Object> columns) {
        if (CollectionUtil.hasElement(columns)) {
            columns.forEach(this::directSet);
        }
        return this;
    }

    @Override
    public AbstractUpdateCriteriaWrapper<T> version(Object version) {
        if (version != null) {
            ColumnWrapper versionColumn = optimisticLockingColumn();
            if (versionColumn != null) {
                this.UPDATE_COLUMN_WRAPPER_CACHE.put(versionColumn, version);
            }
        }
        return this;
    }

    @Override
    public Object getModifyVersionValue() {
        ColumnWrapper column = optimisticLockingColumn();
        if (column == null) {
            return null;
        }
        return Optional.ofNullable(UPDATE_COLUMN_WRAPPER_CACHE.get(column)).orElseGet(() ->
                Optional.ofNullable(UPDATE_COLUMN_CACHE.get(column.getColumn())).orElse(null));
    }

    @Override
    public Object getConditionVersionValue() {
        ColumnWrapper column = optimisticLockingColumn();
        if (column == null) {
            return null;
        }
        return Optional.ofNullable(optimisticLockingColumn()).map(this.segmentManager::getVersionValue).orElse(null);
    }

    /**
     * 检查指定属性是否已设置值
     * @param property 属性
     * @return true: 是 false: 否
     */
    public boolean notExists(String property) {
        if (StringUtil.hasText(property)) {
            // 检查实体是否存在该属性
            ColumnWrapper column = searchColumn(property);
            if (column != null) {
                // 检查包装对象中是否已设置值
                if (UPDATE_COLUMN_WRAPPER_CACHE.containsKey(column)) {
                    return false;
                } else {
                    if (this.UPDATE_COLUMN_CACHE.isEmpty()) {
                        return true;
                    } else {
                        // 检查直接指定的字段中是否已设置值(忽略大小写比较)
                        String columnName = column.getColumn().toLowerCase(Locale.ENGLISH);
                        return this.UPDATE_COLUMN_CACHE.keySet().parallelStream()
                                .noneMatch(columnName::equalsIgnoreCase);
                    }
                }
            } else {
                // 如果搜索不到字段包装对象，默认为已存在
                return false;
            }
        }
        return false;
    }

    @Override
    public String getUpdateSegment() {
        boolean columnIsNotEmpty = !this.UPDATE_COLUMN_CACHE.isEmpty();
        boolean wrapperIsNotEmpty = !this.UPDATE_COLUMN_WRAPPER_CACHE.isEmpty();
        if (columnIsNotEmpty || wrapperIsNotEmpty) {
            List<String> script = new ArrayList<>();
            this.UPDATE_COLUMN_WRAPPER_CACHE.forEach((k, v) ->
                    script.add(ScriptUtil.convertConditionArg(null, k, Symbol.EQ, null, defaultPlaceholder(v))));
            this.UPDATE_COLUMN_CACHE.forEach((k, v) ->
                    script.add(ScriptUtil.convertConditionArg(null, k, Symbol.EQ, null, defaultPlaceholder(v))));
            return String.join(Constants.COMMA_SPACE, script);
        }
        return Constants.EMPTY;
    }
}
