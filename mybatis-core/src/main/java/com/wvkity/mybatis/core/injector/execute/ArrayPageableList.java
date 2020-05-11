package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.GeneralCriteriaCreator;

/**
 * 根据条件包装对象分页查询记录方法映射(数组)
 * @author wvkity
 */
public class ArrayPageableList  extends AbstractGeneralCriteriaMethod<GeneralCriteriaCreator> {

    @Override
    public Class<?> getResultType() {
        return Object[].class;
    }
}
