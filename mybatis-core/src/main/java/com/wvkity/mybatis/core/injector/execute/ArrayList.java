package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.GeneralCriteriaProvider;

/**
 * 根据条件包装对象查询记录方法映射(数组)
 * @author wvkity
 */
public class ArrayList extends AbstractGeneralCriteriaMethod<GeneralCriteriaProvider> {
    
    @Override
    public Class<?> getResultType() {
        return Object[].class;
    }
}
