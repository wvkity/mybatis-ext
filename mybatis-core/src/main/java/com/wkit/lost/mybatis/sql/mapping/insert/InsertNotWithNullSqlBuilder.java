package com.wkit.lost.mybatis.sql.mapping.insert;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存记录SQL构建器
 * @author wvkity
 */
public class InsertNotWithNullSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        Set<Column> columns = table.getInsertableColumns();
        // 字段部分
        String columnSegment = "(<trim suffixOverrides=\", \">\n" +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( false, Execute.INSERT, false, 0, Constants.PARAM_ENTITY, column, ", ", null ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</trim>)";
        // 值部分
        String valueSegment = "(<trim suffixOverrides=\", \">\n" +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.INSERT, false, 0, Constants.PARAM_ENTITY, column, ", ", null ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) ) +
                "\n</trim>)";
        return insert( columnSegment, valueSegment );
    }
}
