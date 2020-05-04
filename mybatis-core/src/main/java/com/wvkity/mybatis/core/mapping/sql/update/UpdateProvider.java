package com.wvkity.mybatis.core.mapping.sql.update;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

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
        if (primaryKey == null && lockingColumn == null) {
            return EMPTY;
        }
        Set<ColumnWrapper> columns = table.updatableColumnsExcludeLocking();
        StringBuilder scriptBuilder = new StringBuilder(200);
        for (ColumnWrapper it : columns) {
            scriptBuilder.append(SPACE).append(ScriptUtil.convertPartArg(it, PARAM_ENTITY, Execute.REPLACE)).append(COMMA);
        }
        // 乐观锁
        if (lockingColumn != null) {
            scriptBuilder.append(convertIfTagForLocking(lockingColumn));
        }
        // 条件
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
}
