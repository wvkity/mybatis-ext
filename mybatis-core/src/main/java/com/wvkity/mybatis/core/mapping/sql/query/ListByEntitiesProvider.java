package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象集合查询记录SQL
 * @author wvkity
 */
public class ListByEntitiesProvider extends AbstractProvider {

    @Override
    public String build() {
        String script = table.columns().stream().map(it -> ScriptUtil.convertIfTagWithNotNull(null, it, Constants.ITEM,
                true, false, Symbol.EQ, Logic.AND, Constants.EMPTY, Execute.REPLACE)).collect(Collectors.joining(Constants.EMPTY));
        return select(table.columns().stream().map(ColumnWrapper::getColumn).collect(Collectors.joining(Constants.COMMA_SPACE)),
                ScriptUtil.convertWhereTag(ScriptUtil.convertTrimTag(ScriptUtil.convertForeachTag((Constants.BRACKET_LEFT +
                                Constants.NEW_LINE + ScriptUtil.convertTrimTag(script, null, null, Constants.AND_SPACE, null) + Constants.NEW_LINE +
                                Constants.BRACKET_RIGHT), Constants.PARAM_ENTITIES, Constants.ITEM, null, null, Constants.SPACE_OR_SPACE), null, null, Constants.OR_SPACE,
                        null)));
    }
}
