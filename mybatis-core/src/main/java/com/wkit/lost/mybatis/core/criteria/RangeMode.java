package com.wkit.lost.mybatis.core.criteria;

/**
 * Range查询方式
 * @author wvkity
 */
public enum RangeMode {

    /**
     * 直接指定范围
     */
    IMMEDIATE,
    /**
     * 指定页数
     */
    PAGEABLE,
    /**
     * 不操作
     */
    NONE
}
