package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 根据指定对象删除记录SQL构建器
 * @author wvkity
 */
public class DeleteSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String condition = "<where>" +
                table.getColumns().stream()
                        .map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false, 0, Constants.PARAM_ENTITY, column, "", AND ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) )
                + "\n</where>";
        return delete( condition );
    }
}
