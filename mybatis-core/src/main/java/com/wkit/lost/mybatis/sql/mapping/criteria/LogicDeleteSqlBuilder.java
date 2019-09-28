package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

public class LogicDeleteSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return logicDelete( getCondition() );
    }
}
