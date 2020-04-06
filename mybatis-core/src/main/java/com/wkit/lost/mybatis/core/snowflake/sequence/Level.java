package com.wkit.lost.mybatis.core.snowflake.sequence;

/**
 * 级别
 * @author wvkity
 */
public enum Level {

    /**
     * 秒级
     */
    SECONDS,

    /**
     * 毫秒级
     */
    MILLISECONDS,

    /**
     * 未知(采用默认: {@link #MILLISECONDS})
     */
    UNKNOWN
}
