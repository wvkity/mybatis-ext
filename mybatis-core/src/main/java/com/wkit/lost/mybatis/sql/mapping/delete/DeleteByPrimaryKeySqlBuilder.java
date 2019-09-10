package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

/**
 * 根据主键删除记录SQL构建器
 * @author DT
 */
public class DeleteByPrimaryKeySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        return delete( " WHERE " + table.getPrimaryKey().convertToArg( Execute.NONE ) );
    }
}
