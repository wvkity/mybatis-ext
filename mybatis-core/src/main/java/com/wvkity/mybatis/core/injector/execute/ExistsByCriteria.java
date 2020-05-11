package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.ExistsByCriteriaCreator;

/**
 * 根据条件包装对象查询记录是否存在方法映射
 * @author wvkity
 */
public class ExistsByCriteria extends AbstractGeneralCriteriaMethod<ExistsByCriteriaCreator> {

    @Override
    public Class<?> getResultType() {
        return Integer.class;
    }
}
