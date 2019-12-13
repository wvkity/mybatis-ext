package com.wkit.lost.mybatis.snowflake.sequence;

/**
 * 级别
 * @author wvkity
 */
public enum Level {

    /**
     * 秒级
     */
    SECOND,

    /**
     * 毫秒级
     */
    MILLISECOND,

    /**
     * 未知(采用默认: {@link #MILLISECOND})
     */
    UNKNOWN
}
