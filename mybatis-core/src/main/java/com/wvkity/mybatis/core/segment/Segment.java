package com.wvkity.mybatis.core.segment;

import java.io.Serializable;

/**
 * SQL片段接口
 * @author wvkity
 */
@FunctionalInterface
public interface Segment extends Serializable {

    /**
     * 获取SQL片段
     * @return SQL片段
     */
    String getSegment();
}
