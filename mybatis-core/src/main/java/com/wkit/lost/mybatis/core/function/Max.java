package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * MAX聚合函数
 * @author DT
 */
@Accessors( chain = true )
public class Max extends AbstractFunction {

    private static final long serialVersionUID = 4385429253292099255L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     */
    public Max( Criteria<?> criteria, String property, boolean distinct ) {
        this.name = FunctionType.MAX.getSqlSegment();
        this.criteria = criteria;
        this.property = property;
        this.distinct = distinct;
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param alias    别名
     * @param property 属性
     * @param distinct 是否去重
     */
    public Max( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        this.name = FunctionType.MAX.getSqlSegment();
        this.criteria = criteria;
        this.alias = alias;
        this.property = property;
        this.distinct = distinct;
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param alias    别名
     * @param distinct 是否去重
     * @param property 属性
     * @param values   值
     */
    public Max( Criteria<?> criteria, String alias, boolean distinct, String property, List<Object> values ) {
        this( criteria, alias, distinct, Comparator.EQ, Logic.AND, property, values );
    }

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param alias    别名
     * @param distinct 是否去重
     * @param logic    逻辑操作
     * @param property 属性
     * @param values   值
     */
    public Max( Criteria<?> criteria, String alias, boolean distinct, Logic logic, String property, List<Object> values ) {
        this( criteria, alias, distinct, Comparator.EQ, logic, property, values );
    }

    /**
     * 构造方法
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param property   属性
     * @param values     值
     */
    public Max( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, String property, List<Object> values ) {
        this( criteria, alias, distinct, comparator, Logic.AND, property, values );
    }

    /**
     * 构造方法
     * @param criteria   条件对象
     * @param alias      别名
     * @param distinct   是否去重
     * @param comparator 比较运算
     * @param logic      逻辑操作
     * @param property   属性
     * @param values     值
     */
    public Max( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, List<Object> values ) {
        this.name = FunctionType.MAX.getSqlSegment();
        this.criteria = criteria;
        this.alias = alias;
        this.distinct = distinct;
        this.comparator = comparator;
        this.logic = logic;
        this.property = property;
        this.values = values;
    }

}
