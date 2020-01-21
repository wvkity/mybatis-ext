package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.sql.method.AbstractCriteriaMethod;

public class ArrayList extends AbstractCriteriaMethod {

    @Override
    public String mappedMethod() {
        return "arrayList";
    }

    @Override
    public Class<?> getResultType() {
        return Object[].class;
    }
}
