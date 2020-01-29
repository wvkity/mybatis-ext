package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.metadata.Column;

@SuppressWarnings( "serial" )
public class AbstractNull<T> extends AbstractExpression<T> {

    @Override
    public String getSqlSegment() {
        StringBuilder builder = new StringBuilder();
        Column column = getColumn();
        String alias = criteria.getAlias();
        boolean isEnable = criteria.isEnableAlias();
        builder.append( logic.getSqlSegment() ).append( " " );
        if ( isEnable ) {
            builder.append( alias ).append( "." );
        }
        if ( column == null ) {
            builder.append( this.property );
        } else {
            builder.append( column.getColumn() );
        }
        builder.append( " " ).append( operator.getSqlSegment() );
        return builder.toString();
    }
}
