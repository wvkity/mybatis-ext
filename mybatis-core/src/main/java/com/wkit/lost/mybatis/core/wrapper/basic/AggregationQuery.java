package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.wrapper.aggreate.Aggregation;
import com.wkit.lost.mybatis.core.wrapper.criteria.Criteria;

public class AggregationQuery<T> extends AbstractQueryWrapper<T, Aggregation> {

    private static final long serialVersionUID = 5643412018618027094L;

    @Override
    public AbstractQueryWrapper<?, ?> transform( Criteria<?> criteria ) {
        return null;
    }

    @Override
    public String getSegment() {
        return null;
    }

    @Override
    public String getSegment( boolean applyQuery ) {
        return null;
    }
}
