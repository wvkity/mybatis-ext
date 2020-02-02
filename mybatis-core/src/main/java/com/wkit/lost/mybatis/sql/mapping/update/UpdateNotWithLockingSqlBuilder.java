package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

public class UpdateNotWithLockingSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        Set<ColumnWrapper> columns = table.updatableColumns();
        StringBuilder builder = new StringBuilder( 200 );
        builder.append( NEW_LINE ).append( "<trim prefix=\"SET\" suffixOverrides=\",\">" ).append( NEW_LINE );
        for ( ColumnWrapper column : columns ) {
            builder.append( " " ).append( ColumnConvert.convertToArg( column, Execute.REPLACE,
                    Constants.PARAM_ENTITY ) ).append( "," );
        }
        builder.append( NEW_LINE ).append( "</trim>" ).append( NEW_LINE );
        String condition = "WHERE " + ColumnConvert.convertToArg( table.getPrimaryKey(), Execute.REPLACE,
                Constants.PARAM_ENTITY );
        return update( builder.toString(), condition );
    }
}
