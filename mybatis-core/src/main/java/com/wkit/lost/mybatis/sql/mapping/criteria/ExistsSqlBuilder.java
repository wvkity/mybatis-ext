package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

/**
 * 根据指定查询对象查询记录是否存在SQL构建器
 * @author DT
 */
public class ExistsSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        String querySegment = "CASE WHEN COUNT(*) > 0 THEN 1 ELSE 0 END COUNT";
        return criteriaSelect( querySegment, getConditionForQuery() );
    }
}
