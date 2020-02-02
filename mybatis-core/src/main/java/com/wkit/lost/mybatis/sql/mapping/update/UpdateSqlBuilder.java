package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

/**
 * 根据指定对象更新记录SQL构建器
 * @author wvkity
 */
public class UpdateSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        Set<Column> columns = table.getUpdatableColumnsExcludeLocker();
        Column lockerColumn = table.getOptimisticLockerColumn();
        StringBuilder builder = new StringBuilder( 200 );
        builder.append( "\n<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        for ( Column column : columns ) {
            builder.append( " " ).append( ColumnConvert.convertToArg( column, Execute.REPLACE, Constants.PARAM_ENTITY ) ).append( "," );
        }
        if ( lockerColumn != null ) {
            builder.append( this.convertIfTagForLocker( true, Constants.PARAM_OPTIMISTIC_LOCK_KEY,
                    lockerColumn, ",", 1 ) );
        }
        builder.append( "\n</trim>\n" );
        StringBuilder conditionBuilder = new StringBuilder( 80 );
        conditionBuilder.append( "<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">\n" );
        conditionBuilder.append( " " ).append( ColumnConvert.convertToArg( table.getPrimaryKey(),
                Execute.REPLACE, Constants.PARAM_ENTITY ) );
        if ( lockerColumn != null ) {
            conditionBuilder.append( "\n" ).append( this.convertToIfTagOfNotNull( true, Execute.REPLACE,
                    false, 1, Constants.PARAM_ENTITY, lockerColumn, "", AND ) );
        }
        conditionBuilder.append( "</trim>" );
        //String condition = "WHERE " + ColumnUtil.convertToArg( table.getPrimaryKey(), Execute.REPLACE, Constants.PARAM_ENTITY );
        return update( builder.toString(), conditionBuilder.toString() );
    }
}
