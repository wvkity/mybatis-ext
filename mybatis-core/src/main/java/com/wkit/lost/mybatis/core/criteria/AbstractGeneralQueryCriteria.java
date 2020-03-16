package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.AbstractFunction;
import com.wkit.lost.mybatis.core.aggregate.AggregateType;
import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.aggregate.Aggregator;
import com.wkit.lost.mybatis.core.aggregate.Comparator;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.core.wrapper.QueryManager;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.CaseFormat;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings( { "serial", "unchecked" } )
public abstract class AbstractGeneralQueryCriteria<T, Context extends AbstractGeneralQueryCriteria<T, Context>>
        extends AbstractCriteriaWrapper<T> implements QueryCorrelation<T, Context, Property<T, ?>>,
        Aggregate<T, Context, Property<T, ?>> {

    // region fields

    protected final Context context = ( Context ) this;

    /**
     * 返回值映射Map
     */
    protected String resultMap;

    /**
     * 返回值类型
     */
    protected Class<?> resultType;


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
     * 查询管理器
     */
    @Getter
    protected QueryManager queryManager;

    /**
     * 联表对象集合
     */
    protected Set<ForeignCriteria<?>> foreignCriteriaSet = Collections.synchronizedSet( new LinkedHashSet<>( 8 ) );

    /**
     * 联表对象缓存
     */
    protected Map<String, ForeignCriteria<?>> foreignCriteriaCache = new ConcurrentHashMap<>( 8 );

    // endregion

    @Override
    protected void init() {
        super.init();
        this.queryManager = new QueryManager( this );
    }

    /**
     * 检查是否存在查询列对象
     * @return true: 是, false: 否
     */
    public boolean hasQueries() {
        return this.queryManager.hasQueries();
    }


    // region aggregate functions

    // region count function

    @Override
    public Context count( String alias, boolean distinct, Comparator comparator, Logic logic, String property, Object... values ) {
        return addFunction( Aggregator.count( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region sum function

    @Override
    public Context sum( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.sum( this, alias, distinct, comparator, logic, property, values ) );
    }

    @Override
    public Context sum( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.sum( this, alias, scale, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region avg function

    @Override
    public Context avg( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.avg( this, alias, distinct, comparator, logic, property, values ) );
    }

    @Override
    public Context avg( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.avg( this, alias, scale, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region max function

    @Override
    public Context max( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.max( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region min function

    @Override
    public Context min( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregator.min( this, alias, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region quickly create aggregate functions

    @Override
    public Context functions( String property ) {
        return functions( property, -1 );
    }

    @Override
    public Context functions( String property, int scale ) {
        return functions( property, false, null, scale );
    }

    @Override
    public Context functions( String property, boolean distinct ) {
        return functions( property, distinct, -1 );
    }

    @Override
    public Context functions( String property, boolean distinct, int scale ) {
        return functions( property, distinct, null, scale );
    }

    @Override
    public Context functions( String property, String aliasPrefix ) {
        return functions( property, aliasPrefix, -1 );
    }

    @Override
    public Context functions( String property, String aliasPrefix, int scale ) {
        return functions( property, false, aliasPrefix, scale );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix ) {
        return functions( property, distinct, aliasPrefix, -1 );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix, int scale ) {
        count( getFuncAlias( aliasPrefix, AggregateType.COUNT ), property, distinct );
        sum( getFuncAlias( aliasPrefix, AggregateType.SUM ), property, scale, distinct );
        avg( getFuncAlias( aliasPrefix, AggregateType.AVG ), property, scale, distinct );
        max( getFuncAlias( aliasPrefix, AggregateType.MAX ), property, distinct );
        min( getFuncAlias( aliasPrefix, AggregateType.MIN ), property, distinct );
        return this.context;
    }

    @Override
    public Context functions( String property, AggregateType... functions ) {
        return functions( property, false, null, functions );
    }

    @Override
    public Context functions( String property, int scale, AggregateType... functions ) {
        return functions( property, false, null, scale, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, AggregateType... functions ) {
        return functions( property, distinct, null, -1, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, int scale, AggregateType... functions ) {
        return functions( property, distinct, null, scale, functions );
    }

    @Override
    public Context functions( String property, String aliasPrefix, AggregateType... functions ) {
        return functions( property, false, aliasPrefix, -1, functions );
    }

    @Override
    public Context functions( String property, String aliasPrefix, int scale, AggregateType... functions ) {
        return functions( property, false, aliasPrefix, scale, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix, AggregateType... functions ) {
        return functions( property, distinct, aliasPrefix, -1, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix, int scale,
                              AggregateType... functions ) {
        if ( !ArrayUtil.isEmpty( functions ) ) {
            for ( AggregateType function : functions ) {
                addFunction( createFunction( getFuncAlias( aliasPrefix, function ), property, scale, distinct, function ) );
            }
        }
        return this.context;
    }

    private String getFuncAlias( String aliasPrefix, AggregateType function ) {
        return StringUtil.hasText( aliasPrefix ) ?
                ( CaseFormat.UPPER_UNDERSCORE.to( CaseFormat.LOWER_CAMEL,
                        ( CaseFormat.LOWER_CAMEL.to( CaseFormat.UPPER_UNDERSCORE, aliasPrefix ) + "_" + function.getSqlSegment() ) ) )
                : null;
    }

    protected AbstractFunction createFunction( String alias, String property, int scale, boolean distinct, AggregateType function ) {
        switch ( function ) {
            case COUNT:
                return Aggregator.count( this, alias, property, distinct );
            case SUM:
                return Aggregator.sum( this, alias, property, scale, distinct );
            case AVG:
                return Aggregator.avg( this, alias, property, scale, distinct );
            case MAX:
                return Aggregator.max( this, alias, property, distinct );
            case MIN:
                return Aggregator.min( this, alias, property, distinct );
        }
        return null;
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
    public Context addFunction( Aggregation function ) {
        if ( function != null ) {
            getRootMaster().aggregations.add( function );
            if ( StringUtil.hasText( function.getAlias() ) ) {
                getRootMaster().aggregationCache.put( function.getAlias(), function );
            }
        }
        return this.context;
    }

    @Override
    public Context addFunction( Collection<Aggregation> functions ) {
        if ( CollectionUtil.hasElement( functions ) ) {
            List<Aggregation> aggregationList = functions.stream().filter( Objects::nonNull ).collect( Collectors.toList() );
            getRootMaster().aggregations.addAll( aggregationList );
            // 存在别名的直接缓存起来
            if ( !aggregationList.isEmpty() ) {
                Map<String, Aggregation> map = aggregationList.stream()
                        .filter( function -> StringUtil.hasText( function.getAlias() ) )
                        .collect( Collectors.toMap( Aggregation::getAlias, Function.identity() ) );
                if ( !map.isEmpty() ) {
                    getRootMaster().aggregationCache.putAll( map );
                }
            }
        }
        return this.context;
    }

    @Override
    public Context having( Collection<String> aliases ) {
        if ( CollectionUtil.hasElement( aliases ) ) {
            addHaving( aliases.stream().filter( Objects::nonNull )
                    .map( aggregationCache::get ).collect( Collectors.toList() ) );
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

    // region foreign criteria builder

    @Override
    public <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                                 Collection<Criterion<?>> withClauses ) {
        return new ForeignCriteria<>( entity, alias, reference, ( AbstractQueryCriteria<?> ) this,
                foreign, withClauses );
    }

    @Override
    public <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                                 Function<ForeignCriteria<E>, AbstractCriteriaWrapper<E>> function ) {
        ForeignCriteria<E> foreignCriteria = createForeign( entity, alias, reference, foreign );
        function.apply( foreignCriteria );
        return foreignCriteria;
    }

    @Override
    public <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                                    Collection<Criterion<?>> withClauses ) {
        return new ForeignSubCriteria<>( subCriteria, reference, ( AbstractQueryCriteria<?> ) this,
                foreign, withClauses );
    }

    @Override
    public <E> ForeignSubCriteria<E> createForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                                    Function<ForeignCriteria<E>, AbstractCriteriaWrapper<E>> function ) {
        ForeignSubCriteria<E> foreignSubCriteria = createForeign( subCriteria, reference, foreign );
        function.apply( foreignSubCriteria );
        return foreignSubCriteria;
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                   Collection<Criterion<?>> withClauses ) {
        return this.addForeign( createForeign( entity, alias, reference, foreign, withClauses ) );
    }

    @Override
    public <E> Context addForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                   Function<ForeignCriteria<E>, AbstractCriteriaWrapper<E>> function ) {
        return addForeign( createForeign( entity, alias, reference, foreign, function ) );
    }

    @Override
    public <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                   Collection<Criterion<?>> withClauses ) {
        return addForeign( createForeign( subCriteria, reference, foreign, withClauses ) );
    }

    @Override
    public <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                   Function<ForeignCriteria<E>, AbstractCriteriaWrapper<E>> function ) {
        return addForeign( createForeign( subCriteria, reference, foreign, function ) );
    }

    protected Context addForeign( ForeignCriteria<?> foreignCriteria ) {
        if ( foreignCriteria != null ) {
            if ( Ascii.isNullOrEmpty( foreignCriteria.getAlias() ) ) {
                foreignCriteria.useAlias( "" );
            }
            foreignCriteria.enableAlias( true );
            foreignCriteriaSet.add( foreignCriteria );
            foreignCriteriaCache.put( foreignCriteria.getAlias(), foreignCriteria );
            if ( Ascii.isNullOrEmpty( getAlias() ) ) {
                this.useAlias( "" );
            }
            this.enableAlias( true );
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

    // region search foreign criteria

    @Override
    public <E> ForeignCriteria<E> searchForeign( String alias ) {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return ( ForeignCriteria<E> ) foreignCriteriaCache.getOrDefault( alias, null );
        }
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign( Class<E> entity ) {
        if ( entity != null && CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            ForeignCriteria<?> criteria = foreignCriteriaSet.stream()
                    .filter( subCriteria -> entity.equals( subCriteria.getEntityClass() ) ).findFirst().orElse( null );
            return ( ForeignCriteria<E> ) Optional.ofNullable( criteria ).orElse( null );
        }
        return null;
    }

    @Override
    public <E> ForeignCriteria<E> searchForeign( String alias, Class<E> entity ) {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            boolean hasAlias = StringUtil.hasText( alias );
            boolean hasEntity = entity != null;
            if ( hasAlias || hasEntity ) {
                if ( hasAlias && hasEntity ) {
                    ForeignCriteria<E> criteria = searchForeign( entity );
                    if ( criteria != null && alias.equals( criteria.getAlias() ) ) {
                        return criteria;
                    }
                } else if ( StringUtil.hasText( alias ) ) {
                    return searchForeign( alias );
                } else {
                    return searchForeign( entity );
                }
            }
        }
        return null;
    }

    /**
     * 获取联表查询SQL片段
     * @return SQL片段
     */
    public String getForeignSegment() {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return foreignCriteriaSet.stream().map( it -> {
                Criteria<?> master = it.getMaster();
                Foreign linked = it.getForeign();
                ColumnWrapper masterColumn = master.searchColumn( linked.getMaster() );
                // 区分子查询联表条件对象
                String assistantColumn;
                if ( it instanceof ForeignSubCriteria ) {
                    assistantColumn = linked.getForeign();
                    ColumnWrapper column = ( ( ForeignSubCriteria<?> ) it ).getSubCriteria()
                            .searchColumn( assistantColumn );
                    if ( column != null ) {
                        assistantColumn = column.getColumn();
                    }
                } else {
                    assistantColumn = it.searchColumn( linked.getForeign() ).getColumn();
                }
                TableWrapper table = TableHandler.getTable( it.getEntityClass() );
                String catalog = Optional.ofNullable( table ).map( TableWrapper::getCatalog ).orElse( null );
                String schema = Optional.ofNullable( table ).map( TableWrapper::getSchema ).orElse( null );
                String foreignAlias = it.getAlias();
                // 拼接条件
                StringBuilder builder = new StringBuilder( 60 );
                builder.append( linked.getJoinMode().getSqlSegment() );
                if ( StringUtil.hasText( schema ) ) {
                    builder.append( schema ).append( "." );
                } else if ( StringUtil.hasText( catalog ) ) {
                    builder.append( catalog ).append( "." );
                }
                // ForeignSubCriteria对象subTempTabAlias是临时表别名也是alias
                if ( it instanceof ForeignSubCriteria ) {
                    ForeignSubCriteria<?> fsc = ( ForeignSubCriteria<?> ) it;
                    String scTempAlias = fsc.getSubCriteria().getSubTempTabAlias();
                    if (StringUtil.hasText( scTempAlias )) {
                        foreignAlias = scTempAlias;
                    }
                    builder.append( " " ).append( ( ( ForeignSubCriteria<?> ) it ).getTableSegment() )
                            .append( " " ).append( foreignAlias );
                } else {
                    if ( table != null ) {
                        builder.append( table.getName() ).append( " " ).append( foreignAlias );
                    }
                }
                builder.append( " ON " ).append( foreignAlias ).append( "." ).append( assistantColumn );
                builder.append( " = " ).append( master.getAlias() ).append( "." ).append( masterColumn.getColumn() );
                // 拼接其他条件
                if ( it.isHasCondition() ) {
                    builder.append( " " ).append( it.getSqlSegment() );
                }
                return builder.toString();
            } ).collect( Collectors.joining( " \n" ) );
        }
        return "";
    }

    // endregion
    // endregion

    // region order by

    @Override
    public Context asc( List<String> properties ) {
        return addOrder( Order.asc( this, properties ) );
    }

    @Override
    public Context asc( Aggregation... aggregations ) {
        return addOrder( Order.asc( aggregations ) );
    }

    @Override
    public Context ascFunc( List<String> aliases ) {
        return addOrder( Order.ascFunc( this, aliases ) );
    }

    @Override
    public <E> Context aliasAsc( String alias, Property<E, ?>... properties ) {
        return addOrder( Order.asc( this.searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasAsc( String alias, List<String> properties ) {
        return addOrder( Order.asc( this.searchForeign( alias ), properties ) );
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
    public Context descFunc( List<String> aliases ) {
        return addOrder( Order.descFunc( this, aliases ) );
    }

    @Override
    public <E> Context aliasDesc( String alias, Property<E, ?>... properties ) {
        return addOrder( Order.desc( searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasDesc( String alias, List<String> properties ) {
        return addOrder( Order.desc( searchForeign( alias ), properties ) );
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
    public Context group( Collection<String> properties ) {
        return addGroup( Group.group( this, properties ) );
    }

    @Override
    public <E> Context aliasGroup( String alias, Property<E, ?>... properties ) {
        return addGroup( Group.group( searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasGroup( String alias, String... properties ) {
        return aliasGroup( alias, ArrayUtil.toList( properties ) );
    }

    @Override
    public Context aliasGroup( String alias, Collection<String> properties ) {
        return addGroup( Group.group( searchForeign( alias ), properties ) );
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

    // region auxiliary methods

    @Override
    public Context groupAll( boolean enable ) {
        this.groupAll = enable;
        return this.context;
    }

    @Override
    public Context range( long start, long end ) {
        this.start = start;
        this.end = end;
        return this.context;
    }

    @Override
    public Context range( long pageStart, long pageEnd, long size ) {
        this.pageStart = pageStart;
        this.pageEnd = pageEnd;
        this.pageSize = size;
        return this.context;
    }

    @Override
    public boolean isRange() {
        return ( start >= 0 && end > 0 ) || ( pageStart > 0 && pageEnd > 0 );
    }

    @Override
    public RangeMode range() {
        if ( start >= 0 && end > 0 ) {
            return RangeMode.IMMEDIATE;
        }
        if ( pageStart > 0 && pageEnd > 0 ) {
            return RangeMode.PAGEABLE;
        }
        return RangeMode.NONE;
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

    @Override
    public Object getConditionVersionValue() {
        ColumnWrapper column = getOptimisticLockingColumn();
        if ( column != null ) {
            return this.segmentManager.getConditionValue( column.getProperty() );
        }
        return null;
    }

    @Override
    public String resultMap() {
        return this.resultMap;
    }

    @Override
    public Class<?> resultType() {
        return this.resultType;
    }

    @Override
    public String getSqlSegment() {
        return this.segmentManager.getSqlSegment( isGroupAll() ? getGroupSegment() : null );
    }

    /**
     * 获取分组SQL片段
     * <p><i>当groupAll为true时，会调用该方法</i></p>
     * @return SQL片段
     * @see #getSqlSegment()
     * @see SegmentManager#getSqlSegment(String)
     */
    protected abstract String getGroupSegment();

    // endregion

}
