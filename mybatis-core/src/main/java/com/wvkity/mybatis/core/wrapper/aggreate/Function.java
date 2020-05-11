package com.wvkity.mybatis.core.wrapper.aggreate;

import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;

/**
 * 聚合函数接口
 * @author wvkity
 */
public interface Function extends Segment {

    /**
     * 条件包装对象
     * @return {@link Criteria}
     */
    Criteria<?> getCriteria();

    /**
     * 别名
     * @return 别名
     */
    String getAlias();

    /**
     * 转成HAVING条件SQL片段
     * @return SQL片段
     */
    String getQuerySegment();

    /**
     * 转成ORDER BY排序SQL片段
     * @return SQL片段
     */
    String getOrderSegment();
}
