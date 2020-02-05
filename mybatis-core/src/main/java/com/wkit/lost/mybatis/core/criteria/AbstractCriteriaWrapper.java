package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.core.condition.expression.Nested;
import com.wkit.lost.mybatis.core.function.AbstractFunction;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.function.Aggregations;
import com.wkit.lost.mybatis.core.function.Comparator;
import com.wkit.lost.mybatis.core.function.FunctionType;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.CaseFormat;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 条件容器
 * @param <T>       泛型类型
 * @param <R>       Lambda类型
 * @param <Context> 当前对象
 * @author wvkity
 */
@Log4j2
@Accessors( chain = true )
@SuppressWarnings( value = { "unchecked", "serial" } )
public abstract class AbstractCriteriaWrapper<T, R, Context extends AbstractCriteriaWrapper<T, R, Context>>
        extends AbstractConditionExpressionWrapper<T, R> implements CriteriaWrapper<T, Context, R> {

    // region fields

    private static final String AND_OR_REGEX = "^(\\s*AND\\s+|\\s*OR\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile( AND_OR_REGEX );
    protected static final String SQL_ALIAS_PREFIX = "__self_";

    /**
     * 表名缓存
     */
    protected static final Map<TableWrapper, String> TABLE_NAME_CACHE = new ConcurrentHashMap<>( 64 );

    /**
     * 参数前置
     */
    protected static final String PARAMETER_KEY_PREFIX = "v_idx_";

    /**
     * 参数别名[@Param("xxx")]
     */
    protected static final String PARAMETER_ALIAS = Constants.PARAM_CRITERIA;

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
     * 主表查询条件对象
     */
    protected AbstractQueryCriteria<?> master;

    /**
     * 子查询临时表名
     */
    @Getter
    protected String subTempTabAlias;

    /**
     * SQL片段
     */
    protected String sqlSegment;

    /**
     * WHERE SQL片段
     */
    protected String whereSqlSegment;

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
    protected Class<T> entityClass;

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
     * 别名序列生成
     */
    protected AtomicInteger aliasSequence;

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
     * 联表SQL片段
     */
    protected String foreignSegment;

    /**
     * 联表条件对象集合
     */
    protected Set<ForeignCriteria<?>> foreignCriteriaSet = Collections.synchronizedSet( new LinkedHashSet<>( 8 ) );

    /**
     * 联表条件对象缓存
     */
    protected Map<String, ForeignCriteria<?>> foreignCriteriaCache = new ConcurrentHashMap<>( 8 );

    /**
     * 子查询条件对象集合
     */
    protected Set<SubCriteria<?>> subCriteriaSet = Collections.synchronizedSet( new LinkedHashSet<>( 8 ) );

    /**
     * 子查询条件对象缓存
     */
    protected Map<String, SubCriteria<?>> subCriteriaCache = new ConcurrentHashMap<>( 8 );

    /**
     * 聚合函数
     */
    protected Set<Aggregation> aggregations = Collections.synchronizedSet( new LinkedHashSet<>( 8 ) );

    /**
     * 聚合函数缓存
     */
    protected Map<String, Aggregation> aggregationCache = new ConcurrentHashMap<>( 8 );

    // endregion

    // region foreign criteria

    // region foreign criteria builder

    @Override
    public <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                                 Collection<Criterion<?>> withClauses ) {
        return new ForeignCriteria<>( entity, alias, reference, ( AbstractQueryCriteria<?> ) this,
                foreign, withClauses );
    }

    @Override
    public <E> ForeignCriteria<E> createForeign( Class<E> entity, String alias, String reference, Foreign foreign,
                                                 Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
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
                                                    Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
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
                                   Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( createForeign( entity, alias, reference, foreign, function ) );
    }

    @Override
    public <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                   Collection<Criterion<?>> withClauses ) {
        return addForeign( createForeign( subCriteria, reference, foreign, withClauses ) );
    }

    @Override
    public <E> Context addForeign( SubCriteria<E> subCriteria, String reference, Foreign foreign,
                                   Function<ForeignCriteria<E>, AbstractQueryCriteria<E>> function ) {
        return addForeign( createForeign( subCriteria, reference, foreign, function ) );
    }

    @Override
    public Context addForeign( ForeignCriteria<?> foreignCriteria ) {
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

    // endregion

    /**
     * 获取联表查询SQL片段
     * @return SQL片段
     */
    public String getForeignSegment() {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return foreignCriteriaSet.stream().map( criteria -> {
                Criteria<?> master = criteria.getMaster();
                Foreign linked = criteria.getForeign();
                ColumnWrapper masterColumn = master.searchColumn( linked.getMaster() );
                // 区分子查询联表条件对象
                String assistantColumn;
                if ( criteria instanceof ForeignSubCriteria ) {
                    assistantColumn = linked.getForeign();
                    ColumnWrapper column = ( ( ForeignSubCriteria<?> ) criteria ).getSubCriteria()
                            .searchColumn( assistantColumn );
                    if ( column != null ) {
                        assistantColumn = column.getColumn();
                    }
                } else {
                    assistantColumn = criteria.searchColumn( linked.getForeign() ).getColumn();
                }
                TableWrapper table = TableHandler.getTable( criteria.getEntityClass() );
                String catalog = Optional.ofNullable( table ).map( TableWrapper::getCatalog ).orElse( null );
                String schema = Optional.ofNullable( table ).map( TableWrapper::getSchema ).orElse( null );
                String foreignAlias = criteria.getAlias();
                // 拼接条件
                StringBuilder builder = new StringBuilder( 60 );
                builder.append( linked.getJoinMode().getSqlSegment() );
                if ( StringUtil.hasText( schema ) ) {
                    builder.append( schema ).append( "." );
                } else if ( StringUtil.hasText( catalog ) ) {
                    builder.append( catalog ).append( "." );
                }
                // ForeignSubCriteria对象subTempTabAlias是临时表别名也是alias
                if ( criteria instanceof ForeignSubCriteria ) {
                    builder.append( " " ).append( ( ( ForeignSubCriteria<?> ) criteria ).getTableSegment() )
                            .append( " " ).append( foreignAlias );
                } else {
                    if ( table != null ) {
                        builder.append( table.getName() ).append( " " ).append( foreignAlias );
                    }
                }
                builder.append( " ON " ).append( foreignAlias ).append( "." ).append( assistantColumn );
                builder.append( " = " ).append( master.getAlias() ).append( "." ).append( masterColumn.getColumn() );
                // 拼接其他条件
                if ( criteria.isHasCondition() ) {
                    builder.append( " " ).append( criteria.getSqlSegment() );
                }
                return builder.toString();
            } ).collect( Collectors.joining( " \n" ) );
        }
        return "";
    }

    // region search foreign criteria

    @Override
    public <E> ForeignCriteria<E> searchForeign( String alias ) {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return ( ForeignCriteria<E> ) Optional.ofNullable( foreignCriteriaCache.get( alias ) ).orElse( null );
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

    // endregion

    // endregion

    // region sub criteria

    // region sub criteria builder

    @Override
    public <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias,
                                         Collection<Criterion<?>> withClauses ) {
        return new SubCriteria<>( entity, alias, ( AbstractQueryCriteria<?> ) this, subTempTabAlias, withClauses );
    }

    @Override
    public <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias,
                                         Function<SubCriteria<E>, AbstractQueryCriteria<E>> function ) {
        SubCriteria<E> subCriteria = createSub( entity, alias, subTempTabAlias );
        function.apply( subCriteria );
        return subCriteria;
    }

    @Override
    public Context addSubCriteria( SubCriteria<?> subCriteria ) {
        if ( subCriteria != null ) {
            this.subCriteriaSet.add( subCriteria );
            if ( StringUtil.hasText( subCriteria.getSubTempTabAlias() ) ) {
                this.subCriteriaCache.put( subCriteria.getSubTempTabAlias(), subCriteria );
            }
        }
        return this.context;
    }

    @Override
    public Context addSubCriteria( SubCriteria<?>... subCriteriaArray ) {
        return addSubCriteria( ArrayUtil.toList( subCriteriaArray ) );
    }

    @Override
    public Context addSubCriteria( Collection<SubCriteria<?>> subCriteriaList ) {
        if ( CollectionUtil.hasElement( subCriteriaList ) ) {
            subCriteriaList.forEach( this::addSubCriteria );
        }
        return this.context;
    }

    // endregion

    // region sub criteria search

    @Override
    public <E> SubCriteria<E> searchSubCriteria( String subTempTabAlias ) {
        if ( StringUtil.hasText( subTempTabAlias ) && CollectionUtil.hasElement( subCriteriaSet ) ) {
            return ( SubCriteria<E> ) Optional.ofNullable( subCriteriaCache.get( subTempTabAlias ) ).orElse( null );
        }
        return null;
    }

    @Override
    public <E> SubCriteria<E> searchSubCriteria( Class<E> entity ) {
        if ( entity != null && CollectionUtil.hasElement( subCriteriaSet ) ) {
            SubCriteria<?> subCriteria = subCriteriaSet.stream().filter( criteria -> entity.equals( criteria.getEntityClass() ) )
                    .findFirst().orElse( null );
            return ( SubCriteria<E> ) Optional.ofNullable( subCriteria ).orElse( null );
        }
        return null;
    }

    @Override
    public <E> SubCriteria<E> searchSubCriteria( String subTempTabAlias, Class<E> entity ) {
        if ( CollectionUtil.hasElement( subCriteriaSet ) ) {
            boolean hasTempAlias = StringUtil.hasText( subTempTabAlias );
            boolean hasEntity = entity != null;
            if ( hasTempAlias || hasEntity ) {
                if ( hasTempAlias && hasEntity ) {
                    SubCriteria<E> criteria = searchSubCriteria( entity );
                    if ( criteria != null && subTempTabAlias.equals( criteria.getSubTempTabAlias() ) ) {
                        return criteria;
                    }
                } else if ( hasTempAlias ) {
                    return searchSubCriteria( subTempTabAlias );
                } else {
                    return searchSubCriteria( entity );
                }
            }
        }
        return null;
    }
    // endregion

    // endregion

    // region conditions

    // region simple conditions

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
        Context instance = instance( this.parameterSequence, this.aliasSequence, this.paramValueMappings, new SegmentManager() );
        // 设置当前查询对象别名
        instance.useAlias( this.getAlias() );
        Context newInstance = function.apply( instance );
        // 获取条件
        List<Segment> segments = newInstance.segmentManager.getWheres().getSegments();
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
    public Context propertyEq( String property, String otherProperty ) {
        return add( Restrictions.eq( this, property, getMaster(), otherProperty ) );
    }

    @Override
    public <E> Context eq( String property, Criteria<E> other, String otherProperty ) {
        return add( Restrictions.eq( this, property, other, otherProperty ) );
    }

    @Override
    public Context orEq( String property, Object value ) {
        return add( Restrictions.eq( this, property, value, Logic.OR ) );
    }

    @Override
    public Context orPropertyEq( String property, String otherProperty ) {
        return add( Restrictions.eq( this, property, getMaster(), otherProperty, Logic.OR ) );
    }

    @Override
    public <E> Context orEq( String property, Criteria<E> other, String otherProperty ) {
        return add( Restrictions.eq( this, property, other, otherProperty, Logic.OR ) );
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
    public Context version( Object version ) {
        if ( version != null ) {
            ColumnWrapper column = getOptimisticLockingColumn();
            if ( column != null ) {
                return add( Restrictions.eq( this, column.getProperty(), version ) );
            }
        }
        return this.context;
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
    public Context nested( Criteria<?> criteria, Collection<Criterion<?>> conditions ) {
        Nested<?> expression = Restrictions.nested( criteria, conditions );
        return add( expression );
    }

    @Override
    public Context orNested( Collection<Criterion<?>> conditions ) {
        return this.orNested( this, conditions );
    }

    @Override
    public Context orNested( Criteria<?> criteria, Collection<Criterion<?>> conditions ) {
        Nested<?> expression = Restrictions.nested( criteria, Logic.OR, conditions );
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
                        // 检查条件表达式是否包含Criteria对象，没有则设置当前对象
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
    // region sub query conditions

    @Override
    public Context idEq( SubCriteria<?> subCriteria ) {
        return add( Restrictions.idEq( this, subCriteria ) );
    }

    @Override
    public Context orIdEq( SubCriteria<?> subCriteria ) {
        return add( Restrictions.idEq( this, subCriteria, Logic.OR ) );
    }

    @Override
    public Context eq( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.eq( this, property, subCriteria ) );
    }

    @Override
    public Context orEq( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.eq( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context ne( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.ne( this, property, subCriteria ) );
    }

    @Override
    public Context orNe( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.ne( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context lt( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.lt( this, property, subCriteria ) );
    }

    @Override
    public Context orLt( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.lt( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context le( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.le( this, property, subCriteria ) );
    }

    @Override
    public Context orLe( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.le( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context gt( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.gt( this, property, subCriteria ) );
    }

    @Override
    public Context orGt( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.gt( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context ge( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.ge( this, property, subCriteria ) );
    }

    @Override
    public Context orGe( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.ge( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context in( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.in( this, property, subCriteria ) );
    }

    @Override
    public Context orIn( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.in( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context notIn( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.notIn( this, property, subCriteria ) );
    }

    @Override
    public Context orNotIn( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.notIn( this, property, subCriteria, Logic.OR ) );
    }

    @Override
    public Context exists( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.exists( this, property, subCriteria ) );
    }

    @Override
    public Context notExists( String property, SubCriteria<?> subCriteria ) {
        return add( Restrictions.notExists( this, property, subCriteria ) );
    }

    // endregion

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
        return addOrder( Order.asc( this.searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasAsc( String alias, String... properties ) {
        return addOrder( Order.asc( this.searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasAsc( String alias, List<String> properties ) {
        return addOrder( Order.asc( this.searchForeign( alias ), properties ) );
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
        return addOrder( Order.desc( searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasDesc( String alias, String... properties ) {
        return addOrder( Order.desc( searchForeign( alias ), properties ) );
    }

    @Override
    public Context aliasDesc( String alias, List<String> properties ) {
        return addOrder( Order.desc( searchForeign( alias ), properties ) );
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
    public Context sum( String property, Integer scale ) {
        return addFunction( Aggregations.sum( this, property, scale ) );
    }

    @Override
    public Context sum( String alias, String property ) {
        return addFunction( Aggregations.sum( this, alias, property ) );
    }

    @Override
    public Context sum( String alias, String property, Integer scale ) {
        return addFunction( Aggregations.sum( this, alias, property, scale ) );
    }

    @Override
    public Context sum( String property, boolean distinct ) {
        return addFunction( Aggregations.sum( this, property, distinct ) );
    }

    @Override
    public Context sum( String property, Integer scale, boolean distinct ) {
        return addFunction( Aggregations.sum( this, property, scale, distinct ) );
    }

    @Override
    public Context sum( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.sum( this, alias, property, distinct ) );
    }

    @Override
    public Context sum( String alias, String property, Integer scale, boolean distinct ) {
        return addFunction( Aggregations.sum( this, alias, property, scale, distinct ) );
    }

    @Override
    public Context sum( String alias, String property, Object... values ) {
        return sum( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Integer scale, String property, Object... values ) {
        return sum( alias, scale, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Comparator comparator, String property, Object... values ) {
        return sum( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return sum( alias, scale, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return sum( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context sum( String alias, Integer scale, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return sum( alias, scale, false, comparator, logic, property, values );
    }

    @Override
    public Context sum( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return sum( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, Integer scale, boolean distinct, Comparator comparator,
                        String property, Object... values ) {
        return sum( alias, scale, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context sum( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregations.sum( this, alias, distinct, comparator, logic, property, values ) );
    }

    @Override
    public Context sum( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregations.sum( this, alias, scale, distinct, comparator, logic, property, values ) );
    }
    // endregion

    // region avg function

    @Override
    public Context avg( String property ) {
        return addFunction( Aggregations.avg( this, property ) );
    }

    @Override
    public Context avg( String property, Integer scale ) {
        return addFunction( Aggregations.avg( this, property, scale ) );
    }

    @Override
    public Context avg( String alias, String property ) {
        return addFunction( Aggregations.avg( this, alias, property ) );
    }

    @Override
    public Context avg( String alias, String property, Integer scale ) {
        return addFunction( Aggregations.avg( this, alias, property, scale ) );
    }

    @Override
    public Context avg( String property, boolean distinct ) {
        return addFunction( Aggregations.avg( this, property, distinct ) );
    }

    @Override
    public Context avg( String property, Integer scale, boolean distinct ) {
        return addFunction( Aggregations.avg( this, property, scale, distinct ) );
    }

    @Override
    public Context avg( String alias, String property, boolean distinct ) {
        return addFunction( Aggregations.avg( this, alias, property, distinct ) );
    }

    @Override
    public Context avg( String alias, String property, Integer scale, boolean distinct ) {
        return addFunction( Aggregations.avg( this, alias, property, scale, distinct ) );
    }

    @Override
    public Context avg( String alias, String property, Object... values ) {
        return avg( alias, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Integer scale, String property, Object... values ) {
        return avg( alias, scale, false, Comparator.EQ, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Comparator comparator, String property, Object... values ) {
        return avg( alias, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Integer scale, Comparator comparator, String property, Object... values ) {
        return avg( alias, scale, false, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Comparator comparator, Logic logic, String property, Object... values ) {
        return avg( alias, false, comparator, logic, property, values );
    }

    @Override
    public Context avg( String alias, Integer scale, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return avg( alias, scale, false, comparator, logic, property, values );
    }

    @Override
    public Context avg( String alias, boolean distinct, Comparator comparator, String property, Object... values ) {
        return avg( alias, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, Integer scale, boolean distinct, Comparator comparator,
                        String property, Object... values ) {
        return avg( alias, scale, distinct, comparator, Logic.AND, property, values );
    }

    @Override
    public Context avg( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregations.avg( this, alias, distinct, comparator, logic, property, values ) );
    }

    @Override
    public Context avg( String alias, Integer scale, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregations.avg( this, alias, scale, distinct, comparator, logic, property, values ) );
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
    public Context max( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
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
    public Context min( String alias, boolean distinct, Comparator comparator, Logic logic,
                        String property, Object... values ) {
        return addFunction( Aggregations.min( this, alias, distinct, comparator, logic, property, values ) );
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
        count( getFuncAlias( aliasPrefix, FunctionType.COUNT ), property, distinct );
        sum( getFuncAlias( aliasPrefix, FunctionType.SUM ), property, distinct, scale );
        avg( getFuncAlias( aliasPrefix, FunctionType.AVG ), property, distinct, scale );
        max( getFuncAlias( aliasPrefix, FunctionType.MAX ), property, distinct );
        min( getFuncAlias( aliasPrefix, FunctionType.MIN ), property, distinct );
        return this.context;
    }

    @Override
    public Context functions( String property, FunctionType... functions ) {
        return functions( property, false, null, functions );
    }

    @Override
    public Context functions( String property, int scale, FunctionType... functions ) {
        return functions( property, false, null, scale, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, FunctionType... functions ) {
        return functions( property, distinct, null, -1, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, int scale, FunctionType... functions ) {
        return functions( property, distinct, null, scale, functions );
    }

    @Override
    public Context functions( String property, String aliasPrefix, FunctionType... functions ) {
        return functions( property, false, aliasPrefix, -1, functions );
    }

    @Override
    public Context functions( String property, String aliasPrefix, int scale, FunctionType... functions ) {
        return functions( property, false, aliasPrefix, scale, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix, FunctionType... functions ) {
        return functions( property, distinct, aliasPrefix, -1, functions );
    }

    @Override
    public Context functions( String property, boolean distinct, String aliasPrefix, int scale,
                              FunctionType... functions ) {
        if ( !ArrayUtil.isEmpty( functions ) ) {
            for ( FunctionType function : functions ) {
                addFunction( createFunction( getFuncAlias( aliasPrefix, function ), property, scale, distinct, function ) );
            }
        }
        return this.context;
    }

    private String getFuncAlias( String aliasPrefix, FunctionType function ) {
        return StringUtil.hasText( aliasPrefix ) ?
                ( CaseFormat.UPPER_UNDERSCORE.to( CaseFormat.LOWER_CAMEL,
                        ( CaseFormat.LOWER_CAMEL.to( CaseFormat.UPPER_UNDERSCORE, aliasPrefix ) + "_" + function.getSqlSegment() ) ) )
                : null;
    }

    protected AbstractFunction createFunction( String alias, String property, int scale, boolean distinct, FunctionType function ) {
        switch ( function ) {
            case COUNT:
                return Aggregations.count( this, alias, property, distinct );
            case SUM:
                return Aggregations.sum( this, alias, property, scale, distinct );
            case AVG:
                return Aggregations.avg( this, alias, property, scale, distinct );
            case MAX:
                return Aggregations.max( this, alias, property, distinct );
            case MIN:
                return Aggregations.min( this, alias, property, distinct );
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
    public Context having( String... aliases ) {
        return having( ArrayUtil.toList( aliases ) );
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
                    list.add( ( Criterion<?> ) segment );
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
                        return template.replace( String.format( PARAMETER_PLACEHOLDER, 0 ),
                                String.format( PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName ) );
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
                template = template.replace( String.format( PARAMETER_PLACEHOLDER, i ),
                        String.format( PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName ) );
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
        this.aliasSequence = new AtomicInteger( 0 );
        this.paramValueMappings = new ConcurrentHashMap<>( 16 );
        this.segmentManager = new SegmentManager();
    }

    protected void initAlias() {
        if ( this.aliasSequence != null && Ascii.isNullOrEmpty( this.alias ) ) {
            this.alias = SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
        }
    }

    ColumnWrapper getOptimisticLockingColumn() {
        return TableHandler.getTable( this.entityClass ).getOptimisticLockingColumn();
    }

    @Override
    public Object getConditionVersionValue() {
        ColumnWrapper column = getOptimisticLockingColumn();
        if ( column != null ) {
            return this.segmentManager.getConditionValue( column.getProperty() );
        }
        return null;
    }

    // endregion

    // region get or set methods

    @Override
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public <E> AbstractQueryCriteria<E> getMaster() {
        return this.master != null ? ( AbstractQueryCriteria<E> ) this.master : null;
    }

    @Override
    public <E> AbstractQueryCriteria<E> getRootMaster() {
        AbstractQueryCriteria<E> rootMaster;
        AbstractQueryCriteria<E> root = ( AbstractQueryCriteria<E> ) this;
        while ( ( rootMaster = root.getMaster() ) != null ) {
            root = rootMaster;
        }
        return root;
    }

    @Override
    public String getSqlSegment() {
        return this.segmentManager.getSqlSegment( isGroupAll() ? getGroupSegment() : null );
    }

    @Override
    public String getWhereSqlSegment() {
        if ( isHasCondition() ) {
            String condition = getSqlSegment();
            if ( AND_OR_PATTERN.matcher( condition ).matches() ) {
                return " WHERE " + condition.replaceFirst( AND_OR_REGEX, "$2" );
            }
            return condition;
        }
        return "";
    }

    /**
     * 获取表名
     * @return 表名字符串
     */
    public String getTableName() {
        String realTableName = this.getTableNameFromCache();
        this.tableName = this.isEnableAlias() ? ( realTableName + " " + this.alias ) : realTableName;
        return this.tableName;
    }

    /**
     * 从缓存中读取表名
     * @return 表名
     */
    private String getTableNameFromCache() {
        TableWrapper table = TableHandler.getTable( this.entityClass );
        String cacheTableName = TABLE_NAME_CACHE.get( table );
        if ( StringUtil.hasText( cacheTableName ) ) {
            return cacheTableName;
        } else {
            String tempTableName = table.getName();
            String schema = StringUtil.hasText( table.getSchema() ) ? table.getSchema() :
                    StringUtil.hasText( table.getCatalog() ) ? table.getCatalog() : "";
            String realTableName = StringUtil.hasText( schema ) ? ( schema + "." + tempTableName ) : tempTableName;
            if ( !TABLE_NAME_CACHE.containsKey( table ) ) {
                TABLE_NAME_CACHE.putIfAbsent( table, realTableName );
                return realTableName;
            } else {
                return TABLE_NAME_CACHE.get( table );
            }
        }
    }

    @Override
    public Context setAlias( String alias ) {
        if ( alias != null ) {
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
    public Context useAlias() {
        return this.enableAlias( true );
    }

    @Override
    public Context useAlias( String alias ) {
        return this.enableAlias( true ).setAlias( alias );
    }

    @Override
    public String getAlias() {
        if ( StringUtil.isBlank( this.alias ) ) {
            this.alias = AliasCache.getAlias( this.entityClass );
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
     * 获取分组SQL片段
     * <p><i>当groupAll为true时，会调用该方法</i></p>
     * @return SQL片段
     * @see #getSqlSegment()
     * @see SegmentManager#getSqlSegment(String)
     */
    protected abstract String getGroupSegment();

    /**
     * 创建实例对象
     * @param parameterSequence      参数序号生成
     * @param parameterValueMappings 参数值映射
     * @param segmentManager         SQL片段管理器
     * @return {@code this}
     */
    protected abstract Context instance( AtomicInteger parameterSequence, AtomicInteger aliasSequence, Map<String, Object>
            parameterValueMappings, SegmentManager segmentManager );

    // endregion

}
