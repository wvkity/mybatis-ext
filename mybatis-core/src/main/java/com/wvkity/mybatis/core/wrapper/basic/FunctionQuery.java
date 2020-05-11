package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 聚合函数查询
 * @author wvkity
 */
public class FunctionQuery extends AbstractQueryWrapper<Function> {

    private static final long serialVersionUID = 5643412018618027094L;

    /**
     * 构造方法
     * @param function 聚合函数
     */
    private FunctionQuery(Function function) {
        this.column = function;
        this.columnAlias = function.getAlias();
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param function 聚合函数
     */
    private FunctionQuery(Criteria<?> criteria, Function function) {
        this.criteria = criteria;
        this.column = function;
        this.columnAlias = function.getAlias();
    }

    /**
     * 获取聚合函数对象
     * @return {@link Function}
     */
    public Function getFunction() {
        return this.column;
    }

    @Override
    public String columnName() {
        return null;
    }

    @Override
    public String getSegment() {
        return this.column.getSegment();
    }

    @Override
    public String getSegment(boolean applyQuery) {
        return applyQuery ? this.column.getQuerySegment() : this.column.getOrderSegment();
    }

    /**
     * 单个聚合函数查询
     */
    public static final class Single {

        private Single() {
        }

        /**
         * 查询聚合函数
         * @param function 聚合函数
         * @return {@link FunctionQuery}
         */
        public static FunctionQuery query(Function function) {
            if (function != null) {
                return new FunctionQuery(function);
            }
            return null;
        }
    }

    /**
     * 多个聚合函数查询
     */
    public static final class Multi {
        private Multi() {
        }

        /**
         * 查询多个聚合函数
         * @param functions 聚合函数数组
         * @return {@link FunctionQuery}集合
         */
        public static List<FunctionQuery> queries(Function... functions) {
            return queries(ArrayUtil.toList(functions));
        }

        /**
         * 查询多个聚合函数
         * @param functions 聚合函数集合
         * @return {@link FunctionQuery}集合
         */
        public static List<FunctionQuery> queries(Collection<Function> functions) {
            if (CollectionUtil.hasElement(functions)) {
                return functions.stream().filter(Objects::nonNull).map(FunctionQuery.Single::query)
                        .collect(Collectors.toList());
            }
            return new ArrayList<>(0);
        }
    }
}
