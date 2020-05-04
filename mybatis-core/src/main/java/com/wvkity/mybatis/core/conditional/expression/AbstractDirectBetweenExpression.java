package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import lombok.Getter;
import lombok.Setter;

@SuppressWarnings({"serial"})
public abstract class AbstractDirectBetweenExpression extends DirectExpressionWrapper {

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
