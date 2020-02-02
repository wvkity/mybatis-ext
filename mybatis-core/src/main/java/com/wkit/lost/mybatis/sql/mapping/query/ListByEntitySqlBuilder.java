package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;

import java.util.stream.Collectors;

/**
 * 根据指定对象查询记录
 * @author wvkity
 */
public class ListByEntitySqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.columns()
                .stream()
                .map( column -> ColumnConvert.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        String condition = "<where>" +
                table.columns().stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false,
                                0, null, column, "", AND ) )
                        .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) )
                + "\n</where>";
        return select( querySegment, condition );
    }
}
