package com.wvkity.mybatis.core.mapping.sql;

import com.wvkity.mybatis.core.constant.Execute;
import com.wvkity.mybatis.core.constant.Symbol;
import com.wvkity.mybatis.core.mapping.sql.utils.ScriptUtil;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 逻辑删除SQL构建器
 * @author wvkity
 */
public abstract class AbstractLogicDeleteProvider extends AbstractProvider {

    /**
     * criteria != null and criteria.hasCondition
     */
    protected static final String CRITERIA_HAS_CONDITION_SEGMENT = PARAM_CRITERIA + " != null and "
            + PARAM_CRITERIA + ".hasCondition";
    /**
     * ${criteria.whereSegment}
     */
    protected static final String CRITERIA_WHERE_SEGMENT = ScriptUtil.unSafeJoint(PARAM_CRITERIA,
            Constants.DOT, "whereSegment");

    /**
     * 逻辑删除
     * @param condition 条件
     * @return SQL字符串
     */
    protected String logicDelete(final String condition) {
        // 检查是否存在逻辑删除标识
        if (!table.isEnableLogicDeleted()) {
            return Constants.EMPTY;
        }
        ColumnWrapper logicColumn = table.getLogicDeletedColumn();
        StringBuilder script = new StringBuilder(ScriptUtil.convertPartArg(null, logicColumn.getColumn(), null, 
                PARAM_LOGIC_DELETED_AUDITING_KEY, Symbol.EQ, logicColumn.getJdbcType(), logicColumn.getTypeHandler(), 
                logicColumn.getJavaType(), logicColumn.isUseJavaType(), COMMA, Execute.REPLACE));
        Set<ColumnWrapper> columns = table.deletedAuditableColumns();
        if (!columns.isEmpty()) {
            script.append(columns.stream().map(it ->
                    ScriptUtil.convertIfTagWithNotNull(null, it, PARAM_ENTITY, true, false, Symbol.EQ, null,
                            COMMA, Execute.REPLACE)).collect(Collectors.joining(EMPTY, NEW_LINE, NEW_LINE)));
        }
        return update(ScriptUtil.convertTrimTag(script.toString(), "SET", null, null, COMMA), condition);
    }
}
