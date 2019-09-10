package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据指定对象查询记录数SQL构建器
 * @author DT
 */
public class CountSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = "<where>" + table.getColumns().stream()
                .map( column -> converToIfTagOfNotNull( true, Execute.REPLACE, true, 0, null, column, "", AND ) )
                .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</where>";
        return select( "COUNT(*) COUNT", condition );
    }
}
