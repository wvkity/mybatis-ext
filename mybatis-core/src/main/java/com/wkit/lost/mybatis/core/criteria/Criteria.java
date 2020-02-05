package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.executor.resultset.ReturnType;
import com.wkit.lost.mybatis.lambda.Property;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Criteria接口
 * @param <T> 泛型类型
 * @author wvkity
 */
public interface Criteria<T> extends Segment, ReturnType, AggregationFunction<T>,
        Search<T>, ParamValuePlaceholderConverter {

    /**
     * 获取WHERE条件SQL片段
     * @return SQL片段
     */
    String getWhereSqlSegment();

    /**
     * 设置自定义resultMap key
     * @param resultMap result key
     * @return 当前对象
     */
    Criteria<T> resultMap( String resultMap );

    /**
     * 设置自定义返回值类型
     * @return 当前对象
     */
    Criteria<T> resultType( Class<?> resultType );

    /**
     * 开启自动映射列别名(针对查询自动映射属性名)
     * @return 当前对象
     */
    Criteria<T> autoMappingColumnAlias();

    /**
     * 是否开启自动映射列别名(针对查询自动映射属性名)
     * @param enable 是否开启
     * @return 当前对象
     */
    Criteria<T> autoMappingColumnAlias( boolean enable );

    /**
     * 获取实体类
     * @return 实体类
     */
    Class<T> getEntityClass();

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
     * 使用默认别名
     * @return 当前对象
     */
    Criteria<T> useAlias();

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
     * 设置是否启用根据所有查询字段分组
     * <p>针对oracle、mysql5.7+等数据库</p>
     * @param enable 是否启用
     * @return 当前对象
     */
    Criteria<T> groupAll( boolean enable );

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long, long)}高</p>
     * @param start 开始位置
     * @param end   结束位置
     * @return 当前对象
     */
    Criteria<T> range( long start, long end );

    /**
     * 设置范围
     * <p>优先级比{@link #range(long, long)}低</p>
     * @param pageStart 开始页
     * @param pageEnd   结束页
     * @param size      每页数目
     * @return 当前对象
     */
    Criteria<T> range( long pageStart, long pageEnd, long size );

    /**
     * 是否执行范围查询
     * @return true: 是 false: 否
     */
    boolean isRange();

    /**
     * Range方式
     * @return {@link RangeMode}
     */
    RangeMode range();

    /**
     * 获取开始位置
     * @return 开始位置
     */
    long getStart();

    /**
     * 获取结束位置
     * @return 结束位置
     */
    long getEnd();

    /**
     * 获取开始页
     * @return 页数
     */
    long getPageStart();

    /**
     * 获取结束页
     * @return 页数
     */
    long getPageEnd();

    /**
     * 获取每页数目
     * @return 每页大小
     */
    long getPageSize();

    /**
     * 获取主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractQueryCriteria<E> getMaster();

    /**
     * 获取顶级主条件对象
     * @param <E> 泛型类型
     * @return 条件对象
     */
    <E> AbstractQueryCriteria<E> getRootMaster();

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
     * 添加子查询条件对象
     * @param subCriteria 子查询条件对象
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( SubCriteria<?> subCriteria );

    /**
     * 添加子查询条件对象
     * @param subCriteriaArray 子查询条件对象数组
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( SubCriteria<?>... subCriteriaArray );

    /**
     * 添加子查询条件对象
     * @param subCriteriaList 子查询条件对象集合
     * @return 当前对象
     */
    Criteria<T> addSubCriteria( Collection<SubCriteria<?>> subCriteriaList );

    /**
     * 获取聚合函数对象
     * @param alias 聚合函数别名
     * @return 聚合函数对象
     */
    Aggregation getFunction( String alias );

    /**
     * 检查是否存在条件(包含WHERE/GROUP BY/HAVING/ORDER BY)
     * @return true: 是 , false: 否
     */
    boolean isHasCondition();

    /**
     * 获取乐观锁更新值
     * @return 乐观锁值
     */
    Object getModifyVersionValue();

    /**
     * 获取乐观锁条件值
     * @return 乐观锁值
     */
    Object getConditionVersionValue();
}
