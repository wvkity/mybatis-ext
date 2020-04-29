package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 查询字段接口
 * @param <T> 字段类型
 * @author wvkity
 */
public interface QueryWrapper<T> extends Segment {

    /**
     * 字段名
     * @return 字段名
     */
    String columnName();

    /**
     * 字段别名
     * @return 别名
     */
    String alias();
}
