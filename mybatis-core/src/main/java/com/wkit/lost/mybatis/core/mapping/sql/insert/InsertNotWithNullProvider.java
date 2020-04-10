package com.wkit.lost.mybatis.core.mapping.sql.insert;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.mapping.sql.AbstractProvider;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

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
        return insert(
                ("(" + ScriptUtil.convertTrimTag(columns.stream().map(it ->
                                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY,
                                        false, false, null, null, ", ",
                                        Execute.INSERT)).collect(Collectors.joining("",
                        Constants.NEW_LINE, Constants.NEW_LINE)), null, null,
                        null, ", ") + ")"),
                ("(" + ScriptUtil.convertTrimTag(columns.stream().map(it ->
                                ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY,
                                        true, false, null, null, ", ",
                                        Execute.INSERT)).collect(Collectors.joining("",
                        Constants.NEW_LINE, Constants.NEW_LINE)), null, null,
                        null, ", ") + ")")
        );
    }
}
