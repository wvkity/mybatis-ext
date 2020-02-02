package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

public class UpdateNotWithNullAndLockingSqlBuilder extends AbstractSqlBuilder {
    
    @Override
    public String build() {
        StringBuilder builder = new StringBuilder( 300 );
        Set<Column> columns = table.getUpdatableColumns();
        builder.append( "<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        for ( Column column : columns ) {
            builder.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0, 
                    Constants.PARAM_ENTITY, column, ",", "" ) );
        }
        builder.append( "</trim>" );
        String condition = "WHERE " + ColumnConvert.convertToArg( table.getPrimaryKey(), 
                Execute.REPLACE, Constants.PARAM_ENTITY );
        return update( builder.toString(), condition );
    }
}
