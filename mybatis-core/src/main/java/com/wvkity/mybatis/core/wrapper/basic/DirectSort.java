package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排序(字符串字段)
 * @author wvkity
 */
public class DirectSort extends AbstractSortWrapper<String> {

    private static final long serialVersionUID = 7837358423348936221L;

    /**
     * 表别名
     */
    @Getter
    private String alias;

    /**
     * 构造方法
     * @param criteria  条件包装对象
     * @param ascending 排序方式(是否为ASC排序)
     * @param columns   字段集合
     */
    private DirectSort(Criteria<?> criteria, boolean ascending, Collection<String> columns) {
        this.criteria = criteria;
        this.ascending = ascending;
        this.columns = distinct(columns);
    }

    /**
     * 构造方法
     * @param alias     表别名
     * @param ascending 排序方式(是否为ASC排序)
     * @param columns   字段集合
     */
    private DirectSort(String alias, boolean ascending, Collection<String> columns) {
        this.alias = alias;
        this.ascending = ascending;
        this.columns = distinct(columns);
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String orderMode = ascending ? " ASC" : " DESC";
            String realAlias = StringUtil.hasText(this.alias) ? (this.alias + ".") :
                    (this.criteria != null && criteria.isEnableAlias() ? (criteria.as() + ".") : "");
            return this.columns.stream().map(it -> realAlias + it + orderMode)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * ASC排序
     * @param column 字段
     * @return 排序对象
     */
    public static DirectSort asc(String column) {
        return sort(true, column);
    }

    /**
     * ASC排序
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort asc(String... columns) {
        return sort(true, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 排序对象
     */
    public static DirectSort asc(Criteria<?> criteria, String column) {
        return sort(criteria, true, column);
    }

    /**
     * ASC排序
     * @param criteria 条件包装对象
     * @param columns  字段
     * @return 排序对象
     */
    public static DirectSort asc(Criteria<?> criteria, String... columns) {
        return sort(criteria, true, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort ascWithAlias(String alias, String... columns) {
        return ascWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort ascWithAlias(String alias, List<String> columns) {
        return sort(alias, true, columns);
    }

    /**
     * DESC排序
     * @param column 字段
     * @return 排序对象
     */
    public static DirectSort desc(String column) {
        return sort(false, column);
    }

    /**
     * DESC排序
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort desc(String... columns) {
        return sort(false, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param criteria 条件包装对象
     * @param column   字段
     * @return 排序对象
     */
    public static DirectSort desc(Criteria<?> criteria, String column) {
        return sort(criteria, false, column);
    }

    /**
     * DESC排序
     * @param criteria 条件包装对象
     * @param columns  字段
     * @return 排序对象
     */
    public static DirectSort desc(Criteria<?> criteria, String... columns) {
        return sort(criteria, false, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort descWithAlias(String alias, String... columns) {
        return descWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段
     * @return 排序对象
     */
    public static DirectSort descWithAlias(String alias, List<String> columns) {
        return sort(alias, false, columns);
    }

    /**
     * 排序
     * @param ascending 是否升序
     * @param column    字段
     * @return {@link DirectSort}
     */
    public static DirectSort sort(boolean ascending, String column) {
        if (column == null || column.trim().isEmpty()) {
            return null;
        }
        return new DirectSort((Criteria<?>) null, ascending, Collections.singletonList(column));
    }

    /**
     * 排序
     * @param ascending 是否升序
     * @param columns   字段集合
     * @return {@link DirectSort}
     */
    public static DirectSort sort(boolean ascending, Collection<String> columns) {
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        return new DirectSort((Criteria<?>) null, ascending, columns);
    }

    /**
     * 排序
     * @param criteria  条件包装对象
     * @param ascending 是否升序
     * @param column    字段
     * @return {@link DirectSort}
     */
    public static DirectSort sort(Criteria<?> criteria, boolean ascending, String column) {
        if (column == null || column.trim().isEmpty()) {
            return null;
        }
        return new DirectSort(criteria, ascending, Collections.singletonList(column));
    }

    /**
     * 排序
     * @param criteria  条件包装对象
     * @param ascending 是否升序
     * @param columns   字段集合
     * @return {@link DirectSort}
     */
    public static DirectSort sort(Criteria<?> criteria, boolean ascending, Collection<String> columns) {
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        return new DirectSort(criteria, ascending, columns);
    }

    /**
     * 排序
     * @param alias     表别名
     * @param ascending 是否升序
     * @param column    字段
     * @return {@link DirectSort}
     */
    public static DirectSort sort(String alias, boolean ascending, String column) {
        if (column == null || column.trim().isEmpty()) {
            return null;
        }
        return new DirectSort(alias, ascending, Collections.singletonList(column));
    }

    /**
     * 排序
     * @param alias     表别名
     * @param ascending 是否升序
     * @param columns   字段集合
     * @return {@link DirectSort}
     */
    public static DirectSort sort(String alias, boolean ascending, Collection<String> columns) {
        if (columns == null || columns.isEmpty()) {
            return null;
        }
        return new DirectSort(alias, ascending, columns);
    }
}
