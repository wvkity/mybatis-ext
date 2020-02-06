package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.lambda.LambdaConverter;

import java.io.Serializable;

/**
 * 空条件
 * @param <Context> 当前对象
 * @param <R>       Lambda对象
 * @author wvkity
 */
public interface Null<Context, R> extends LambdaConverter<R>, Segment, Serializable {

    /**
     * IS NULL
     * @param property 属性
     * @return 当前对象
     */
    default Context isNull( R property ) {
        return isNull( lambdaToProperty( property ) );
    }

    /**
     * IS NULL
     * @param property 属性
     * @return 当前对象
     */
    Context isNull( String property );

    /**
     * OR IS NULL
     * @param property 属性
     * @return 当前对象
     */
    default Context orIsNull( R property ) {
        return orIsNull( lambdaToProperty( property ) );
    }

    /**
     * OR IS NULL
     * @param property 属性
     * @return 当前对象
     */
    Context orIsNull( String property );

    /**
     * IS NOT NULL
     * @param property 属性
     * @return 当前对象
     */
    default Context notNull( R property ) {
        return notNull( lambdaToProperty( property ) );
    }

    /**
     * IS NOT NULL
     * @param property 属性
     * @return 当前对象
     */
    Context notNull( String property );

    /**
     * OR IS NOT NULL
     * @param property 属性
     * @return 当前对象
     */
    default Context orNotNull( R property ) {
        return orNotNull( lambdaToProperty( property ) );
    }

    /**
     * OR IS NOT NULL
     * @param property 属性
     * @return 当前对象
     */
    Context orNotNull( String property );
}
