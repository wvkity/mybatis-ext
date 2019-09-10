package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 聚合函数类型
 * @author DT
 */
public enum FunctionType implements Segment {

    /**
     * COUNT聚合函数
     */
    COUNT {
        @Override
        public String getSqlSegment() {
            return "COUNT";
        }
    },

    /**
     * SUM聚合函数
     */
    SUM {
        @Override
        public String getSqlSegment() {
            return "SUM";
        }
    },

    /**
     * AVG聚合函数
     */
    AVG {
        @Override
        public String getSqlSegment() {
            return "AVG";
        }
    },

    /**
     * MAX聚合函数
     */
    MAX {
        @Override
        public String getSqlSegment() {
            return "MAX";
        }
    },

    /**
     * MIN聚合函数
     */
    MIN {
        @Override
        public String getSqlSegment() {
            return "MIN";
        }
    }

}
