package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;

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
     * 等于(Normal Equals)
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V1>          属性1值类型
     * @param <V2>          属性2值类型
     * @return {@code this}
     */
    default <E, V1, V2> Chain nq(Property<T, V1> property, Criteria<E> otherCriteria, Property<E, V2> otherProperty) {
        return nq(convert(property), otherCriteria, otherCriteria.convert(otherProperty));
    }

    /**
     * 等于(Normal Equals)
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain nq(String property, Criteria<E> otherCriteria, String otherProperty);

    /**
     * 等于(Normal Equals)
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    default <E, V> Chain nq(Property<T, V> property, Criteria<E> otherCriteria) {
        return nq(convert(property), otherCriteria);
    }

    /**
     * 等于(Normal Equals)
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain nq(String property, Criteria<E> otherCriteria);

    /**
     * 等于(Normal Equals)
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    default <E, V> Chain nq(Criteria<E> otherCriteria, Property<E, V> otherProperty) {
        return nq(otherCriteria, otherCriteria.convert(otherProperty));
    }

    /**
     * 等于(Normal Equals)
     * @param otherCriteria 其他条件包装对象
     * @param otherProperty 其他属性
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain nq(Criteria<E> otherCriteria, String otherProperty);

    /**
     * 等于(Normal Equals)
     * @param property      属性
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    <E, V> Chain nq(Property<T, V> property, Criteria<E> otherCriteria, String otherColumn);
    
    /**
     * 等于(Normal Equals)
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @param <V>             属性值类型
     * @return {@code this}
     */
    default <V> Chain nq(Property<T, V> property, String otherTableAlias, String otherColumn) {
        return nq(convert(property), otherTableAlias, otherColumn);
    }

    /**
     * 等于(Normal Equals)
     * @param property        属性
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @return {@code this}
     */
    Chain nq(String property, String otherTableAlias, String otherColumn);

    /**
     * 等于(Normal Equals)
     * @param otherCriteria 其他条件包装对象
     * @param otherColumn   其他表字段
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain nqWith(Criteria<E> otherCriteria, String otherColumn);

    /**
     * 等于(Normal Equals)
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他表字段
     * @return {@code this}
     */
    Chain nqWith(String otherTableAlias, String otherColumn);

    /**
     * 等于(Normal Equals)
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @return {@code this}
     */
    <E> Chain nqWith(String column, Criteria<E> otherCriteria);

    /**
     * 等于(Normal Equals)
     * @param column          字段
     * @param otherTableAlias 其他表别名
     * @param otherColumn     其他字段
     * @return {@code this}
     */
    Chain nqWith(String column, String otherTableAlias, String otherColumn);

    /**
     * 等于(Normal Equals)
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param property      其他属性
     * @param <E>           实体类型
     * @param <V>           属性值类型
     * @return {@code this}
     */
    default <E, V> Chain nqWith(String column, Criteria<E> otherCriteria, Property<E, V> property) {
        return nqWith(column, otherCriteria, otherCriteria.convert(property));
    }

    /**
     * 等于(Normal Equals)
     * @param column        字段
     * @param otherCriteria 其他条件包装对象
     * @param property      其他属性
     * @param <E>           实体类型
     * @return {@code this}
     */
    <E> Chain nqWith(String column, Criteria<E> otherCriteria, String property);

    /**
     * 等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain eqWith(String column, Object value);

    /**
     * 等于
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @return {@code this}
     */
    default Chain eqWith(String c1, Object v1, String c2, Object v2) {
        Map<String, Object> columns = new HashMap<>(2);
        columns.put(c1, v1);
        columns.put(c2, v2);
        return eqWith(columns);
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
    default Chain eqWith(String c1, Object v1, String c2, Object v2, String c3, Object v3) {
        Map<String, Object> columns = new HashMap<>(3);
        columns.put(c1, v1);
        columns.put(c2, v2);
        columns.put(c3, v3);
        return eqWith(columns);
    }

    /**
     * 等于
     * @param columns 字段-值集合
     * @return {@code this}
     */
    Chain eqWith(Map<String, Object> columns);

    /**
     * 等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain eqWith(String tableAlias, String column, Object value);

    /**
     * 等于
     * @param tableAlias 表别名
     * @param c1         字段1
     * @param v1         字段1对应值
     * @param c2         字段2
     * @param v2         字段2对应值
     * @return {@code this}
     */
    default Chain eqWith(String tableAlias, String c1, Object v1, String c2, Object v2) {
        Map<String, Object> columns = new HashMap<>(2);
        columns.put(c1, v1);
        columns.put(c2, v2);
        return eqWith(tableAlias, columns);
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
    default Chain eqWith(String tableAlias, String c1, Object v1, String c2, Object v2, String c3, Object v3) {
        Map<String, Object> columns = new HashMap<>(3);
        columns.put(c1, v1);
        columns.put(c2, v2);
        columns.put(c3, v3);
        return eqWith(tableAlias, columns);
    }

    /**
     * 等于
     * @param tableAlias 表别名
     * @param columns    字段-值集合
     * @return {@code this}
     */
    Chain eqWith(String tableAlias, Map<String, Object> columns);

    /**
     * 或等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orEqWith(String column, Object value);

    /**
     * 或等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orEqWith(String tableAlias, String column, Object value);

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
    Chain neWith(String column, Object value);

    /**
     * 不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain neWith(String tableAlias, String column, Object value);

    /**
     * 或不等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orNeWith(String column, Object value);

    /**
     * 或不等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orNeWith(String tableAlias, String column, Object value);

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
    Chain ltWith(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain ltWith(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orLtWith(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orLtWith(String tableAlias, String column, Object value);

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
    Chain leWith(String column, Object value);

    /**
     * 小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain leWith(String tableAlias, String column, Object value);

    /**
     * 或小于等于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orLeWith(String column, Object value);

    /**
     * 或小于等于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orLeWith(String tableAlias, String column, Object value);

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
    Chain gtWith(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain gtWith(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orGtWith(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orGtWith(String tableAlias, String column, Object value);

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
    Chain geWith(String column, Object value);

    /**
     * 小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain geWith(String tableAlias, String column, Object value);

    /**
     * 或小于
     * @param column 字段
     * @param value  值
     * @return {@code this}
     */
    Chain orGeWith(String column, Object value);

    /**
     * 或小于
     * @param tableAlias 表别名
     * @param column     字段
     * @param value      值
     * @return {@code this}
     */
    Chain orGeWith(String tableAlias, String column, Object value);

}

