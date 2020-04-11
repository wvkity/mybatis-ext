package com.wkit.lost.mybatis.core.mapping.sql.criteria;

import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;

/**
 * 构建根据条件包装对象查询记录是否存在SQL
 * @author wvkity
 */
public class ExistsByCriteriaProvider extends GeneralCriteriaProvider {


    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        final String part = primaryKey == null ? STAR : primaryKey.getColumn();
        return criteriaQuery(("CASE WHEN COUNT(" + part + ") > 0 THEN 1 ELSE 0 END COUNT"), getQueryCondition());
    }
}
