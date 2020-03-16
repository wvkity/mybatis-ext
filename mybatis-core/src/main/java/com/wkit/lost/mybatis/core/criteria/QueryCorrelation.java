package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.lambda.LambdaConverter;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;

import java.util.Collection;
import java.util.List;

public interface QueryCorrelation<T, Context, P> extends ForeignBuilder<T, Context, P>,  LambdaConverter<P> {

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @param <E>   泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( Class<E> entity );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> searchForeign( String alias, Class<E> entity );
    
    /**
     * 开启自动映射列别名(针对查询自动映射属性名)
     * @return 当前对象
     */
    Context columnAliasAutoMapping();

    /**
     * 是否开启自动映射列别名(针对查询自动映射属性名)
     * @param enable 是否开启
     * @return 当前对象
     */
    Context columnAliasAutoMapping( boolean enable );

    /**
     * 设置是否启用根据所有查询字段分组
     * <p>针对oracle、mysql5.7+等数据库</p>
     * @param enable 是否启用
     * @return 当前对象
     */
    Context groupAll( boolean enable );

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long, long)}高</p>
     * @param end   结束位置
     * @return 当前对象
     */
    default Context range( long end ) {
        return range( 0, end );
    }

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long, long)}高</p>
     * @param start 开始位置
     * @param end   结束位置
     * @return 当前对象
     */
    Context range( long start, long end );

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long)}低</p>
     * @param pageStart 开始页
     * @param pageEnd   结束页
     * @param size      每页数目
     * @return 当前对象
     */
    Context range( long pageStart, long pageEnd, long size );

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    default Context asc( P... properties ) {
        return asc( lambdaToProperties( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    default Context asc( String... properties ) {
        return asc( ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    Context asc( List<String> properties );

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @return 排序对象
     */
    Context asc( Aggregation... aggregations );

    /**
     * ASC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    default Context ascFunc( String... aliases ) {
        return ascFunc( ArrayUtil.toList( aliases ) );
    }

    /**
     * ASC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Context ascFunc( List<String> aliases );

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @param <E>        泛型类型
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Context aliasAsc( String alias, Property<E, ?>... properties );

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    default Context aliasAsc( String alias, String... properties ) {
        return aliasAsc( alias, ArrayUtil.toList( properties ) );
    }

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Context aliasAsc( String alias, List<String> properties );

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    default Context desc( P... properties ) {
        return desc( lambdaToProperties( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    default Context desc( String... properties ) {
        return desc( ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    Context desc( List<String> properties );

    /**
     * DESC排序
     * @param aggregations 聚合函数
     * @return 排序对象
     */
    Context desc( Aggregation... aggregations );

    /**
     * DESC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    default Context descFunc( String... aliases ) {
        return descFunc( ArrayUtil.toList( aliases ) );
    }

    /**
     * DESC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Context descFunc( List<String> aliases );

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @param <E>        泛型类型
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Context aliasDesc( String alias, Property<E, ?>... properties );

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    default Context aliasDesc( String alias, String... properties ) {
        return aliasDesc( alias, ArrayUtil.toList( properties ) );
    }

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Context aliasDesc( String alias, List<String> properties );

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    default Context group( P... properties ) {
        return group( lambdaToProperties( properties ) );
    }

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    default Context group( String... properties ) {
        return group( ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    Context group( Collection<String> properties );

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @param <E>        泛型类型
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Context aliasGroup( String alias, Property<E, ?>... properties );

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    default Context aliasGroup( String alias, String... properties ) {
        return aliasGroup( alias, ArrayUtil.toList( properties ) );
    }

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Context aliasGroup( String alias, Collection<String> properties );

    /**
     * 添加分组
     * @param groups 分组对象
     * @return 当前对象
     */
    Context addGroup( Group<?>... groups );

    /**
     * 添加分组
     * @param groups 分组对象
     * @return 当前对象
     */
    Context addGroup( Collection<Group<?>> groups );

    /**
     * 查询是否包含聚合函数
     * @param include 是否包含
     * @return 条件对象
     */
    Context includeFunction( boolean include );

    /**
     * 是否只查询聚合函数
     * @param only 是否
     * @return 条件对象
     */
    Context onlyFunction( boolean only );

    /**
     * 添加多个排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    default Context addOrder( Order<?>... orders ) {
        return addOrder( ArrayUtil.toList( orders ) );
    }

    /**
     * 添加多个排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Context addOrder( List<Order<?>> orders );
    
    /**
     * 添加联表条件对象
     * @param foreignCriteriaArray 联表条件对象数组
     * @return 当前对象
     */
    Context addForeign( ForeignCriteria<?>... foreignCriteriaArray );

    /**
     * 添加联表条件对象
     * @param foreignCriteriaList 联表条件对象集合
     * @return 当前对象
     */
    Context addForeign( Collection<ForeignCriteria<?>> foreignCriteriaList );
    
}
