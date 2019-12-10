package com.wkit.lost.mybatis.annotation.extension;

/**
 * 主键生成方式
 * @author wvkity
 */
public enum GenerationType {

    /**
     *
     */
    TABLE,

    /**
     * 序列(Oracle)
     */
    SEQUENCE,

    /**
     * 自增(MySql、SQL SERVER)
     */
    IDENTITY,

    /**
     * 自动
     */
    AUTO
}
