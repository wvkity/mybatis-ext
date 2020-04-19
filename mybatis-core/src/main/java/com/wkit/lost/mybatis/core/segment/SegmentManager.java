package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

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
    private final OrderSegmentWrapper orderWrapper = new OrderSegmentWrapper();

    /**
     * 添加多个条件对象
     * @param segments 条件对象数组
     * @return {@code this}
     */
    public SegmentManager add(Criterion<?>... segments) {
        return addCondition(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个条件对象
     * @param segments 条件对象集合
     * @return {@code this}
     */
    public SegmentManager addCondition(Collection<Criterion<?>> segments) {
        this.whereWrapper.addAll(segments);
        return this;
    }

    /**
     * 添加多个分组对象
     * @param segments 分组对象数组
     * @return {@code this}
     */
    public SegmentManager add(AbstractGroupWrapper<?, ?>... segments) {
        return addGroup(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个分组对象
     * @param segments 分组对象集合
     * @return {@code this}
     */
    public SegmentManager addGroup(Collection<AbstractGroupWrapper<?, ?>> segments) {
        this.groupWrapper.addAll(segments);
        return this;
    }

    /**
     * 添加多个排序对象
     * @param segments 排序对象数组
     * @return {@code this}
     */
    public SegmentManager add(AbstractOrderWrapper<?, ?>... segments) {
        return addOrder(ArrayUtil.toList(segments));
    }

    /**
     * 添加多个排序对象
     * @param segments 排序对象集合
     * @return {@code this}
     */
    public SegmentManager addOrder(Collection<AbstractOrderWrapper<?, ?>> segments) {
        this.orderWrapper.addAll(segments);
        return this;
    }

    /**
     * 检查是否存在SQL片段对象
     * @return boolean
     */
    public boolean hasSegment() {
        return this.whereWrapper.isNotEmpty() || this.groupWrapper.isNotEmpty()
                || this.havingWrapper.isNotEmpty() || this.orderWrapper.isNotEmpty();
    }

    @Override
    public String getSegment() {
        return getWhereSegment() + this.groupWrapper.getSegment() +
                this.havingWrapper.getSegment() + this.orderWrapper.getSegment();
    }

    /**
     * 获取SQL片段
     * @param replacement group分组替换字符串
     * @return SQL片段
     */
    public String getSegment(String replacement) {
        return StringUtil.hasText(replacement) ? (getWhereSegment() + replacement + this.havingWrapper.getSegment()
                + this.orderWrapper.getSegment()) : getSegment();
    }

    private String getWhereSegment() {
        return this.whereWrapper.getSegment();
    }

    /**
     * 获取条件集合
     * @return 条件集合
     */
    public List<Criterion<?>> getConditions() {
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
