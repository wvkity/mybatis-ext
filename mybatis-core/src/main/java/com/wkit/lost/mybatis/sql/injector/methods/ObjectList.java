package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.sql.method.AbstractCriteriaMethod;

public class ObjectList extends AbstractCriteriaMethod {

    @Override
    public String mappedMethod() {
        return "objectList";
    }

    @Override
    public Class<?> getResultType() {
        return Object.class;
    }
}
