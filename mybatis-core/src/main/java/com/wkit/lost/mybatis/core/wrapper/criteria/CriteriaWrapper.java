package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.lambda.Property;

/**
 * 条件包装接口
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @param <P>     Lambda类型
 * @author wvkity
 */
public interface CriteriaWrapper<T, Chain extends CriteriaWrapper<T, Chain, P>, P> extends Criteria<T>,
        CompareWrapper<Chain, P>, NullWrapper<Chain, P>, RangeWrapper<Chain, P>, SubQueryWrapper<Chain, P>, 
        FuzzyWrapper<Chain, P>, TemplateWrapper<Chain, P>, NestedWrapper<Chain>, SubCriteriaBuilder<T> {

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @return 属性名
     */
    <E> String methodToProperty(Property<E, ?> property);
}
