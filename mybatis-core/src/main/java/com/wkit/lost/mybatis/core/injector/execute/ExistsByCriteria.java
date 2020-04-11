package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wkit.lost.mybatis.core.mapping.sql.criteria.ExistsByCriteriaProvider;

/**
 * 根据条件包装对象查询记录是否存在方法映射
 * @author wvkity
 */
public class ExistsByCriteria extends AbstractGeneralCriteriaMethod<ExistsByCriteriaProvider> {

    @Override
    public Class<?> getResultType() {
        return Integer.class;
    }
}
