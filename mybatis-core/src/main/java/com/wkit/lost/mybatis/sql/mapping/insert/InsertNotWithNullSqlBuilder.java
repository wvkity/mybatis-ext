package com.wkit.lost.mybatis.sql.mapping.insert;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
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
        Set<ColumnWrapper> columns = table.insertableColumns();
        // 字段部分
        String columnSegment = "(<trim suffixOverrides=\", \">" + NEW_LINE +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( false, Execute.INSERT, false, 0,
                                Constants.PARAM_ENTITY, column, ", ", null ) )
                        .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) )
                + "\n</trim>)";
        // 值部分
        String valueSegment = "(<trim suffixOverrides=\", \">" + NEW_LINE +
                columns.stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.INSERT, false, 0,
                                Constants.PARAM_ENTITY, column, ", ", null ) )
                        .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) ) +
                "\n</trim>)";
        return insert( columnSegment, valueSegment );
    }
}
