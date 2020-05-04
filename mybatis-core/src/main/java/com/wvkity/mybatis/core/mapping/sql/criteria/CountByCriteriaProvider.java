package com.wvkity.mybatis.core.mapping.sql.criteria;

import com.wvkity.mybatis.core.metadata.ColumnWrapper;

/**
 * 构建根据条件包装对象查询记录数SQL
 * @author wvkity
 */
public class CountByCriteriaProvider extends GeneralCriteriaProvider {

    @Override
    public String build() {
        ColumnWrapper primaryKey = table.getPrimaryKey();
        return criteriaQuery((primaryKey == null ? "COUNT(*) COUNT" : ("COUNT(" + primaryKey.getColumn() + ") COUNT")),
                getQueryCondition());
    }
}
