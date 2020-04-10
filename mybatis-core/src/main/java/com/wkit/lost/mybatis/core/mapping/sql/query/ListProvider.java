package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 构建根据多个主键查询记录SQL
 * @author wvkity
 */
public class ListProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if ( primaryKey == null ) {
            return "";
        }
        return select( table.columns().stream().map( ScriptUtil::convertQueryArg )
                .collect( Collectors.joining( ", " ) ), ( "WHERE " + primaryKey.getColumn() + " IN " +
                NEW_LINE + ScriptUtil.convertForeachTag( Constants.ARG_HASH_ITEM, Constants.PARAM_PRIMARY_KEYS,
                Constants.ARG_ITEM, Constants.CHAR_BRACKET_LEFT, Constants.CHAR_BRACKET_RIGHT, ", " ) ) );
    }
}
