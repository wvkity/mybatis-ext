package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.stream.Collectors;

/**
 * 构建根据多个主键查询记录SQL
 * @author wvkity
 */
public class ListProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return "";
        }
        return select(table.columns().stream().map(ScriptUtil::convertQueryArg).collect(Collectors.joining(COMMA_SPACE)),
                ("WHERE " + primaryKey.getColumn() + " IN " + NEW_LINE +
                        ScriptUtil.convertForeachTag(HASH_ITEM, PARAM_PRIMARY_KEYS, ITEM, BRACKET_LEFT, BRACKET_RIGHT,
                                COMMA_SPACE)));
    }
}
