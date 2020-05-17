package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 查询所有记录SQL
 * @author wvkity
 */
public class AllListCreator extends AbstractCreator {

    @Override
    public String build() {
        return select(table.columns().stream().map(ScriptUtil::convertQueryArg)
                .collect(Collectors.joining(Constants.COMMA_SPACE)), Constants.EMPTY);
    }
}
