package com.wkit.lost.mybatis.core.segment;

import java.util.stream.Collectors;

/**
 * 分组筛选SQL片段类
 * @author DT
 */
public class HavingSegment extends AbstractSegment {
    
    private static final long serialVersionUID = -6309950211878447367L;
    
    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            String havingSegment = this.segments.stream().map( Segment::getSqlSegment ).collect( Collectors.joining(" ")).trim();
            if ( havingSegment.startsWith( "AND" ) ) {
                return " HAVING " + havingSegment.substring( 3 );
            } else if ( havingSegment.startsWith( "OR" ) ) {
                return " HAVING " + havingSegment.substring( 2 );
            }
            return " HAVING " + havingSegment;
        }
        return "";
    }
}
