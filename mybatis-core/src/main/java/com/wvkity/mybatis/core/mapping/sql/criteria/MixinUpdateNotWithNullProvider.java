package com.wvkity.mybatis.core.mapping.sql.criteria;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractCriteriaProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.Set;

public class MixinUpdateNotWithNullProvider extends AbstractCriteriaProvider {

    @Override
    public String build() {
        StringBuilder script = new StringBuilder(200);
        Set<ColumnWrapper> columns = table.updatableColumns();
        for (ColumnWrapper it : columns) {
            script.append(ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, Symbol.EQ, null,
                    COMMA, Execute.REPLACE));
        }
        return update(ScriptUtil.convertTrimTag(script.toString(), "SET", null, null, COMMA_SPACE), getUpdateCondition());
    }
}
