package com.wkit.lost.mybatis.core.wrapper;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.criteria.Criteria;

public class AggregationQuery<T> extends AbstractQueryWrapper<T, Aggregation> {
    
    private static final long serialVersionUID = 5643412018618027094L;

    @Override
    public AbstractQueryWrapper<?, ?> transform( Criteria<?> criteria ) {
        return null;
    }

    @Override
    public String getSqlSegment() {
        return null;
    }

    @Override
    public String getSqlSegment( boolean applyQuery ) {
        return null;
    }
}
