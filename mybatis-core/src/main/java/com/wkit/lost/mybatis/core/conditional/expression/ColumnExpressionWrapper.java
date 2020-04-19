package com.wkit.lost.mybatis.core.conditional.expression;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import lombok.Getter;

@SuppressWarnings({"serial"})
public abstract class ColumnExpressionWrapper<T> extends ExpressionWrapper<T, ColumnWrapper> {

    /**
     * 获取属性名
     * @return 属性名
     */
    public String getProperty() {
        return this.column.getProperty();
    }

    @Override
    public String getSegment() {
        return ScriptUtil.convertConditionArg(getAlias(), this.column, this.symbol, this.logic,
                defaultPlaceholder(this.value));
    }
}
