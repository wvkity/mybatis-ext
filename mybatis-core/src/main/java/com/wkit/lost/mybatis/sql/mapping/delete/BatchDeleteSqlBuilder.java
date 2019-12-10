package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据指定对象批量删除记录SQL构建器
 * @author wvkity
 */
public class BatchDeleteSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
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
        return delete( condition );
    }
}
