package com.wkit.lost.mybatis.core.mapping.sql.criteria;

import com.wkit.lost.mybatis.core.mapping.sql.AbstractCriteriaProvider;

/**
 * Criteria条件查询相关通用SQL构建器
 * @author wvkity
 */
public class GeneralCriteriaProvider extends AbstractCriteriaProvider {

    @Override
    public String build() {
        return criteriaQuery(getQueryCondition());
    }
}
