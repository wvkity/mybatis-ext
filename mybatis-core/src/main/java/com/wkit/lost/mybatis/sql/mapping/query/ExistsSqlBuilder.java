package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据指定对象查询记录是否存在SQL构建器
 * @author wvkity
 */
public class ExistsSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = "CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END COUNT";
        String condition = "<where>" + table.columns().stream()
                .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, true,
                        0, null, column, "", AND ) )
                .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) )
                + NEW_LINE + "</where>";
        return select( querySegment, condition );
    }
}
