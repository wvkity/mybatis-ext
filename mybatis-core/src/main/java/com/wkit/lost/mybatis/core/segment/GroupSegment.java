package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.Group;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分组SQL片段类
 * @author DT
 */
public class GroupSegment extends AbstractSegment {

    private static final long serialVersionUID = -3933820801144405605L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            List<Group<?>> groups = segments.stream()
                    .filter( group -> group instanceof Group )
                    .map( group -> ( Group<?> ) group )
                    .collect( Collectors.toList() );
            if ( !groups.isEmpty() ) {
                return " GROUP BY " + groups.stream().map( Group::getSqlSegment ).collect( Collectors.joining( ", " ) );
            }
        }
        return "";
    }
}
