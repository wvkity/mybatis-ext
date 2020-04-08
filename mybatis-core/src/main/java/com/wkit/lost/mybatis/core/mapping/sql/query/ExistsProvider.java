package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

public class ExistsProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        String script = "CASE WHEN COUNT(" + ( primaryKey == null ? "*" : primaryKey.getColumn() ) + ") > 0 " +
                "THEN 1 ELSE 0 END COUNT";
        return select( script, ScriptUtil.convertWhereTag( table.columns().stream().map( it ->
                ScriptUtil.convertIfTagWithNotNull( null, it, Constants.PARAM_ENTITY,
                        true, true, Symbol.EQ, Logic.AND, Constants.CHAR_EMPTY, Execute.REPLACE ) )
                .collect( Collectors.joining( Constants.CHAR_EMPTY, NEW_LINE, NEW_LINE ) ) ) );

    }
}
