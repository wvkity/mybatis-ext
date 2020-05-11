package com.wvkity.mybatis.core.constant;

import com.wvkity.mybatis.core.segment.Segment;

/**
 * 比较运算符
 * @author wvkity
 */
public enum Comparator implements Segment {

    EQ {
        @Override
        public String getSegment() {
            return "=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    NE {
        @Override
        public String getSegment() {
            return "<>";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    LT {
        @Override
        public String getSegment() {
            return "<";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    LE {
        @Override
        public String getSegment() {
            return "<=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },
    
    GT {
        @Override
        public String getSegment() {
            return ">";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    GE {
        @Override
        public String getSegment() {
            return ">=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },
    
    LT_OR_GT {
        @Override
        public String getSegment() {
            return "(:@ < ?0 OR :@ > ?1)";
        }
    },
    
    LE_OR_GT {
        @Override
        public String getSegment() {
            return "(:@ <= ?0 OR :@ > ?1)";
        }
    },
    
    LT_OR_GE {
        @Override
        public String getSegment() {
            return "(:@ < ?0 OR :@ >= ?1)";
        }
    },
    
    LE_OR_GE {
        @Override
        public String getSegment() {
            return "(:@ <= ?0 OR :@ >= ?1)";
        }
    },
    
    GT_AND_LT {
        @Override
        public String getSegment() {
            return "(?0 < :@ AND :@ < ?1)";
        }
    },
    
    GE_AND_LT {
        @Override
        public String getSegment() {
            return "(?0 <= :@ AND :@ < ?1)";
        }
    },
    
    GT_AND_LE {
        @Override
        public String getSegment() {
            return "(?0 < :@ AND :@ <= ?1)";
        }
    },
    
    GE_AND_LE {
        @Override
        public String getSegment() {
            return "(?0 <= :@ AND :@ <= ?1)";
        }
    };

    /**
     * 检查是否为简单比较
     * @return boolean
     */
    public boolean isSimple() {
        return false;
    }
}
