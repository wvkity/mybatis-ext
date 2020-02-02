package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;

/**
 * LIKE匹配模式枚举类
 * @author wvkity
 */
public enum MatchMode implements Segment {
    
    /**
     * LIKE 'value'
     */
    EXACT {
        @Override
        public String getSqlSegment( String value ) {
            return value;
        }
    },
    
    /**
     * LIKE 'value%'
     */
    START {
        @Override
        public String getSqlSegment( String value ) {
            return value + '%';
        }
    },
    
    /**
     * LIKE '%value'
     */
    END {
        @Override
        public String getSqlSegment( String value ) {
            return '%' + value;
        }
    },
    
    /**
     * LIKE '%value%'
     */
    ANYWHERE {
        @Override
        public String getSqlSegment( String value ) {
            return '%' + value + '%';
        }
    };
    
    
    @Override
    public String getSqlSegment() {
        return this.name();
    }
    
    /**
     * 转成SQL片段
     * @param value 值
     * @return SQL字符串
     */
    public abstract String getSqlSegment( String value );
}
