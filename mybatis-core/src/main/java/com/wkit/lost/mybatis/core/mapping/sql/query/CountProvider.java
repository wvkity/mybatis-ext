package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象查询记录数SQL
 * @author wvkity
 */
public class CountProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        return select((primaryKey == null ? "COUNT(*) COUNT" : ("COUNT(" + primaryKey.getColumn() + ") COUNT")),
                ScriptUtil.convertWhereTag(table.columns().stream().map(it ->
                        ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, Symbol.EQ, Logic.AND,
                                EMPTY, Execute.REPLACE)).collect(Collectors.joining(EMPTY, NEW_LINE, NEW_LINE))));
    }
}
