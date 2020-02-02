package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据指定对象查询记录数SQL构建器
 * @author wvkity
 */
public class CountSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = "<where>" + table.columns().stream()
                .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, true, 0,
                        null, column, "", AND ) )
                .collect( Collectors.joining( "", NEW_LINE, NEW_LINE ) )
                + NEW_LINE + "</where>";
        return select( "COUNT(*) COUNT", condition );
    }
}
