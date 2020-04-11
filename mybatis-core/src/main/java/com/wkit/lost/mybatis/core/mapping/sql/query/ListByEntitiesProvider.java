package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象集合查询记录SQL
 * @author wvkity
 */
public class ListByEntitiesProvider extends AbstractProvider {

    @Override
    public String build() {
        String script = table.columns().stream().map(it -> ScriptUtil.convertIfTagWithNotNull(null, it,
                ITEM, true, false, Symbol.EQ, Logic.AND, EMPTY, Execute.REPLACE)).collect(Collectors.joining(EMPTY));
        return select(
                table.columns().stream().map(ColumnWrapper::getColumn).collect(Collectors.joining(COMMA_SPACE)),
                ScriptUtil.convertWhereTag(ScriptUtil.convertTrimTag(ScriptUtil.convertForeachTag((BRACKET_LEFT +
                        NEW_LINE + ScriptUtil.convertTrimTag(script, null, null, AND_SPACE, null) + NEW_LINE +
                        BRACKET_RIGHT), PARAM_ENTITIES, ITEM, null, null, SPACE_OR_SPACE), null, null, OR_SPACE, null)));
    }
}
