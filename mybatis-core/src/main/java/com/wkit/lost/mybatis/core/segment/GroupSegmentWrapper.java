package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.stream.Collectors;

/**
 * 分组片段容器
 * @author wvkity
 */
public class GroupSegmentWrapper extends AbstractSegment<AbstractGroupWrapper<?, ?>> {

    private static final long serialVersionUID = 4448965526038678902L;

    @Override
    public String getSegment() {
        if (isNotEmpty()) {
            return " GROUP BY " + this.segments.stream().map(Segment::getSegment)
                    .collect(Collectors.joining(Constants.COMMA_SPACE));
        }
        return "";
    }
}
