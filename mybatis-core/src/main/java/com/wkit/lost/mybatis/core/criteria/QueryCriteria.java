package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.SegmentManager;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryCriteria<T> extends AbstractQueryCriteria<T> {

    private static final long serialVersionUID = -1253597753461923353L;

    @Override
    protected AbstractQueryCriteria<T> instance( AtomicInteger parameterSequence, 
                                                 AtomicInteger aliasSequence, 
                                                 Map<String, Object> parameterValueMappings, 
                                                 SegmentManager segmentManager ) {
        return null;
    }

    @Override
    public Object getModifyVersionValue() {
        return null;
    }

    @Override
    public AbstractQueryCriteria<T> deepClone() {
        return null;
    }
}
