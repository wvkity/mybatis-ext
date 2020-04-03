package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;

@SuppressWarnings( { "serial" } )
public abstract class AbstractImmediateEmptyExpression<T> extends ImmediateExpressionWrapper<T> {

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg( getAlias(), this.column, this.symbol, this.logic );
    }
}
