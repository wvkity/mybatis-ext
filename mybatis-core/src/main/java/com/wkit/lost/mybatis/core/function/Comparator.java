package com.wkit.lost.mybatis.core.function;

import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * 比较运算
 * @author wvkity
 */
public enum Comparator implements Segment {

    /**
     * fun = value
     */
    EQ {
        @Override
        public String getSqlSegment() {
            return "=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * fun &lt;&gt; value
     */
    NE {
        @Override
        public String getSqlSegment() {
            return "<>";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * fun &lt; value
     */
    LT {
        @Override
        public String getSqlSegment() {
            return "<";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * fun &lt;= value
     */
    LE {
        @Override
        public String getSqlSegment() {
            return "<=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * fun &gt; value
     */
    GT {
        @Override
        public String getSqlSegment() {
            return ">";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * fun &gt;= value
     */
    GE {
        @Override
        public String getSqlSegment() {
            return ">=";
        }

        @Override
        public boolean isSimple() {
            return true;
        }
    },

    /**
     * value &gt; fun or fun &gt; value
     */
    GT_OR_LT {
        @Override
        public String getSqlSegment() {
            return "({} > {} OR {} > {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &gt; fun or fun &ge; value
     */
    GE_OR_LT {
        @Override
        public String getSqlSegment() {
            return "({} > {} OR {} >= {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &ge; fun or fun &gt; value
     */
    GT_OR_LE {
        @Override
        public String getSqlSegment() {
            return "({} >= {} OR {} > {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &ge; fun or fun &ge; value
     */
    GE_OR_LE {
        @Override
        public String getSqlSegment() {
            return "({} >= {} OR {} >= {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &lt; fun and fun &lt; value
     */
    GT_AND_LT {
        @Override
        public String getSqlSegment() {
            return "({} < {} AND {} < {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &le; fun and fun &lt; value
     */
    GE_AND_LT {
        @Override
        public String getSqlSegment() {
            return "({} <= {} AND {} < {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &lt; fun and fun &le; value
     */
    GT_AND_LE {
        @Override
        public String getSqlSegment() {
            return "{} < {} AND {} <= {}";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    },

    /**
     * value &le; fun and fun &le; value
     */
    GE_AND_LE {
        @Override
        public String getSqlSegment() {
            return "({} <= {} AND {} <= {})";
        }

        @Override
        public boolean isSimple() {
            return false;
        }
    };

    /**
     * 是否为简单比较
     * @return true: 是, false: 否
     */
    public abstract boolean isSimple();
}
