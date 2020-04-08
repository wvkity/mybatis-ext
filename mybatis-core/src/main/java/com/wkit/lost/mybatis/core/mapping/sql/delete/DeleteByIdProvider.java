package com.wkit.lost.mybatis.core.mapping.sql.delete;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;

/**
 * 构建根据指定ID删除记录SQL
 * @author wvkity
 */
public class DeleteByIdProvider extends AbstractProvider {

    @Override
    public String build() {
        return delete( " WHERE " + ScriptUtil.convertPartArg( table.getPrimaryKey(), null,
                Execute.NONE ) );
    }
}
