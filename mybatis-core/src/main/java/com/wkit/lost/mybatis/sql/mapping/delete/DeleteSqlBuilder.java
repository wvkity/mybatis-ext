package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 根据指定对象删除记录SQL构建器
 * @author DT
 */
public class DeleteSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = "<where>" +
                table.getColumns().stream()
                        .map( column -> converToIfTagOfNotNull( true, Execute.REPLACE, false, 0, null, column, "", AND ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</where>";
        return delete( condition );
    }
}
