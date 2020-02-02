package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;

import java.util.stream.Collectors;

/**
 * 根据多个对象查询记录SQL构建器
 * @author wvkity
 */
public class ListByEntitiesSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.columns()
                .stream()
                .map( column -> ColumnConvert.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        String condition = "<where>" + NEW_LINE +
                " <trim prefixOverrides=\"OR \">" + NEW_LINE +
                "  <foreach collection=\"entities\" item=\"item\" separator=\"OR \">" + NEW_LINE +
                "   (" + NEW_LINE +
                "    <trim prefixOverrides=\"AND \">" +
                table.columns().stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false,
                                4, "item", column, "", AND ) )
                        .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) ) +
                "    " + NEW_LINE + "</trim>" + NEW_LINE +
                "   )" + NEW_LINE +
                "  </foreach>" + NEW_LINE +
                " </trim>" + NEW_LINE +
                "</where>";
        return select( querySegment, condition );
    }
}
