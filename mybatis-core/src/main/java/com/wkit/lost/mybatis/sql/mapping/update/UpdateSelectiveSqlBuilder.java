package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.Set;

/**
 * 根据指定对象更新记录SQL构建器
 * @author DT
 */
public class UpdateSelectiveSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        StringBuffer buffer = new StringBuffer( 300 );
        Set<Column> columns = table.getUpdatableColumns();
        buffer.append( "<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        for ( Column column : columns ) {
            buffer.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0, null, column, ",", "" ) );
        }
        buffer.append( "</trim>" );
        String condition = "WHERE " + this.table.getPrimaryKey().convertToArg( Execute.REPLACE, null );
        return update( buffer.toString(), condition );
    }
}
