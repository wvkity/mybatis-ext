package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.constant.Range;

/**
 * 指定范围查询列表接口
 * @author wvkity
 */
public interface RangeFetch {
    
    /**
     * 是否执行范围查询
     * @return true: 是 false: 否
     */
    boolean isRange();

    /**
     * Range方式
     * @return {@link Range}
     */
    Range range();

    /**
     * 获取开始行
     * @return 开始行
     */
    long getRowStart();

    /**
     * 获取结束行
     * @return 结束行
     */
    long getRowEnd();

    /**
     * 获取开始页
     * @return 页数
     */
    long getPageStart();

    /**
     * 获取结束页
     * @return 页数
     */
    long getPageEnd();

    /**
     * 获取每页数目
     * @return 每页大小
     */
    long getPageSize();
}
