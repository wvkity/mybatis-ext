package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

/**
 * 根据主键批量删除记录SQL构建器
 * @author wvkity
 */
public class BatchDeleteByPrimaryKeySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = " WHERE " + table.getPrimaryKey().getColumn() + " IN \n" +
                "<foreach collection=\"primaryKeys\" open=\"(\" close=\")\" item=\"item\" separator=\",\">\n" +
                " #{item}\n" +
                "</foreach>";
        return delete( condition );
    }
}
