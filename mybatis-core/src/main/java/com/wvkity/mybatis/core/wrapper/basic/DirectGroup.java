package com.wvkity.mybatis.core.wrapper.basic;

import com.wvkity.mybatis.core.wrapper.criteria.Criteria;
import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分组(字符串字段)
 * @param <T> 泛型类型
 * @author wvkity
 */
public class DirectGroup<T> extends AbstractGroupWrapper<T, String> {

    private static final long serialVersionUID = -579446881939269926L;

    /**
     * 表别名
     */
    private String alias;

    /**
     * 构造方法
     * @param criteria 条件包装对象
     * @param columns  字段集合
     */
    private DirectGroup(Criteria<T> criteria, Collection<String> columns) {
        this.criteria = criteria;
        this.columns = distinct(columns);
    }

    /**
     * 构造方法
     * @param alias   表别名
     * @param columns 字段集合
     */
    private DirectGroup(String alias, Collection<String> columns) {
        this.alias = alias;
        this.columns = distinct(columns);
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String realAlias = StringUtil.hasText(this.alias) ? (this.alias + ".") :
                    (this.criteria != null && criteria.isEnableAlias() ? (criteria.as() + ".") : "");
            return columns.stream().map(it -> realAlias + it).collect(Collectors.joining(", "));
        }
        return "";
    }

    /**
     * 分组
     * @param columns 字段数组
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(String... columns) {
        return group(null, columns);
    }

    /**
     * 分组
     * @param criteria 条件包装对象
     * @param columns  字段数组
     * @param <T>      泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(Criteria<T> criteria, String... columns) {
        return group(criteria, ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param columns 字段数组
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(List<String> columns) {
        return group(null, columns);
    }

    /**
     * 分组
     * @param criteria 条件包装对象
     * @param columns  字段数组
     * @param <T>      泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(Criteria<T> criteria, List<String> columns) {
        return new DirectGroup<>(criteria, columns);
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段数组
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> groupWithAlias(String alias, String... columns) {
        return groupWithAlias(alias, ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段集合
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> groupWithAlias(String alias, Collection<String> columns) {
        return new DirectGroup<>(alias, columns);
    }

}
