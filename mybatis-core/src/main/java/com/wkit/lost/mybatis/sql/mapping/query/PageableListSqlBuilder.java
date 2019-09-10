package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 分页查询记录SQL构建器
 * @author DT
 */
public class PageableListSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.getColumns()
                .stream()
                .map( column -> column.convertToQueryArg( this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        String condition = "<where>" +
                table.getColumns().stream()
                        .map( column -> converToIfTagOfNotNull( true, Execute.REPLACE, false, 0, "entity", column, "", AND ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</where>";
        return select( querySegment, condition );
    }
}
