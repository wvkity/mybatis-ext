package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;

/**
 * 根据主键删除记录SQL构建器
 * @author wvkity
 */
public class DeleteByPrimaryKeySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        return delete( " WHERE " + ColumnConvert.convertToArg( table.getPrimaryKey(), Execute.NONE ) );
    }
}
