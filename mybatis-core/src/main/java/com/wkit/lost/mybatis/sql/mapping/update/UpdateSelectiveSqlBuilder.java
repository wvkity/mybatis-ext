package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

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
            buffer.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0, Constants.PARAM_ENTITY, column, ",", "" ) );
        }
        buffer.append( "</trim>" );
        String condition = "WHERE " + this.table.getPrimaryKey().convertToArg( Execute.REPLACE, Constants.PARAM_ENTITY );
        return update( buffer.toString(), condition );
    }
}
