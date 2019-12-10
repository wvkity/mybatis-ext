package com.wkit.lost.mybatis.lambda;

import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lambda对象解析类
 * @param <R> Lambda对象
 * @author wvkity
 */
@FunctionalInterface
public interface LambdaResolver<R> extends Serializable {
    
    /**
     * lambda对象转属性
     * @param lambda lambda对象
     * @return 字符串
     */
    String lambdaToProperty( R lambda );

    /**
     * lambda对象转属性
     * @param lambdas lambda对象数组
     * @return 属性集合
     */
    @SuppressWarnings( "unchecked" )
    default List<String> lambdaToProperty( R... lambdas ) {
        List<String> props;
        if ( !ArrayUtil.isEmpty( lambdas ) ) {
            props = Stream.of( lambdas )
                    .filter( Objects::nonNull )
                    .map( this::lambdaToProperty )
                    .collect( Collectors.toList() );
        } else {
            props = new ArrayList<>( 0 );
        }
        return props;
    }
}
