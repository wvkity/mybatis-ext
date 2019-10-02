package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.meta.Column;

@SuppressWarnings( "serial" )
public class AbstractNullExpression<T> extends AbstractExpression<T> {

    @Override
    public String getSqlSegment() {
        StringBuffer buffer = new StringBuffer();
        Column column = this.criteria.searchColumn( property );
        String alias = criteria.getAlias();
        boolean isEnable = criteria.isEnableAlias();
        buffer.append( logic.getSqlSegment() ).append( " " );
        if ( isEnable ) {
            buffer.append( alias ).append( "." );
        }
        buffer.append( column.getColumn() ).append( " " ).append( operator.getSqlSegment() );
        return buffer.toString();
    }
}
