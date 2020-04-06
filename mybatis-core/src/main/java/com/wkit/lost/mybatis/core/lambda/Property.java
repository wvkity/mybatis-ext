package com.wkit.lost.mybatis.core.lambda;

import java.io.Serializable;
import java.util.function.Function;

@FunctionalInterface
public interface Property<T, V> extends Function<T, V>, Serializable {

}
