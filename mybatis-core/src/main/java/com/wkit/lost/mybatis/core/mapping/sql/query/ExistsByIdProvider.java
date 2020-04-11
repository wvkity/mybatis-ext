package com.wkit.lost.mybatis.core.mapping.sql.query;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

/**
 * 构建根据主键检查记录是否存在SQL
 * @author wvkity
 */
public class ExistsByIdProvider extends AbstractProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        if (primaryKey == null) {
            return EMPTY;
        }
        return select(("CASE WHEN COUNT(" + primaryKey.getColumn() + ") > 0 THEN 1 ELSE 0 END COUNT"),
                " WHERE " + ScriptUtil.convertPartArg(primaryKey, null, Execute.NONE));
    }
}
