package com.wvkity.mybatis.core.constant;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 排序模式
 * @author wvkity
 */
public enum OrderSymbol implements Segment {

    /**
     * ASC排序
     */
    ASC {
        @Override
        public String getSegment() {
            return "ASC";
        }
    },

    /**
     * DESC排序
     */
    DESC {
        @Override
        public String getSegment() {
            return "DESC";
        }
    }

}
