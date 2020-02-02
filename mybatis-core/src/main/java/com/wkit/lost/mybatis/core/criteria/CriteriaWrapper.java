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
 * @param <R>       Lambda对象
 * @author wvkity
 */
public interface CriteriaWrapper<T, Context, R> extends Criteria<T>, InstanceClone<Context>,
        Compare<Context, R>, Null<Context, R>, Range<Context, R>, IndistinctMatching<Context, R>,
        Between<Context, R>, Customize<Context, R>, com.wkit.lost.mybatis.core.criteria.Nested<Context>, 
        ForeignBuilder<T, Context, R>, SubCriteriaBuilder<T>, ConditionBuilder<T, AbstractConditionManager<T>, R>, 
        SubQueryCondition<T, Context, R> {

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
    Criteria<T> asc( R... properties );

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    Criteria<T> desc( R... properties );

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    Criteria<T> group( R... properties );

}
