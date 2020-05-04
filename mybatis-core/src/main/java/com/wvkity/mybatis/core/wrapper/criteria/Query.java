package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;
import com.wvkity.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.Map;

/**
 * 查询列接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface Query<T, Chain extends Query<T, Chain>> extends CriteriaSearch, PropertyConverter<T> {


    /**
     * 添加查询列
     * @param property 属性
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> Chain select(Property<T, V> property) {
        return select(convert(property));
    }

    /**
     * 添加查询列
     * @param property 属性
     * @return {@code this}
     */
    Chain select(String property);

    /**
     * 添加查询列
     * @param p1   属性1
     * @param as1  属性1别名
     * @param p2   属性2
     * @param as2  属性2别名
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @return {@code this}
     */
    default <V1, V2> Chain select(Property<T, V1> p1, String as1, Property<T, V2> p2, String as2) {
        return select(p1, as1).select(p2, as2);
    }

    /**
     * 添加查询列
     * @param p1   属性1
     * @param as1  属性1别名
     * @param p2   属性2
     * @param as2  属性2别名
     * @param p3   属性3
     * @param as3  属性3别名
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @param <V3> 属性3值类型
     * @return {@code this}
     */
    default <V1, V2, V3> Chain select(Property<T, V1> p1, String as1, Property<T, V2> p2, String as2,
                                      Property<T, V3> p3, String as3) {
        return select(p1, as1).select(p2, as2).select(p3, as3);
    }

    /**
     * 添加查询列
     * @param property 属性
     * @param alias    列别名
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain select(Property<T, V> property, String alias) {
        return select(convert(property), alias);
    }

    /**
     * 添加查询列
     * @param p1  属性1
     * @param as1 属性1别名
     * @param p2  属性2
     * @param as2 属性2别名
     * @return {@code this}
     */
    default Chain select(String p1, String as1, String p2, String as2) {
        return select(p1, as1).select(p2, as2);
    }

    /**
     * 添加查询列
     * @param p1  属性1
     * @param as1 属性1别名
     * @param p2  属性2
     * @param as2 属性2别名
     * @param p3  属性3
     * @param as3 属性3别名
     * @return {@code this}
     */
    default Chain select(String p1, String as1, String p2, String as2, String p3, String as3) {
        return select(p1, as1).select(p2, as2).select(p3, as3);
    }

    /**
     * 添加查询列
     * @param property 属性
     * @param alias    列别名
     * @return {@code this}
     */
    Chain select(String property, String alias);

    /**
     * 添加查询列
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain select(Collection<String> properties);

    /**
     * 添加查询列
     * @param properties 列别名-属性集合
     * @return {@code this}
     */
    Chain select(Map<String, String> properties);

    /**
     * 添加查询列
     * @param column 列名
     * @return {@code this}
     */
    Chain selectWith(String column);

    /**
     * 添加查询列
     * @param c1  字段1
     * @param as1 字段1别名
     * @param c2  字段2
     * @param as2 字段2别名
     * @return {@code this}
     */
    default Chain selectWith(String c1, String as1, String c2, String as2) {
        return selectWith(c1, as1).selectWith(c2, as2);
    }

    /**
     * 添加查询列
     * @param c1  字段1
     * @param as1 字段1别名
     * @param c2  字段2
     * @param as2 字段2别名
     * @param c3  字段3
     * @param as3 字段3别名
     * @return {@code this}
     */
    default Chain selectWith(String c1, String as1, String c2, String as2, String c3, String as3) {
        return selectWith(c1, as1).selectWith(c2, as2).selectWith(c3, as3);
    }

    /**
     * 添加查询列
     * @param column 列名
     * @param alias  列别名
     * @return {@code this}
     */
    Chain selectWith(String column, String alias);

    /**
     * 添加查询列
     * @param columns 列名集合
     * @return {@code this}
     */
    Chain selectWith(Collection<String> columns);

    /**
     * 添加查询列
     * @param columns 列别名-列名集合
     * @return {@code this}
     */
    Chain selectWith(Map<String, String> columns);

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param column     列名
     * @param alias      列别名
     * @return {@code this}
     */
    Chain selectWith(String tableAlias, String column, String alias);

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名集合
     * @return {@code this}
     */
    Chain selectWith(String tableAlias, Collection<String> columns);

    /**
     * 添加参数列
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param as1        字段1别名
     * @param c2         字段2
     * @param as2        字段2别名
     * @return {@code this}
     */
    default Chain selectWith(String tableAlias, String c1, String as1, String c2, String as2) {
        return selectWith(tableAlias, c1, as1).selectWith(tableAlias, c2, as2);
    }

    /**
     * 添加参数列
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param as1        字段1别名
     * @param c2         字段2
     * @param as2        字段2别名
     * @param c3         字段3
     * @param as3        字段3别名
     * @return {@code this}
     */
    default Chain selectWith(String tableAlias, String c1, String as1,
                             String c2, String as2, String c3, String as3) {
        return selectWith(tableAlias, c1, as1).selectWith(tableAlias, c2, as2)
                .selectWith(tableAlias, c3, as3);
    }

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列别名-列名集合
     * @return {@code this}
     */
    Chain selectWith(String tableAlias, Map<String, String> columns);

    /**
     * 添加查询列
     * @param tableAlias 表别名
     * @param columns    列名数组
     * @return {@code this}
     */
    default Chain selectWithAlias(String tableAlias, String... columns) {
        return selectWith(tableAlias, ArrayUtil.toList(columns));
    }

    /**
     * 添加查询列
     * @param scAlias  子查询对象别名
     * @param property 属性
     * @return {@code this}
     */
    default <E> Chain subSelect(String scAlias, String property) {
        return subSelect(searchSub(scAlias), property);
    }


    /**
     * 添加查询列
     * @param sc       子查询对象
     * @param property 属性
     * @param <E>      实体类型
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <E, V> Chain subSelect(SubCriteria<E> sc, Property<E, V> property) {
        return subSelect(sc, sc.convert(property));
    }

    /**
     * 添加查询列
     * @param sc       子查询对象
     * @param property 属性
     * @return {@code this}
     */
    <E> Chain subSelect(SubCriteria<E> sc, String property);

    /**
     * 添加查询列
     * @param sc       子查询对象
     * @param property 属性
     * @param alias    列别名
     * @param <E>      实体类型
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <E, V> Chain subSelect(SubCriteria<E> sc, Property<E, V> property, String alias) {
        return subSelect(sc, sc.convert(property), alias);
    }

    /**
     * 添加查询列
     * @param sc       子查询对象
     * @param property 属性
     * @param alias    列别名
     * @param <E>      实体类型
     * @return {@code this}
     */
    <E> Chain subSelect(SubCriteria<E> sc, String property, String alias);

    /**
     * 添加查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain select(Property<T, V>... properties) {
        return select(convert(ArrayUtil.toList(properties)));
    }

    /**
     * 添加查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain selects(String... properties) {
        return select(ArrayUtil.toList(properties));
    }

    /**
     * 添加查询列
     * @param columns 列名数组
     * @return {@code this}
     */
    default Chain selectsWith(String... columns) {
        return selectWith(ArrayUtil.toList(columns));
    }

    /**
     * 添加子查询列
     * @param scAlias    子查询条件对象别名
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return {@code this}
     */
    default <E> Chain subSelects(String scAlias, String... properties) {
        return subSelect(scAlias, ArrayUtil.toList(properties));
    }

    /**
     * 添加子查询列
     * @param scAlias    子查询条件对象别名
     * @param properties 属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    default <E> Chain subSelect(String scAlias, Collection<String> properties) {
        return subSelect(searchSub(scAlias), properties);
    }

    /**
     * 添加子查询列
     * @param sc         子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @param <V>        值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <E, V> Chain subSelects(SubCriteria<E> sc, Property<E, V>... properties) {
        return subSelect(sc, sc.convert(ArrayUtil.toList(properties)));
    }

    /**
     * 添加子查询列
     * @param sc         子查询条件对象
     * @param properties 属性数组
     * @param <E>        实体类型
     * @return {@code this}
     */
    default <E> Chain subSelects(SubCriteria<E> sc, String... properties) {
        return subSelect(sc, ArrayUtil.toList(properties));
    }

    /**
     * 添加子查询列
     * @param sc         子查询条件对象
     * @param properties 属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    <E> Chain subSelect(SubCriteria<E> sc, Collection<String> properties);

    /**
     * 添加子查询列
     * @param scAlias    子查询条件对象别名
     * @param properties 列别名-属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    default <E> Chain subSelect(String scAlias, Map<String, String> properties) {
        return subSelect(searchSub(scAlias), properties);
    }


    /**
     * 添加子查询列
     * @param sc         子查询条件对象
     * @param properties 列别名-属性集合
     * @param <E>        实体类型
     * @return {@code this}
     */
    <E> Chain subSelect(SubCriteria<E> sc, Map<String, String> properties);

    /**
     * 排除查询列
     * @param property 属性
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> Chain exclude(Property<T, V> property) {
        return exclude(convert(property));
    }

    /**
     * 排除查询列
     * @param property 属性
     * @return {@code this}
     */
    default Chain exclude(String property) {
        return excludes(property);
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @param <V>        值类型
     * @return {@code this}
     */
    @SuppressWarnings({"unchecked"})
    default <V> Chain excludes(Property<T, V>... properties) {
        return excludes(convert(ArrayUtil.toList(properties)));
    }

    /**
     * 排除查询列
     * @param properties 属性数组
     * @return {@code this}
     */
    default Chain excludes(String... properties) {
        return excludes(ArrayUtil.toList(properties));
    }

    /**
     * 排除查询列
     * @param properties 属性集合
     * @return {@code this}
     */
    Chain excludes(Collection<String> properties);

    /**
     * 排除查询列
     * @param columns 列名数组
     * @return {@code this}
     */
    default Chain excludesWith(String... columns) {
        return excludesWith(ArrayUtil.toList(columns));
    }

    /**
     * 排除查询列
     * @param columns 列名集合
     * @return {@code this}
     */
    Chain excludesWith(Collection<String> columns);

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();

}
