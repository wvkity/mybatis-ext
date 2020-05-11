package com.wvkity.mybatis.core.mapping.sql.delete;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.Constants;

/**
 * 构建根据指定ID删除记录SQL
 * @author wvkity
 */
public class DeleteByIdCreator extends AbstractCreator {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return Constants.EMPTY;
        }
        return delete(" WHERE " + ScriptUtil.convertPartArg(primaryKey, null,
                Execute.NONE));
    }
}
