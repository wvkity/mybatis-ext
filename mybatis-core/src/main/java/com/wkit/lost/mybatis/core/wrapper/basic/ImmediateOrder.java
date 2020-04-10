package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 排序(字符串字段)
 * @param <T> 实体类型
 * @author wvkity
 */
public class ImmediateOrder<T> extends AbstractOrderWrapper<T, String> {

    private static final long serialVersionUID = 7837358423348936221L;

    /**
     * 表别名
     */
    @Getter
    private final String alias;

    /**
     * 构造方法
     * @param alias     表别名
     * @param ascending 排序方式(是否为ASC排序)
     * @param columns   字段集合
     */
    public ImmediateOrder(String alias, boolean ascending, Collection<String> columns) {
        this.alias = alias;
        this.ascending = ascending;
        this.columns = distinct(columns);
    }

    /**
     * ASC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ImmediateOrder<T> asc(String... properties) {
        return new ImmediateOrder<>(null, true, ArrayUtil.toList(properties));
    }

    /**
     * ASC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ImmediateOrder<T> ascWithAlias(String alias, String... properties) {
        return new ImmediateOrder<>(alias, true, ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ImmediateOrder<T> desc(String... properties) {
        return new ImmediateOrder<>(null, false, ArrayUtil.toList(properties));
    }

    /**
     * DESC排序
     * @param alias      表别名
     * @param properties 属性(字段)
     * @param <T>        泛型类型
     * @return 排序对象
     */
    public static <T> ImmediateOrder<T> descWithAlias(String alias, String... properties) {
        return new ImmediateOrder<>(alias, false, ArrayUtil.toList(properties));
    }

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String orderMode = ascending ? " ASC" : " DESC";
            String realAlias = StringUtil.hasText(this.alias) ? (this.alias + ".") : "";
            return this.columns.stream().map(it -> realAlias + it + orderMode)
                    .collect(Collectors.joining(", "));
        }
        return "";
    }
}
