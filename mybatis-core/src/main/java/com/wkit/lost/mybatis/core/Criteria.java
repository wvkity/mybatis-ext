package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.executor.resultset.ReturnType;
import com.wkit.lost.mybatis.lambda.Property;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Criteria接口
 * @param <T> 泛型类型
 * @author DT
 */
public interface Criteria<T> extends Segment, ReturnType, AggregationFunction<T>,
        ParamValuePlaceholderConverter, Serializable {

    /**
     * 设置自定义resultMap key
     * @param resultMap result key
     * @return 当前对象
     */
    Criteria<T> setResultMap( String resultMap );

    /**
     * 设置自定义返回值类型
     * @return 当前对象
     */
    Criteria<T> setResultType( Class<?> resultType );

    /**
     * 获取实体类
     * @return 实体类
     */
    Class<T> getEntity();

    /**
     * 获取表别名
     * @return 别名
     */
    String getAlias();

    /**
     * 设置别名
     * @param alias 别名
     * @return 当前对象
     */
    Criteria<T> setAlias( String alias );

    /**
     * 是否启用别名
     * @param enabled 是否启用
     * @return 当前对象
     */
    Criteria<T> enableAlias( boolean enabled );

    /**
     * 使用别名
     * @param alias 别名
     * @return 当前对象
     */
    Criteria<T> useAlias( String alias );

    /**
     * 是否启用别名
     * @return true: 是 | false: 否
     */
    boolean isEnableAlias();

    /**
     * 获取主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractCriteria<E> getMaster();

    /**
     * 获取顶级主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractCriteria<E> getRootMaster();

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias 别名
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> search( String alias );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> search( Class<E> entity );

    /**
     * 搜索{@link ForeignCriteria}对象
     * @param alias  别名
     * @param entity 实体类
     * @param <E>    泛型类型
     * @return {@link ForeignCriteria}对象
     */
    <E> ForeignCriteria<E> search( String alias, Class<E> entity );

    /**
     * 搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    Column searchColumn( Property<T, ?> property );

    /**
     * 根据属性搜索字段映射对象
     * @param property 属性
     * @return 字段映射对象
     */
    Column searchColumn( String property );

    /**
     * 获取查询字段片段
     * @return SQL字符串
     */
    String getQuerySegment();

    /**
     * 获取更新字段片段
     * @return SQL字符串
     */
    String getUpdateSegment();

    /**
     * AND连接
     * @return 当前对象
     */
    Criteria<T> and();

    /**
     * OR连接
     * @return 当前对象
     */
    Criteria<T> or();

    /**
     * 重置连接符
     * @return 当前对象
     */
    Criteria<T> reset();

    /**
     * 添加条件
     * @param conditionManager 条件管理器
     * @return 当前对象
     */
    Criteria<T> add( AbstractConditionManager<T> conditionManager );

    /**
     * 添加条件
     * @param conditionFunction 条件函数
     * @return 当前对象
     */
    Criteria<T> add( Function<AbstractConditionManager<T>, AbstractConditionManager<T>> conditionFunction );

    /**
     * 添加条件
     * @param condition 条件对象
     * @return 当前对象
     */
    Criteria<T> add( Criterion<?> condition );

    /**
     * 添加多个条件
     * @param conditions 条件对象数组
     * @return 当前对象
     */
    Criteria<T> add( Criterion<?>... conditions );

    /**
     * 添加多个条件
     * @param conditions 条件对象集合
     * @return 当前对象
     */
    Criteria<T> add( Collection<Criterion<?>> conditions );

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, null, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign, Criterion<?>... withClauses ) {
        return createForeign( entity, alias, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    default <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, Foreign foreign, Collection<Criterion<?>> withClauses ) {
        return createForeign( entity, alias, null, foreign, withClauses );
    }

    /**
     * 创建联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 联表条件对象
     */
    <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param master      主表属性
     * @param foreign     副表属性
     * @param joinMode    连接方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件数组
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Criterion<?>... withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param entity      实体类
     * @param alias       别名
     * @param reference   引用属性
     * @param foreign     联表方式
     * @param withClauses 条件集合
     * @return 当前对象
     */
    <E> Criteria<T> addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses );

    /**
     * 添加联表条件对象
     * @param foreignCriteria 联表条件对象
     * @return 当前对象
     */
    Criteria<T> addForeign( ForeignCriteria<?> foreignCriteria );

    /**
     * 添加联表条件对象
     * @param foreignCriteriaArray 联表条件对象数组
     * @return 当前对象
     */
    Criteria<T> addForeign( ForeignCriteria<?>... foreignCriteriaArray );

    /**
     * 添加联表条件对象
     * @param foreignCriteriaList 联表条件对象集合
     * @return 当前对象
     */
    Criteria<T> addForeign( Collection<ForeignCriteria<?>> foreignCriteriaList );

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> asc( String... properties );

    /**
     * ASC排序
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> asc( List<String> properties );

    /**
     * ASC排序
     * @param aggregations 聚合函数
     * @return 排序对象
     */
    Criteria<T> asc( Aggregation... aggregations );

    /**
     * ASC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Criteria<T> ascFunc( String... aliases );

    /**
     * ASC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Criteria<T> ascFunc( List<String> aliases );

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Criteria<T> aliasAsc( String alias, Property<E, ?>... properties );

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasAsc( String alias, String... properties );

    /**
     * ASC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasAsc( String alias, List<String> properties );

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> desc( String... properties );

    /**
     * DESC排序
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> desc( List<String> properties );

    /**
     * DESC排序
     * @param aggregations 聚合函数
     * @return 排序对象
     */
    Criteria<T> desc( Aggregation... aggregations );

    /**
     * DESC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Criteria<T> descFunc( String... aliases );

    /**
     * DESC排序
     * @param aliases 聚合函数别名
     * @return 当前对象
     */
    Criteria<T> descFunc( List<String> aliases );

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Criteria<T> aliasDesc( String alias, Property<E, ?>... properties );

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasDesc( String alias, String... properties );

    /**
     * DESC排序
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasDesc( String alias, List<String> properties );

    /**
     * 添加多个排序
     * @param order 排序对象
     * @return 当前对象
     */
    Criteria<T> addOrder( Order<?> order );

    /**
     * 添加多个排序
     * @param orders 排序对象数组
     * @return 当前对象
     */
    Criteria<T> addOrder( Order<?>... orders );

    /**
     * 添加多个排序
     * @param orders 排序对象集合
     * @return 当前对象
     */
    Criteria<T> addOrder( List<Order<?>> orders );

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> group( String... properties );

    /**
     * 分组
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> group( Collection<String> properties );

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    @SuppressWarnings( "unchecked" )
    <E> Criteria<T> aliasGroup( String alias, Property<E, ?>... properties );

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasGroup( String alias, String... properties );

    /**
     * 分组
     * @param alias      联表条件对象别名
     * @param properties 属性
     * @return 当前对象
     */
    Criteria<T> aliasGroup( String alias, Collection<String> properties );

    /**
     * 添加分组
     * @param group 分组对象
     * @return 当前对象
     */
    Criteria<T> addGroup( Group<?> group );

    /**
     * 添加分组
     * @param groups 分组对象
     * @return 当前对象
     */
    Criteria<T> addGroup( Group<?>... groups );

    /**
     * 添加分组
     * @param groups 分组对象
     * @return 当前对象
     */
    Criteria<T> addGroup( Collection<Group<?>> groups );

    /**
     * 获取聚合函数对象
     * @param alias 聚合函数别名
     * @return 聚合函数对象
     */
    Aggregation getFunction( String alias );

    /**
     * 检查是否存在条件
     * @return true: 是 , false: 否
     */
    boolean isHasCondition();
}
