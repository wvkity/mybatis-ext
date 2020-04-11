package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractLogicDeleteProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建逻辑删除记录SQL
 * @author wvkity
 */
public class LogicDeleteProvider extends AbstractLogicDeleteProvider {

    @Override
    public String build() {
        return logicDelete(ScriptUtil.convertWhereTag(table.excludeDeletedAuditableColumns().stream().map(it ->
                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY, true,
                        false, Symbol.EQ, Logic.AND, null, Execute.REPLACE)
        ).collect(Collectors.joining(Constants.EMPTY, NEW_LINE, NEW_LINE))));
    }
}
