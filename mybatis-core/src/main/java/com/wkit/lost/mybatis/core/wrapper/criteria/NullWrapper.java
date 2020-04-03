package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.lambda.LambdaConverter;

/**
 * NULL条件
 * @param <Chain> 子类
 * @param <P>     Lambda类
 * @author wvkity
 */
public interface NullWrapper<Chain extends NullWrapper<Chain, P>, P> extends LambdaConverter<P> {

    /**
     * IS NULL
     * @param property 属性
     * @return {@code this}
     */
    default Chain isNull( P property ) {
        return isNull( lambdaToProperty( property ) );
    }

    /**
     * IS NULL
     * @param property 属性
     * @return {@code this}
     */
    Chain isNull( String property );

    /**
     * 或IS NULL
     * @param property 属性
     * @return {@code this}
     */
    default Chain orIsNull( P property ) {
        return orIsNull( lambdaToProperty( property ) );
    }

    /**
     * 或IS NULL
     * @param property 属性
     * @return {@code this}
     */
    Chain orIsNull( String property );

    /**
     * IS NULL
     * @param column 字段
     * @return {@code this}
     */
    Chain immediateIsNull( String column );

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    Chain immediateIsNull( String tableAlias, String column );

    /**
     * 或IS NULL
     * @param column 字段
     * @return {@code this}
     */
    Chain orImmediateIsNull( String column );

    /**
     * 或IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    Chain orImmediateIsNull( String tableAlias, String column );

    /**
     * IS NULL
     * @param property 属性
     * @return {@code this}
     */
    default Chain notNull( P property ) {
        return notNull( lambdaToProperty( property ) );
    }

    /**
     * IS NULL
     * @param property 属性
     * @return {@code this}
     */
    Chain notNull( String property );

    /**
     * 或IS NULL
     * @param property 属性
     * @return {@code this}
     */
    default Chain orNotNull( P property ) {
        return orNotNull( lambdaToProperty( property ) );
    }

    /**
     * 或IS NULL
     * @param property 属性
     * @return {@code this}
     */
    Chain orNotNull( String property );

    /**
     * IS NULL
     * @param column 字段
     * @return {@code this}
     */
    Chain immediateNotNull( String column );

    /**
     * IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    Chain immediateNotNull( String tableAlias, String column );

    /**
     * 或IS NULL
     * @param column 字段
     * @return {@code this}
     */
    Chain orImmediateNotNull( String column );

    /**
     * 或IS NULL
     * @param tableAlias 表别名
     * @param column     字段
     * @return {@code this}
     */
    Chain orImmediateNotNull( String tableAlias, String column );
    
}
