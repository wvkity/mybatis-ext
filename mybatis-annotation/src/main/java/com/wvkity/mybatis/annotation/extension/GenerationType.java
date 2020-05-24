package com.wvkity.mybatis.annotation.extension;

/**
 * 主键生成方式
 * @author wvkity
 */
public enum GenerationType {

    /**
     * 通过表产生主键
     */
    TABLE,

    /**
     * 通过序列产生主键，通过@SequenceGenerator注解指定序列名(Oracle)
     */
    SEQUENCE,

    /**
     * 采用数据库 ID自增长的方式来自增主键字段(MySql、SQL SERVER)
     */
    IDENTITY,

    /**
     * 自动
     */
    AUTO
}
