package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.core.condition.expression.NestedExpression;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.function.Aggregations;
import com.wkit.lost.mybatis.core.function.Comparator;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 条件容器
 * @param <T>       泛型类型
 * @param <R>       Lambda类型
 * @param <Context> 当前对象
 * @author DT
 */
@Accessors( chain = true )
@SuppressWarnings( value = { "unchecked", "serial" } )
public abstract class AbstractCriteriaWrapper<T, R, Context extends AbstractCriteriaWrapper<T, R, Context>> extends AbstractConditionExpressionWrapper<T, R>
        implements CriteriaWrapper<T, Context, R> {

    // region fields
    /**
     * 参数前置
     */
    protected static final String PARAMETER_KEY_PREFIX = "v_idx_";

    /**
     * 参数别名[@Param("xxx")]
     */
    protected static final String PARAMETER_ALIAS = "criteria";

    /**
     * 占位符
     */
    protected static final String PARAMETER_PLACEHOLDER = "{%s}";

    /**
     * 参数值占位符
     */
    protected static final String PARAMETER_VALUE_PLACEHOLDER = "%s.paramValueMappings.%s";

    /**
     * 当前对象
     */
    protected final Context context = ( Context ) this;

    /**
     * 主表查询对象
     */
    protected AbstractCriteria<?> master;

    /**
     * SQL片段
     */
    protected String sqlSegment;

    /**
     * 是否存在条件
     */
    protected boolean hasCondition;

    /**
     * 最后一次逻辑操作(AND | OR | NORMAL): 默认是NORMAL
     */
    protected volatile Logic lastLogic = Logic.NORMAL;

    /**
     * 返回值映射Map
     */
    @Getter
    protected String resultMap;

    /**
     * 返回值类型
     */
    @Getter
    protected Class<?> resultType;

    /**
     * 实体类型
     */
    protected Class<T> entity;

    /**
     * 表名
     */
    protected String tableName = "";

    /**
     * 别名
     */
    protected String alias = "";

    /**
     * 是否启用别名
     */
    protected boolean enableAlias;

    /**
     * 开始位置
     */
    @Getter
    protected long start;

    /**
     * 结束位置
     */
    @Getter
    protected long end;

    /**
     * 开始页
     */
    @Getter
    protected long pageStart;

    /**
     * 结束页
     */
    @Getter
    protected long pageEnd;

    /**
     * 每页数目
     */
    @Getter
    protected long pageSize;

    /**
     * 参数序号生成
     */
    protected AtomicInteger parameterSequence;

    /**
     * 参数值映射
     */
    protected Map<String, Object> paramValueMappings;

    /**
     * SQL片段管理器
     */
    protected SegmentManager segmentManager;

    /**
     * 参数别名
     */
    @Getter
    protected String parameterAlias = PARAMETER_ALIAS;

    /**
     * 联表查询副表引用属性
     */
    @Getter
    @Setter
    protected String reference;

    /**
     * 是否根据所有查询列group
     * <p>如oracle数据库</p>
     */
    @Getter
    @Setter
    protected boolean groupAll;

    /**
     * 查询是否包含聚合函数
     */
    protected boolean includeFunctionForQuery = true;

    /**
     * 仅仅只查询聚合函数
     */
    protected boolean onlyFunctionForQuery = false;

    /**
     * 查询列(属性)
     */
    protected Set<Column> queries = new LinkedHashSet<>();

    /**
     * 排除列(属性)
     */
    protected Set<String> excludes = new HashSet<>();

    /**
     * 联表条件对象集合
     */
    protected Set<ForeignCriteria<?>> foreignCriteriaSet = new LinkedHashSet<>( 8 );

    /**
     * 联表条件对象缓存
     */
    protected Map<String, ForeignCriteria<?>> foreignCriteriaCache = new LinkedHashMap<>( 8 );

    /**
     * 聚合函数
     */
    protected Set<Aggregation> aggregations = new LinkedHashSet<>( 8 );

    /**
     * 聚合函数缓存
     */
    protected Map<String, Aggregation> aggregationCache = new LinkedHashMap<>( 8 );

    // endregion

    // region foreign criteria

    @Override
    public <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses ) {
        return new ForeignCriteria<>( entity, alias, reference, ( AbstractCriteria<?> ) this, foreign, this.parameterSequence, this.paramValueMappings, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, master, foreign, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), ArrayUtil.toList( withClauses ) );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference, master, foreign, JoinMode.INNER, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, master, foreign, joinMode, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, String master, String foreign, JoinMode joinMode, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, reference, new Foreign( master, foreign, joinMode ), withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, Foreign foreign, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, null, foreign, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Criterion<?>... withClauses ) {
        return addForeign( entity, alias, reference, foreign, ArrayUtil.toList( withClauses ) );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, Foreign foreign, Collection<Criterion<?>> withClauses ) {
        return addForeign( entity, alias, null, foreign, withClauses );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign, Collection<Criterion<?>> withClauses ) {
        return this.addForeign( createForeign( entity, alias, reference, foreign, withClauses ) );
    }

    @Override
    public Context addForeign( ForeignCriteria<?> foreignCriteria ) {
        if ( foreignCriteria != null ) {
            AbstractCriteria<?> root = getRootMaster();
            foreignCriteria.enableAlias( true );
            root.foreignCriteriaSet.add( foreignCriteria );
            root.foreignCriteriaCache.put( foreignCriteria.getAlias(), foreignCriteria );
            if ( !root.isEnableAlias() ) {
                root.enableAlias( true );
            }
        }
        return this.context;
    }

    @Override
    public Context addForeign( ForeignCriteria<?>... foreignCriteriaArray ) {
        return addForeign( ArrayUtil.toList( foreignCriteriaArray ) );
    }

    @Override
    public Context addForeign( Collection<ForeignCriteria<?>> foreignCriteriaList ) {
        if ( CollectionUtil.hasElement( foreignCriteriaList ) ) {
            foreignCriteriaList.forEach( this::addForeign );
        }
        return this.context;
    }

    // endregion

    // region search foreign criteria methods

    @Override
    public <E> ForeignCriteria<E> search( String alias ) {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return ( ForeignCriteria<E> ) Optional.ofNullable( foreignCriteriaCache.get( alias ) ).orElse( null );
        }
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> search( Class<E> entity ) {
        if ( entity != null && CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            ForeignCriteria<?> criteria = foreignCriteriaSet.stream()
                    .filter( subCriteria -> entity.equals( subCriteria.getEntity() ) ).findFirst().orElse( null );
            return ( ForeignCriteria<E> ) Optional.ofNullable( criteria ).orElse( null );
        }
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> search( String alias, Class<E> entity ) {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            if ( StringUtil.hasText( alias ) || entity != null ) {
                if ( StringUtil.hasText( alias ) && entity != null ) {
                    ForeignCriteria<E> criteria = search( alias );
                    if ( criteria != null && entity.equals( criteria.getEntity() ) ) {
                        return criteria;
                    }
                } else if ( StringUtil.hasText( alias ) ) {
                    return search( alias );
                } else {
                    return search( entity );
                }
            }
        }
        return null;
    }

    // endregion

    // region conditions

    @Override
    public Context and( Function<Context, Context> function ) {
        return doIt( function, true );
    }

    @Override
    public Context or( Function<Context, Context> function ) {
        return doIt( function, false );
    }

    protected Context doIt( Function<Context, Context> function, boolean isAnd ) {
        // 创建实例
        Context instance = instance( this.parameterSequence, this.paramValueMappings, new SegmentManager() );
        // 设置当前查询对象别名
        instance.useAlias( this.getAlias() );
        Context newInstance = function.apply( instance );
        // 获取条件
        List<Segment> segments = newInstance.segmentManager.getNormals().getSegments();
        // 添加到当前对象
        if ( isAnd ) {
            Optional.ofNullable( segmentConvertToCondition( segments ) ).ifPresent( this::nested );
        } else {
            Optional.ofNullable( segmentConvertToCondition( segments ) ).ifPresent( this::orNested );
        }
        return context;
    }

    @Override
    public Context idEq( Object value ) {
        return add( Restrictions.idEq( this, value ) );
    }

    @Override
    public Context orIdEq( Object value ) {
        return add( Restrictions.idEq( this, value, Logic.OR ) );
    }

    @Override
    public Context eq( String property, Object value ) {
        return add( Restrictions.eq( this, property, value ) );
    }

    @Override
    public Context orEq( String property, Object value ) {
        return add( Restrictions.eq( this, property, value, Logic.OR ) );
    }

    @Override
    public Context ne( String property, Object value ) {
        return add( Restrictions.ne( this, property, value ) );
    }

    @Override
    public Context orNe( String property, Object value ) {
        return add( Restrictions.ne( this, property, value, Logic.OR ) );
    }

    @Override
    public Context lt( String property, Object value ) {
        return add( Restrictions.lt( this, property, value ) );
    }

    @Override
    public Context orLt( String property, Object value ) {
        return add( Restrictions.lt( this, property, value, Logic.OR ) );
    }

    @Override
    public Context le( String property, Object value ) {
        return add( Restrictions.le( this, property, value ) );
    }

    @Override
    public Context orLe( String property, Object value ) {
        return add( Restrictions.le( this, property, value, Logic.OR ) );
    }

    @Override
    public Context gt( String property, Object value ) {
        return add( Restrictions.gt( this, property, value ) );
    }

    @Override
    public Context orGt( String property, Object value ) {
        return add( Restrictions.gt( this, property, value, Logic.OR ) );
    }

    @Override
    public Context ge( String property, Object value ) {
        return add( Restrictions.ge( this, property, value ) );
    }

    @Override
    public Context orGe( String property, Object value ) {
        return add( Restrictions.ge( this, property, value, Logic.OR ) );
    }

    @Override
    public Context in( String property, Collection<Object> values ) {
        return add( Restrictions.in( this, property, values ) );
    }

    @Override
    public Context orIn( String property, Collection<Object> values ) {
        return add( Restrictions.in( this, property, Logic.OR, values ) );
    }

    @Override
    public Context notIn( String property, Collection<Object> values ) {
        return add( Restrictions.notIn( this, property, values ) );
    }

    @Override
    public Context orNotIn( String property, Collection<Object> values ) {
        return add( Restrictions.notIn( this, property, Logic.OR, values ) );
    }

    @Override
    public Context isNull( String property ) {
        return add( Restrictions.isNull( this, property ) );
    }

    @Override
    public Context orIsNull( String property ) {
        return add( Restrictions.isNull( this, property, Logic.OR ) );
    }

    @Override
    public Context notNull( String property ) {
        return add( Restrictions.notNull( this, property ) );
    }

    @Override
    public Context orNotNull( String property ) {
        return add( Restrictions.notNull( this, property, Logic.OR ) );
    }

    @Override
    public Context like( String property, String value, MatchMode matchMode, Character escape ) {
        return add( Restrictions.like( this, property, value, matchMode, escape ) );
    }

    @Override
    public Context orLike( String property, String value, MatchMode matchMode, Character escape ) {
        return add( Restrictions.like( this, property, value, matchMode, escape, Logic.OR ) );
    }

    @Override
    public Context between( String property, Object begin, Object end ) {
        return add( Restrictions.between( this, property, begin, end ) );
    }

    @Override
    public Context orBetween( String property, Object begin, Object end ) {
        return add( Restrictions.between( this, property, begin, end, Logic.OR ) );
    }

    @Override
    public Context notBetween( String property, Object begin, Object end ) {
        return add( Restrictions.notBetween( this, property, begin, end ) );
    }

    @Override
    public Context orNotBetween( String property, Object begin, Object end ) {
        return add( Restrictions.notBetween( this, property, begin, end, Logic.OR ) );
    }

    @Override
    public Context template( String template, String property, Object value ) {
        return add( Restrictions.template( this, template, property, value ) );
    }

    @Override
    public Context template( String template, String property, Collection<Object> values ) {
        return add( Restrictions.template( this, template, property, values ) );
    }

    @Override
    public Context orTemplate( String template, String property, Object value ) {
        return add( Restrictions.template( this, template, Logic.OR, property, value ) );
    }

    @Override
    public Context orTemplate( String template, String property, Collection<Object> values ) {
        return add( Restrictions.template( this, template, Logic.OR, property, values ) );
    }

    @Override
    public Context exactTemplate( String template, String property, Object value ) {
        return add( Restrictions.template( this, template, Logic.NORMAL, property, value ) );
    }

    @Override
    public Context exactTemplate( String template, String property, Collection<Object> values ) {
        return add( Restrictions.template( this, template, Logic.NORMAL, property, values ) );
    }

    @Override
    public Context nativeSql( String sql ) {
        return add( Restrictions.nativeSql( sql ) );
    }

    @Override
    public Context nested( Collection<Criterion<?>> conditions ) {
        return this.nested( this, conditions );
    }

    @Override
    public Context nested( Criteria criteria, Collection<Criterion<?>> conditions ) {
        NestedExpression expression = Restrictions.nested( criteria, conditions );
        return add( expression );
    }

    @Override
    public Context orNested( Collection<Criterion<?>> conditions ) {
        return this.orNested( this, conditions );
    }

    @Override
    public Context orNested( Criteria criteria, Collection<Criterion<?>> conditions ) {
        NestedExpression expression = Restrictions.nested( criteria, Logic.OR, conditions );
        return add( expression );
    }

    @Override
    public Context add( AbstractConditionManager<T> conditionManager ) {
        if ( conditionManager != null && conditionManager.hasCondition() ) {
            // 添加到容器中
            this.add( conditionManager.all() );
            // 清空条件管理器中的条件
            conditionManager.clear();
        }
        return this.context;
    }

    @Override
    public Context add( Function<AbstractConditionManager<T>, AbstractConditionManager<T>> conditionFunction ) {
        AbstractConditionManager<T> instance = conditionFunction.apply( this.conditionManager );
        return this.add( instance );
    }

    @Override
    public Context add( Criterion<?> condition ) {
        if ( condition != null ) {
            if ( condition.getCriteria() == null ) {
                condition.setCriteria( this );
            }
            if ( this.lastLogic != Logic.NORMAL ) {
                condition.setLogic( lastLogic );
            }
            this.segmentManager.conditions( condition );
        }
        return this.context;
    }

    @Override
    public Context add( Criterion<?>... conditions ) {
        return add( Arrays.asList( conditions ) );
    }

    @Override
    public Context add( Collection<Criterion<?>> conditions ) {
        if ( CollectionUtil.hasElement( conditions ) ) {
            List<Criterion<?>> list = conditions.stream()
                    .filter( Objects::nonNull )
                    .peek( condition -> {
                        if ( condition.getCriteria() == null ) {
                            condition.setCriteria( this );
                        }
                    } )
                    .collect( Collectors.toList() );
            if ( lastLogic != Logic.NORMAL ) {
                list.forEach( criterion -> criterion.setLogic( lastLogic ) );
            }
            this.segmentManager.conditions( list );
        }
        return this.context;
    }

    // endregion

    // region query fields

    @Override
    public Context query( String property ) {
        if ( StringUtil.hasText( property ) ) {
            this.queries.add( searchColumn( property ) );
        }
        return this.context;
    }

    @Override
    public Context query( Collection<String> properties ) {
        if ( CollectionUtil.hasElement( properties ) ) {
            this.queries.addAll( properties.stream().filter( StringUtil::hasText ).map( this::searchColumn ).collect( Collectors.toList() ) );
        }
        return this.context;
    }

    @Override
    public Context exclude( String property ) {
        if ( StringUtil.hasText( property ) ) {
            this.excludes.add( property );
        }
        return this.context;
    }

    @Override
    public Context exclude( Collection<String> properties ) {
        if ( CollectionUtil.hasElement( properties ) ) {
            this.excludes.addAll( properties.stream().filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        }
        return this.context;
    }

    // endregion

    // region order by

    @Override
    public Context asc( String... properties ) {
        return addOrder( Order.asc( this, properties ) );
    }

    @Override
    public Context asc( List<String> properties ) {
        return addOrder( Order.asc( this, properties ) );
    }

    @Override
    public Context asc( R... properties ) {
        return addOrder( Order.asc( this, lambdaToProperty( properties ) ) );
    }

    @Override
    public Context asc( Aggregation... aggregations ) {
        return addOrder( Order.asc( aggregations ) );
    }

    @Override
    public Context ascFunc( String... aliases ) {
        return ascFunc( ArrayUtil.toList( aliases ) );
    }

    @Override
    public Context ascFunc( List<String> aliases ) {
        return addOrder( Order.ascFunc( this, aliases ) );
    }

    @Override
    public <E> Context aliasAsc( String alias, Property<E, ?>... properties ) {
        return addOrder( Order.asc( this.search( alias ), properties ) );
    }

    @Override
    public Context aliasAsc( String alias, String... properties ) {
        return addOrder( Order.asc( this.search( alias ), properties ) );
    }

    @Override
    public Context aliasAsc( String alias, List<String> properties ) {
        return addOrder( Order.asc( this.search( alias ), properties ) );
    }

    @Override
    public Context desc( String... properties ) {
        return addOrder( Order.desc( this, properties ) );
    }

    @Override
    public Context desc( R... properties ) {
        return addOrder( Order.desc( this, lambdaToProperty( properties ) ) );
    }

    @Override
    public Context desc( List<String> properties ) {
        return addOrder( Order.desc( this, properties ) );
    }

    @Override
    public Context desc( Aggregation... aggregations ) {
        return addOrder( Order.desc( aggregations ) );
    }

    @Override
    public Context descFunc( String... aliases ) {
        return descFunc( ArrayUtil.toList( aliases ) );
    }

    @Override
    public Context descFunc( List<String> aliases ) {
        return addOrder( Order.descFunc( this, aliases ) );
    }

    @Override
    public <E> Context aliasDesc( String alias, Property<E, ?>... properties ) {
        return addOrder( Order.desc( search( alias ), properties ) );
    }

    @Override
    public Context aliasDesc( String alias, String... properties ) {
        return addOrder( Order.desc( search( alias ), properties ) );
    }

    @Override
    public Context aliasDesc( String alias, List<String> properties ) {
        return addOrder( Order.desc( search( alias ), properties ) );
    }

    @Override
    public Context addOrder( Order<?> order ) {
        Optional.ofNullable( order ).ifPresent( value -> getRootMaster().segmentManager.orders( value ) );
        return this.context;
    }

    @Override
    public Context addOrder( Order<?>... orders ) {
        getRootMaster().segmentManager.orders( orders );
        return this.context;
    }

    @Override
    public Context addOrder( List<Order<?>> orders ) {
        getRootMaster().segmentManager.orders( orders );
        return this.context;
    }

    // endregion

    // region group by

    @Override
    public Context group( String... properties ) {
        return addGroup( Group.group( this, properties ) );
    }

    @Override
    public Context group( R... properties ) {
        return addGroup( Group.group( this, lambdaToProperty( properties ) ) );
    }

    @Override
    public Context group( Collection<String> properties ) {
        return addGroup( Group.group( this, properties ) );
    }

    @Override
    public <E> Context aliasGroup( String alias, Property<E, ?>... properties ) {
        return addGroup( Group.group( search( alias ), properties ) );
    }

    @Override
    public Context aliasGroup( String alias, String... properties ) {
        return aliasGroup( alias, ArrayUtil.toList( properties ) );
    }

    @Override
    public Context aliasGroup( String alias, Collection<String> properties ) {
        return addGroup( Group.group( search( alias ), properties ) );
    }

    @Override
    public Context addGroup( Group<?> group ) {
        if ( group != null ) {
            getRootMaster().segmentManager.groups( group );
        }
        return this.context;
    }

    @Override
    public Context addGroup( Group<?>... groups ) {
        getRootMaster().segmentManager.groups( groups );
        return this.context;
    }

    @Override
    public Context addGroup( Collection<Group<?>> groups ) {
        getRootMaster().segmentManager.groups( groups );
        return this.context;
    }

    // endregion

    // region aggregate functions

    // region count function

    @Override
    public Context count() {
        return count( "*" );
    }

    @Override
    public Context count( String property ) {
        return addFunction( Aggregations.count( this, property ) );
    }

    @Override
    public Context count( String alias, String property ) {
        return addFunction( Aggregations.count( this, alias, property ) );
    }

    @Override
    public Context count( String property, boolean distinct ) {
        return addFunction( Aggregations.count( this, property, distinct ) );
    }

    @Override
    public Context count( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.count( this, alias, property, distinct ) );
    }

    @Override
    public Context count( String alias, String property, Object... values ) {
        return count( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context count( String alias, Comparator comparator, String property, Object... values ) {
        return count( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context count( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return count( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context count( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return count( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context count( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregations.count( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region sum function

    @Override
    public Context sum( String property ) {
        return addFunction( Aggregations.sum( this, property ) );
    }

    @Override
    public Context sum( String alias, String property ) {
        return addFunction( Aggregations.sum( this, alias, property ) );
    }

    @Override
    public Context sum( String property, boolean distinct ) {
        return addFunction( Aggregations.sum( this, property, distinct ) );
    }

    @Override
    public Context sum( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.sum( this, alias, property, distinct ) );
    }

    @Override
    public Context sum( String alias, String property, Object... values ) {
        return sum( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Comparator comparator, String property, Object... values ) {
        return sum( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return sum( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context sum( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return sum( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregations.sum( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region avg function

    @Override
    public Context avg( String property ) {
        return addFunction( Aggregations.avg( this, property ) );
    }

    @Override
    public Context avg( String alias, String property ) {
        return addFunction( Aggregations.avg( this, alias, property ) );
    }

    @Override
    public Context avg( String property, boolean distinct ) {
        return addFunction( Aggregations.avg( this, property, distinct ) );
    }

    @Override
    public Context avg( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.avg( this, alias, property, distinct ) );
    }

    @Override
    public Context avg( String alias, String property, Object... values ) {
        return avg( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Comparator comparator, String property, Object... values ) {
        return avg( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return avg( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context avg( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return avg( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregations.avg( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region max function

    @Override
    public Context max( String property ) {
        return addFunction( Aggregations.max( this, property ) );
    }

    @Override
    public Context max( String alias, String property ) {
        return addFunction( Aggregations.max( this, alias, property ) );
    }

    @Override
    public Context max( String property, boolean distinct ) {
        return addFunction( Aggregations.max( this, property, distinct ) );
    }

    @Override
    public Context max( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.max( this, alias, property, distinct ) );
    }

    @Override
    public Context max( String alias, String property, Object... values ) {
        return max( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context max( String alias, Comparator comparator, String property, Object... values ) {
        return max( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context max( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return max( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context max( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return max( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context max( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregations.max( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region min function

    @Override
    public Context min( String property ) {
        return addFunction( Aggregations.min( this, property ) );
    }

    @Override
    public Context min( String alias, String property ) {
        return addFunction( Aggregations.min( this, alias, property ) );
    }

    @Override
    public Context min( String property, boolean distinct ) {
        return addFunction( Aggregations.min( this, property, distinct ) );
    }

    @Override
    public Context min( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.min( this, alias, property, distinct ) );
    }

    @Override
    public Context min( String alias, String property, Object... values ) {
        return min( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context min( String alias, Comparator comparator, String property, Object... values ) {
        return min( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context min( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return min( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context min( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return min( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context min( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregations.min( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region aggregate function assist methods
    @Override
    public Context includeFunction( boolean include ) {
        this.includeFunctionForQuery = include;
        return this.context;
    }

    @Override
    public boolean isInclude() {
        return this.includeFunctionForQuery;
    }

    @Override
    public Context onlyFunction( boolean only ) {
        this.onlyFunctionForQuery = only;
        return this.context;
    }

    @Override
    public boolean isOnly() {
        return this.onlyFunctionForQuery;
    }

    @Override
    public Context addFunction( Aggregation... functions ) {
        return addFunction( ArrayUtil.toList( functions ) );
    }

    @Override
    public Context addFunction( Collection<Aggregation> functions ) {
        if ( CollectionUtil.hasElement( functions ) ) {
            List<Aggregation> aggregationList = functions.stream().filter( Objects::nonNull ).collect( Collectors.toList() );
            getRootMaster().aggregations.addAll( aggregationList );
            // 存在别名的直接缓存起来
            if ( !aggregationList.isEmpty() ) {
                Map<String, Aggregation> map = aggregationList.stream().filter( function -> StringUtil.hasText( function.getAlias() ) )
                        .collect( Collectors.toMap( Aggregation::getAlias, Function.identity() ) );
                if ( !map.isEmpty() ) {
                    getRootMaster().aggregationCache.putAll( map );
                }
            }
        }
        return this.context;
    }

    @Override
    public Context having( String... aliases ) {
        return having( ArrayUtil.toList( aliases ) );
    }

    @Override
    public Context having( Collection<String> aliases ) {
        if ( CollectionUtil.hasElement( aliases ) ) {
            addHaving( aliases.stream().filter( Objects::nonNull ).map( aggregationCache::get ).collect( Collectors.toList() ) );
        }
        return this.context;
    }

    @Override
    public Context having( Aggregation... functions ) {
        getRootMaster().segmentManager.having( functions );
        return this.context;
    }

    /**
     * 添加分组筛选条件
     * @param functions 聚合函数
     */
    private void addHaving( Collection<Aggregation> functions ) {
        getRootMaster().segmentManager.having( functions );
    }

    @Override
    public Aggregation getFunction( String alias ) {
        return this.aggregationCache.get( alias );
    }
// endregion

    // endregion

    // region auxiliary methods

    @Override
    public Context limit( long start, long end ) {
        this.start = start;
        this.end = end;
        return this.context;
    }

    @Override
    public Context limit( long pageStart, long pageEnd, long size ) {
        this.pageStart = pageStart;
        this.pageEnd = pageEnd;
        this.pageSize = size;
        return this.context;
    }

    @Override
    public boolean isLimit() {
        return ( start >= 0 && end > 0 ) || ( pageStart > 0 && pageEnd > 0 );
    }

    @Override
    public LimitMode limitMode() {
        if ( start >= 0 && end > 0 ) {
            return LimitMode.IMMEDIATE;
        }
        if ( pageStart > 0 && pageEnd > 0 ) {
            return LimitMode.PAGEABLE;
        }
        return LimitMode.NORMAL;
    }

    @Override
    public Context and() {
        this.lastLogic = Logic.AND;
        return context;
    }

    @Override
    public Context or() {
        this.lastLogic = Logic.OR;
        return context;
    }

    @Override
    public Context reset() {
        this.lastLogic = Logic.NORMAL;
        return this.context;
    }

    @Override
    public Context resultMap( String resultMap ) {
        this.resultMap = resultMap;
        return this.context;
    }

    @Override
    public Context resultType( Class<?> resultType ) {
        this.resultType = resultType;
        return this.context;
    }

    /**
     * SQL片段对象转条件对象
     * @param segments SQL片段对象集合
     * @return 条件对象集合
     */
    private Collection<Criterion<?>> segmentConvertToCondition( Collection<Segment> segments ) {
        if ( CollectionUtil.hasElement( segments ) ) {
            List<Criterion<?>> list = new ArrayList<>( segments.size() );
            for ( Segment segment : segments ) {
                // 判断是否为条件对象
                if ( segment instanceof Criterion ) {
                    list.add( ( Criterion ) segment );
                }
            }
            return list;
        }
        return null;
    }

    @Override
    public ArrayList<String> placeholders( String template, Collection<Object> values ) {
        if ( StringUtil.hasText( template ) && CollectionUtil.hasElement( values ) ) {
            return values.stream()
                    .filter( Objects::nonNull )
                    .map( value -> {
                        String paramName = PARAMETER_KEY_PREFIX + parameterSequence.incrementAndGet();
                        this.paramValueMappings.put( paramName, value );
                        return template.replace( String.format( PARAMETER_PLACEHOLDER, 0 ), String.format( PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName ) );
                    } ).collect( Collectors.toCollection( ArrayList::new ) );
        }
        return null;
    }

    @Override
    public String placeholder( boolean need, String template, Object... values ) {
        if ( !need || StringUtil.isBlank( template ) ) {
            return null;
        }
        if ( !ArrayUtil.isEmpty( values ) ) {
            int size = values.length;
            for ( int i = 0; i < size; i++ ) {
                String paramName = PARAMETER_KEY_PREFIX + parameterSequence.incrementAndGet();
                template = template.replace( String.format( PARAMETER_PLACEHOLDER, i ), String.format( PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName ) );
                this.paramValueMappings.put( paramName, values[ i ] );
            }
        }
        return template;
    }

    /**
     * 初始化
     */
    protected void init() {
        this.parameterSequence = new AtomicInteger( 0 );
        this.paramValueMappings = new HashMap<>( 16 );
        this.segmentManager = new SegmentManager();
        //this.conditionManager = new ConditionManager<>( this );
    }

    // endregion

    // region get or set methods
    @Override
    public Class<T> getEntity() {
        return this.entity;
    }

    @Override
    public <E> AbstractCriteria<E> getMaster() {
        return this.master != null ? ( AbstractCriteria<E> ) this.master : null;
    }

    @Override
    public <E> AbstractCriteria<E> getRootMaster() {
        AbstractCriteria<E> rootMaster;
        AbstractCriteria<E> root = ( AbstractCriteria<E> ) this;
        while ( ( rootMaster = root.getMaster() ) != null ) {
            root = rootMaster;
        }
        return root;
    }

    @Override
    public String getSqlSegment() {
        return this.segmentManager.getSqlSegment( isGroupAll() ? getGroupSegment() : null );
    }

    /**
     * 获取表名
     * @return 表名字符串
     */
    public String getTableName() {
        if ( this.enableAlias ) {
            this.tableName = EntityHandler.getTable( this.entity ).getName() + " " + getAlias();
        } else {
            this.tableName = EntityHandler.getTable( this.entity ).getName();
        }
        return this.tableName;
    }

    @Override
    public Context setAlias( String alias ) {
        if ( StringUtil.hasText( alias ) ) {
            this.alias = alias;
        } else {
            this.alias = "";
            this.enableAlias( false );
        }
        return this.context;
    }

    @Override
    public Context enableAlias( boolean enabled ) {
        this.enableAlias = enabled;
        return this.context;
    }

    @Override
    public Context useAlias( String alias ) {
        return this.enableAlias( true ).setAlias( alias );
    }

    @Override
    public String getAlias() {
        if ( StringUtil.isBlank( this.alias ) ) {
            this.alias = AliasCache.getAlias( this.entity );
        }
        return this.alias;
    }

    @Override
    public boolean isEnableAlias() {
        return this.enableAlias;
    }

    @Override
    public boolean isHasCondition() {
        return this.segmentManager.hasCondition();
    }

    // endregion

    // region abstract methods

    /**
     * 获取排序SQL片段
     * @return SQL片段
     */
    protected abstract String getGroupSegment();

    /**
     * 创建实例对象
     * @param parameterSequence      参数序号生成
     * @param parameterValueMappings 参数值映射
     * @param segmentManager         SQL片段管理器
     * @return {@code this}
     */
    protected abstract Context instance( AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings, SegmentManager segmentManager );

    // endregion

}
