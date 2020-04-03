package com.wkit.lost.mybatis.core.constant;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 数据库条件符号
 * @author wvkity
 */
public enum Symbol implements Segment {

    /**
     * 空
     */
    NULL {
        @Override
        public String getSegment() {
            return "IS NULL";
        }
    },

    /**
     * 非空
     */
    NOT_NULL {
        @Override
        public String getSegment() {
            return "IS NOT NULL";
        }
    },

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
    },

    /**
     * 小于
     */
    LT {
        @Override
        public String getSegment() {
            return "<";
        }
    },

    /**
     * 小于等于
     */
    LE {
        @Override
        public String getSegment() {
            return "<=";
        }
    },

    /**
     * 大于
     */
    GT {
        @Override
        public String getSegment() {
            return ">";
        }
    },

    /**
     * 大于等于
     */
    GE {
        @Override
        public String getSegment() {
            return ">=";
        }
    },

    /**
     * IN
     */
    IN {
        @Override
        public String getSegment() {
            return "IN";
        }
    },

    /**
     * NOT IN
     */
    NOT_IN {
        @Override
        public String getSegment() {
            return "NOT IN";
        }
    },
    
    /**
     * EXISTS
     */
    EXISTS {
        @Override
        public String getSegment() {
            return "EXISTS";
        }
    },

    /**
     * EXISTS
     */
    NOT_EXISTS {
        @Override
        public String getSegment() {
            return "NOT EXISTS";
        }
    },

    /**
     * 模糊匹配
     */
    LIKE {
        @Override
        public String getSegment() {
            return "LIKE";
        }
    },

    /**
     * 模糊匹配
     */
    NOT_LIKE {
        @Override
        public String getSegment() {
            return "NOT LIKE";
        }
    },
    
    /**
     * BETWEEN
     */
    BETWEEN {
        @Override
        public String getSegment() {
            return "BETWEEN";
        }
    },

    /**
     * NOT BETWEEN
     */
    NOT_BETWEEN {
        @Override
        public String getSegment() {
            return "NOT BETWEEN";
        }
    };

    public static boolean filter( Symbol symbol ) {
        return true;
    }
}
