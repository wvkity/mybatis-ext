package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.aggregate.Comparator;
import com.wkit.lost.mybatis.core.aggregate.AggregateType;
import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import static com.wkit.lost.mybatis.core.criteria.Logic.AND;
import static com.wkit.lost.mybatis.core.aggregate.Comparator.EQ;

import java.util.Collection;

/**
 * 聚合函数接口
 * @param <T> 泛型类型
 */
public interface Aggregate<T, Context, P> extends LambdaConverter<P> {

    // region count

    /**
     * COUNT聚合函数
     * @return 条件对象
     */
    default Context count() {
        return count( "*" );
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @return 条件对象
     */
    default Context count( String property ) {
        return count( null, property );
    }

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    default Context count( String alias, String property ) {
        return count( alias, property, false );
    }

    /**
     * COUNT聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context count( String property, boolean distinct ) {
        return count( null, property, distinct );
    }

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context count( String alias, String property, boolean distinct ) {
        return count( alias, distinct, EQ, AND, property );
    }

    /**
     * COUNT聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context count( String alias, String property, Object... values ) {
        return count( alias, false, EQ, AND, property, values );
    }

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context count( String alias, Comparator comparator, String property, Object... values ) {
        return count( alias, false, comparator, AND, property, values );
    }

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context count( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return count( alias, false, comparator, logic, property, values );
    }

    /**
     * COUNT聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context count( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return count( alias, distinct, comparator, AND, property, values );
    }

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
    Context count( String alias, boolean distinct, Comparator comparator, Logic logic,
                   String property, Object... values );

    // endregion

    // region sum

    /**
     * SUM聚合函数
     * @param property 属性
     * @return 条件对象
     */
    default Context sum( String property ) {
        return sum( null, property );
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    default Context sum( String property, Integer scale ) {
        return sum( null, property, scale );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    default Context sum( String alias, String property ) {
        return sum( alias, property, false );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    default Context sum( String alias, String property, Integer scale ) {
        return sum( alias, property, scale, false );
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context sum( String property, boolean distinct ) {
        return sum( null, property, distinct );
    }

    /**
     * SUM聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context sum( String property, Integer scale, boolean distinct ) {
        return sum( null, property, scale, distinct );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context sum( String alias, String property, boolean distinct ) {
        return sum( alias, distinct, EQ, property );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context sum( String alias, String property, Integer scale, boolean distinct ) {
        return sum( alias, scale, distinct, EQ, property );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context sum( String alias, String property, Object... values ) {
        return sum( alias, false, EQ, property, values );
    }

    /**
     * SUM聚合函数
     * @param alias    聚合函数别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context sum( String alias, Integer scale, String property, Object... values ) {
        return sum( alias, scale, false, EQ, AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context sum( String alias, Comparator comparator, String property, Object... values ) {
        return sum( alias, false, comparator, property, values );
    }

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context sum( String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return sum( alias, scale, false, comparator, AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context sum( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return sum( alias, false, comparator, logic, property, values );
    }

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
    default Context sum( String alias, Integer scale, Comparator comparator, Logic logic,
                         String property, Object... values ) {
        return sum( alias, scale, false, comparator, logic, property, values );
    }

    /**
     * SUM聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context sum( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return sum( alias, distinct, comparator, AND, property, values );
    }

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
    default Context sum( String alias, Integer scale, boolean distinct, Comparator comparator,
                         String property, Object... values ) {
        return sum( alias, scale, distinct, comparator, AND, property, values );
    }

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
    Context sum( String alias, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

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
    Context sum( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

    // endregion

    // region avg

    /**
     * AVG聚合函数
     * @param property 属性
     * @return 条件对象
     */
    default Context avg( String property ) {
        return avg( null, property );
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    default Context avg( String property, Integer scale ) {
        return avg( null, property, scale );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    default Context avg( String alias, String property ) {
        return avg( alias, property, false );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return 条件对象
     */
    default Context avg( String alias, String property, Integer scale ) {
        return avg( alias, property, scale, false );
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context avg( String property, boolean distinct ) {
        return avg( null, property, distinct );
    }

    /**
     * AVG聚合函数
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context avg( String property, Integer scale, boolean distinct ) {
        return avg( null, property, scale, distinct );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context avg( String alias, String property, boolean distinct ) {
        return avg( alias, distinct, EQ, property );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context avg( String alias, String property, Integer scale, boolean distinct ) {
        return avg( alias, scale, distinct, EQ, property );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context avg( String alias, String property, Object... values ) {
        return avg( alias, EQ, property, values );
    }

    /**
     * AVG聚合函数
     * @param alias    聚合函数别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context avg( String alias, Integer scale, String property, Object... values ) {
        return avg( alias, scale, EQ, property, values );
    }

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context avg( String alias, Comparator comparator, String property, Object... values ) {
        return avg( alias, comparator, AND, property, values );
    }

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context avg( String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return avg( alias, scale, false, comparator, property, values );
    }

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context avg( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return avg( alias, false, comparator, logic, property, values );
    }

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
    default Context avg( String alias, Integer scale, Comparator comparator, Logic logic,
                         String property, Object... values ) {
        return avg( alias, scale, false, comparator, logic, property, values );
    }

    /**
     * AVG聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context avg( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return avg( alias, distinct, comparator, AND, property, values );
    }

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
    default Context avg( String alias, Integer scale, boolean distinct, Comparator comparator,
                         String property, Object... values ) {
        return avg( alias, scale, distinct, comparator, AND, property, values );
    }

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
    Context avg( String alias, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

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
    Context avg( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

    // endregion

    // region max

    /**
     * MAX聚合函数
     * @param property 属性
     * @return 条件对象
     */
    default Context max( String property ) {
        return max( null, property );
    }

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    default Context max( String alias, String property ) {
        return max( alias, property, false );
    }

    /**
     * MAX聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context max( String property, boolean distinct ) {
        return max( null, property, distinct );
    }

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context max( String alias, String property, boolean distinct ) {
        return max( alias, distinct, EQ, property );
    }

    /**
     * MAX聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context max( String alias, String property, Object... values ) {
        return max( alias, EQ, property, values );
    }

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context max( String alias, Comparator comparator, String property, Object... values ) {
        return max( alias, comparator, AND, property, values );
    }

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context max( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return max( alias, false, comparator, logic, property, values );
    }

    /**
     * MAX聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context max( String alias, boolean distinct, Comparator comparator,
                         String property, Object... values ) {
        return max( alias, distinct, comparator, AND, property, values );
    }

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
    Context max( String alias, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

    // endregion

    // region min

    /**
     * MIN聚合函数
     * @param property 属性
     * @return 条件对象
     */
    default Context min( String property ) {
        return min( null, property );
    }

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @return 条件对象
     */
    default Context min( String alias, String property ) {
        return min( alias, property, false );
    }

    /**
     * MIN聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context min( String property, boolean distinct ) {
        return min( null, property, distinct );
    }

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    default Context min( String alias, String property, boolean distinct ) {
        return min( alias, distinct, EQ, AND, property );
    }

    /**
     * MIN聚合函数
     * @param alias    聚合函数别名
     * @param property 属性
     * @param values   值
     * @return 条件对象
     */
    default Context min( String alias, String property, Object... values ) {
        return min( alias, EQ, property, values );
    }

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context min( String alias, Comparator comparator, String property, Object... values ) {
        return min( alias, false, comparator, property, values );
    }

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param comparator 比较运算
     * @param logic      逻辑操作符
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context min( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return min( alias, false, comparator, logic, property, values );
    }

    /**
     * MIN聚合函数
     * @param alias      聚合函数别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return 条件对象
     */
    default Context min( String alias, boolean distinct, Comparator comparator,
                         String property, Object... values ) {
        return min( alias, distinct, comparator, AND, property, values );
    }

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
    Context min( String alias, boolean distinct, Comparator comparator, Logic logic,
                 String property, Object... values );

    // endregion

    // region quickly create aggregate functions

    /**
     * 创建多个聚合函数
     * @param property 属性
     * @return 条件对象
     */
    Context functions( String property );

    /**
     * 创建多个聚合函数
     * @param property 属性
     * @param scale    保留小数位数(针对sum,avg聚合函数)
     * @return 条件对象
     */
    Context functions( String property, int scale );

    /**
     * 创建多个聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @return 条件对象
     */
    Context functions( String property, boolean distinct );

    /**
     * 创建多个聚合函数
     * @param property 属性
     * @param distinct 是否去重
     * @param scale    保留小数位数(针对sum,avg聚合函数)
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, int scale );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param aliasPrefix 别名前缀
     * @return 条件对象
     */
    Context functions( String property, String aliasPrefix );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param aliasPrefix 别名前缀
     * @param scale       保留小数位数(针对sum,avg聚合函数)
     * @return 条件对象
     */
    Context functions( String property, String aliasPrefix, int scale );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param distinct    是否去重
     * @param aliasPrefix 别名前缀
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, String aliasPrefix );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param distinct    是否去重
     * @param aliasPrefix 别名前缀
     * @param scale       保留小数位数(针对sum,avg聚合函数)
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, String aliasPrefix, int scale );


    /**
     * 创建多个聚合函数
     * @param property  属性
     * @param functions 函数类型
     * @return 条件对象
     */
    Context functions( String property, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property  属性
     * @param scale     保留小数位数(针对sum,avg聚合函数)
     * @param functions 函数类型
     * @return 条件对象
     */
    Context functions( String property, int scale, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property  属性
     * @param distinct  是否去重
     * @param functions 函数类型
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property  属性
     * @param distinct  是否去重
     * @param scale     保留小数位数(针对sum,avg聚合函数)
     * @param functions 函数类型
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, int scale, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param aliasPrefix 别名前缀
     * @param functions   函数类型
     * @return 条件对象
     */
    Context functions( String property, String aliasPrefix, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param aliasPrefix 别名前缀
     * @param scale       保留小数位数(针对sum,avg聚合函数)
     * @param functions   函数类型
     * @return 条件对象
     */
    Context functions( String property, String aliasPrefix, int scale, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param distinct    是否去重
     * @param aliasPrefix 别名前缀
     * @param functions   函数类型
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, String aliasPrefix, AggregateType... functions );

    /**
     * 创建多个聚合函数
     * @param property    属性
     * @param distinct    是否去重
     * @param aliasPrefix 别名前缀
     * @param scale       保留小数位数(针对sum,avg聚合函数)
     * @param functions   函数类型
     * @return 条件对象
     */
    Context functions( String property, boolean distinct, String aliasPrefix, int scale, AggregateType... functions );

    // endregion

    // region assist methods

    /**
     * 添加聚合函数
     * @param function 聚合函数
     * @return 条件对象
     */
    Context addFunction( Aggregation function );

    /**
     * 添加聚合函数
     * @param functions 聚合函数数组
     * @return 条件对象
     */
    default Context addFunction( Aggregation... functions ) {
        return addFunction( ArrayUtil.toList( functions ) );
    }

    /**
     * 添加聚合函数
     * @param functions 聚合函数集合
     * @return 条件对象
     */
    Context addFunction( Collection<Aggregation> functions );

    /**
     * 添加分组筛选条件
     * @param aliases 聚合函数别名数组
     * @return 条件对象
     */
    default Context having( String... aliases ) {
        return having( ArrayUtil.toList( aliases ) );
    }

    /**
     * 添加分组筛选条件
     * @param aliases 聚合函数别名集合
     * @return 条件对象
     */
    Context having( Collection<String> aliases );

    /**
     * 添加分组筛选条件
     * @param functions 聚合函数对象数组
     * @return 条件对象
     */
    Context having( Aggregation... functions );

    // endregion

}
