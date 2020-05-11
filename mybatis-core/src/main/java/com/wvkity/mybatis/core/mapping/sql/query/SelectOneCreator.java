package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.stream.Collectors;

/**
 * 构建根据ID查询记录SQL
 * @author wvkity
 */
public class SelectOneCreator extends AbstractCreator {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return EMPTY;
        }
        return select(table.columns().stream().map(ScriptUtil::convertQueryArg).collect(Collectors.joining(COMMA_SPACE)),
                ("WHERE " + ScriptUtil.convertPartArg(null, primaryKey, Execute.NONE)));
    }
}
