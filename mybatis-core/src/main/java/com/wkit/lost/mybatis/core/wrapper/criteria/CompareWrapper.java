package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.converter.Property;
import com.wkit.lost.mybatis.core.converter.PropertyConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 比较条件包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface CompareWrapper<T, Chain extends CompareWrapper<T, Chain>> extends PropertyConverter<T> {

    /**
     * 主键等于
     * @param value 值
     * @return {@code this}
     */
    Chain idEq(Object value);

    /**
     * 或主键等于
     * @param value 值
     * @return {@code this}
     */
    Chain orIdEq(Object value);

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain eq(Property<T, V> property, V value) {
        return eq(convert(property), value);
    }

    /**
     * 等于
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @return {@code this}
     */
    default <V1, V2> Chain eq(Property<T, V1> p1, V1 v1, Property<T, V2> p2, V2 v2) {
        Map<String, Object> props = new HashMap<>(2);
        props.put(convert(p1), v1);
        props.put(convert(p2), v2);
        return eq(props);
    }

    /**
     * 等于
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性1对应值
     * @param <V1> 属性1值类型
     * @param <V2> 属性2值类型
     * @param <V3> 属性3值类型
     * @return {@code this}
     */
    default <V1, V2, V3> Chain eq(Property<T, V1> p1, V1 v1, Property<T, V2> p2, V2 v2,
                                  Property<T, V3> p3, V3 v3) {
        Map<String, Object> props = new HashMap<>(3);
        props.put(convert(p1), v1);
        props.put(convert(p2), v2);
        props.put(convert(p3), v3);
        return eq(props);
    }

    /**
     * 等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain eq(String property, Object value);

    /**
     * 等于
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @return {@code this}
     */
    default Chain eq(String p1, Object v1, String p2, Object v2) {
        Map<String, Object> props = new HashMap<>(2);
        props.put(p1, v1);
        props.put(p2, v2);
        return eq(props);
    }

    /**
     * 等于
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @param p3 属性3
     * @param v3 属性3对应值
     * @return {@code this}
     */
    default Chain eq(String p1, Object v1, String p2, Object v2, String p3, Object v3) {
        Map<String, Object> props = new HashMap<>(3);
        props.put(p1, v1);
        props.put(p2, v2);
        props.put(p3, v3);
        return eq(props);
    }

    /**
     * 等于
     * @param properties 属性-值集合
     * @return {@code this}
     */
    Chain eq(Map<String, Object> properties);

    /**
     * 等于
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V1>          属性1值类型
     * @param <V2>          属性2值类型
     * @return {@code this}
     */
    default <E, V1, V2> Chain normalEq(Property<T, V1> property, Criteria<E> otherCriteria, Property<E, V2> otherProperty) {
        return normalEq(convert(property), otherCriteria, otherCriteria.convert(otherProperty));
    }

    /**
     * 等于
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain normalEq(String property, Criteria<E> otherCriteria, String otherProperty);

    /**
     * 等于
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    default <E, V> Chain normalEq(Property<T, V> property, Criteria<E> otherCriteria) {
        return normalEq(convert(property), otherCriteria);
    }

    /**
     * 等于
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain normalEq(String property, Criteria<E> otherCriteria);

    /**
     * 等于
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    default <E, V> Chain normalEq(Criteria<E> otherCriteria, Property<E, V> otherProperty) {
        return normalEq(otherCriteria, otherCriteria.convert(otherProperty));
    }

    /**
     * 等于
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain normalEq(Criteria<E> otherCriteria, String otherProperty);

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orEq(Property<T, V> property, V value) {
        return orEq(convert(property), value);
    }

    /**
     * 或等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orEq(String property, Object value);

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directEq(String column, Object value);

    /**
     * 等于
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @return {@code this}
     */
    default Chain directEq(String c1, Object v1, String c2, Object v2) {
        Map<String, Object> columns = new HashMap<>(2);
        columns.put(c1, v1);
        columns.put(c2, v2);
        return directEq(columns);
    }

    /**
     * 等于
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @param c3 字段3
     * @param v3 字段3对应值
     * @return {@code this}
     */
    default Chain directEq(String c1, Object v1, String c2, Object v2, String c3, Object v3) {
        Map<String, Object> columns = new HashMap<>(3);
        columns.put(c1, v1);
        columns.put(c2, v2);
        columns.put(c3, v3);
        return directEq(columns);
    }

    /**
     * 等于
     * @param columns 字段-值集合
     * @return {@code this}
     */
    Chain directEq(Map<String, Object> columns);

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directEq(String tableAlias, String column, Object value);

    /**
     * 等于
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param v1         字段1对应值
     * @param c2         字段2
     * @param v2         字段2对应值
     * @return {@code this}
     */
    default Chain directEq(String tableAlias, String c1, Object v1, String c2, Object v2) {
        Map<String, Object> columns = new HashMap<>(2);
        columns.put(c1, v1);
        columns.put(c2, v2);
        return directEq(tableAlias, columns);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param v1         字段1对应值
     * @param c2         字段2
     * @param v2         字段2对应值
     * @param c3         字段3
     * @param v3         字段3对应值
     * @return {@code this}
     */
    default Chain directEq(String tableAlias, String c1, Object v1, String c2, Object v2, String c3, Object v3) {
        Map<String, Object> columns = new HashMap<>(3);
        columns.put(c1, v1);
        columns.put(c2, v2);
        columns.put(c3, v3);
        return directEq(tableAlias, columns);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param columns    字段-值集合
     * @return {@code this}
     */
    Chain directEq(String tableAlias, Map<String, Object> columns);

    /**
     * 或等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectEq(String column, Object value);

    /**
     * 或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectEq(String tableAlias, String column, Object value);

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain ne(Property<T, V> property, V value) {
        return ne(convert(property), value);
    }

    /**
     * 不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain ne(String property, Object value);

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orNe(Property<T, V> property, V value) {
        return orNe(convert(property), value);
    }

    /**
     * 或不等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orNe(String property, Object value);

    /**
     * 不等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directNe(String column, Object value);

    /**
     * 不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directNe(String tableAlias, String column, Object value);

    /**
     * 或不等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectNe(String column, Object value);

    /**
     * 或不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectNe(String tableAlias, String column, Object value);

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain lt(Property<T, V> property, V value) {
        return lt(convert(property), value);
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain lt(String property, Object value);

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orLt(Property<T, V> property, V value) {
        return orLt(convert(property), value);
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orLt(String property, Object value);

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directLt(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directLt(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectLt(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectLt(String tableAlias, String column, Object value);

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain le(Property<T, V> property, Object value) {
        return le(convert(property), value);
    }

    /**
     * 小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain le(String property, Object value);

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orLe(Property<T, V> property, V value) {
        return orLe(convert(property), value);
    }

    /**
     * 或小于等于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orLe(String property, Object value);

    /**
     * 小于等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directLe(String column, Object value);

    /**
     * 小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directLe(String tableAlias, String column, Object value);

    /**
     * 或小于等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectLe(String column, Object value);

    /**
     * 或小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectLe(String tableAlias, String column, Object value);

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain gt(Property<T, V> property, V value) {
        return gt(convert(property), value);
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain gt(String property, Object value);

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orGt(Property<T, V> property, Object value) {
        return orGt(convert(property), value);
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orGt(String property, Object value);

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directGt(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directGt(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectGt(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectGt(String tableAlias, String column, Object value);

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain ge(Property<T, V> property, V value) {
        return ge(convert(property), value);
    }

    /**
     * 小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain ge(String property, Object value);

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @param <V>      属性值类型
     * @return {@code this}
     */
    default <V> Chain orGe(Property<T, V> property, V value) {
        return orGe(convert(property), value);
    }

    /**
     * 或小于
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain orGe(String property, Object value);

    /**
     * 小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain directGe(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain directGe(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orDirectGe(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orDirectGe(String tableAlias, String column, Object value);

}

