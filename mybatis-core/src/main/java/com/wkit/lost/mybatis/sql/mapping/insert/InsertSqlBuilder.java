package com.wkit.lost.mybatis.sql.mapping.insert;

import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.sql.mapping.AbstractSqlBuilder;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 保存记录SQL构建器
 * @author wvkity
 */
public class InsertSqlBuilder extends AbstractSqlBuilder {

    @Override
    public String build() {
        ColumnWrapper primary = table.getPrimaryKey();
        Set<ColumnWrapper> columns = table.insertableColumns();
        // 字段部分
        String columnSegment = "(" +
                columns.stream().map( ColumnWrapper::getColumn ).collect( Collectors.joining( ", " ) ) + ")";
        // 值部分
        String valueSegment = "(" +
                columns.stream().map( column -> ColumnConvert.convertToArg( column, Execute.INSERT,
                        Constants.PARAM_ENTITY ) ).collect( Collectors.joining( ", " ) ) + ")";
        return insert( columnSegment, valueSegment );
    }
}
