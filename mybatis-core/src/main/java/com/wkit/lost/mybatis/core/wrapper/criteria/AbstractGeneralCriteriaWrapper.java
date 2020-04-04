package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.Restrictions;
import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
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
    private static final Pattern AND_OR_PATTERN = Pattern.compile( AND_OR_REGEX, Pattern.CASE_INSENSITIVE );
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

    // region simple condition
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
    public Chain immediateEq( String column, Object value ) {
        return add( Restrictions.immediateEq( this, column, value ) );
    }

    @Override
    public Chain immediateEq( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateEq( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateEq( String column, Object value ) {
        return add( Restrictions.immediateEq( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateEq( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateEq( tableAlias, column, value, Logic.OR ) );
    }

    @Override
    public Chain ne( String property, Object value ) {
        return add( Restrictions.ne( this, property, value ) );
    }

    @Override
    public Chain orNe( String property, Object value ) {
        return add( Restrictions.ne( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediateNe( String column, Object value ) {
        return add( Restrictions.immediateNe( this, column, value ) );
    }

    @Override
    public Chain immediateNe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateNe( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateNe( String column, Object value ) {
        return add( Restrictions.immediateNe( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateNe( tableAlias, column, value, Logic.OR ) );
    }

    @Override
    public Chain lt( String property, Object value ) {
        return add( Restrictions.lt( this, property, value ) );
    }

    @Override
    public Chain orLt( String property, Object value ) {
        return add( Restrictions.lt( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediateLt( String column, Object value ) {
        return add( Restrictions.immediateLt( this, column, value ) );
    }

    @Override
    public Chain immediateLt( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateLt( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateLt( String column, Object value ) {
        return add( Restrictions.immediateLt( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateLt( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateLt( tableAlias, column, value, Logic.OR ) );
    }

    @Override
    public Chain le( String property, Object value ) {
        return add( Restrictions.le( this, property, value ) );
    }

    @Override
    public Chain orLe( String property, Object value ) {
        return add( Restrictions.le( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediateLe( String column, Object value ) {
        return add( Restrictions.immediateLe( this, column, value ) );
    }

    @Override
    public Chain immediateLe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateLe( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateLe( String column, Object value ) {
        return add( Restrictions.immediateLe( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateLe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateLe( tableAlias, column, value, Logic.OR ) );
    }

    @Override
    public Chain gt( String property, Object value ) {
        return add( Restrictions.gt( this, property, value ) );
    }

    @Override
    public Chain orGt( String property, Object value ) {
        return add( Restrictions.gt( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediateGt( String column, Object value ) {
        return add( Restrictions.immediateGt( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain immediateGt( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateGt( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateGt( String column, Object value ) {
        return add( Restrictions.immediateGt( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateGt( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateGt( tableAlias, column, value, Logic.OR ) );
    }

    @Override
    public Chain ge( String property, Object value ) {
        return add( Restrictions.ge( this, property, value ) );
    }

    @Override
    public Chain orGe( String property, Object value ) {
        return add( Restrictions.ge( this, property, value, Logic.OR ) );
    }

    @Override
    public Chain immediateGe( String column, Object value ) {
        return add( Restrictions.immediateGe( this, column, value ) );
    }

    @Override
    public Chain immediateGe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateGe( tableAlias, column, value ) );
    }

    @Override
    public Chain orImmediateGe( String column, Object value ) {
        return add( Restrictions.immediateGe( this, column, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateGe( String tableAlias, String column, Object value ) {
        return add( Restrictions.immediateGe( tableAlias, column, value, Logic.OR ) );
    }

    // endregion

    // region empty condition

    @Override
    public Chain isNull( String property ) {
        return add( Restrictions.isNull( this, property ) );
    }

    @Override
    public Chain orIsNull( String property ) {
        return add( Restrictions.isNull( this, property, Logic.OR ) );
    }

    @Override
    public Chain immediateIsNull( String column ) {
        return add( Restrictions.immediateIsNull( this, column ) );
    }

    @Override
    public Chain immediateIsNull( String tableAlias, String column ) {
        return add( Restrictions.immediateIsNull( tableAlias, column ) );
    }

    @Override
    public Chain orImmediateIsNull( String column ) {
        return add( Restrictions.immediateIsNull( this, column, Logic.OR ) );
    }

    @Override
    public Chain orImmediateIsNull( String tableAlias, String column ) {
        return add( Restrictions.immediateIsNull( tableAlias, column, Logic.OR ) );
    }

    @Override
    public Chain notNull( String property ) {
        return add( Restrictions.notNull( this, property ) );
    }

    @Override
    public Chain orNotNull( String property ) {
        return add( Restrictions.notNull( this, property, Logic.OR ) );
    }

    @Override
    public Chain immediateNotNull( String column ) {
        return add( Restrictions.immediateNotNull( this, column ) );
    }

    @Override
    public Chain immediateNotNull( String tableAlias, String column ) {
        return add( Restrictions.immediateNotNull( tableAlias, column ) );
    }

    @Override
    public Chain orImmediateNotNull( String column ) {
        return add( Restrictions.immediateNotNull( this, column, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotNull( String tableAlias, String column ) {
        return add( Restrictions.immediateNotNull( tableAlias, column, Logic.OR ) );
    }

    // endregion

    // region range condition

    @Override
    public Chain in( String property, Collection<Object> values ) {
        return add( Restrictions.in( this, property, values ) );
    }

    @Override
    public Chain orIn( String property, Collection<Object> values ) {
        return add( Restrictions.in( this, property, values, Logic.OR ) );
    }

    @Override
    public Chain immediateIn( String column, Collection<Object> values ) {
        return add( Restrictions.immediateIn( this, column, values ) );
    }

    @Override
    public Chain immediateIn( String tableAlias, String column, Collection<Object> values ) {
        return add( Restrictions.immediateIn( tableAlias, column, values ) );
    }

    @Override
    public Chain orImmediateIn( String column, Collection<Object> values ) {
        return add( Restrictions.immediateIn( this, column, values, Logic.OR ) );
    }

    @Override
    public Chain orImmediateIn( String tableAlias, String column, Collection<Object> values ) {
        return add( Restrictions.immediateIn( tableAlias, column, values, Logic.OR ) );
    }

    @Override
    public Chain notIn( String property, Collection<Object> values ) {
        return add( Restrictions.notIn( this, property, values ) );
    }

    @Override
    public Chain orNotIn( String property, Collection<Object> values ) {
        return add( Restrictions.notIn( this, property, values, Logic.OR ) );
    }

    @Override
    public Chain immediateNotIn( String column, Collection<Object> values ) {
        return add( Restrictions.immediateNotIn( this, column, values ) );
    }

    @Override
    public Chain immediateNotIn( String tableAlias, String column, Collection<Object> values ) {
        return add( Restrictions.immediateNotIn( tableAlias, column, values ) );
    }

    @Override
    public Chain orImmediateNotIn( String column, Collection<Object> values ) {
        return add( Restrictions.immediateNotIn( this, column, values, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotIn( String tableAlias, String column, Collection<Object> values ) {
        return add( Restrictions.immediateNotIn( tableAlias, column, values, Logic.OR ) );
    }

    @Override
    public Chain between( String property, Object begin, Object end ) {
        return add( Restrictions.between( this, property, begin, end ) );
    }

    @Override
    public Chain orBetween( String property, Object begin, Object end ) {
        return add( Restrictions.between( this, property, begin, end, Logic.OR ) );
    }

    @Override
    public Chain immediateBetween( String column, Object begin, Object end ) {
        return add( Restrictions.immediateBetween( this, column, begin, end ) );
    }

    @Override
    public Chain immediateBetween( String tableAlias, String column, Object begin, Object end ) {
        return add( Restrictions.immediateBetween( tableAlias, column, begin, end ) );
    }

    @Override
    public Chain orImmediateBetween( String column, Object begin, Object end ) {
        return add( Restrictions.immediateBetween( this, column, begin, end, Logic.OR ) );
    }

    @Override
    public Chain orImmediateBetween( String tableAlias, String column, Object begin, Object end ) {
        return add( Restrictions.immediateBetween( tableAlias, column, begin, end, Logic.OR ) );
    }

    @Override
    public Chain notBetween( String property, Object begin, Object end ) {
        return add( Restrictions.notBetween( this, property, begin, end ) );
    }

    @Override
    public Chain orNotBetween( String property, Object begin, Object end ) {
        return add( Restrictions.notBetween( this, property, begin, end, Logic.OR ) );
    }

    @Override
    public Chain immediateNotBetween( String column, Object begin, Object end ) {
        return add( Restrictions.immediateNotBetween( this, column, begin, end ) );
    }

    @Override
    public Chain immediateNotBetween( String tableAlias, String column, Object begin, Object end ) {
        return add( Restrictions.immediateNotBetween( tableAlias, column, begin, end ) );
    }

    @Override
    public Chain orImmediateNotBetween( String column, Object begin, Object end ) {
        return add( Restrictions.immediateNotBetween( this, column, begin, end, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotBetween( String tableAlias, String column, Object begin, Object end ) {
        return add( Restrictions.immediateNotBetween( tableAlias, column, begin, end, Logic.OR ) );
    }

    // endregion

    // region fuzzy condition

    @Override
    public Chain like( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, escape ) );
    }

    @Override
    public Chain orLike( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, escape, Logic.OR ) );
    }

    @Override
    public Chain likeLeft( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, Match.END, escape ) );
    }

    @Override
    public Chain orLikeLeft( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain likeRight( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, Match.START, escape ) );
    }

    @Override
    public Chain orLikeRight( String property, Object value, Character escape ) {
        return add( Restrictions.like( this, property, value, Match.START, escape, Logic.OR ) );
    }

    @Override
    public Chain notLike( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, escape ) );
    }

    @Override
    public Chain orNotLike( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, escape, Logic.OR ) );
    }

    @Override
    public Chain notLikeLeft( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, Match.END, escape ) );
    }

    @Override
    public Chain orNotLikeLeft( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain notLikeRight( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, Match.START, escape ) );
    }

    @Override
    public Chain orNotLikeRight( String property, Object value, Character escape ) {
        return add( Restrictions.notLike( this, property, value, Match.START, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateLike( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, escape ) );
    }

    @Override
    public Chain immediateLike( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, escape ) );
    }

    @Override
    public Chain orImmediateLike( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateLike( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateLikeLeft( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, Match.END, escape ) );
    }

    @Override
    public Chain immediateLikeLeft( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, Match.END, escape ) );
    }

    @Override
    public Chain orImmediateLikeLeft( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateLikeLeft( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateLikeRight( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, Match.START, escape ) );
    }

    @Override
    public Chain immediateLikeRight( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, Match.START, escape ) );
    }

    @Override
    public Chain orImmediateLikeRight( String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( this, column, value, Match.START, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateLikeRight( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateLike( tableAlias, column, value, Match.START, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateNotLike( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, escape ) );
    }

    @Override
    public Chain immediateNotLike( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, escape ) );
    }

    @Override
    public Chain orImmediateNotLike( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotLike( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateNotLikeLeft( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, Match.END, escape ) );
    }

    @Override
    public Chain immediateNotLikeLeft( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, Match.END, escape ) );
    }

    @Override
    public Chain orImmediateNotLikeLeft( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotLikeLeft( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, Match.END, escape, Logic.OR ) );
    }

    @Override
    public Chain immediateNotLikeRight( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, Match.START, escape ) );
    }

    @Override
    public Chain immediateNotLikeRight( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, Match.START, escape ) );
    }

    @Override
    public Chain orImmediateNotLikeRight( String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( this, column, value, Match.START, escape, Logic.OR ) );
    }

    @Override
    public Chain orImmediateNotLikeRight( String tableAlias, String column, Object value, Character escape ) {
        return add( Restrictions.immediateNotLike( tableAlias, column, value, Match.START, escape, Logic.OR ) );
    }

    // endregion

    // region template condition

    @Override
    public Chain template( String template, String property, Object value ) {
        return add( Restrictions.template( this, property, value, template ) );
    }

    @Override
    public Chain orTemplate( String template, String property, Object value ) {
        return add( Restrictions.template( this, property, value, template, Logic.OR ) );
    }

    @Override
    public Chain template( String template, String property, Collection<Object> values ) {
        return add( Restrictions.template( this, property, values, template ) );
    }

    @Override
    public Chain orTemplate( String template, String property, Collection<Object> values ) {
        return add( Restrictions.template( this, property, values, template, Logic.OR ) );
    }

    @Override
    public Chain template( String template, String property, Map<String, Object> values ) {
        return add( Restrictions.template( this, property, values, template ) );
    }

    @Override
    public Chain orTemplate( String template, String property, Map<String, Object> values ) {
        return add( Restrictions.template( this, property, values, template, Logic.OR ) );
    }

    @Override
    public Chain immediateTemplate( String template, Object value ) {
        return add( Restrictions.immediateTemplate( template, value ) );
    }

    @Override
    public Chain immediateTemplate( String template, Collection<Object> values ) {
        return add( Restrictions.immediateTemplate( template, values ) );
    }

    @Override
    public Chain immediateTemplate( String template, Map<String, Object> values ) {
        return add( Restrictions.immediateTemplate( template, values ) );
    }

    @Override
    public Chain immediateTemplate( String template, String column, Object value ) {
        return add( Restrictions.immediateTemplate( this, column, value, template ) );
    }

    @Override
    public Chain immediateTemplate( String template, String column, Collection<Object> values ) {
        return add( Restrictions.immediateTemplate( this, column, values, template ) );
    }

    @Override
    public Chain immediateTemplate( String template, String column, Map<String, Object> values ) {
        return add( Restrictions.immediateTemplate( this, column, values, template ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, Object value ) {
        return add( Restrictions.immediateTemplate( template, value, Logic.OR ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, Collection<Object> values ) {
        return add( Restrictions.immediateTemplate( template, values, Logic.OR ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, Map<String, Object> values ) {
        return add( Restrictions.immediateTemplate( template, values, Logic.OR ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, String column, Object value ) {
        return add( Restrictions.immediateTemplate( this, column, value, template, Logic.OR ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, String column, Collection<Object> values ) {
        return add( Restrictions.immediateTemplate( this, column, values, template, Logic.OR ) );
    }

    @Override
    public Chain orImmediateTemplate( String template, String column, Map<String, Object> values ) {
        return add( Restrictions.immediateTemplate( this, column, values, template, Logic.OR ) );
    }

    // endregion

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
        this.tableName = realTableName + getAlias();
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
        if ( this.enableAlias ) {
            if ( StringUtil.hasText( this.tableAlias ) ) {
                return Constants.CHAR_SPACE + this.tableAlias;
            }
            return Constants.CHAR_SPACE + this.builtinAlias;
        }
        return Constants.CHAR_EMPTY;
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
        return Constants.CHAR_EMPTY;
    }
    // endregion
}
