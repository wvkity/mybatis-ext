package com.wkit.lost.mybatis.core.constant;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 逻辑操作符
 * @author wvkity
 */
public enum Logic implements Segment {

    /**
     * AND
     */
    AND {
        @Override
        public String getSegment() {
            return "AND";
        }
    },

    /**
     * OR
     */
    OR {
        @Override
        public String getSegment() {
            return "OR";
        }
    },

    /**
     * 不操作
     */
    NONE {
        @Override
        public String getSegment() {
            return "";
        }
    }

}
