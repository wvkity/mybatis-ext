package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"serial"})
public abstract class AbstractImmediateBetweenExpression<T> extends ImmediateExpressionWrapper<T> {

    /**
     * 开始值
     */
    @Getter
    @Setter
    protected Object begin;

    /**
     * 结束值
     */
    @Getter
    @Setter
    protected Object end;

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg(getAlias(), this.column,
                this.symbol, this.logic, defaultPlaceholder(this.begin), defaultPlaceholder(this.end));
    }
}
