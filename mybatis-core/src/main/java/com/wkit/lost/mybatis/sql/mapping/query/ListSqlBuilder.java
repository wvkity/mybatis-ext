package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据多个主键查询记录SQL构建器
 * @author wvkity
 */
public class ListSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.columns()
                .stream()
                .map( column -> ColumnConvert.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        StringBuffer buffer = new StringBuffer( 50 );
        buffer.append( " WHERE " );
        if ( StringUtil.hasText( this.alias ) ) {
            buffer.append( this.alias ).append( "." );
        }
        buffer.append( table.getPrimaryKey().getColumn() ).append( " IN " ).append( NEW_LINE );
        buffer.append( "<foreach collection=\"primaryKeys\" item=\"item\" open=\"(\" close=\")\" separator=\", \">" )
                .append( NEW_LINE );
        buffer.append( " #{item}" ).append( NEW_LINE );
        buffer.append( NEW_LINE ).append( "</foreach>" );
        return select( querySegment, buffer.toString() );
    }
}
