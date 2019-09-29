package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.lambda.LambdaResolver;

import java.util.Map;

public interface Modifier<T, Context, R> extends LambdaResolver<R> {

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    default Context modify( R property, Object value ) {
        return modify( lambdaToProperty( property ), value );
    }

    /**
     * 添加更新列
     * @param property 属性
     * @param value    值
     * @return 当前对象
     */
    Context modify( String property, Object value );

    /**
     * 添加多个更新列
     * @param map 列集合
     * @return 当前对象
     */
    Context modify( Map<String, Object> map );
}
