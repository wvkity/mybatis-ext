package com.wkit.lost.mybatis.sql.mapping.criteria;

import com.wkit.lost.mybatis.sql.mapping.AbstractCriteriaSqlBuilder;

/**
 * 根据指定条件对象删除记录SQL构建器
 * @author DT
 */
public class DeleteSqlBuilder extends AbstractCriteriaSqlBuilder {

    @Override
    public String build() {
        return delete( getConditionForUpdateOrDelete() );
    }
}
