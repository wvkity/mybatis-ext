package com.wkit.lost.mybatis.core;

/**
 * limit查询方式
 * @author DT
 */
public enum LimitMode {

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
    NORMAL
}
