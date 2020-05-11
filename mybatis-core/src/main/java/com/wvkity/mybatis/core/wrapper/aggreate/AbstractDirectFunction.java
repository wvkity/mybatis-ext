package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.constant.AggregateType;

@SuppressWarnings("serial")
public abstract class AbstractDirectFunction extends AbstractFunction<String> {

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
            if (this.type == AggregateType.COUNT && ("*".equals(this.column) || "1".equals(this.column)
                    || "0".equals(this.column))) {
                builder.append(this.column);
            } else {
                if (this.distinct) {
                    builder.append("DISTINCT ");
                }
                builder.append(this.as()).append(this.column);
            }
        }
        builder.append(")");
        if (isScale) {
            builder.append(" AS DECIMAL(38, ").append(this.scale).append("))");
        }
        return builder.toString();
    }
}
