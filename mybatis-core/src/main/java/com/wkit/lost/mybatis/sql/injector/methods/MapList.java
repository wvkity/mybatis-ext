package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.sql.method.AbstractCriteriaMethod;

import java.util.LinkedHashMap;

public class MapList extends AbstractCriteriaMethod {

    @Override
    public String mappedMethod() {
        return "mapList";
    }

    @Override
    public Class<?> getResultType() {
        return LinkedHashMap.class;
    }
}
