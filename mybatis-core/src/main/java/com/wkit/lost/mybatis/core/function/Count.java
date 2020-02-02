package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.criteria.Logic;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * COUNT聚合函数
 * @author wvkity
 */
@Accessors( chain = true )
public class Count extends AbstractFunction {

    private static final long serialVersionUID = 2837296671612645330L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param property 属性
     * @param distinct 是否去重
     */
    public Count( Criteria<?> criteria, String property, boolean distinct ) {
        this.name = FunctionType.COUNT.getSqlSegment();
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
    public Count( Criteria<?> criteria, String alias, String property, boolean distinct ) {
        this.name = FunctionType.COUNT.getSqlSegment();
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
    public Count( Criteria<?> criteria, String alias, boolean distinct, String property, List<Object> values ) {
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
    public Count( Criteria<?> criteria, String alias, boolean distinct, Logic logic, String property, List<Object> values ) {
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
    public Count( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, String property, List<Object> values ) {
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
    public Count( Criteria<?> criteria, String alias, boolean distinct, Comparator comparator, Logic logic, String property, List<Object> values ) {
        this.name = FunctionType.COUNT.getSqlSegment();
        this.criteria = criteria;
        this.alias = alias;
        this.distinct = distinct;
        this.comparator = comparator;
        this.logic = logic;
        this.property = property;
        this.values = values;
    }

}
