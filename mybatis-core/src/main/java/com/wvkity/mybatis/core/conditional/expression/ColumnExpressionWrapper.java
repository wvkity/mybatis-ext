package com.wvkity.mybatis.core.conditional.expression;

import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

@SuppressWarnings({"serial"})
public abstract class ColumnExpressionWrapper extends ExpressionWrapper<ColumnWrapper> {

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
