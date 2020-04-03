package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

/**
 * BETWEEN范围条件
 * @param <T> 实体类型
 * @author wvkity
 */
public class ImmediateNotBetween<T> extends AbstractImmediateBetweenExpression<T> {

    private static final long serialVersionUID = -4154660572376109929L;

    /**
     * 构造方法
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     */
    ImmediateNotBetween( String column, Object begin, Object end, Logic logic ) {
        this.column = column;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.symbol = Symbol.BETWEEN;
    }

    /**
     * 构造方法
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     */
    ImmediateNotBetween( String tableAlias, String column, Object begin, Object end, Logic logic ) {
        this.tableAlias = tableAlias;
        this.column = column;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.symbol = Symbol.BETWEEN;
    }

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     */
    ImmediateNotBetween( Criteria<T> criteria, String column, Object begin, Object end, Logic logic ) {
        this.criteria = criteria;
        this.column = column;
        this.begin = begin;
        this.end = end;
        this.logic = logic;
        this.symbol = Symbol.BETWEEN;
    }

    /**
     * 创建BETWEEN条件对象
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( String column, Object begin, Object end ) {
        return create( column, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN条件对象
     * @param column 字段
     * @param begin  开始值
     * @param end    结束值
     * @param logic  逻辑符号
     * @param <T>    实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( String column, Object begin, Object end, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateNotBetween<>( column, begin, end, logic );
        }
        return null;
    }

    /**
     * 创建BETWEEN条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( String tableAlias, String column, Object begin, Object end ) {
        return create( tableAlias, column, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN条件对象
     * @param tableAlias 表别名
     * @param column     字段
     * @param begin      开始值
     * @param end        结束值
     * @param logic      逻辑符号
     * @param <T>        实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( String tableAlias, String column, Object begin,
                                                     Object end, Logic logic ) {
        if ( hasText( column ) ) {
            return new ImmediateNotBetween<>( tableAlias, column, begin, end, logic );
        }
        return null;
    }

    /**
     * 创建BETWEEN条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( Criteria<T> criteria, String column, Object begin, Object end ) {
        return create( criteria, column, begin, end, Logic.AND );
    }

    /**
     * 创建BETWEEN条件对象
     * @param criteria 条件包装对象
     * @param column   字段
     * @param begin    开始值
     * @param end      结束值
     * @param logic    逻辑符号
     * @param <T>      实体类型
     * @return 条件对象
     */
    public static <T> ImmediateNotBetween<T> create( Criteria<T> criteria, String column, Object begin,
                                                     Object end, Logic logic ) {
        if ( criteria != null && hasText( column ) ) {
            return new ImmediateNotBetween<>( criteria, column, begin, end, logic );
        }
        return null;
    }
}
