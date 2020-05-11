package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.metadata.ColumnWrapper;

@SuppressWarnings("serial")
public abstract class AbstractColumnFunction extends AbstractFunction<ColumnWrapper> {

    @Override
    protected String getFunctionBody() {
        StringBuilder builder = new StringBuilder();
        boolean isScale = this.hasScale();
        if (isScale) {
            builder.append("CAST(");
        }
        builder.append(this.type.getSegment()).append("(");
        if (this._case != null) {
            builder.append(this._case.getConditionSegment());
        } else {
            if (this.distinct) {
                builder.append("DISTINCT ");
            }
            builder.append(this.as()).append(this.column.getColumn());
        }
        builder.append(")");
        if (isScale) {
            builder.append(" AS DECIMAL(38, ").append(this.scale).append("))");
        }
        return builder.toString();
    }
}
