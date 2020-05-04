package com.wvkity.mybatis.core.converter;

import com.wvkity.mybatis.utils.CollectionUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Lambda属性转换器
 * @param <T> 实体类型
 * @author wvkity
 */
public interface PropertyConverter<T> extends Serializable {

    /**
     * Lambda对象转属性
     * @param property Lambda对象
     * @param <R>      返回值类型
     * @return 属性名称
     */
    <R> String convert(Property<T, R> property);

    /**
     * Lambda对象转属性
     * @param properties Lambda对象集合
     * @param <R>        返回值类型
     * @return 属性名称
     */
    default <R> List<String> convert(Collection<Property<T, R>> properties) {
        if (CollectionUtil.hasElement(properties)) {
            return properties.stream().filter(Objects::nonNull).map(this::convert).collect(Collectors.toList());
        }
        return new ArrayList<>(0);
    }
}
