package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.conditional.expression.ColumnExpressionWrapper;
import com.wvkity.mybatis.core.conditional.expression.DirectExpressionWrapper;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.Constants;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 条件片段容器
 * @author wvkity
 */
public class WhereSegmentWrapper extends AbstractSegmentWrapper<Criterion> {

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

    /**
     * 获取条件
     * @return 条件集合
     */
    final List<Criterion> getConditions() {
        return this.isNotEmpty() ? Collections.unmodifiableList(new ArrayList<>(this.segments))
                : Collections.emptyList();
    }

    /**
     * 获取条件中的乐观锁值
     * @param column 乐观锁字段包装对象
     * @return 乐观锁值
     */
    final Object optimisticLockingValue(final ColumnWrapper column) {
        if (CollectionUtil.hasElement(this.segments)) {
            String columnName = column.getColumn().toLowerCase(Locale.ENGLISH);
            for (Criterion it : this.segments) {
                if (it instanceof ColumnExpressionWrapper) {
                    if (column == ((ColumnExpressionWrapper) it).getColumn()) {
                        return it.getValue();
                    }
                } else if (it instanceof DirectExpressionWrapper) {
                    if (columnName.equalsIgnoreCase(((DirectExpressionWrapper) it).getColumn())) {
                        return it.getValue();
                    }
                }
            }
        }
        return null;
    }
}
