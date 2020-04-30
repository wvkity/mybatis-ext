package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.conditional.expression.ColumnExpressionWrapper;
import com.wkit.lost.mybatis.core.conditional.expression.DirectExpressionWrapper;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * 条件片段容器
 * @author wvkity
 */
public class WhereSegmentWrapper extends AbstractSegment<Criterion> {

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
