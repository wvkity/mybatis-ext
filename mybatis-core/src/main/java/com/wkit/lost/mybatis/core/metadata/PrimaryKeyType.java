package com.wkit.lost.mybatis.core.metadata;

/**
 * 主键枚举类型
 * @author wvkity
 */
public enum PrimaryKeyType {

    /**
     * JDBC
     */
    JDBC,
    
    /**
     * GUID
     */
    UUID,
    
    /**
     * 自增
     */
    IDENTITY,
    
    /**
     * 序列(oracle)
     */
    SEQUENCE,
    
    /**
     * 雪花算法序列
     */
    SNOWFLAKE_SEQUENCE,
    
    /**
     * 雪花算法序列字符串
     */
    SNOWFLAKE_SEQUENCE_STRING
}
