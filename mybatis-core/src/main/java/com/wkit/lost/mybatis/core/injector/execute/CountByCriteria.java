package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wkit.lost.mybatis.core.mapping.sql.criteria.CountByCriteriaProvider;

/**
 * 根据指定条件包装对象查询记录数
 * @author wvkity
 */
public class CountByCriteria extends AbstractGeneralCriteriaMethod<CountByCriteriaProvider> {
    
    @Override
    public Class<?> getResultType() {
        return Long.class;
    }
}
