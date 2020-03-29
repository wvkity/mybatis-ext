package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * 主键等于条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class IdEqual<T> extends ColumnExpressionWrapper<T> {

    private static final long serialVersionUID = 529013059984458176L;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段包装对象
     * @param value    值
     * @param logic    逻辑符号
     */
    IdEqual( Criteria<T> criteria, ColumnWrapper column, Object value, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.value = value;
        this.logic = logic;
    }

    /**
     * 创建主键等于条件对象
     * @param criteria 条件包装对象
     * @param value    值
     * @param <T>      实体类型
     * @return 主键等于条件对象
     */
    public static <T> IdEqual<T> create( Criteria<T> criteria, Object value ) {
        return create( criteria, value, Logic.AND );
    }

    /**
     * 创建主键等于条件对象
     * @param criteria 条件包装对象
     * @param value    值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 主键等于条件对象
     */
    public static <T> IdEqual<T> create( Criteria<T> criteria, Object value, Logic logic ) {
        if ( criteria != null ) {
            ColumnWrapper wrapper = loadIdColumn( criteria.getEntityClass() );
            if ( wrapper != null ) {
                return new IdEqual<>( criteria, wrapper, value, logic );
            }
        }
        return null;
    }

}
