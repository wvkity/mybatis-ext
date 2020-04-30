package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import lombok.Getter;

@SuppressWarnings({"serial"})
public abstract class AbstractBetweenExpression extends ColumnExpressionWrapper {

    /**
     * 开始值
     */
    @Getter
    protected Object begin;

    /**
     * 结束值
     */
    @Getter
    protected Object end;

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg(getAlias(), this.column,
                this.symbol, this.logic, defaultPlaceholder(this.begin), defaultPlaceholder(this.end));
    }
}
