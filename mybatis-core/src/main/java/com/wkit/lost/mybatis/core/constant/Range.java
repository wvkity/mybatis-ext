package com.wkit.lost.mybatis.core.constant;

/**
 * Range查询方式
 * @author wvkity
 */
public enum Range {

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
