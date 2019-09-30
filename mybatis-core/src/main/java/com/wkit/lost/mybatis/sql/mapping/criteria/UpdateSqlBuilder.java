package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

public class UpdateSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return update( String.format( " SET ${%s.updateSegment}", Constants.PARAM_CRITERIA ), getConditionForUpdateOrDelete() );
    }
}
