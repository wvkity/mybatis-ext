package com.wkit.lost.mybatis.lambda;

import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lambda转换器(对象转属性)
 * @param <P> Lambda对象
 * @author wvkity
 */
@FunctionalInterface
public interface LambdaConverter<P> extends Serializable {
    
    /**
     * lambda对象转属性
     * @param property lambda对象
     * @return 字符串
     */
    String lambdaToProperty( P property );

    /**
     * lambda对象转属性
     * @param properties lambda对象数组
     * @return 属性集合
     */
    @SuppressWarnings( "unchecked" )
    default List<String> lambdaToProperty( P... properties ) {
        List<String> props;
        if ( !ArrayUtil.isEmpty( properties ) ) {
            props = Stream.of( properties )
                    .filter( Objects::nonNull )
                    .map( this::lambdaToProperty )
                    .collect( Collectors.toList() );
        } else {
            props = new ArrayList<>( 0 );
        }
        return props;
    }
}
