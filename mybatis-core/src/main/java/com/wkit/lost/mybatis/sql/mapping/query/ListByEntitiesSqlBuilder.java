package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnUtil;

import java.util.stream.Collectors;

/**
 * 根据多个对象查询记录SQL构建器
 * @author wvkity
 */
public class ListByEntitiesSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.getColumns()
                .stream()
                .map( column -> ColumnUtil.convertToQueryArg( column, this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        String condition = "<where>\n" +
                " <trim prefixOverrides=\"OR \">\n" +
                "  <foreach collection=\"entities\" item=\"item\" separator=\"OR \">\n" +
                "   (\n" +
                "    <trim prefixOverrides=\"AND \">" +
                table.getColumns().stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false, 4, "item", column, "", AND ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) ) +
                "    \n</trim>\n" +
                "   )\n" +
                "  </foreach>\n" +
                " </trim>\n" +
                "</where>";
        return select( querySegment, condition );
    }
}
