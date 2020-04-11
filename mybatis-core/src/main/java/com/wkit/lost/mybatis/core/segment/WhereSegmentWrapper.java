package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件片段容器
 * @author wvkity
 */
public class WhereSegmentWrapper extends AbstractSegment<Criterion<?>> {

    private static final long serialVersionUID = -217408456601550416L;

    @Override
    public String getSegment() {
        if (isNotEmpty()) {
            List<String> conditions = new ArrayList<>(this.segments.size());
            for (Segment it : this.segments) {
                String condition = it.getSegment();
                if (StringUtil.hasText(condition)) {
                    conditions.add(condition);
                }
            }
            if (!conditions.isEmpty()) {
                return String.join(" ", conditions).trim();
            }
        }
        return Constants.EMPTY;
    }
}
