package com.wvkity.mybatis.core.constant;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 连接模式
 * @author wvkity
 */
public enum Join implements Segment {

    /**
     * INNER JOIN
     */
    INNER {
        @Override
        public String getSegment() {
            return " INNER JOIN ";
        }
    },

    /**
     * LEFT JOIN
     */
    LEFT {
        @Override
        public String getSegment() {
            return " LEFT JOIN ";
        }
    },

    /**
     * RIGHT JOIN
     */
    RIGHT {
        @Override
        public String getSegment() {
            return " RIGHT JOIN ";
        }
    },

    /**
     * FULL JOIN
     */
    FULL {
        @Override
        public String getSegment() {
            return " FULL JOIN ";
        }
    }
}
