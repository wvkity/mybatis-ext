package com.wvkity.mybatis.core.mapping.sql.insert;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.mapping.sql.AbstractProvider;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 构建保存操作SQL
 * @author wvkity
 */
public class InsertNotWithNullProvider extends AbstractProvider {

    @Override
    public String build() {
        Set<ColumnWrapper> columns = table.insertableColumns();
        return insert((BRACKET_LEFT + ScriptUtil.convertTrimTag(columns.stream().map(it ->
                        ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, false, false, null, null, COMMA_SPACE,
                                Execute.INSERT)).collect(Collectors.joining(EMPTY, NEW_LINE, NEW_LINE)), null, null, null,
                COMMA_SPACE) + BRACKET_RIGHT), (BRACKET_LEFT + ScriptUtil.convertTrimTag(columns.stream().map(it ->
                        ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, null, null, COMMA_SPACE,
                                Execute.INSERT)).collect(Collectors.joining(EMPTY, NEW_LINE, NEW_LINE)), null, null, null,
                COMMA_SPACE) + BRACKET_RIGHT));
    }
}
