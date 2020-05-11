package com.wvkity.mybatis.core.mapping.sql.delete;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Logic;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象删除记录SQL
 * @author wvkity
 */
public class DeleteCreator extends AbstractCreator {

    @Override
    public String build() {
        return delete(ScriptUtil.convertWhereTag(table.columns().stream().map(it ->
                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY,
                        true, false, Symbol.EQ, Logic.AND, Constants.EMPTY,
                        Execute.REPLACE)).collect(Collectors.joining(Constants.EMPTY))));
    }
}
