package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;
import com.wkit.lost.mybatis.utils.Constants;

public class UpdateSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        String condition = "\n<if test=\"criteria != null and criteria.hasCondition\">\n ${criteria.sqlSegment}\n</if>\n";
        return update( String.format( " SET ${%s.updateSegment}", Constants.PARAM_CRITERIA ), condition );
    }
}
