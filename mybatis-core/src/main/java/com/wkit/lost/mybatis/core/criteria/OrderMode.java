package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 排序模式
 * @author wvkity
 */
public enum OrderMode implements Segment {
    
    /**
     * ASC排序
     */
    ASC {
        @Override
        public String getSqlSegment() {
            return "ASC";
        }
    },
    
    /**
     * DESC排序
     */
    DESC {
        @Override
        public String getSqlSegment() {
            return "DESC";
        }
    }
    
}