package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.GeneralCriteriaProvider;

/**
 * 根据指定条件包装对象查询记录(Object)
 * @author wvkity
 */
public class ObjectList extends AbstractGeneralCriteriaMethod<GeneralCriteriaProvider> {
    
    @Override
    public Class<?> getResultType() {
        return Object.class;
    }
}
