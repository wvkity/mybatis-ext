package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

/**
 * 分页查询记录数SQL构建器
 * @author wvkity
 */
public class PageableListSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return criteriaSelect( "${criteria.querySegment}", getConditionForQuery() );
    }
}
