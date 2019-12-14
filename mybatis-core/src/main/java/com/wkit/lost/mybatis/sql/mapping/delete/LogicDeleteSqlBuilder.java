package com.wkit.lost.mybatis.sql.mapping.delete;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.sql.mapping.AbstractLogicDeletionSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 根据指定对象进行逻辑删除记录SQL构建器
 * @author wvkity
 */
public class LogicDeleteSqlBuilder extends AbstractLogicDeletionSqlBuilder {

    @Override
    public String build() {
        // 条件部分
        Set<Column> columns = table.getIgnoreDeleteFillingsColumns();
        String condition = "<where>" +
                columns.stream().map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, 
                        false, 0, Constants.PARAM_ENTITY, column, null, AND ) )
                        .collect( Collectors.joining( "", "\n", "\n" ) ) +
                "</where>";
        return logicDelete( condition );
    }
}
