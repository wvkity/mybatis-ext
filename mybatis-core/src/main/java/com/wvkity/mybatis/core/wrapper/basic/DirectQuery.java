package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * 查询列(字符串列)
 * @author wvkity
 */
public class DirectQuery extends AbstractQueryWrapper<String> {

    private static final long serialVersionUID = 6355908236271301633L;

    /**
     * 表别名
     */
    private String tableAlias;

    /**
     * 构造方法
     * @param column 列
     * @param alias  列别名
     */
    private DirectQuery(String column, String alias) {
        this.column = column;
        this.columnAlias = alias;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     列
     * @param alias      列别名
     */
    private DirectQuery(String tableAlias, String column, String alias) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.columnAlias = alias;
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param column   列
     * @param alias    列别名
     */
    private DirectQuery(Criteria<?> criteria, String column, String alias) {
        this.criteria = criteria;
        this.column = column;
        this.columnAlias = alias;
    }

    /**
     * 获取列名
     * @return 列名
     */
    public String getColumn() {
        return this.column;
    }

    @Override
    public String columnName() {
        return this.column;
    }

    @Override
    public AbstractQueryWrapper<?> transform(Criteria<?> criteria) {
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
                    (this.criteria != null && this.criteria.isEnableAlias() ? this.criteria.as() : null);
            return ScriptUtil.convertQueryArg(realTableAlias, this.column, applyQuery ? this.columnAlias : null);
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
         * @param column 表列
         * @param alias  列别名
         * @return 字符串列对象
         */
        public static DirectQuery query(String column, String alias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery(column, alias);
        }

        /**
         * 查询列
         * @param tableAlias 表别名
         * @param column     表列
         * @param alias      列别名
         * @return 字符串查询列对象
         */
        public static DirectQuery query(String tableAlias, String column, String alias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery(tableAlias, column, alias);
        }

        /**
         * 查询列
         * @param criteria 条件对象
         * @param column   表列
         * @param alias    列别名
         * @return 字符串查询列对象
         */
        public static DirectQuery query(Criteria<?> criteria, String column, String alias) {
            return StringUtil.isBlank(column) ? null : new DirectQuery(criteria, column, alias);
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
         * @return 字符串查询列对象集合
         */
        public static ArrayList<DirectQuery> query(String tableAlias, Collection<String> columns) {
            Set<String> its = distinct(columns);
            if (!its.isEmpty()) {
                ArrayList<DirectQuery> list = new ArrayList<>(its.size());
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
        public static ArrayList<DirectQuery> query(String tableAlias, Map<String, String> columns) {
            Map<String, String> its = filterNullValue(columns);
            if (CollectionUtil.hasElement(its)) {
                ArrayList<DirectQuery> list = new ArrayList<>(its.size());
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
         * @return 字符串查询列对象
         */
        public static ArrayList<DirectQuery> query(Criteria<?> criteria, String... columns) {
            return query(criteria, ArrayUtil.toList(columns));
        }

        /**
         * 查询列
         * @param criteria 条件对象
         * @param columns  表列集合
         * @return 字符串查询列对象集合
         */
        public static ArrayList<DirectQuery> query(Criteria<?> criteria, Collection<String> columns) {
            Set<String> its = distinct(columns);
            if (!its.isEmpty()) {
                ArrayList<DirectQuery> list = new ArrayList<>(its.size());
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
         * @return 字符串查询列对象集合
         */
        public static ArrayList<DirectQuery> query(Criteria<?> criteria, Map<String, String> columns) {
            Map<String, String> its = filterNullValue(columns);
            if (CollectionUtil.hasElement(its)) {
                ArrayList<DirectQuery> list = new ArrayList<>(its.size());
                for (Map.Entry<String, String> entry : its.entrySet()) {
                    list.add(Single.query(criteria, entry.getValue(), entry.getKey()));
                }
                return list;
            }
            return new ArrayList<>(0);
        }
    }
}
