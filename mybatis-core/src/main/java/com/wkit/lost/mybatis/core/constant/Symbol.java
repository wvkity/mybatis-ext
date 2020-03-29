package com.wkit.lost.mybatis.core.constant;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 数据库条件符号
 * @author wvkity
 */
public enum Symbol implements Segment {

    /**
     * 等于
     */
    EQ {
        @Override
        public String getSegment() {
            return "=";
        }
    },

    /**
     * 不等于
     */
    NE {
        @Override
        public String getSegment() {
            return "<>";
        }
    };

    public static boolean filter( Symbol symbol ) {
        return true;
    }
}
