package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.utils.ColumnUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据多个主键查询记录SQL构建器
 * @author DT
 */
public class ListSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.getColumns()
                .stream()
                .map( column -> ColumnUtil.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        StringBuffer buffer = new StringBuffer( 50 );
        buffer.append( " WHERE " );
        if ( StringUtil.hasText( this.alias ) ) {
            buffer.append( this.alias ).append( "." );
        }
        buffer.append( table.getPrimaryKey().getColumn() ).append( " IN \n" );
        buffer.append( "<foreach collection=\"primaryKeys\" item=\"item\" open=\"(\" close=\")\" separator=\", \">\n" );
        buffer.append( " #{item}\n" );
        buffer.append( "\n</foreach>" );
        return select( querySegment, buffer.toString() );
    }
}
