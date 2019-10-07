package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * WHERE条件SQL片段类
 * @author DT
 */
public class WhereSegment extends AbstractSegment {

    private static final long serialVersionUID = -1005264914827625587L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            List<String> list = new ArrayList<>( segments.size() );
            for ( Segment segment : segments ) {
                String condition = segment.getSqlSegment();
                if ( StringUtil.hasText( condition ) ) {
                    list.add( condition );
                }
            }
            if ( !list.isEmpty() ) {
                return String.join( " ", list ).trim();
            }
        }
        return "";
    }
}
