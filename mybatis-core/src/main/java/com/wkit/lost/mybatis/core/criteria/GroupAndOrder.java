package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.wrapper.AbstractGroupWrapper;
import com.wkit.lost.mybatis.core.wrapper.AbstractOrderWrapper;
import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 分组&排序
 * @param <T>       实体类型
 * @param <Context> 当前对象
 * @param <P>       属性lambda对象
 * @author wvkity
 */
public interface GroupAndOrder<T, Context, P> extends LambdaConverter<P> {

    // region order

    // region ASC

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Context asc( P... properties ) {
        return asc( lambdaToProperties( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context asc( String... properties ) {
        return asc( ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Context asc( Collection<String> properties );

    /**
     * ASC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateAsc( String... columns ) {
        return immediateAsc( ArrayUtil.toList( columns ) );
    }

    /**
     * ASC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Context immediateAsc( Collection<String> columns ) {
        return immediateAscWithAlias( null, columns );
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateAscWithAlias( String alias, String... columns ) {
        return immediateAscWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Context immediateAscWithAlias( String alias, Collection<String> columns );

    /**
     * ASC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Context asc( Aggregation... aggregations );

    /**
     * ASC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Context aggregateAsc( String... aliases ) {
        return aggregateAsc( ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Context aggregateAsc( Collection<String> aliases );

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Context foreignAsc( String foreignAlias, P... properties );

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Context foreignAsc( String foreignAlias, String... properties ) {
        return foreignAsc( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Context foreignAsc( String foreignAlias, Collection<String> properties );
    // endregion

    // region DESC

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Context desc( P... properties ) {
        return desc( lambdaToProperties( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context desc( String... properties ) {
        return desc( ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Context desc( Collection<String> properties );

    /**
     * DESC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateDesc( String... columns ) {
        return immediateDesc( ArrayUtil.toList( columns ) );
    }

    /**
     * DESC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Context immediateDesc( Collection<String> columns ) {
        return immediateDescWithAlias( null, columns );
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateDescWithAlias( String alias, String... columns ) {
        return immediateDescWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Context immediateDescWithAlias( String alias, Collection<String> columns );

    /**
     * DESC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Context desc( Aggregation... aggregations );

    /**
     * DESC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Context aggregateDesc( String... aliases ) {
        return aggregateDesc( ArrayUtil.toList( aliases ) );
    }

    /**
     * DESC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Context aggregateDesc( Collection<String> aliases );

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Context foreignDesc( String foreignAlias, P... properties );

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Context foreignDesc( String foreignAlias, String... properties ) {
        return foreignDesc( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Context foreignDesc( String foreignAlias, Collection<String> properties );
    // endregion

    /**
     * 添加排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    default Context add( AbstractOrderWrapper<?, ?>... orders ) {
        return addOrder( ArrayUtil.toList( orders ) );
    }

    /**
     * 添加排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Context addOrder( Collection<AbstractOrderWrapper<?, ?>> orders );
    // endregion

    // region group

    /**
     * 分组
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Context group( P... properties ) {
        return group( lambdaToProperties( properties ) );
    }

    /**
     * 分组
     * @param properties 属性数组
     * @return 当前对象
     */
    default Context group( String... properties ) {
        return group( ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param properties 属性集合
     * @return 当前对象
     */
    Context group( Collection<String> properties );

    /**
     * 分组
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateGroup( String... columns ) {
        return immediateGroup( ArrayUtil.toList( columns ) );
    }

    /**
     * 分组
     * @param columns 字段集合
     * @return 当前对象
     */
    default Context immediateGroup( Collection<String> columns ) {
        return immediateGroupWithAlias( null, columns );
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Context immediateGroupWithAlias( String alias, String... columns ) {
        return immediateGroupWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Context immediateGroupWithAlias( String alias, Collection<String> columns );

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Context foreignGroup( String foreignAlias, P... properties );

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Context foreignGroup( String foreignAlias, String... properties ) {
        return foreignGroup( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Context foreignGroup( String foreignAlias, Collection<String> properties );

    /**
     * 添加分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    default Context add( AbstractGroupWrapper<?, ?>... groups ) {
        return addGroup( ArrayUtil.toList( groups ) );
    }

    /**
     * 添加分组
     * @param groups 分组对象集合
     * @return 当前对象
     */
    Context addGroup( Collection<AbstractGroupWrapper<?, ?>> groups );
    // endregion

}
