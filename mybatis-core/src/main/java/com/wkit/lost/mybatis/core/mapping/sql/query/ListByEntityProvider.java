package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象查询记录SQL
 * @author wvkity
 */
public class ListByEntityProvider extends AbstractProvider {

    @Override
    public String build() {
        return select(
                table.columns().stream().map(ColumnWrapper::getColumn).collect(Collectors.joining(", ")),
                ScriptUtil.convertWhereTag(table.columns().stream().map(it ->
                        ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY, true,
                                false, Symbol.EQ, Logic.AND, Constants.CHAR_EMPTY, Execute.REPLACE))
                        .collect(Collectors.joining(Constants.CHAR_EMPTY, NEW_LINE, NEW_LINE))));
    }
}
