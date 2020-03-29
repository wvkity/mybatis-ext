package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.Restrictions;
import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.converter.AbstractPlaceholderConverter;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings( { "serial", "unchecked" } )
public abstract class AbstractGeneralCriteriaWrapper<T, Chain extends AbstractGeneralCriteriaWrapper<T, Chain, P>, P>
        extends AbstractPlaceholderConverter implements CriteriaWrapper<T, Chain, P> {

    // region fields

    private static final String AND_OR_REGEX = "^(\\s*AND\\s+|\\s*OR\\s+)(.*)";
    private static final Pattern AND_OR_PATTERN = Pattern.compile( AND_OR_REGEX );
    protected static final String SYS_SQL_ALIAS_PREFIX = "_self_";

    /**
     * 表名缓存
     */
    private static final Map<String, String> TABLE_NAME_CACHE = new ConcurrentHashMap<>( 64 );

    /**
     * 参数前置
     */
    protected static final String PARAMETER_KEY_PREFIX = "_VAL_IDX_";

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
    protected final Chain context = ( Chain ) this;

    /**
     * 主表查询条件对象
     */
    protected AbstractCriteriaWrapper<?> master;

    /**
     * 表名
     */
    protected String tableName = "";

    /**
     * 子查询临时表名
     */
    @Getter
    protected String subTempTabAlias;

    /**
     * SQL片段
     */
    protected String segment;

    /**
     * WHERE SQL片段
     */
    protected String whereSegment;

    /**
     * 是否存在条件
     */
    protected boolean hasCondition;

    /**
     * 最后一次逻辑操作(AND | OR | NORMAL): 默认是NONE
     */
    protected volatile Logic lastLogic = Logic.NONE;

    /**
     * 实体类型
     */
    protected Class<T> entityClass;

    /**
     * 表别名
     */
    protected String tableAlias = "";

    /**
     * 内置别名(系统自动生成)
     */
    protected String builtinAlias;

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
    protected Set<SubCriteria<?>> subCriteriaSet;

    /**
     * 子查询条件缓存
     */
    protected Map<String, SubCriteria<?>> subCriteriaCache;
    // endregion

    /**
     * 初始化
     */
    protected void inits() {
        this.parameterSequence = new AtomicInteger( 0 );
        this.aliasSequence = new AtomicInteger( 0 );
        this.paramValueMappings = new ConcurrentHashMap<>();
        this.segmentManager = new SegmentManager();
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
    }

    // region conditions

    @Override
    public Chain idEq( Object value ) {
        return add( Restrictions.idEq( this, value ) );
    }

    @Override
    public Chain orIdEq( Object value ) {
        return add( Restrictions.idEq( this, value, Logic.OR ) );
    }

    @Override
    public Chain eq( String property, Object value ) {
        return add( Restrictions.eq( this, property, value ) );
    }

    @Override
    public Chain orEq( String property, Object value ) {
        return add( Restrictions.eq( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediatePureEq( String column, Object value ) {
        return add( Restrictions.immediateEq( column, value ) );
    }

    @Override
    public Chain immediateEq( String column, Object value ) {
        return add( Restrictions.immediateEq( this, column, value ) );
    }

    @Override
    public Chain immediateEq( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateEq( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediatePureEq( String column, Object value ) {
        return add( Restrictions.immediateEq( column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateEq( String column, Object value ) {
        return add( Restrictions.immediateEq( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateEq( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateEq( tableAlias, column, value, Logic.OR ) );
    }

    // endregion

    // region add methods

    @Override
    public Chain add( Criterion<?>... array ) {
        return add( ArrayUtil.toList( array ) );
    }

    @Override
    public Chain add( Collection<Criterion<?>> list ) {
        this.segmentManager.addCondition( list.stream().filter( Objects::nonNull ).peek( it -> {
            if ( it.getCriteria() == null ) {
                it.criteria( this );
            }
        } ).collect( Collectors.toList() ) );
        return this.context;
    }

    @Override
    public Chain add( SubCriteria<?>... array ) {
        return this.context;
    }

    @Override
    public Chain addSubCriteria( Collection<SubCriteria<?>> list ) {
        return this.context;
    }

    // endregion 

    // region dep methods
    @Override
    public <E> SubCriteria<E> searchSubCriteria( String alias ) {
        if ( StringUtil.hasText( alias ) && CollectionUtil.hasElement( subCriteriaSet ) ) {
            return ( SubCriteria<E> ) Optional.ofNullable( subCriteriaCache.get( alias ) ).orElse( null );
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
    public <E> SubCriteria<E> searchSubCriteria( String alias, Class<E> entity ) {
        if ( CollectionUtil.hasElement( subCriteriaSet ) ) {
            boolean hasTempAlias = StringUtil.hasText( alias );
            boolean hasEntity = entity != null;
            if ( hasTempAlias || hasEntity ) {
                if ( hasTempAlias && hasEntity ) {
                    SubCriteria<E> criteria = searchSubCriteria( entity );
                    if ( criteria != null && alias.equals( criteria.getAlias() ) ) {
                        return criteria;
                    }
                } else if ( hasTempAlias ) {
                    return searchSubCriteria( alias );
                } else {
                    return searchSubCriteria( entity );
                }
            }
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

    @Override
    public ArrayList<String> placeholders( String template, Collection<Object> values ) {
        if ( StringUtil.hasText( template ) && CollectionUtil.hasElement( values ) ) {
            return values.stream()
                    .filter( Objects::nonNull )
                    .map( it -> {
                        String paramName = PARAMETER_KEY_PREFIX + parameterSequence.incrementAndGet();
                        this.paramValueMappings.put( paramName, it );
                        return template.replace( String.format( PARAMETER_PLACEHOLDER, 0 ),
                                String.format( PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName ) );
                    } ).collect( Collectors.toCollection( ArrayList::new ) );
        }
        return null;
    }
    // endregion

    // region get/set methods

    @Override
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取表名
     * @return 表名
     */
    public final String getTableName() {
        String realTableName = getCacheTableName();
        this.tableName = this.enableAlias ? ( realTableName + " " + this.tableAlias ) : realTableName;
        return this.tableName;
    }

    /**
     * 获取缓存中的表名
     * <pre>
     *     ([schema|catalog].)tableName
     * </pre>
     * @return 表名
     */
    private String getCacheTableName() {
        String key = this.entityClass.toString();
        String cacheTableName = TABLE_NAME_CACHE.getOrDefault( key, null );
        if ( StringUtil.hasText( cacheTableName ) ) {
            return cacheTableName;
        } else {
            TableWrapper table = TableHandler.getTable( this.entityClass );
            String realTableName;
            if ( StringUtil.hasText( table.getSchema() ) ) {
                realTableName = table.getSchema() + "." + table.getName();
            } else if ( StringUtil.hasText( table.getCatalog() ) ) {
                realTableName = table.getCatalog() + "." + table.getName();
            } else {
                realTableName = table.getName();
            }
            if ( TABLE_NAME_CACHE.containsKey( key ) ) {
                return TABLE_NAME_CACHE.get( key );
            } else {
                TABLE_NAME_CACHE.putIfAbsent( key, realTableName );
                return realTableName;
            }
        }
    }

    @Override
    public String getAlias() {
        return StringUtil.hasText( this.tableAlias ) ? this.tableAlias : "";
    }

    @Override
    public Chain enableAlias( boolean enabled ) {
        return context;
    }

    @Override
    public boolean isHasCondition() {
        return this.segmentManager.hasSegment();
    }

    @Override
    public String getSegment() {
        return this.segmentManager.getSegment();
    }

    @Override
    public String getWhereSegment() {
        if ( isHasCondition() ) {
            String condition = getSegment();
            if ( AND_OR_PATTERN.matcher( condition ).matches() ) {
                return " WHERE " + condition.replaceFirst( AND_OR_REGEX, "$2" );
            }
            return condition;
        }
        return "";
    }
    // endregion
}
