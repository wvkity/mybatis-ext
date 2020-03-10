package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.criteria.Group;
import com.wkit.lost.mybatis.core.criteria.Order;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SQL片段管理器
 * @author wvkity
 */
public class SegmentManager implements Segment {

    private static final long serialVersionUID = 5602233858409623471L;

    /**
     * 普通SQL片段
     */
    @Getter
    private WhereSegment wheres = new WhereSegment();

    /**
     * 分组SQL片段
     */
    @Getter
    private GroupSegment groups = new GroupSegment();

    /**
     * 分组筛选SQL片段
     */
    @Getter
    private HavingSegment having = new HavingSegment();

    /**
     * 排序SQL片段
     */
    @Getter
    private OrderSegment orders = new OrderSegment();

    /**
     * 添加多个条件
     * @param conditions 条件数组
     * @return 当前对象
     */
    public SegmentManager conditions( Criterion<?>... conditions ) {
        if ( !ArrayUtil.isEmpty( conditions ) ) {
            conditions( Arrays.asList( conditions ) );
        }
        return this;
    }

    /**
     * 添加多个条件
     * @param conditions 条件集合
     * @return 当前对象
     */
    public SegmentManager conditions( Collection<Criterion<?>> conditions ) {
        if ( CollectionUtil.hasElement( conditions ) ) {
            this.wheres.addAll( conditions.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
        return this;
    }

    /**
     * 添加多个排序
     * @param orders 排序数组
     * @return 当前对象
     */
    public SegmentManager orders( Order<?>... orders ) {
        return orders( ArrayUtil.toList( orders ) );
    }

    /**
     * 添加多个排序
     * @param orders 排序数组
     * @return 当前对象
     */
    public SegmentManager orders( Collection<Order<?>> orders ) {
        if ( CollectionUtil.hasElement( orders ) ) {
            this.orders.addAll( orders.stream().filter( Objects::nonNull )
                    .collect( Collectors.toCollection( LinkedHashSet::new ) ) );
        }
        return this;
    }

    /**
     * 添加多个分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    public SegmentManager groups( Group<?>... groups ) {
        return groups( ArrayUtil.toList( groups ) );
    }

    /**
     * 添加多个分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    public SegmentManager groups( Collection<Group<?>> groups ) {
        if ( CollectionUtil.hasElement( groups ) ) {
            this.groups.addAll( groups.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
        return this;
    }

    /**
     * 添加多个分组筛选条件
     * @param aggregations 聚合函数对象数组
     * @return 当前对象
     */
    public SegmentManager having( Aggregation... aggregations ) {
        return having( ArrayUtil.toList( aggregations ) );
    }

    /**
     * 添加多个分组筛选条件
     * @param aggregations 聚合函数对象集合
     * @return 当前对象
     */
    public SegmentManager having( Collection<Aggregation> aggregations ) {
        if ( CollectionUtil.hasElement( aggregations ) ) {
            this.having.addAll( aggregations.stream().filter( Objects::nonNull ).collect( Collectors.toList() ) );
        }
        return this;
    }

    /**
     * 检查是否存在条件拼接
     * @return true: 是 false: 否
     */
    public boolean hasCondition() {
        return wheres.isNotEmpty() || groups.isNotEmpty() || having.isNotEmpty() || orders.isNotEmpty();
    }

    /**
     * 根据属性名称获取值
     * @param property 属性名
     * @return 属性值
     * @see WhereSegment#getConditionValue(String) 
     */
    public Object getConditionValue( final String property ) {
        return this.wheres.getConditionValue( property );
    }

    private String getWhereSqlSegment() {
        return wheres.getSqlSegment();
    }

    @Override
    public String getSqlSegment() {
        return getWhereSqlSegment() + this.groups.getSqlSegment() + this.having.getSqlSegment() + this.orders.getSqlSegment();
    }

    /**
     * 获取SQL片段
     * @param groupReplace 替换Group by SQL片段
     * @return SQL片段
     */
    public String getSqlSegment( String groupReplace ) {
        return StringUtil.hasText( groupReplace ) ?
                ( getWhereSqlSegment() + groupReplace + this.having.getSqlSegment() + this.orders.getSqlSegment() )
                : getSqlSegment();
    }
}
