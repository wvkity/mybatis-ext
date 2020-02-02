package com.wkit.lost.mybatis.utils;

/**
 * 常量
 * @author wvkity
 */
public interface Constants {

    String PARAM_BATCH_BEAN_WRAPPER = "batchDataBeanWrapper";

    /**
     * 实体参数对象
     */
    String PARAM_ENTITY = "entity";

    /**
     * 实体类
     */
    String PARAM_ENTITY_CLASS = "entityClass";

    /**
     * 实体参数对象
     */
    String PARAM_ENTITIES = "entities";

    /**
     * 分页参数对象
     */
    String PARAM_PAGEABLE = "pageable";

    /**
     * 条件参数对象
     */
    String PARAM_CRITERIA = "criteria";

    /**
     * 主键参数对象
     */
    String PARAM_PRIMARY_KEYS = "primaryKeys";

    /**
     * 逻辑删除填充值键
     */
    String PARAM_LOGIC_DELETED_AUDITING_KEY = "LOGIC_DELETED_AUDITING_VALUE";

    /**
     * 乐观锁填充值键
     */
    String PARAM_OPTIMISTIC_LOCK_KEY = "OPTIMISTIC_LOCK_FV";

    /**
     * UUID主键生成方式
     */
    String GENERATOR_UUID = "UUID";

    /**
     * JDBC主键生成方式
     */
    String GENERATOR_JDBC = "JDBC";
    
    /**
     * 自增主键生成方式
     */
    String GENERATOR_IDENTITY = "IDENTITY";

    /**
     * 雪花算法主键生成方式
     */
    String GENERATOR_SNOWFLAKE_SEQUENCE = "SNOWFLAKE_SEQUENCE";

    /**
     * 雪花算法字符串主键生成方式
     */
    String GENERATOR_SNOWFLAKE_SEQUENCE_STRING = "SNOWFLAKE_SEQUENCE_STRING";
}
