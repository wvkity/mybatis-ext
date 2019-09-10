package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;

import java.util.stream.Collectors;

/**
 * 根据主键查询记录SQL构建器
 * @author DT
 */
public class SelectOneSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        String querySegment = table.getColumns()
                .stream()
                .map( column -> column.convertToQueryArg( this.alias, null, true ) )
                .collect( Collectors.joining( ", " ) );
        return select( querySegment, "WHERE " + table.getPrimaryKey().convertToArg( Execute.NONE, null, this.alias ) );
    }
}
