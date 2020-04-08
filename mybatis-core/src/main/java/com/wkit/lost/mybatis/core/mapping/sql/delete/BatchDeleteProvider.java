package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象批量删除记录SQL
 * @author wvkity
 */
public class BatchDeleteProvider extends AbstractProvider {

    @Override
    public String build() {
        String script = "(" + ScriptUtil.convertTrimTag( table.columns().stream().map( it ->
                        ScriptUtil.convertIfTagWithNotNull( null, it, "item", true,
                                false, Symbol.EQ, Logic.AND, Constants.CHAR_EMPTY, Execute.REPLACE ) )
                        .collect( Collectors.joining( Constants.CHAR_EMPTY, Constants.NEW_LINE, Constants.NEW_LINE ) ),
                null, null, "AND ", null ) + ") ";
        return delete( ScriptUtil.convertWhereTag( ScriptUtil.convertTrimTag(
                ScriptUtil.convertForeachTag( ( script ), Constants.PARAM_ENTITIES, "item", null, 
                        null, "OR " ), null, null, "OR ", null ) ) );
    }
}
