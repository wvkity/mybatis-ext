package com.wkit.lost.mybatis.core.mapping.sql.insert;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 构建保存操作SQL
 * @author wvkity
 */
public class InsertProvider extends AbstractProvider {

    @Override
    public String build() {
        Set<ColumnWrapper> columns = table.insertableColumns();
        return insert(columns.stream().map(ColumnWrapper::getColumn).collect(Collectors.joining(COMMA_SPACE,
                BRACKET_LEFT, BRACKET_RIGHT)), columns.stream().map(it ->
                ScriptUtil.convertPartArg(it, PARAM_ENTITY, Execute.INSERT)).collect(Collectors.joining(COMMA_SPACE,
                BRACKET_LEFT, BRACKET_RIGHT)));
    }
}
