package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

/**
 * 构建根据指定ID删除记录SQL
 * @author wvkity
 */
public class DeleteByIdProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return Constants.CHAR_EMPTY;
        }
        return delete(" WHERE " + ScriptUtil.convertPartArg(primaryKey, null,
                Execute.NONE));
    }
}
