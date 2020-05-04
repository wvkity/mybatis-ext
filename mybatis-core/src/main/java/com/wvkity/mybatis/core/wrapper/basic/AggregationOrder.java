package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.aggreate.Aggregation;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 排序(聚合函数对象)
 * @param <T> 实体类型
 * @author wvkity
 */
public class AggregationOrder<T> extends AbstractOrderWrapper<T, Aggregation> {

    private static final long serialVersionUID = -6042427127886318392L;

    /**
     * 构造方法
     * @param aggregations 聚合函数集合
     * @param ascending    排序方式(是否为ASC排序)
     */
    private AggregationOrder(boolean ascending, Collection<Aggregation> aggregations) {
        this.ascending = ascending;
        if (CollectionUtil.hasElement(aggregations)) {
            this.columns.addAll(aggregations.stream().filter(Objects::nonNull).collect(Collectors.toList()));
        }
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> asc(Aggregation... aggregations) {
        return asc(ArrayUtil.toList(aggregations));
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> asc(Criteria<T> criteria, String... aliases) {
        return asc(criteria, ArrayUtil.toList(aliases));
    }

    /**
     * ASC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> asc(Criteria<T> criteria, List<String> aliases) {
        if (CollectionUtil.hasElement(aliases)) {
            return asc(aliases.stream().map(criteria::getAggregate).collect(Collectors.toList()));
        }
        return null;
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> asc(List<Aggregation> aggregations) {
        return new AggregationOrder<>(true, aggregations);
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> desc(Aggregation... aggregations) {
        return desc(ArrayUtil.toList(aggregations));
    }

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @param <T>          泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> desc(List<Aggregation> aggregations) {
        return new AggregationOrder<>(false, aggregations);
    }

    /**
     * DESC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> desc(Criteria<T> criteria, String... aliases) {
        return desc(criteria, ArrayUtil.toList(aliases));
    }

    /**
     * DESC排序
     * @param criteria 条件对象
     * @param aliases  聚合函数别名
     * @param <T>      泛型类型
     * @return 排序对象
     */
    public static <T> AggregationOrder<T> desc(Criteria<T> criteria, List<String> aliases) {
        if (CollectionUtil.hasElement(aliases)) {
            return desc(aliases.stream().map(criteria::getAggregate).collect(Collectors.toList()));
        }
        return null;
    }


    @Override
    public String getSegment() {
        if (notEmpty()) {
            String orderMode = ascending ? " ASC" : " DESC";
            return this.columns.stream().map(it -> it.toOrderSegment() + orderMode)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }
}
