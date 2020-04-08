package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.utils.Constants;

/**
 * 构建根据主键批量删除SQL
 * @author wvkity
 */
public class BatchDeleteByIdProvider extends AbstractProvider {

    @Override
    public String build() {
        return delete( " WHERE " + table.getPrimaryKey().getColumn() + " IN " +
                ScriptUtil.convertForeachTag( "#{item}" + Constants.NEW_LINE,
                        Constants.PARAM_PRIMARY_KEYS, "item", "(", ")", Constants.CHAR_COMMA ) );
    }
}
