package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.metadata.PropertyMappingCache;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * 条件包装器
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 */
@SuppressWarnings( "serial" )
public abstract class AbstractChainConditionWrapper<T, Context extends AbstractChainConditionWrapper<T, Context>>
        extends AbstractConditionWrapper<T, Property<T, ?>, Context> {

    @Override
    public String lambdaToProperty( Property<T, ?> lambda ) {
        return PropertyMappingCache.lambdaToProperty( lambda );
    }

}
