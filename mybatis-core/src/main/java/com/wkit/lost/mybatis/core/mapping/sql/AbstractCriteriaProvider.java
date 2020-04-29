package com.wkit.lost.mybatis.core.mapping.sql;

import com.wkit.lost.mybatis.core.mapping.sql.utils.ScriptUtil;

/**
 * Criteria相关SQL构件器
 * @author wvkity
 */
public abstract class AbstractCriteriaProvider extends AbstractLogicDeleteProvider {

    /**
     * 获取查询条件
     * @return 查询条件表达式
     */
    protected String getQueryCondition() {
        return SPACE + ScriptUtil.unSafeJoint(PARAM_CRITERIA, DOT, "foreignSegment") + SPACE + NEW_LINE +
                ScriptUtil.convertIfTag(CRITERIA_HAS_CONDITION_SEGMENT, CRITERIA_WHERE_SEGMENT, true) + NEW_LINE;
    }

    /**
     * 更新条件
     * @return 条件表达式
     */
    protected String getUpdateCondition() {
        return NEW_LINE + ScriptUtil.convertIfTag(CRITERIA_HAS_CONDITION_SEGMENT,
                CRITERIA_WHERE_SEGMENT, true) + NEW_LINE;
    }
}
