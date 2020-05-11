package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.metadata.ColumnWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.AbstractQueryCriteriaWrapper;
import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.core.wrapper.criteria.ForeignCriteria;
import com.wvkity.mybatis.core.wrapper.utils.CriteriaUtil;
import com.wvkity.mybatis.exception.MyBatisException;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.CollectionUtil;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 排序(字段包装对象)
 * @author wvkity
 */
public class Sort extends AbstractSortWrapper<ColumnWrapper> {

    private static final long serialVersionUID = -7981752420346514008L;

    /**
     * 构造方法
     * @param criteria  查询对象
     * @param columns   字段集合
     * @param ascending 排序方式(是否为ASC排序)
     */
    private Sort(Criteria<?> criteria, boolean ascending, Collection<ColumnWrapper> columns) {
        if (criteria == null) {
            throw new MyBatisException("The Criteria object cannot be empty");
        }
        this.criteria = criteria;
        this.ascending = ascending;
        if (CollectionUtil.hasElement(columns)) {
            this.columns = columns.stream().filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String orderMode = ascending ? " ASC" : " DESC";
            String realAlias = this.criteria != null && criteria.isEnableAlias() ? (criteria.as() + ".") : "";
            return this.columns.stream().map(it -> realAlias + it.getColumn() + orderMode)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <V>        属性值类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, V> Sort asc(Criteria<T> criteria, Property<T, V>... properties) {
        return new Sort(criteria, true, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort asc(Criteria<?> criteria, String... properties) {
        return asc(criteria, ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort asc(Criteria<?> criteria, List<String> properties) {
        return new Sort(criteria, true, CriteriaUtil.propertyToColumn(criteria, distinct(properties)));
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort asc(String alias, AbstractQueryCriteriaWrapper<?> master, String... properties) {
        return asc(master.searchForeign(alias), ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <E>        泛型类型
     * @param <V>        属性值类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <E, V> Sort asc(String alias, AbstractQueryCriteriaWrapper<?> master,
                                  Property<E, V>... properties) {
        Criteria<E> criteria = master.searchForeign(alias);
        return new Sort(criteria, true, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * ASC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort asc(String alias, AbstractQueryCriteriaWrapper<?> master, List<String> properties) {
        return asc(master.searchForeign(alias), properties);
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <V>        属性值类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <T, V> Sort desc(Criteria<T> criteria, Property<T, V>... properties) {
        return new Sort(criteria, false, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort desc(Criteria<?> criteria, String... properties) {
        return desc(criteria, ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param criteria   查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort desc(Criteria<?> criteria, List<String> properties) {
        return new Sort(criteria, false, CriteriaUtil.propertyToColumn(criteria, distinct(properties)));
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @param <E>        泛型类型
     * @param <V>        属性值类型
     * @return 排序对象
     */
    @SafeVarargs
    public static <E, V> Sort desc(String alias, AbstractQueryCriteriaWrapper<E> master,
                                   Property<E, V>... properties) {
        ForeignCriteria<E> criteria = master.searchForeign(alias);
        return new Sort(criteria, false, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort desc(String alias, AbstractQueryCriteriaWrapper<?> master, String... properties) {
        return desc(master.searchForeign(alias), properties);
    }

    /**
     * DESC排序
     * @param alias      联表对象别名
     * @param master     主查询对象
     * @param properties 属性
     * @return 排序对象
     */
    public static Sort desc(String alias, AbstractQueryCriteriaWrapper<?> master, List<String> properties) {
        return desc(master.searchForeign(alias), properties);
    }

}
