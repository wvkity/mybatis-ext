package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

/**
 * 根据主键批量删除记录SQL构建器
 * @author wvkity
 */
public class BatchDeleteByPrimaryKeySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = " WHERE " + table.getPrimaryKey().getColumn() + " IN " + NEW_LINE +
                "<foreach collection=\"primaryKeys\" open=\"(\" close=\")\" item=\"item\" separator=\",\">" + NEW_LINE +
                " #{item}" + NEW_LINE +
                "</foreach>";
        return delete( condition );
    }
}
