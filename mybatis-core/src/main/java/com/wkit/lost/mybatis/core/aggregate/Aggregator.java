package com.wkit.lost.mybatis.core.aggregate;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;

/**
 * 聚合函数工具类
 * @author wvkity
 */
public final class Aggregator {

    // region count

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String property ) {
        return new Count( criteria, property, false );
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String property, boolean distinct ) {
        return new Count( criteria, property, distinct );
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, String property ) {
        return new Count( criteria, alias, property, false );
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        return new Count( criteria, alias, property, distinct );
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param values   值
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, String property, Object... values ) {
        return count( criteria, alias, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, Comparator comparator, String property, Object... values ) {
        return count( criteria, alias, comparator, Logic.AND, property, values );
    }

    /**
     * COUNT聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, Logic logic, String property, Object... values ) {
        return count( criteria, alias, Comparator.EQ, logic, property, values );
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Count( criteria, alias, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * COUNT聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return COUNT聚合函数对象
     */
    public static Count count( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Count( criteria, alias, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    // endregion

    // region sum

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String property ) {
        return new Sum( criteria, property, false );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param scale    保留小数位数
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String property, Integer scale ) {
        return new Sum( criteria, property, scale, false );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String property, boolean distinct ) {
        return new Sum( criteria, property, distinct );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String property, Integer scale, boolean distinct ) {
        return new Sum( criteria, property, scale, distinct );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, String property ) {
        return new Sum( criteria, alias, property, false );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, String property, Integer scale ) {
        return new Sum( criteria, alias, property, scale, false );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        return new Sum( criteria, alias, property, distinct );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, String property, Integer scale, boolean distinct ) {
        return new Sum( criteria, alias, property, scale, distinct );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param values   值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, String property, Object... values ) {
        return sum( criteria, alias, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Integer scale, String property, Object... values ) {
        return sum( criteria, alias, scale, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Comparator comparator, String property, Object... values ) {
        return sum( criteria, alias, comparator, Logic.AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return sum( criteria, alias, scale, comparator, Logic.AND, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Logic logic, String property, Object... values ) {
        return sum( criteria, alias, Comparator.EQ, logic, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param scale    保留小数位数
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Integer scale, Logic logic, String property, Object... values ) {
        return sum( criteria, alias, scale, Comparator.EQ, logic, property, values );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Sum( criteria, alias, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Integer scale, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Sum( criteria, alias, scale, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Sum( criteria, alias, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * SUM聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return SUM聚合函数对象
     */
    public static Sum sum( Criteria<?> criteria, String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Sum( criteria, alias, scale, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }
    // endregion

    // region avg

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String property ) {
        return new Avg( criteria, property, false );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param scale    保留小数位数
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String property, Integer scale ) {
        return new Avg( criteria, property, scale, false );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String property, boolean distinct ) {
        return new Avg( criteria, property, distinct );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String property, Integer scale, boolean distinct ) {
        return new Avg( criteria, property, scale, distinct );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, String property ) {
        return new Avg( criteria, alias, property, false );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param scale    保留小数位数
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, String property, Integer scale ) {
        return new Avg( criteria, alias, property, scale, false );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        return new Avg( criteria, alias, property, distinct );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param scale    保留小数位数
     * @param distinct 是否去重
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, String property, Integer scale, boolean distinct ) {
        return new Avg( criteria, alias, property, scale, distinct );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param values   值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, String property, Object... values ) {
        return avg( criteria, alias, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param scale    保留小数位数
     * @param property 属性
     * @param values   值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Integer scale, String property, Object... values ) {
        return avg( criteria, alias, scale, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Comparator comparator, String property, Object... values ) {
        return avg( criteria, alias, comparator, Logic.AND, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return avg( criteria, alias, scale, comparator, Logic.AND, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Logic logic, String property, Object... values ) {
        return avg( criteria, alias, Comparator.EQ, logic, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param scale    保留小数位数
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Integer scale, Logic logic, String property, Object... values ) {
        return avg( criteria, alias, scale, Comparator.EQ, logic, property, values );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Avg( criteria, alias, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Integer scale, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Avg( criteria, alias, scale, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Avg( criteria, alias, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * AVG聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param scale      保留小数位数
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return AVG聚合函数对象
     */
    public static Avg avg( Criteria<?> criteria, String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Avg( criteria, alias, scale, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    // endregion

    // region max

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String property ) {
        return new Max( criteria, property, false );
    }

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String property, boolean distinct ) {
        return new Max( criteria, property, distinct );
    }

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, String property ) {
        return new Max( criteria, alias, property, false );
    }

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        return new Max( criteria, alias, property, distinct );
    }

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param values   值
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, String property, Object... values ) {
        return max( criteria, alias, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * MAX聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, Comparator comparator, String property, Object... values ) {
        return max( criteria, alias, comparator, Logic.AND, property, values );
    }

    /**
     * MAX聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, Logic logic, String property, Object... values ) {
        return max( criteria, alias, Comparator.EQ, logic, property, values );
    }

    /**
     * MAX聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Max( criteria, alias, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * MAX聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return MAX聚合函数对象
     */
    public static Max max( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Max( criteria, alias, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    // endregion

    // region min

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String property ) {
        return new Min( criteria, property, false );
    }

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String property, boolean distinct ) {
        return new Min( criteria, property, distinct );
    }

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, String property ) {
        return new Min( criteria, alias, property, false );
    }

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        return new Min( criteria, alias, property, distinct );
    }

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param values   值
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, String property, Object... values ) {
        return min( criteria, alias, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * MIN聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, Comparator comparator, String property, Object... values ) {
        return min( criteria, alias, comparator, Logic.AND, property, values );
    }

    /**
     * MIN聚合函数
     * @param criteria 条件对象
     * @param alias    别名
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, Logic logic, String property, Object... values ) {
        return min( criteria, alias, Comparator.EQ, logic, property, values );
    }

    /**
     * MIN聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Min( criteria, alias, false, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    /**
     * MIN聚合函数
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     * @return MIN聚合函数对象
     */
    public static Min min( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return new Min( criteria, alias, distinct, comparator, logic, property, ArrayUtil.toList( values ) );
    }

    // endregion
}
