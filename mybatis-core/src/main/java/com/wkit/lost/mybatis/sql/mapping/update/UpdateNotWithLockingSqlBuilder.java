package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

public class UpdateNotWithLockingSqlBuilder extends AbstractSqlBuilder {
    
    @Override
    public String build() {
        Set<Column> columns = table.getUpdatableColumns();
        StringBuilder builder = new StringBuilder( 200 );
        builder.append( "\n<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        for ( Column column : columns ) {
            builder.append( " " ).append( ColumnConvert.convertToArg( column, Execute.REPLACE, Constants.PARAM_ENTITY ) ).append( "," );
        }
        builder.append( "\n</trim>\n" );
        String condition = "WHERE " + ColumnConvert.convertToArg( table.getPrimaryKey(), Execute.REPLACE, Constants.PARAM_ENTITY );
        return update( builder.toString(), condition );
    }
}
