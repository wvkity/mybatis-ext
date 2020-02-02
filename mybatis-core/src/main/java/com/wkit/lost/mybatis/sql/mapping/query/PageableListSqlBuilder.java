package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;

import java.util.stream.Collectors;

/**
 * 分页查询记录SQL构建器
 * @author wvkity
 */
public class PageableListSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.columns()
                .stream()
                .map( column -> ColumnConvert.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        String condition = "<where>" +
                table.columns().stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false,
                                0, "entity", column, "", AND ) )
                        .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) )
                + NEW_LINE + "</where>";
        return select( querySegment, condition );
    }
}
