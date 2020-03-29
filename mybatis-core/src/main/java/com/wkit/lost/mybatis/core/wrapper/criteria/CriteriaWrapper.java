package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.lambda.Property;

public interface CriteriaWrapper<T, Chain, P> extends Criteria<T>, CompareWrapper<Chain, P> {

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @return 属性名
     */
    <E> String methodToProperty( Property<E, ?> property );
}
