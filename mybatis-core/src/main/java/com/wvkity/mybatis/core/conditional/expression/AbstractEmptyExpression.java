package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;

@SuppressWarnings({"serial"})
public abstract class AbstractEmptyExpression extends ColumnExpressionWrapper {

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg(getAlias(), this.column, this.symbol, this.logic);
    }
}
