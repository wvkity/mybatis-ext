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
 * 构建非空更新操作SQL(排除空值属性)
 * @author wvkity
 */
public class UpdateNotWithNullProvider extends AbstractProvider {

    @Override
    public String build() {
        // 乐观锁
        ColumnWrapper lockingColumn = table.getOptimisticLockingColumn();
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if ( primaryKey == null && lockingColumn == null ) {
            return Constants.CHAR_EMPTY;
        }
        // script
        StringBuilder scriptBuilder = new StringBuilder( 200 );
        if ( lockingColumn == null ) {
            appendScript( scriptBuilder, table.updatableColumns() );
        } else {
            // when condition
            String whenCondition = ScriptUtil.convertIfTest( Constants.PARAM_ENTITY, lockingColumn );
            // when script
            StringBuilder whenScriptBuilder = new StringBuilder( 100 );
            appendScript( whenScriptBuilder, table.updatableColumnsExcludeLocking() );
            whenScriptBuilder.append( convertIfTagForLocking( lockingColumn ) );
            // otherwise script
            StringBuilder otherwiseBuilder = new StringBuilder( 150 );
            appendScript( otherwiseBuilder, table.updatableColumns() );
            scriptBuilder.append( ScriptUtil.convertChooseTag( whenCondition, whenScriptBuilder.toString(),
                    otherwiseBuilder.toString() ) );
        }
        // condition
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
                        "AND |OR ", null ) );
    }

    private void appendScript( StringBuilder builder, Set<ColumnWrapper> columns ) {
        for ( ColumnWrapper it : columns ) {
            builder.append( ScriptUtil.convertIfTagWithNotNull( null, it, Constants.PARAM_ENTITY,
                    true, false, Symbol.EQ, null, Constants.CHAR_COMMA, Execute.REPLACE ) );
        }
    }
}
