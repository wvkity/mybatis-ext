package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.wrapper.aggreate.Aggregation;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractGroupWrapper;
import com.wkit.lost.mybatis.core.wrapper.basic.AbstractOrderWrapper;
import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;

/**
 * 分组&排序
 * @param <T>     实体类型
 * @param <Chain> 当前对象
 * @param <P>     属性lambda对象
 * @author wvkity
 */
public interface GroupAndOrderBuilder<T, Chain extends GroupAndOrderBuilder<T, Chain, P>, P>
        extends LambdaConverter<P> {

    // region order

    // region ASC

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Chain asc( P... properties ) {
        return asc( lambdaToProperties( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain asc( String... properties ) {
        return asc( ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain asc( Collection<String> properties );

    /**
     * ASC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateAsc( String... columns ) {
        return immediateAsc( ArrayUtil.toList( columns ) );
    }

    /**
     * ASC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain immediateAsc( Collection<String> columns ) {
        return immediateAscWithAlias( null, columns );
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateAscWithAlias( String alias, String... columns ) {
        return immediateAscWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * ASC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain immediateAscWithAlias( String alias, Collection<String> columns );

    /**
     * ASC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Chain asc( Aggregation... aggregations );

    /**
     * ASC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Chain aggregateAsc( String... aliases ) {
        return aggregateAsc( ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Chain aggregateAsc( Collection<String> aliases );

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Chain foreignAsc( String foreignAlias, P... properties );

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignAsc( String foreignAlias, String... properties ) {
        return foreignAsc( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignAsc( String foreignAlias, Collection<String> properties );
    // endregion

    // region DESC

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Chain desc( P... properties ) {
        return desc( lambdaToProperties( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain desc( String... properties ) {
        return desc( ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain desc( Collection<String> properties );

    /**
     * DESC排序
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateDesc( String... columns ) {
        return immediateDesc( ArrayUtil.toList( columns ) );
    }

    /**
     * DESC排序
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain immediateDesc( Collection<String> columns ) {
        return immediateDescWithAlias( null, columns );
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateDescWithAlias( String alias, String... columns ) {
        return immediateDescWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * DESC排序
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain immediateDescWithAlias( String alias, Collection<String> columns );

    /**
     * DESC排序
     * @param aggregations 聚合函数数组
     * @return 当前对象
     */
    Chain desc( Aggregation... aggregations );

    /**
     * DESC排序
     * @param aliases 聚合函数别名数组
     * @return 当前对象
     */
    default Chain aggregateDesc( String... aliases ) {
        return aggregateDesc( ArrayUtil.toList( aliases ) );
    }

    /**
     * DESC排序
     * @param aliases 聚合函数别名集合
     * @return 当前对象
     */
    Chain aggregateDesc( Collection<String> aliases );

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Chain foreignDesc( String foreignAlias, P... properties );

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignDesc( String foreignAlias, String... properties ) {
        return foreignDesc( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignDesc( String foreignAlias, Collection<String> properties );
    // endregion

    /**
     * 添加排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    default Chain add( AbstractOrderWrapper<?, ?>... orders ) {
        return addOrder( ArrayUtil.toList( orders ) );
    }

    /**
     * 添加排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Chain addOrder( Collection<AbstractOrderWrapper<?, ?>> orders );
    // endregion

    // region group

    /**
     * 分组
     * @param properties 属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    default Chain group( P... properties ) {
        return group( lambdaToProperties( properties ) );
    }

    /**
     * 分组
     * @param properties 属性数组
     * @return 当前对象
     */
    default Chain group( String... properties ) {
        return group( ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param properties 属性集合
     * @return 当前对象
     */
    Chain group( Collection<String> properties );

    /**
     * 分组
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateGroup( String... columns ) {
        return immediateGroup( ArrayUtil.toList( columns ) );
    }

    /**
     * 分组
     * @param columns 字段集合
     * @return 当前对象
     */
    default Chain immediateGroup( Collection<String> columns ) {
        return immediateGroupWithAlias( null, columns );
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段数组
     * @return 当前对象
     */
    default Chain immediateGroupWithAlias( String alias, String... columns ) {
        return immediateGroupWithAlias( alias, ArrayUtil.toList( columns ) );
    }

    /**
     * 分组
     * @param alias   表别名
     * @param columns 字段集合
     * @return 当前对象
     */
    Chain immediateGroupWithAlias( String alias, Collection<String> columns );

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    @SuppressWarnings( { "unchecked" } )
    Chain foreignGroup( String foreignAlias, P... properties );

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性数组
     * @return 当前对象
     */
    default Chain foreignGroup( String foreignAlias, String... properties ) {
        return foreignGroup( foreignAlias, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param foreignAlias 连表对象别名
     * @param properties   属性集合
     * @return 当前对象
     */
    Chain foreignGroup( String foreignAlias, Collection<String> properties );

    /**
     * 添加分组
     * @param groups 分组对象数组
     * @return 当前对象
     */
    default Chain add( AbstractGroupWrapper<?, ?>... groups ) {
        return addGroup( ArrayUtil.toList( groups ) );
    }

    /**
     * 添加分组
     * @param groups 分组对象集合
     * @return 当前对象
     */
    Chain addGroup( Collection<AbstractGroupWrapper<?, ?>> groups );
    // endregion

}
