package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.converter.Property;

/**
 * 条件包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @author wvkity
 */
public interface CriteriaWrapper<T, Chain extends CriteriaWrapper<T, Chain>> extends Criteria<T>,
        CompareWrapper<T, Chain>, NullWrapper<T, Chain>, RangeWrapper<T, Chain>, SubQueryWrapper<T, Chain>,
        FuzzyWrapper<T, Chain>, TemplateWrapper<T, Chain>, NestedWrapper<Chain>, SubCriteriaBuilder<T> {

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @param <E>      实体类型
     * @param <V>      属性值类型
     * @return 属性名
     */
    <E, V> String methodToProperty(Property<E, V> property);
}
