package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;
import com.wvkity.mybatis.core.converter.PropertyConverter;

import java.util.Map;

/**
 * 更新操作包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface UpdateWrapper<T, Chain extends UpdateWrapper<T, Chain>> extends PropertyConverter<T> {
    
    /**
     * 添加更新字段
     * @param property 属性
     * @param value    值
     * @param <V>      值类型
     * @return {@code this}
     */
    default <V> Chain set(Property<T, V> property, Object value) {
        return set(convert(property), value);
    }

    /**
     * 添加更新字段
     * @param property 属性
     * @param value    值
     * @return {@code this}
     */
    Chain set(String property, Object value);

    /**
     * 添加更新字段
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param <V1> 值1类型
     * @param <V2> 值2类型
     * @return {@code this}
     */
    default <V1, V2> Chain set(Property<T, V1> p1, V1 v1, Property<T, V2> p2, V2 v2) {
        return set(convert(p1), v1, convert(p2), v2);
    }

    /**
     * 添加更新字段
     * @param p1   属性1
     * @param v1   属性1对应值
     * @param p2   属性2
     * @param v2   属性2对应值
     * @param p3   属性3
     * @param v3   属性3对应值
     * @param <V1> 值1类型
     * @param <V2> 值2类型
     * @param <V3> 值3类型
     * @return {@code this}
     */
    default <V1, V2, V3> Chain set(Property<T, V1> p1, V1 v1, Property<T, V2> p2, V2 v2, Property<T, V3> p3, V3 v3) {
        return set(convert(p1), v1, convert(p2), v2, convert(p3), v3);
    }

    /**
     * 添加更新字段
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @return {@code this}
     */
    default Chain set(String p1, Object v1, String p2, Object v2) {
        return set(p1, v1).set(p2, v2);
    }

    /**
     * 添加更新字段
     * @param p1 属性1
     * @param v1 属性1对应值
     * @param p2 属性2
     * @param v2 属性2对应值
     * @param p3 属性3
     * @param v3 属性3对应值
     * @return {@code this}
     */
    default Chain set(String p1, Object v1, String p2, Object v2, String p3, Object v3) {
        return set(p1, v1).set(p2, v2).set(p3, v3);
    }

    /**
     * 添加多个更新字段
     * @param properties 属性-值集合
     * @return {@code this}
     */
    Chain set(Map<String, Object> properties);

    /**
     * 添加更新字段
     * @param column 字段名
     * @param value  值
     * @return {@code this}
     */
    Chain setWith(String column, Object value);

    /**
     * 添加多个更新字段
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @return {@code this}
     */
    default Chain setWith(String c1, Object v1, String c2, Object v2) {
        return setWith(c1, v1).setWith(c2, v2);
    }

    /**
     * 添加多个更新字段
     * @param c1 字段1
     * @param v1 字段1对应值
     * @param c2 字段2
     * @param v2 字段2对应值
     * @param c3 字段3
     * @param v3 字段3对应值
     * @return {@code this}
     */
    default Chain setWith(String c1, Object v1, String c2, Object v2, String c3, Object v3) {
        return setWith(c1, v1).setWith(c2, v2).setWith(c3, v3);
    }

    /**
     * 添加多个更新字段
     * @param columns 字段-值集合
     * @return {@code this}
     */
    Chain setWith(Map<String, Object> columns);

    /**
     * 修改版本
     * @param version 版本号
     * @return 当前对象
     */
    Chain version(Object version);

    /**
     * 获取更新字段片段
     * @return 更新片段
     */
    String getUpdateSegment();
    
}
