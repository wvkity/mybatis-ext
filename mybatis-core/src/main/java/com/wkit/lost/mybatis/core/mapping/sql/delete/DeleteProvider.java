package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象删除记录SQL
 * @author wvkity
 */
public class DeleteProvider extends AbstractProvider {

    @Override
    public String build() {
        return delete(ScriptUtil.convertWhereTag(table.columns().stream().map(it ->
                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY,
                        true, false, Symbol.EQ, Logic.AND, Constants.EMPTY,
                        Execute.REPLACE)).collect(Collectors.joining(Constants.EMPTY))));
    }
}
