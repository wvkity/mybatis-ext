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
 * 分组(字段包装对象)
 * @param <T> 泛型类型
 * @author wvkity
 */
public class Group<T> extends AbstractGroupWrapper<T, ColumnWrapper> {

    private static final long serialVersionUID = -579446881939269926L;

    /**
     * 构造方法
     * @param criteria 条件对象
     * @param columns  字段集合
     */
    public Group(Criteria<T> criteria, Collection<ColumnWrapper> columns) {
        if (criteria == null) {
            throw new MyBatisException("The Criteria object cannot be empty");
        }
        this.criteria = criteria;
        if (CollectionUtil.hasElement(columns)) {
            this.columns = columns.stream().filter(Objects::nonNull)
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        }
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String alias = criteria.isEnableAlias() ? (criteria.as() + ".") : "";
            return columns.stream().map(column -> alias + column.getColumn()).collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性数组
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group(Criteria<T> criteria, String... properties) {
        return group(criteria, ArrayUtil.toList(properties));
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性数组
     * @param <T>        泛型类型
     * @param <V>        属性值类型
     * @return 分组对象
     */
    @SafeVarargs
    public static <T, V> Group<T> group(Criteria<T> criteria, Property<T, V>... properties) {
        return new Group<>(criteria, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * 分组
     * @param criteria   条件对象
     * @param properties 属性集合
     * @param <T>        泛型类型
     * @return 分组对象
     */
    public static <T> Group<T> group(Criteria<T> criteria, List<String> properties) {
        return new Group<>(criteria, CriteriaUtil.propertyToColumn(criteria, distinct(properties)));
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 分组对象
     */
    public static <T, E> Group<E> group(String alias, AbstractQueryCriteriaWrapper<T> master,
                                        String... properties) {
        return group(alias, master, ArrayUtil.toList(properties));
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @param <V>        属性值类型
     * @return 分组对象
     */
    @SuppressWarnings({"unchecked"})
    public static <T, E, V> Group<E> group(String alias, AbstractQueryCriteriaWrapper<T> master,
                                           Property<E, V>... properties) {
        ForeignCriteria<E> criteria = master.searchForeign(alias);
        return new Group<>(criteria, CriteriaUtil.lambdaToColumn(criteria, ArrayUtil.toList(properties)));
    }

    /**
     * 分组
     * @param alias      联表对象别名
     * @param master     条件对象
     * @param properties 属性
     * @param <T>        泛型类型
     * @param <E>        泛型类型
     * @return 分组对象
     */
    public static <T, E> Group<E> group(String alias, AbstractQueryCriteriaWrapper<T> master,
                                        List<String> properties) {
        ForeignCriteria<E> criteria = master.searchForeign(alias);
        return new Group<>(criteria, CriteriaUtil.propertyToColumn(criteria, distinct(properties)));
    }
    
}
