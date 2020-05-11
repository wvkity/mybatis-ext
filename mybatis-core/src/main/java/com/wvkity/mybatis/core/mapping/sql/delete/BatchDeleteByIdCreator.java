package com.wvkity.mybatis.core.mapping.sql.delete;

import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.Constants;

/**
 * 构建根据主键批量删除SQL
 * @author wvkity
 */
public class BatchDeleteByIdCreator extends AbstractCreator {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return Constants.EMPTY;
        }
        return delete(" WHERE " + primaryKey.getColumn() + " IN " +
                ScriptUtil.convertForeachTag("#{item}" + Constants.NEW_LINE,
                        Constants.PARAM_PRIMARY_KEYS, "item", "(", ")", Constants.COMMA));
    }
}
