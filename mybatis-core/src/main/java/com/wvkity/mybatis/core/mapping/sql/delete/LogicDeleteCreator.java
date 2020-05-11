package com.wvkity.mybatis.core.mapping.sql.delete;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractLogicDeleteCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建逻辑删除记录SQL
 * @author wvkity
 */
public class LogicDeleteCreator extends AbstractLogicDeleteCreator {

    @Override
    public String build() {
        return logicDelete(ScriptUtil.convertWhereTag(table.excludeDeletedAuditableColumns().stream().map(it ->
                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY, true,
                        false, Symbol.EQ, Logic.AND, null, Execute.REPLACE)
        ).collect(Collectors.joining(Constants.EMPTY, NEW_LINE, NEW_LINE))));
    }
}
