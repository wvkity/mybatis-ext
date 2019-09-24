package com.wkit.lost.mybatis.core.condition.expression;

import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.ArgumentUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * BETWEEN范围条件
 * @param <T> 泛型类型
 * @author DT
 */
@Accessors( chain = true )
@SuppressWarnings( "serial" )
public abstract class AbstractBetweenExpression<T> extends AbstractExpression<T> {

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
    public String getSqlSegment() {
        if ( begin != null && end != null ) {
            StringBuffer buffer = new StringBuffer( 80 );
            String alias = criteria.getAlias();
            boolean isEnable = criteria.isEnableAlias();
            Column column = getColumn();
            buffer.append( logic.getSqlSegment() ).append( " " );
            if ( isEnable ) {
                buffer.append( alias ).append( "." );
            }
            buffer.append( column.getColumn() ).append( " " );
            if ( not ) {
                buffer.append( "NOT " );
            }
            buffer.append( operator.getSqlSegment() ).append( " " );
            buffer.append( ArgumentUtil.fill( column, defaultPlaceholder( begin ) ) ).append( " AND " ).append( ArgumentUtil.fill( column, defaultPlaceholder( end ) ) );
            return buffer.toString();
        }
        return "";
    }
}
