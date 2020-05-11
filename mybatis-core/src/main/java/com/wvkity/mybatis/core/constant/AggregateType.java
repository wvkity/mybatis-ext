package com.wvkity.mybatis.core.constant;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 聚合函数类型
 * @author wvkity
 */
public enum AggregateType implements Segment {
    
    COUNT, SUM, AVG, MAX, MIN;

    @Override
    public String getSegment() {
        return this.name();
    }
}
