package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 查询字段接口
 * @param <E> 字段类型
 * @author wvkity
 */
public interface QueryWrapper<E> extends Segment {

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
