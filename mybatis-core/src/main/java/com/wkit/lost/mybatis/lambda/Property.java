package com.wkit.lost.mybatis.lambda;

import java.io.Serializable;

@FunctionalInterface
public interface Property<T, R> extends Serializable {
    
    R apply( T t );
}
