package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.handler.EntityHandler;

/**
 * 主键条件对象
 * @param <T> 泛型类型
 * @author DT
 */
public class IdentifierExpression<T> extends AbstractExpression<T> {

    private static final long serialVersionUID = -363753826395421496L;

    /**
     * 构造方法
     * @param value 值
     */
    public IdentifierExpression( Object value ) {
        this( value, Logic.AND );
    }

    /**
     * 构造方法
     * @param logic 逻辑操作
     * @param value 值
     */
    public IdentifierExpression( Object value, Logic logic ) {
        this.value = value;
        this.logic = logic;
        this.operator = Operator.EQ;
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param value    值
     */
    public IdentifierExpression( Criteria<T> criteria, Object value ) {
        this( criteria, value, Logic.AND );
    }

    /**
     * 构造方法
     * @param criteria 查询对象
     * @param value    值
     * @param logic    逻辑操作
     */
    public IdentifierExpression( Criteria<T> criteria, Object value, Logic logic ) {
        this.logic = logic;
        this.value = value;
        this.operator = Operator.EQ;
        this.criteria = criteria;
    }

    @Override
    public Column getColumn() {
        return EntityHandler.getTable( this.criteria.getEntity() ).getPrimaryKey();
    }
}
