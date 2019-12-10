package com.wkit.lost.mybatis.sql.mapping.query;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.sql.SqlTemplate;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnUtil;

import java.util.stream.Collectors;

/**
 * 根据主键查询记录SQL构建器
 * @author wvkity
 */
public class SelectOneSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        // selectOne不需要别名及属性名
        String querySegment = table.getColumns()
                .stream()
                .map( column -> ColumnUtil.convertToQueryArg( column, null, null, false ) )
                .collect( Collectors.joining( ", " ) );
        return select( querySegment, "WHERE " + ColumnUtil.convertToArg( table.getPrimaryKey(), Execute.NONE, null, null ) );
    }

    @Override
    protected String toSqlString( SqlTemplate template, String segment, String condition ) {
        return String.format( template.toSqlString( this.table, null ), segment, condition );
    }
}
