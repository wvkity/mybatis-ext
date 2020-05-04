package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象分页查询记录SQL
 * @author wvkity
 */
public class PageableListProvider extends AbstractProvider {

    @Override
    public String build() {
        return select(table.columns().stream().map(ScriptUtil::convertQueryArg).collect(Collectors.joining(Constants.COMMA_SPACE)),
                ScriptUtil.convertWhereTag(table.columns().stream().map(it -> ScriptUtil.convertIfTagWithNotNull(null,
                        it, Constants.PARAM_ENTITY, true, false, Symbol.EQ, Logic.AND, Constants.EMPTY, Execute.REPLACE))
                        .collect(Collectors.joining(Constants.EMPTY))));
    }
}
