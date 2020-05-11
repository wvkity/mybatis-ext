package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.utils.Constants;

/**
 * 抽象子查询条件包装器
 * @param <T> 实体类型
 * @author wvkity
 */
@SuppressWarnings("serial")
public abstract class AbstractSubCriteria<T> extends AbstractQueryCriteriaWrapper<T> {

    /**
     * 获取条件
     * @return 条件片段
     */
    public String getSegmentForCondition() {
        StringBuilder builder = new StringBuilder(Constants.BRACKET_LEFT);
        builder.append("SELECT ").append(this.getQuerySegment()).append(" FROM ").append(this.getTableName());
        builder.append(getForeignSegment());
        if (this.isHasCondition()) {
            builder.append(Constants.SPACE).append(this.getWhereSegment());
        }
        builder.append(Constants.BRACKET_RIGHT);
        return builder.toString();
    }
}
