package com.wvkity.mybatis.core.snowflake.sequence;

/**
 * 生成规则
 * @author wvkity
 */
public enum Rule {

    /**
     * 根据MAC地址自动获取
     */
    MAC,

    /**
     * 指定机器码-数据中心
     */
    SPECIFIED,

    /**
     * 未知(采用默认: {@link #SPECIFIED})
     */
    UNKNOWN
}
