package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 范围
 * @author DT
 */
public enum Range implements Segment {
    
    /**
     * IN
     */
    IN {
        @Override
        public String getSqlSegment() {
            return "IN";
        }
    },
    
    /**
     * NOT IN
     */
    NOT_IN {
        @Override
        public String getSqlSegment() {
            return "NOT IN";
        }
    }
}
