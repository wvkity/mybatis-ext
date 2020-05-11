package com.wvkity.mybatis.core.mapping.sql.criteria;

import com.wvkity.mybatis.core.mapping.sql.AbstractCriteriaCreator;

/**
 * Criteria条件查询相关通用SQL构建器
 * @author wvkity
 */
public class GeneralCriteriaCreator extends AbstractCriteriaCreator {

    @Override
    public String build() {
        return criteriaQuery(getQueryCondition());
    }
}
