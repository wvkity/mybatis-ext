package com.wvkity.mybatis.core.mapping.sql.update;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.Set;

public class UpdateNotWithNullAndLockingProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return EMPTY;
        }
        StringBuilder script = new StringBuilder(200);
        Set<ColumnWrapper> columns = table.updatableColumns();
        for (ColumnWrapper it : columns) {
            script.append(ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, Symbol.EQ, null, 
                    COMMA, Execute.REPLACE));
        }
        return update(ScriptUtil.convertTrimTag(script.toString(), "SET", null, null, COMMA),
                (" WHERE " + ScriptUtil.convertPartArg(primaryKey, PARAM_ENTITY, Execute.REPLACE)));
    }
}
