package com.wkit.lost.mybatis.core.segment;

import java.util.stream.Collectors;

/**
 * WHERE条件SQL片段类
 * @author DT
 */
public class WhereSegment extends AbstractSegment {

    private static final long serialVersionUID = -1005264914827625587L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            String whereSegment = this.segments.stream().map( Segment::getSqlSegment ).collect( Collectors.joining( " " ) ).trim();
            if ( whereSegment.startsWith( "AND" ) || whereSegment.startsWith( "and" ) ) {
                return " WHERE " + whereSegment.substring( 3 );
            } else if ( whereSegment.startsWith( "OR" ) || whereSegment.startsWith( "or" ) ) {
                return " WHERE " + whereSegment.substring( 2 );
            } else {
                return " WHERE " + whereSegment;
            }
        }
        return "";
    }
}
