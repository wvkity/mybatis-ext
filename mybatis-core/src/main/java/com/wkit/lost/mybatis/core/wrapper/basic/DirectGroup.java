package com.wkit.lost.mybatis.core.wrapper.basic;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.Collection;
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
    private final String alias;

    /**
     * 构造方法
     * @param alias   表别名
     * @param columns 字段集合
     */
    private DirectGroup(String alias, Collection<String> columns) {
        this.alias = alias;
        this.columns = distinct(columns);
    }

    /**
     * 分组
     * @param columns 字段数组
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(String... columns) {
        return groupWithAlias(null, ArrayUtil.toList(columns));
    }

    /**
     * 分组
     * @param columns 字段数组
     * @param <T>     泛型类型
     * @return 分组对象
     */
    public static <T> DirectGroup<T> group(Collection<String> columns) {
        return groupWithAlias(null, columns);
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

    @Override
    public String getSegment() {
        if (notEmpty()) {
            String realAlias = StringUtil.hasText(this.alias) ? (this.alias + ".") : "";
            return columns.stream().map(it -> realAlias + it).collect(Collectors.joining(", "));
        }
        return "";
    }
}
