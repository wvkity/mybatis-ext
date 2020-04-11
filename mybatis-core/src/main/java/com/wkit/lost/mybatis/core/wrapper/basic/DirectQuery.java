package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 查询列(字符串列)
 * @param <T> 实体类型
 * @author wvkity
 */
public class DirectQuery<T> extends AbstractQueryWrapper<T, String> {

    private static final long serialVersionUID = 6355908236271301633L;

    /**
     * 表别名
     */
    private String tableAlias;

    /**
     * 构造方法
     * @param column      列
     * @param columnAlias 列别名
     */
    private DirectQuery(String column, String columnAlias) {
        this.column = column;
        this.columnAlias = columnAlias;
    }

    /**
     * 构造方法
     * @param tableAlias  表别名
     * @param column      列
     * @param columnAlias 列别名
     */
    private DirectQuery(String tableAlias, String column, String columnAlias) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.columnAlias = columnAlias;
    }

    /**
     * 构造方法
     * @param criteria    条件对象
     * @param column      列
     * @param columnAlias 列别名
     */
    private DirectQuery(Criteria<T> criteria, String column, String columnAlias) {
        this.criteria = criteria;
        this.column = column;
        this.columnAlias = columnAlias;
    }

    /**
     * 获取列名
     * @return 列名
     */
    public String getColumn() {
        return this.column;
    }

    @Override
    public AbstractQueryWrapper<?, ?> transform(Criteria<?> criteria) {
        if (criteria != null) {
            return DirectQuery.Single.query(criteria,
                    StringUtil.hasText(this.columnAlias) ? this.columnAlias : this.column, null);
        }
        return null;
    }

    @Override
    public String getSegment() {
        return getSegment(true);
    }

    @Override
    public String getSegment(boolean applyQuery) {
        if (StringUtil.hasText(this.column)) {
            String realTableAlias = StringUtil.hasText(this.tableAlias) ? this.tableAlias :
                    (this.criteria != null && this.criteria.isEnableAlias() ? this.criteria.getAlias() : null);
            return ColumnConvert.convertToQueryArg(this.column, applyQuery ? this.columnAlias : null, realTableAlias);
        }
        return "";
    }

    /**
     * 单个查询列
     */
    public static final class Single {
        private Single() {
        }

        /**
         * 查询列
         * @param column      表列
         * @param columnAlias 列别名
         * @param <T>         实体类型
         * @return 字符串列对象
         */
        public static <T> DirectQuery<T> query(String column, String columnAlias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery<>(column, columnAlias);
        }

        /**
         * 查询列
         * @param tableAlias  表别名
         * @param column      表列
         * @param columnAlias 列别名
         * @param <T>         实体类型
         * @return 字符串查询列对象
         */
        public static <T> DirectQuery<T> query(String tableAlias, String column, String columnAlias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery<>(tableAlias, column, columnAlias);
        }

        /**
         * 查询列
         * @param criteria    条件对象
         * @param column      表列
         * @param columnAlias 列别名
         * @param <T>         实体类型
         * @return 字符串查询列对象
         */
        public static <T> DirectQuery<T> query(Criteria<T> criteria, String column, String columnAlias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery<>(criteria, column, columnAlias);
        }
    }

    /**
     * 多个查询列
     */
    public static final class Multi {
        private Multi() {
        }

        /**
         * 查询列
         * @param tableAlias 表别名
         * @param columns    表列集合
         * @param <T>        实体类型
         * @return 字符串查询列对象集合
         */
        public static <T> ArrayList<DirectQuery<T>> query(String tableAlias, Collection<String> columns) {
            Set<String> its = distinct(columns);
            if (!its.isEmpty()) {
                ArrayList<DirectQuery<T>> list = new ArrayList<>(its.size());
                for (String it : its) {
                    list.add(Single.query(tableAlias, it, null));
                }
                return list;
            }
            return new ArrayList<>(0);
        }

        /**
         * 查询列
         * @param tableAlias 表别名
         * @param columns    列别名-列集合
         * @param <T>        实体类型
         * @return 字符串查询列对象集合
         */
        public static <T> ArrayList<DirectQuery<T>> query(String tableAlias, Map<String, String> columns) {
            Map<String, String> its = filterNullValue(columns);
            if (CollectionUtil.hasElement(its)) {
                ArrayList<DirectQuery<T>> list = new ArrayList<>(its.size());
                for (Map.Entry<String, String> entry : its.entrySet()) {
                    list.add(Single.query(tableAlias, entry.getValue(), entry.getKey()));
                }
                return list;
            }
            return new ArrayList<>(0);
        }

        /**
         * 查询列
         * @param criteria 条件对象
         * @param columns  表列数组
         * @param <T>      实体类型
         * @return 字符串查询列对象
         */
        public static <T> ArrayList<DirectQuery<T>> query(Criteria<T> criteria, String... columns) {
            return query(criteria, ArrayUtil.toList(columns));
        }

        /**
         * 查询列
         * @param criteria 条件对象
         * @param columns  表列集合
         * @param <T>      实体类型
         * @return 字符串查询列对象集合
         */
        public static <T> ArrayList<DirectQuery<T>> query(Criteria<T> criteria, Collection<String> columns) {
            Set<String> its = distinct(columns);
            if (!its.isEmpty()) {
                ArrayList<DirectQuery<T>> list = new ArrayList<>(its.size());
                for (String it : its) {
                    list.add(Single.query(criteria, it, null));
                }
                return list;
            }
            return new ArrayList<>(0);
        }

        /**
         * 查询列
         * @param criteria 条件对象
         * @param columns  列别名-列集合
         * @param <T>      实体类型
         * @return 字符串查询列对象集合
         */
        public static <T> ArrayList<DirectQuery<T>> query(Criteria<T> criteria, Map<String, String> columns) {
            Map<String, String> its = filterNullValue(columns);
            if (CollectionUtil.hasElement(its)) {
                ArrayList<DirectQuery<T>> list = new ArrayList<>(its.size());
                for (Map.Entry<String, String> entry : its.entrySet()) {
                    list.add(Single.query(criteria, entry.getValue(), entry.getKey()));
                }
                return list;
            }
            return new ArrayList<>(0);
        }
    }
}
