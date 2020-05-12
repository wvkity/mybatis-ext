package com.wvkity.mybatis.core.wrapper.criteria;

import com.wvkity.mybatis.core.converter.Property;

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
     * 纯SQL条件
     * <p>存在SQL注入风险，尽可能的减少使用，可参考{@link com.wvkity.mybatis.core.conditional.expression.Template}、
     * {@link com.wvkity.mybatis.core.conditional.expression.DirectTemplate}模板条件，实现添加对应条件.</p>
     * @param expression 条件
     * @return {@code this}
     */
    Chain pure(String expression);

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @param <E>      实体类型
     * @param <V>      属性值类型
     * @return 属性名
     */
    <E, V> String methodToProperty(Property<E, V> property);
}
