package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 聚合函数类型
 * @author wvkity
 */
public enum FunctionType implements Segment {

    /**
     * COUNT聚合函数
     */
    COUNT,

    /**
     * SUM聚合函数
     */
    SUM,

    /**
     * AVG聚合函数
     */
    AVG,

    /**
     * MAX聚合函数
     */
    MAX,

    /**
     * MIN聚合函数
     */
    MIN;

    public String getSqlSegment() {
        return this.name();
    }
}
