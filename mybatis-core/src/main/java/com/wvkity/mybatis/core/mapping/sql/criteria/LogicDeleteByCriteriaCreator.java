package com.wvkity.mybatis.core.mapping.sql.criteria;

import com.wvkity.mybatis.core.mapping.sql.AbstractCriteriaCreator;

/**
 * 构建根据条件包装对象逻辑删除记录SQL
 * @author wvkity
 */
public class LogicDeleteByCriteriaCreator extends AbstractCriteriaCreator {

    @Override
    public String build() {
        return logicDelete(getUpdateCondition());
    }
}
