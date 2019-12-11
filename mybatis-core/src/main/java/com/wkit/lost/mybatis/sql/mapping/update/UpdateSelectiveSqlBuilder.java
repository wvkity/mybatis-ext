package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

/**
 * 根据指定对象更新记录SQL构建器
 * @author wvkity
 */
public class UpdateSelectiveSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder( 300 );
        Set<Column> columns;
        // 乐观锁
        Column lockerColumn = table.getOptimisticLockerColumn();
        builder.append( "<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        // 检查乐观锁是否存在且值不能为空
        if ( lockerColumn == null ) {
            appendSqlSegment( table.getUpdatableColumns(), builder );
        } else {
            builder.append( " <choose>\n" );
            builder.append( "  <when test=\"" ).append( ColumnUtil.convertToTestCondition( lockerColumn,
                    Constants.PARAM_ENTITY ) ).append( "\">\n" );
            appendSqlSegment( table.getUpdatableColumnsExcludeLocker(), builder );
            builder.append( this.convertIfTagForLocker( true, Constants.PARAM_OPTIMISTIC_LOCK_KEY,
                    lockerColumn, ",", 3 ) );
            builder.append( "  </when>\n" );
            builder.append( "  <otherwise>\n" );
            appendSqlSegment( table.getUpdatableColumns(), builder );
            builder.append( "  </otherwise>\n" );
            builder.append( " </choose>\n" );
        }
        builder.append( "</trim>" );
        StringBuilder conditionBuilder = new StringBuilder( 80 );
        conditionBuilder.append( "<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">\n" );
        conditionBuilder.append( " " ).append( ColumnUtil.convertToArg( table.getPrimaryKey(), Execute.REPLACE, Constants.PARAM_ENTITY ) );
        //conditionBuilder.append( "\n" ).append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 1, Constants.PARAM_ENTITY, table.getPrimaryKey(), "", AND ) );
        if ( lockerColumn != null ) {
            conditionBuilder.append( "\n" ).append( this.convertToIfTagOfNotNull( true, Execute.REPLACE,
                    false, 1, Constants.PARAM_ENTITY, lockerColumn, "", AND ) );
        }
        conditionBuilder.append( "</trim>" );
        System.out.println("============================");
        System.out.println(conditionBuilder.toString());
        return update( builder.toString(), conditionBuilder.toString() );
    }

    private void appendSqlSegment( Set<Column> columns, StringBuilder builder ) {
        for ( Column column : columns ) {
            builder.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 3,
                    Constants.PARAM_ENTITY, column, ",", "" ) );
        }
    }
}
