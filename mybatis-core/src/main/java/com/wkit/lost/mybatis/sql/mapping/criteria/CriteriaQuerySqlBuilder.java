package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

public class CriteriaQuerySqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return criteriaSelect( "${criteria.querySegment}", getCondition() );
    }
}
