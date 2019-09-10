package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.lambda.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 条件工具类
 */
public abstract class CriteriaUtil {

    /**
     * 属性转成对应字段
     * @param criteria   条件对象
     * @param properties 属性集合
     * @param <T>        泛型类型
     * @return 字段集合
     */
    public static <T> List<Column> transform( Criteria<T> criteria, Collection<String> properties ) {
        return CollectionUtil.hasElement( properties ) ?
                properties.stream()
                        .filter( StringUtil::hasText )
                        .map( criteria::searchColumn )
                        .collect( Collectors.toList() ) : new ArrayList<>();
    }

    /**
     * 属性转成对应字段
     * @param criteria   条件对象
     * @param properties 属性集合
     * @param <T>        泛型类型
     * @return 字段集合
     */
    public static <T> List<Column> transform( Collection<Property<T, ?>> properties, Criteria<T> criteria ) {
        return CollectionUtil.hasElement( properties ) ?
                properties.stream()
                        .filter( Objects::nonNull )
                        .map( criteria::searchColumn )
                        .collect( Collectors.toList() ) : new ArrayList<>();
    }
}
