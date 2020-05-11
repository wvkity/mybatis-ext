package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.conditional.criterion.Criterion;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.segment.Segment;
import com.wvkity.mybatis.core.wrapper.aggreate.Function;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.Collection;
import java.util.List;

/**
 * SQL片段管理器
 * @author wvkity
 */
public class SegmentManager implements Segment {

    private static final long serialVersionUID = 4487583124797797922L;

    /**
     * WHERE片段容器
     */
    private final WhereSegmentWrapper whereWrapper = new WhereSegmentWrapper();

    /**
     * 分组片段容器
     */
    private final GroupSegmentWrapper groupWrapper = new GroupSegmentWrapper();

    /**
     * 分组筛选片段容器
     */
    private final HavingSegmentWrapper havingWrapper = new HavingSegmentWrapper();

    /**
     * 排序片段容器
     */
    private final SortSegmentWrapper sortWrapper = new SortSegmentWrapper();

    /**
     * 添加条件
     * @param segment 条件对象
     * @return {@code this}
     */
    public SegmentManager where(Criterion segment) {
        this.whereWrapper.add(segment);
        return this;
    }

    /**
     * 添加多个条件对象
     * @param segments 条件对象数组
     * @return {@code this}
     */
    public SegmentManager wheres(Criterion... segments) {
        return wheres(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个条件对象
     * @param segments 条件对象集合
     * @return {@code this}
     */
    public SegmentManager wheres(Collection<Criterion> segments) {
        this.whereWrapper.addAll(segments);
        return this;
    }

    /**
     * 添加分组
     * @param segment 分组对象
     * @return {@code this}
     */
    public SegmentManager group(AbstractGroupWrapper<?> segment) {
        this.groupWrapper.add(segment);
        return this;
    }

    /**
     * 添加多个分组对象
     * @param segments 分组对象数组
     * @return {@code this}
     */
    public SegmentManager groups(AbstractGroupWrapper<?>... segments) {
        return groups(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个分组对象
     * @param segments 分组对象集合
     * @return {@code this}
     */
    public SegmentManager groups(Collection<AbstractGroupWrapper<?>> segments) {
        this.groupWrapper.addAll(segments);
        return this;
    }

    /**
     * 添加排序
     * @param segment 排序对象
     * @return {@code this}
     */
    public SegmentManager sort(AbstractSortWrapper<?> segment) {
        this.sortWrapper.add(segment);
        return this;
    }

    /**
     * 添加多个排序对象
     * @param segments 排序对象数组
     * @return {@code this}
     */
    public SegmentManager sorts(AbstractSortWrapper<?>... segments) {
        return sorts(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个排序对象
     * @param segments 排序对象集合
     * @return {@code this}
     */
    public SegmentManager sorts(Collection<AbstractSortWrapper<?>> segments) {
        this.sortWrapper.addAll(segments);
        return this;
    }

    /**
     * 添加分组筛选条件
     * @param function 聚合函数对象
     * @return {@code this}
     */
    public SegmentManager having(Function function) {
        this.havingWrapper.add(function);
        return this;
    }

    /**
     * 添加分组筛选条件
     * @param functions 聚合函数对象数组
     * @return {@code this}
     */
    public SegmentManager havings(Function... functions) {
        return this.havings(ArrayUtil.toList(functions));
    }

    /**
     * 添加分组筛选条件
     * @param functions 聚合函数对象集合
     * @return {@code this}
     */
    public SegmentManager havings(Collection<Function> functions) {
        this.havingWrapper.addAll(functions);
        return this;
    }

    /**
     * 检查是否存在SQL片段对象
     * @return boolean
     */
    public boolean hasSegment() {
        return this.whereWrapper.isNotEmpty() || this.groupWrapper.isNotEmpty()
                || this.havingWrapper.isNotEmpty() || this.sortWrapper.isNotEmpty();
    }

    @Override
    public String getSegment() {
        return getWhereSegment() + this.groupWrapper.getSegment() +
                this.havingWrapper.getSegment() + this.sortWrapper.getSegment();
    }

    /**
     * 获取SQL片段
     * @param replacement group分组替换字符串
     * @return SQL片段
     */
    public String getSegment(String replacement) {
        return StringUtil.hasText(replacement) ? (getWhereSegment() + " GROUP BY " + replacement + this.havingWrapper.getSegment()
                + this.sortWrapper.getSegment()) : getSegment();
    }

    private String getWhereSegment() {
        return this.whereWrapper.getSegment();
    }

    /**
     * 获取条件集合
     * @return 条件集合
     */
    public List<Criterion> getConditions() {
        return this.whereWrapper.getConditions();
    }

    /**
     * 获取条件中的乐观锁值
     * @param column 乐观锁字段包装对象
     * @return 乐观锁值
     */
    public Object getVersionValue(final ColumnWrapper column) {
        return this.whereWrapper.optimisticLockingValue(column);
    }
}
