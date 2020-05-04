package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.CollectionUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象包装器
 * @param <T> 实体类型
 * @param <E> 字段类型
 * @author wvkity
 */
@SuppressWarnings({"serial"})
public abstract class AbstractWrapper<T, E> implements Segment {

    /**
     * 条件对象
     */
    protected Criteria<T> criteria;

    /**
     * 字段集合
     */
    protected Set<E> columns;

    /**
     * 检查字段是否不为空
     * @return true: 是, false: 否
     */
    protected boolean notEmpty() {
        return this.columns != null && !this.columns.isEmpty();
    }

    /**
     * 获取字段集合
     * @return 字段集合
     */
    public Set<E> getColumns() {
        return notEmpty() ? Collections.unmodifiableSet(this.columns) :
                Collections.unmodifiableSet(new LinkedHashSet<>(0));
    }

    /**
     * 字符串集合去重
     * @param values 字符串集合
     * @return Set字符串集合
     */
    public static Set<String> distinct(Collection<String> values) {
        return CollectionUtil.isEmpty(values) ? new LinkedHashSet<>(0)
                : values.stream().filter(StringUtil::hasText).collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
