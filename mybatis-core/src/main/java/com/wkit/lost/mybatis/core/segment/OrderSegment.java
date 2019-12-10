package com.wkit.lost.mybatis.core.segment;

import java.util.stream.Collectors;

/**
 * 排序SQL分段类
 * @author wvkity
 */
public class OrderSegment extends AbstractSegment {

    private static final long serialVersionUID = -725413569300627180L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            return " ORDER BY " + this.segments.stream().map( Segment::getSqlSegment ).collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
