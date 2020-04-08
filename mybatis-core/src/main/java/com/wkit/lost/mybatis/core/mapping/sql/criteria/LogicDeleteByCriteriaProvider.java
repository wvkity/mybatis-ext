package com.wkit.lost.mybatis.core.mapping.sql.criteria;

import com.wkit.lost.mybatis.core.mapping.sql.AbstractCriteriaProvider;

/**
 * 构建根据条件包装对象逻辑删除记录SQL
 * @author wvkity
 */
public class LogicDeleteByCriteriaProvider extends AbstractCriteriaProvider {
    
    @Override
    public String build() {
        return logicDelete( getUpdateCondition() );
    }
}
