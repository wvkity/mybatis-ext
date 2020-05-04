package com.wvkity.mybatis.plugins.paging;

/**
 * 查询类型枚举
 * @author wvkity
 */
public enum PageMode {
    /**
     * 指定范围查询(如第1~4页，每页12条数据)
     */
    RANGE,
    /**
     * 常规分页
     */
    PAGEABLE
}
