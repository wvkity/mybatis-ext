package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.meta.Column;

@SuppressWarnings( "serial" )
public class AbstractNull<T> extends AbstractExpression<T> {

    @Override
    public String getSqlSegment() {
        StringBuffer buffer = new StringBuffer();
        Column column = getColumn();
        String alias = criteria.getAlias();
        boolean isEnable = criteria.isEnableAlias();
        buffer.append( logic.getSqlSegment() ).append( " " );
        if ( isEnable ) {
            buffer.append( alias ).append( "." );
        }
        if ( column == null ) {
            buffer.append( this.property );
        } else {
            buffer.append( column.getColumn() );
        }
        buffer.append( " " ).append( operator.getSqlSegment() );
        return buffer.toString();
    }
}
