package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.function.Comparator;

import java.util.Collection;

/**
 * 聚合函数接口
 * @param <T> 泛型类型
 */
public interface AggregationFunction<T> {

    // region count

    /**
     * COUNT聚合函数
     * @return 条件对象
     */
    Criteria<T> count();

    /**
     * COUNT聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> count( String property );

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> count( String alias, String property );

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> count( String property, boolean distinct );

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> count( String alias, String property, boolean distinct );

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> count( String alias, String property, Object... values );

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> count( String alias, Comparator comparator, String property, Object... values );

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> count( String alias, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> count( String alias, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> count( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    // endregion

    // region sum

    /**
     * SUM聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> sum( String property );

    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    Criteria<T> sum( String property, Integer scale );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> sum( String alias, String property );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    Criteria<T> sum( String alias, String property, Integer scale );

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> sum( String property, boolean distinct );

    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> sum( String property, Integer scale, boolean distinct );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> sum( String alias, String property, boolean distinct );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> sum( String alias, String property, Integer scale, boolean distinct );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Integer scale, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Comparator comparator, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Integer scale, Comparator comparator, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Integer scale, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Integer scale, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> sum( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    // endregion

    // region avg

    /**
     * AVG聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> avg( String property );

    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    Criteria<T> avg( String property, Integer scale );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> avg( String alias, String property );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    Criteria<T> avg( String alias, String property, Integer scale );

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> avg( String property, boolean distinct );

    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> avg( String property, Integer scale, boolean distinct );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> avg( String alias, String property, boolean distinct );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> avg( String alias, String property, Integer scale, boolean distinct );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Integer scale, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Comparator comparator, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Integer scale, Comparator comparator, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Integer scale, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Integer scale, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> avg( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    // endregion

    // region max

    /**
     * MAX聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> max( String property );

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> max( String alias, String property );

    /**
     * MAX聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> max( String property, boolean distinct );

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> max( String alias, String property, boolean distinct );

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> max( String alias, String property, Object... values );

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> max( String alias, Comparator comparator, String property, Object... values );

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> max( String alias, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> max( String alias, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> max( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    // endregion

    // region min

    /**
     * MIN聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> min( String property );

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    Criteria<T> min( String alias, String property );

    /**
     * MIN聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> min( String property, boolean distinct );

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Criteria<T> min( String alias, String property, boolean distinct );

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    Criteria<T> min( String alias, String property, Object... values );

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> min( String alias, Comparator comparator, String property, Object... values );

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> min( String alias, Comparator comparator, Logic logic, String property, Object... values );

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> min( String alias, boolean distinct, Comparator comparator, String property, Object... values );

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    Criteria<T> min( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values );

    // endregion

    // region assist methods

    /**
     * 查询是否包含聚合函数
     * @param include 是否包含
     * @return 条件对象
     */
    Criteria<T> includeFunction( boolean include );

    /**
     * 是否包含聚合函数
     * @return true: 是, false: 否
     */
    boolean isInclude();

    /**
     * 是否只查询聚合函数
     * @param only 是否
     * @return 条件对象
     */
    Criteria<T> onlyFunction( boolean only );

    /**
     * 是否只查询聚合函数
     * @return true: 是, false: 否
     */
    boolean isOnly();

    /**
     * 添加聚合函数
     * @param function 聚合函数
     * @return 条件对象
     */
    Criteria<T> addFunction( Aggregation function );

    /**
     * 添加聚合函数
     * @param functions 聚合函数数组
     * @return 条件对象
     */
    Criteria<T> addFunction( Aggregation... functions );

    /**
     * 添加聚合函数
     * @param functions 聚合函数集合
     * @return 条件对象
     */
    Criteria<T> addFunction( Collection<Aggregation> functions );

    /**
     * 添加分组筛选条件
     * @param aliases 聚合函数别名数组
     * @return 条件对象
     */
    Criteria<T> having( String... aliases );

    /**
     * 添加分组筛选条件
     * @param aliases 聚合函数别名集合
     * @return 条件对象
     */
    Criteria<T> having( Collection<String> aliases );

    /**
     * 添加分组筛选条件
     * @param functions 聚合函数对象数组
     * @return 条件对象
     */
    Criteria<T> having( Aggregation... functions );

    // endregion

}
