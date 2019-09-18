package com.wkit.lost.mybatis.sql.mapping.insert;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存记录SQL构建器
 * @author DT
 */
public class InsertSelectiveSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        Column primary = table.getPrimaryKey();
        Set<Column> columns = table.getInsertableColumns().stream().filter( column -> !column.isPrimaryKey() ).collect( Collectors.toCollection( LinkedHashSet::new ) );
        // 字段部分
        String columnSegment = "(<trim suffixOverrides=\", \">\n" +
                ( primary != null ? " " + primary.getColumn() + ", " : "" ) +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( false, Execute.INSERT, false, 0, null, column, ", ", null ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</trim>)";
        // 值部分
        String valueSegment = "(<trim suffixOverrides=\", \">\n" +
                ( primary != null ? primary.convertToInsertArg() + ", " : "" ) +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.INSERT, false, 0, null, column, ", ", null ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) ) +
                "\n</trim>)";
        return insert( columnSegment, valueSegment );
    }
}
