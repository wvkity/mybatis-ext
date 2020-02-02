package com.wkit.lost.mybatis.sql.mapping.update;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

/**
 * 根据指定对象更新记录SQL构建器
 * @author wvkity
 */
public class UpdateNotWithNullSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        StringBuilder builder = new StringBuilder( 300 );
        Set<ColumnWrapper> columns;
        // 乐观锁
        ColumnWrapper lockerColumn = table.getOptimisticLockingColumn();
        builder.append( "<trim prefix=\"SET\" suffixOverrides=\",\">" ).append( NEW_LINE );
        // 检查乐观锁是否存在且值不能为空
        if ( lockerColumn == null ) {
            appendSqlSegment( table.updatableColumns(), builder );
        } else {
            builder.append( " <choose>" ).append( NEW_LINE );
            builder.append( "  <when test=\"" ).append( ColumnConvert.convertToTestCondition( lockerColumn,
                    Constants.PARAM_ENTITY ) ).append( "\">" ).append( NEW_LINE );
            appendSqlSegment( table.updatableColumnsExcludeLocking(), builder );
            builder.append( this.convertIfTagForLocker( true, Constants.PARAM_OPTIMISTIC_LOCK_KEY,
                    lockerColumn, ",", 3 ) );
            builder.append( "  </when>" ).append( NEW_LINE );
            builder.append( "  <otherwise>" ).append( NEW_LINE );
            appendSqlSegment( table.updatableColumns(), builder );
            builder.append( "  </otherwise>" ).append( NEW_LINE );
            builder.append( " </choose>" ).append( NEW_LINE );
        }
        builder.append( "</trim>" );
        StringBuilder conditionBuilder = new StringBuilder( 80 );
        conditionBuilder.append( "<trim prefix=\"WHERE\" prefixOverrides=\"AND |OR \">" ).append( NEW_LINE );
        conditionBuilder.append( " " ).append( ColumnConvert.convertToArg( table.getPrimaryKey(),
                Execute.REPLACE, Constants.PARAM_ENTITY ) );
        //conditionBuilder.append( "\n" ).append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 1, Constants.PARAM_ENTITY, table.getPrimaryKey(), "", AND ) );
        if ( lockerColumn != null ) {
            conditionBuilder.append( NEW_LINE ).append( this.convertToIfTagOfNotNull( true, Execute.REPLACE,
                    false, 1, Constants.PARAM_ENTITY, lockerColumn, "", AND ) );
        }
        conditionBuilder.append( "</trim>" );
        return update( builder.toString(), conditionBuilder.toString() );
    }

    private void appendSqlSegment( Set<ColumnWrapper> columns, StringBuilder builder ) {
        for ( ColumnWrapper column : columns ) {
            builder.append( this.convertToIfTagOfNotNull( true, Execute.REPLACE, false, 3,
                    Constants.PARAM_ENTITY, column, ",", "" ) );
        }
    }
}
