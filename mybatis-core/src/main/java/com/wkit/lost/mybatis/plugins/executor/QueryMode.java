package com.wkit.lost.mybatis.plugins.executor;

/**
 * 查询类型枚举
 * @author wvkity
 */
public enum QueryMode {
    /**
     * 指定范围查询(如第1~4页，每页12条数据)
     */
    LIMIT,
    /**
     * 常规分页
     */
    PAGEABLE
}
