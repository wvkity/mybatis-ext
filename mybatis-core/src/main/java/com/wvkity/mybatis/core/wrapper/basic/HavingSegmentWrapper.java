package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.utils.Constants;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 分组筛选条件包装类
 * @author wvkity
 */
public class HavingSegmentWrapper extends AbstractSegmentWrapper<Function> {

    private static final long serialVersionUID = 6571650585895116066L;

    private static final String AND_OR_REGEX = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile(AND_OR_REGEX, Pattern.CASE_INSENSITIVE);

    @Override
    public String getSegment() {
        if (isNotEmpty()) {
            String segment = this.segments.stream().map(Segment::getSegment)
                    .collect(Collectors.joining(Constants.SPACE)).trim();
            if (AND_OR_PATTERN.matcher(segment).matches()) {
                return " HAVING " + segment.replaceFirst(AND_OR_REGEX, "$2");
            }
            return " HAVING " + segment;
        }
        return "";
    }
}
