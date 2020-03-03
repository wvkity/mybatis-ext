package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.ConditionBuilder;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.condition.expression.Nested;

import java.util.function.Function;

/**
 * 查询条件接口
 * @param <T>       泛型类型
 * @param <Context> 当前对象
 * @param <P>       Lambda属性对象
 * @author wvkity
 */
public interface CriteriaWrapper<T, Context, P> extends Criteria<T>, InstanceClone<Context>,
        Compare<Context, P>, Null<Context, P>, Range<Context, P>, IndistinctMatching<Context, P>,
        Between<Context, P>, Customize<Context, P>, com.wkit.lost.mybatis.core.criteria.Nested<Context>, 
        ForeignBuilder<T, Context, P>, SubCriteriaBuilder<T>, ConditionBuilder<T, AbstractConditionManager<T>, P>, 
        SubQueryCondition<T, Context, P> {

    /**
     * {@link Nested}条件简写形式
     * <p>AND ( A [OR | AND] B [OR AND] C...)</p>
     * @param function lambda对象
     * @return 当前对象
     * @see com.wkit.lost.mybatis.core.criteria.Nested#nested(Criterion[])
     */
    Context and( Function<Context, Context> function );

    /**
     * {@link Nested}条件简写形式
     * <p>OR ( A [OR | AND] B [OR AND] C...)</p>
     * @param function lambda对象
     * @return 当前对象
     * @see com.wkit.lost.mybatis.core.criteria.Nested#orNested(Criterion[])
     */
    Context or( Function<Context, Context> function );

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    Criteria<T> asc( P... properties );

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    Criteria<T> desc( P... properties );

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    Criteria<T> group( P... properties );

}
