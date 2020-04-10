package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 逻辑删除SQL构建器
 * @author wvkity
 */
public abstract class AbstractLogicDeleteProvider extends AbstractProvider {

    protected static final String CRITERIA_HAS_CONDITION_SEGMENT = Constants.PARAM_CRITERIA + " != null and "
            + Constants.PARAM_CRITERIA + ".hasCondition";
    protected static final String CRITERIA_WHERE_SEGMENT = ScriptUtil.unSafeJoint(Constants.PARAM_CRITERIA,
            Constants.CHAR_DOT, "whereSegment");

    /**
     * 逻辑删除
     * @param condition 条件
     * @return SQL字符串
     */
    protected String logicDelete(final String condition) {
        // 检查是否存在逻辑删除标识
        if (!table.isEnableLogicDeleted()) {
            return Constants.CHAR_EMPTY;
        }
        ColumnWrapper logicColumn = table.getLogicDeletedColumn();
        StringBuilder script = new StringBuilder(ScriptUtil.convertPartArg(null, logicColumn.getColumn(),
                null, Constants.PARAM_LOGIC_DELETED_AUDITING_KEY, Symbol.EQ, logicColumn.getJdbcType(),
                logicColumn.getTypeHandler(), logicColumn.getJavaType(), logicColumn.isUseJavaType(),
                Constants.CHAR_COMMA, Execute.REPLACE));
        Set<ColumnWrapper> columns = table.deletedAuditableColumns();
        if (!columns.isEmpty()) {
            script.append(columns.stream().map(it ->
                    ScriptUtil.convertIfTagWithNotNull(null, it, Constants.PARAM_ENTITY, true,
                            false, Symbol.EQ, null, Constants.CHAR_COMMA, Execute.REPLACE))
                    .collect(Collectors.joining(Constants.CHAR_EMPTY, NEW_LINE, NEW_LINE)));
        }
        return update(ScriptUtil.convertTrimTag(script.toString(), "SET", null,
                null, Constants.CHAR_COMMA), condition);
    }
}
