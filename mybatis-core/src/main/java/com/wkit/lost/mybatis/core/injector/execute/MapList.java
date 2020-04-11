package com.wkit.lost.mybatis.core.injector.execute;

import com.wkit.lost.mybatis.core.injector.method.AbstractGeneralCriteriaMethod;
import com.wkit.lost.mybatis.core.mapping.sql.criteria.GeneralCriteriaProvider;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 根据指定条件包装对象查询记录方法映射(Map集合)
 * @author wvkity
 */
public class MapList extends AbstractGeneralCriteriaMethod<GeneralCriteriaProvider> {
    
    @Override
    public Class<?> getResultType() {
        return LinkedHashMap.class;
    }
}
