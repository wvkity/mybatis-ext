package com.wkit.lost.mybatis.core.segment;

import java.io.Serializable;

@FunctionalInterface
public interface Segment extends Serializable {

    /**
     * 转成SQL片段
     * @return SQL字符串
     */
    String getSqlSegment();
}
