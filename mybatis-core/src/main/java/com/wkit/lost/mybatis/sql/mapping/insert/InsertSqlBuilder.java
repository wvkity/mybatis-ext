package com.wkit.lost.mybatis.sql.mapping.insert;

import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存记录SQL构建器
 * @author DT
 */
public class InsertSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        Column primary = table.getPrimaryKey();
        Set<Column> columns = table.getInsertableColumns().stream().filter( column -> !column.isPrimaryKey() ).collect( Collectors.toCollection( LinkedHashSet::new ) );
        // 字段部分
        String columnSegment = "(" +
                ( primary != null ? primary.getColumn() + ", " : "" ) +
                columns.stream().map( Column::getColumn ).collect( Collectors.joining( ", " ) ) + ")";
        // 值部分
        String valueSegment = "(" +
                ( primary != null ? primary.convertToInsertArg() + ", " : "" ) +
                columns.stream().map( Column::convertToInsertArg ).collect( Collectors.joining( ", " ) ) + ")";
        return insert( columnSegment, valueSegment );
    }
}
