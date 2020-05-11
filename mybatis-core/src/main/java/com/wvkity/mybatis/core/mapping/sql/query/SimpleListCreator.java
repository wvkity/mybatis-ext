package com.wvkity.mybatis.core.mapping.sql.query;

import com.wvkity.mybatis.core.mapping.sql.AbstractCreator;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.stream.Collectors;

/**
 * 构建根据指定对象查询记录SQL
 * @author wvkity
 */
public class SimpleListCreator extends AbstractCreator {

    @Override
    public String build() {
        return select(table.columns().stream().map(ColumnWrapper::getColumn).collect(Collectors.joining(COMMA_SPACE)),
                EMPTY);
    }
}
