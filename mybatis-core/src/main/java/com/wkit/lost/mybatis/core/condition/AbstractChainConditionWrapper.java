package com.wkit.lost.mybatis.core.condition;

import com.wkit.lost.mybatis.core.PropertyMappingForLambda;
import com.wkit.lost.mybatis.lambda.Property;

/**
 * @param <T>
 * @param <Context>
 */
@SuppressWarnings( "serial" )
public abstract class AbstractChainConditionWrapper<T, Context extends AbstractChainConditionWrapper<T, Context>>
        extends AbstractConditionWrapper<T, Property<T, ?>, Context> {
    
    @Override
    public String lambdaToProperty( Property<T, ?> lambda ) {
        return PropertyMappingForLambda.lambdaToProperty( lambda );
    }
    
}
