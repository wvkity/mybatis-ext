package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.lambda.LambdaResolver;

/**
 * BETWEEN范围条件接口
 * @param <Context>
 * @param <R>
 */
public interface Between<Context, R> extends LambdaResolver<R> {

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    default Context between( R property, Object begin, Object end ) {
        return between( lambdaToProperty( property ), begin, end );
    }

    /**
     * BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    Context between( String property, Object begin, Object end );

    /**
     * OR BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    default Context orBetween( R property, Object begin, Object end ) {
        return orBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * OR BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    Context orBetween( String property, Object begin, Object end );

    /**
     * NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    default Context notBetween( R property, Object begin, Object end ) {
        return notBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    Context notBetween( String property, Object begin, Object end );

    /**
     * OR NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    default Context orNotBetween( R property, Object begin, Object end ) {
        return orNotBetween( lambdaToProperty( property ), begin, end );
    }

    /**
     * OR NOT BETWEEN条件
     * @param property 属性
     * @param begin    开始值
     * @param end      结束值
     * @return 当前对象
     */
    Context orNotBetween( String property, Object begin, Object end );
}
