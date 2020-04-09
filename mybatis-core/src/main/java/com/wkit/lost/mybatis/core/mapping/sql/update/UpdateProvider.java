package com.wkit.lost.mybatis.core.mapping.sql.update;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;

/**
 * 构建更新操作SQL
 * @author wvkity
 */
public class UpdateProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper lockingColumn = table.getOptimisticLockingColumn();
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if ( primaryKey == null && lockingColumn == null ) {
            return Constants.CHAR_EMPTY;
        }
        Set<ColumnWrapper> columns = table.updatableColumnsExcludeLocking();
        StringBuilder scriptBuilder = new StringBuilder( 200 );
        for ( ColumnWrapper it : columns ) {
            scriptBuilder.append( Constants.CHAR_SPACE )
                    .append( ScriptUtil.convertPartArg( it, Constants.PARAM_ENTITY, Execute.REPLACE ) )
                    .append( Constants.CHAR_COMMA );
        }
        // 乐观锁
        if ( lockingColumn != null ) {
            scriptBuilder.append( convertIfTagForLocking( lockingColumn ) );
        }
        // 条件
        StringBuilder conditionBuilder = new StringBuilder( 80 );
        if ( primaryKey != null ) {
            conditionBuilder.append( Constants.CHAR_SPACE ).append( ScriptUtil.convertPartArg( primaryKey,
                    Constants.PARAM_ENTITY, Execute.REPLACE ) );
        }
        if ( lockingColumn != null ) {
            conditionBuilder.append( Constants.NEW_LINE ).append( ScriptUtil.convertIfTagWithNotNull( null,
                    lockingColumn, Constants.PARAM_ENTITY, true, false, Symbol.EQ,
                    Logic.AND, Constants.CHAR_EMPTY, Execute.REPLACE ) );
        }
        return update(
                ScriptUtil.convertTrimTag( scriptBuilder.toString(), "SET", null,
                        null, Constants.CHAR_COMMA ),
                ScriptUtil.convertTrimTag( conditionBuilder.toString(), "WHERE", null,
                        "AND |OR ", null )
        );
    }
}
