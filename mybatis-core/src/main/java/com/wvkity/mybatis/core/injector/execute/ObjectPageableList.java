package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.GeneralCriteriaCreator;

/**
 * 根据指定条件包装对象分页查询记录(Object)
 * @author wvkity
 */
public class ObjectPageableList extends AbstractGeneralCriteriaMethod<GeneralCriteriaCreator> {

    @Override
    public Class<?> getResultType() {
        return Object.class;
    }
}
