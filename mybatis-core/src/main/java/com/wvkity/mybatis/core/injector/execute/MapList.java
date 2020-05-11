package com.wvkity.mybatis.core.injector.execute;

import com.wvkity.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wvkity.mybatis.core.mapping.sql.criteria.GeneralCriteriaCreator;

import java.util.LinkedHashMap;

/**
 * 根据指定条件包装对象查询记录方法映射(Map集合)
 * @author wvkity
 */
public class MapList extends AbstractGeneralCriteriaMethod<GeneralCriteriaCreator> {
    
    @Override
    public Class<?> getResultType() {
        return LinkedHashMap.class;
    }
}
