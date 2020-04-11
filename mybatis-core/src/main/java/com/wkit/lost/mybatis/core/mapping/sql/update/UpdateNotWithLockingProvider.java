package com.wkit.lost.mybatis.core.mapping.sql.update;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

import java.util.Set;

/**
 * 构建不带乐观锁更新操作SQL
 * @author wvkity
 */
public class UpdateNotWithLockingProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return EMPTY;
        }
        StringBuilder scriptBuilder = new StringBuilder(200);
        Set<ColumnWrapper> columns = table.updatableColumns();
        for (ColumnWrapper it : columns) {
            scriptBuilder.append(ScriptUtil.convertPartArg(it, PARAM_ENTITY, Execute.REPLACE)).append(COMMA);
        }
        return update(ScriptUtil.convertTrimTag(scriptBuilder.toString(), "SET", null, null, COMMA),
                (" WHERE " + ScriptUtil.convertPartArg(primaryKey, PARAM_ENTITY, Execute.REPLACE)));
    }
}
