package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.segment.Segment;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 操作符
 * @author DT
 */
public enum Operator implements Segment {
    
    /**
     * 等于
     */
    EQ {
        @Override
        public String getSqlSegment() {
            return "=";
        }
    },
    
    /**
     * 不等于
     */
    NE {
        @Override
        public String getSqlSegment() {
            return "<>";
        }
    },
    
    /**
     * 大于
     */
    GT {
        @Override
        public String getSqlSegment() {
            return ">";
        }
    },
    
    /**
     * 大于或等于
     */
    GE {
        @Override
        public String getSqlSegment() {
            return ">=";
        }
    },
    
    /**
     * 小于
     */
    LT {
        @Override
        public String getSqlSegment() {
            return "<";
        }
    },
    
    /**
     * 小于或等于
     */
    LE {
        @Override
        public String getSqlSegment() {
            return "<=";
        }
    },
    
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
    },
    
    /**
     * 空
     */
    NULL {
        @Override
        public String getSqlSegment() {
            return "IS NULL";
        }
    },
    
    /**
     * 非空
     */
    NOT_NULL {
        @Override
        public String getSqlSegment() {
            return "IS NOT NULL";
        }
    },
    
    /**
     * 模糊匹配
     */
    LIKE {
        @Override
        public String getSqlSegment() {
            return "LIKE";
        }
    },
    
    /**
     * 非
     */
    NOT {
        @Override
        public String getSqlSegment() {
            return "NOT";
        }
    },
    
    /**
     * BETWEEN
     */
    BETWEEN {
        @Override
        public String getSqlSegment() {
            return "BETWEEN";
        }
    },
    
    /**
     * EXISTS
     */
    EXISTS {
        @Override
        public String getSqlSegment() {
            return "EXISTS";
        }
    },

    /**
     * EXISTS
     */
    NOT_EXISTS {
        @Override
        public String getSqlSegment() {
            return "NOT EXISTS";
        }
    },
    
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
     * GROUP BY
     */
    GROUP {
        @Override
        public String getSqlSegment() {
            return "GROUP BY";
        }
    },
    
    /**
     * HAVING
     */
    HAVING {
        @Override
        public String getSqlSegment() {
            return "HAVING";
        }
    },
    
    /**
     * ORDER BY
     */
    ORDER {
        @Override
        public String getSqlSegment() {
            return "ORDER BY";
        }
    },
    
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
    };
    
    private static final Set<Operator> CACHE = new HashSet<>();
    
    static {
        CACHE.addAll( Arrays.asList( NOT, NULL, NOT_NULL ) );
    }
    
    public static boolean filter( Operator operator ) {
        return !CACHE.contains( operator );
    }
}
