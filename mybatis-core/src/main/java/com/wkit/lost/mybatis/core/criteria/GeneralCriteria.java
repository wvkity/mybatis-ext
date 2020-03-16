package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.lambda.Property;

import java.util.function.Function;

public interface GeneralCriteria<T, Context, P> extends Criteria<T>, InstanceClone<Context>, Compare<Context, P>,
        Null<Context, P>, Range<Context, P>, IndistinctMatching<Context, P>, Between<Context, P>,
        Customize<Context, P>, com.wkit.lost.mybatis.core.criteria.Nested<Context>,
        SubCriteriaBuilder<T>, SubCondition<T, Context, P> {

    /**
     * 设置别名
     * @param alias 别名
     * @return 当前对象
     */
    Context setAlias( String alias );

    /**
     * 使用默认别名
     * @return 当前对象
     */
    Context useAlias();

    /**
     * 使用别名
     * @param alias 别名
     * @return 当前对象
     */
    Context useAlias( String alias );

    /**
     * AND连接
     * @return 当前对象
     */
    Context and();

    /**
     * OR连接
     * @return 当前对象
     */
    Context or();

    /**
     * 重置连接符
     * @return 当前对象
     */
    Context reset();

    /**
     * {@link com.wkit.lost.mybatis.core.condition.expression.Nested}条件简写形式
     * <p>AND ( A [OR | AND] B [OR AND] C...)</p>
     * @param function lambda对象
     * @return 当前对象
     * @see com.wkit.lost.mybatis.core.criteria.Nested#nested(Criterion[])
     */
    Context and( Function<Context, Context> function );

    /**
     * {@link com.wkit.lost.mybatis.core.condition.expression.Nested}条件简写形式
     * <p>OR ( A [OR | AND] B [OR AND] C...)</p>
     * @param function lambda对象
     * @return 当前对象
     * @see com.wkit.lost.mybatis.core.criteria.Nested#orNested(Criterion[])
     */
    Context or( Function<Context, Context> function );

    /**
     * 根据方法名获取属性(getXX|isXX)
     * @param property lambda对象
     * @return 属性名
     */
    <E> String methodToProperty( Property<E, ?> property );

}
