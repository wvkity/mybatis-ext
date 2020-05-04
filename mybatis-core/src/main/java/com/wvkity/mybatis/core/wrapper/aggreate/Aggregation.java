package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 聚合函数接口
 * @author wvkity
 */
public interface Aggregation extends Segment {

    /**
     * 转成HAVING条件SQL片段
     * @return SQL片段
     */
    String toQuerySegment();

    /**
     * 转成ORDER BY排序SQL片段
     * @return SQL片段
     */
    String toOrderSegment();
}
