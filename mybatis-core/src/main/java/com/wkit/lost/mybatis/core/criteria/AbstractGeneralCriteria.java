package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.condition.AbstractConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.condition.criterion.Restrictions;
import com.wkit.lost.mybatis.core.condition.expression.Nested;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.segment.Segment;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

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

@SuppressWarnings( { "unchecked", "serial" } )
public abstract class AbstractGeneralCriteria<T, Context extends AbstractGeneralCriteria<T, Context, P>, P>
        extends AbstractExpressionWrapper<T, P> implements GeneralCriteria<T, Context, P> {

    // region fields

    private static final String AND_OR_REGEX = "^(\\s*AND\\s+|\\s*OR\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile( AND_OR_REGEX );
    protected static final String SQL_ALIAS_PREFIX = "_self_";

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
    protected AbstractCriteriaWrapper<?> master;

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

    // region sub criteria

    // region sub criteria builder

    @Override
    public <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias,
                                         Collection<Criterion<?>> withClauses ) {
        return new SubCriteria<>( entity, alias, ( AbstractQueryCriteria<?> ) this, subTempTabAlias, withClauses );
    }

    @Override
    public <E> SubCriteria<E> createSub( Class<E> entity, String alias, String subTempTabAlias,
                                         Function<SubCriteria<E>, AbstractCriteriaWrapper<E>> function ) {
        SubCriteria<E> subCriteria = createSub( entity, alias, subTempTabAlias );
        function.apply( subCriteria );
        return subCriteria;
    }

    protected Context addSubCriteria( SubCriteria<?> subCriteria ) {
        if ( subCriteria != null ) {
            this.subCriteriaSet.add( subCriteria );
            if ( StringUtil.hasText( subCriteria.getSubTempTabAlias() ) ) {
                this.subCriteriaCache.put( subCriteria.getSubTempTabAlias(), subCriteria );
            }
        }
        return this.context;
    }

    @Override
    public Context add( SubCriteria<?>... subCriteriaArray ) {
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

    // endregion 

    // region conditions

    // region simple conditions

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

    /**
     * SQL片段对象转条件对象
     * @param segments SQL片段对象集合
     * @return 条件对象集合
     */
    Collection<Criterion<?>> segmentConvertToCondition( Collection<Segment> segments ) {
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
        com.wkit.lost.mybatis.core.condition.expression.Nested<?> expression = Restrictions.nested( criteria, conditions );
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

    // region get or set methods

    @Override
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    @Override
    public String getSqlSegment() {
        return this.segmentManager.getSqlSegment();
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

    ColumnWrapper getOptimisticLockingColumn() {
        return TableHandler.getTable( this.entityClass ).getOptimisticLockingColumn();
    }

    @Override
    public String getAlias() {
        if ( StringUtil.isBlank( this.alias ) ) {
            this.alias = AliasCache.getAlias( this.entityClass );
        }
        return this.alias;
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
    public boolean isEnableAlias() {
        return this.enableAlias;
    }

    @Override
    public boolean isHasCondition() {
        return this.segmentManager.hasCondition();
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

    /**
     * 初始化
     */
    protected void init() {
        this.parameterSequence = new AtomicInteger( 0 );
        this.aliasSequence = new AtomicInteger( 0 );
        this.paramValueMappings = new ConcurrentHashMap<>( 16 );
        this.segmentManager = new SegmentManager();
    }

    /**
     * 初始化别名
     */
    protected void initAlias() {
        if ( this.aliasSequence != null && Ascii.isNullOrEmpty( this.alias ) ) {
            this.alias = SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
        }
    }

    // endregion

    // region abstract methods

    /**
     * 创建实例对象
     * @param parameterSequence      参数序号生成
     * @param aliasSequence          别名序号生成
     * @param parameterValueMappings 参数值映射
     * @param segmentManager         SQL片段管理器
     * @return {@code this}
     */
    protected abstract Context instance( AtomicInteger parameterSequence, AtomicInteger aliasSequence, Map<String, Object>
            parameterValueMappings, SegmentManager segmentManager );

    // endregion
}
