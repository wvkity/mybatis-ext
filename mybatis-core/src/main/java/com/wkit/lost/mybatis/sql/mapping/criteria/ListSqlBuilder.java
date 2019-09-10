package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

public class ListSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return criteriaSelect( "${criteria.querySegment}", getCondition() );
    }
}
