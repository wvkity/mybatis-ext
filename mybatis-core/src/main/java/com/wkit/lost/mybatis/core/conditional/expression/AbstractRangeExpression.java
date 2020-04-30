package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@SuppressWarnings({"serial"})
public abstract class AbstractRangeExpression extends ColumnExpressionWrapper {

    /**
     * 值
     */
    @Getter
    protected Collection<Object> values;

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg(getAlias(), this.column,
                this.symbol, this.logic, defaultPlaceholders(this.values));
    }
}
