package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;

import java.util.Locale;

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
        public String getSqlSegment() {
            return "AND";
        }
    },

    /**
     * OR
     */
    OR {
        @Override
        public String getSqlSegment() {
            return "OR";
        }
    },
    
    /**
     * 不操作
     */
    NORMAL {
        @Override
        public String getSqlSegment() {
            return "";
        }
    }
    
}
