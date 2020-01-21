package com.wkit.lost.mybatis.utils;

/**
 * 常量
 * @author wvkity
 */
public interface Constants {

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
}
