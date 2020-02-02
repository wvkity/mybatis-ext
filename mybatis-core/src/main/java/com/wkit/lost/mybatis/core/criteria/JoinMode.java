package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 连接模式
 * @author wvkity
 */
public enum JoinMode implements Segment {

    /**
     * INNER JOIN
     */
    INNER {
        @Override
        public String getSqlSegment() {
            return " INNER JOIN ";
        }
    },

    /**
     * LEFT JOIN
     */
    LEFT {
        @Override
        public String getSqlSegment() {
            return " LEFT JOIN ";
        }
    },

    /**
     * RIGHT JOIN
     */
    RIGHT {
        @Override
        public String getSqlSegment() {
            return " RIGHT JOIN ";
        }
    },

    /**
     * FULL JOIN
     */
    FULL {
        @Override
        public String getSqlSegment() {
            return " FULL JOIN ";
        }
    }
}
