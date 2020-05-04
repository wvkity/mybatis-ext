package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import lombok.Getter;

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
