package com.wkit.lost.mybatis.sql.injector.methods;

import com.wkit.lost.mybatis.sql.method.AbstractCriteriaMethod;

public class RangeList extends AbstractCriteriaMethod {

    @Override
    public String mappedMethod() {
        return "rangeList";
    }

    @Override
    public Class<?> getResultType() {
        return null;
    }
}
