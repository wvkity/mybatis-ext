package com.wvkity.mybatis.core.mapping.sql.update;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

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
        if (primaryKey == null && lockingColumn == null) {
            return EMPTY;
        }
        // script
        StringBuilder scriptBuilder = new StringBuilder(200);
        if (lockingColumn == null) {
            appendScript(scriptBuilder, table.updatableColumns());
        } else {
            // when condition
            String whenCondition = ScriptUtil.convertIfTest(PARAM_ENTITY, lockingColumn);
            // when script
            StringBuilder whenScriptBuilder = new StringBuilder(100);
            appendScript(whenScriptBuilder, table.updatableColumnsExcludeLocking());
            whenScriptBuilder.append(convertIfTagForLocking(lockingColumn));
            // otherwise script
            StringBuilder otherwiseBuilder = new StringBuilder(150);
            appendScript(otherwiseBuilder, table.updatableColumns());
            scriptBuilder.append(ScriptUtil.convertChooseTag(whenCondition, whenScriptBuilder.toString(),
                    otherwiseBuilder.toString()));
        }
        // condition
        StringBuilder conditionBuilder = new StringBuilder(80);
        if (primaryKey != null) {
            conditionBuilder.append(SPACE).append(ScriptUtil.convertPartArg(primaryKey, PARAM_ENTITY, Execute.REPLACE));
        }
        if (lockingColumn != null) {
            conditionBuilder.append(NEW_LINE).append(ScriptUtil.convertIfTagWithNotNull(null, lockingColumn,
                    PARAM_ENTITY, true, false, Symbol.EQ, Logic.AND, EMPTY, Execute.REPLACE));
        }
        return update(ScriptUtil.convertTrimTag(scriptBuilder.toString(), "SET", null, null, COMMA),
                ScriptUtil.convertTrimTag(conditionBuilder.toString(), "WHERE", null, "AND |OR ", null));
    }

    private void appendScript(StringBuilder builder, Set<ColumnWrapper> columns) {
        for (ColumnWrapper it : columns) {
            builder.append(ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, Symbol.EQ, null,
                    COMMA, Execute.REPLACE));
        }
    }
}
