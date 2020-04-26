package com.wkit.lost.mybatis.core.wrapper.criteria;

import com.wkit.lost.mybatis.core.conditional.Restrictions;
import com.wkit.lost.mybatis.core.conditional.criterion.Criterion;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Match;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.converter.AbstractPlaceholderConverter;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.metadata.TableWrapper;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 抽象基础条件包装类(负责添加条件)
 * @param <T>     实体类型
 * @param <Chain> 子类
 * @param <P>     Lambda类
 */
@SuppressWarnings({"serial", "unchecked"})
abstract class AbstractBasicCriteriaWrapper<T, Chain extends AbstractBasicCriteriaWrapper<T, Chain>>
        extends AbstractPlaceholderConverter implements CriteriaWrapper<T, Chain> {

    // region fields

    protected static final String AND_OR_REGEX = "^(?i)(\\s*and\\s+|\\s*or\\s+)(.*)";
    protected static final Pattern AND_OR_PATTERN = Pattern.compile(AND_OR_REGEX, Pattern.CASE_INSENSITIVE);
    protected static final String SYS_SQL_ALIAS_PREFIX = "_self_";

    /**
     * 表名缓存
     */
    private static final Map<String, String> TABLE_NAME_CACHE = new ConcurrentHashMap<>(64);

    /**
     * 参数前置
     */
    protected static final String PARAMETER_KEY_PREFIX = "_val_idx_";

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
    protected final Chain context = (Chain) this;

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
        this.parameterSequence = new AtomicInteger(0);
        this.aliasSequence = new AtomicInteger(0);
        this.paramValueMappings = new ConcurrentHashMap<>();
        this.segmentManager = new SegmentManager();
        this.builtinAlias = SYS_SQL_ALIAS_PREFIX + this.aliasSequence.incrementAndGet();
    }

    // region conditions

    // region simple condition
    @Override
    public Chain idEq(Object value) {
        return where(Restrictions.idEq(this, value));
    }

    @Override
    public Chain orIdEq(Object value) {
        return where(Restrictions.idEq(this, value, Logic.OR));
    }

    @Override
    public Chain eq(String property, Object value) {
        return where(Restrictions.eq(this, property, value));
    }

    @Override
    public Chain eq(Map<String, Object> properties) {
        if (CollectionUtil.isEmpty(properties)) {
            return this.context;
        }
        return where(properties.entrySet().stream().filter(it -> StringUtil.hasText(it.getKey())).map(it ->
                Restrictions.eq(this, it.getKey(), it.getValue())).collect(Collectors.toList()));
    }

    @Override
    public <E> Chain normalEq(String property, Criteria<E> otherCriteria, String otherProperty) {
        return where(Restrictions.eq(this, property, otherCriteria, otherProperty));
    }

    @Override
    public <E> Chain normalEq(String property, Criteria<E> otherCriteria) {
        return where(Restrictions.eq(this, this.searchColumn(property), otherCriteria,
                TableHandler.getPrimaryKey(otherCriteria.getEntityClass())));
    }

    @Override
    public <E> Chain normalEq(Criteria<E> otherCriteria, String otherProperty) {
        return where(Restrictions.eq(this, TableHandler.getPrimaryKey(this.getEntityClass()), otherCriteria,
                otherCriteria.searchColumn(otherProperty)));
    }

    @Override
    public Chain orEq(String property, Object value) {
        return where(Restrictions.eq(this, property, value, Logic.OR));
    }

    @Override
    public Chain eqWith(String column, Object value) {
        return where(Restrictions.eqWith(this, column, value));
    }

    @Override
    public Chain eqWith(Map<String, Object> columns) {
        if (CollectionUtil.isEmpty(columns)) {
            return this.context;
        }
        return where(columns.entrySet().stream().filter(it -> StringUtil.hasText(it.getKey())).map(it ->
                Restrictions.eqWith(this, it.getKey(), it.getValue())).collect(Collectors.toList()));
    }

    @Override
    public Chain eqWith(String tableAlias, String column, Object value) {
        return where(Restrictions.eqWith(tableAlias, column, value));
    }

    @Override
    public Chain eqWith(String tableAlias, Map<String, Object> columns) {
        if (CollectionUtil.isEmpty(columns)) {
            return this.context;
        }
        return where(columns.entrySet().stream().filter(it -> StringUtil.hasText(it.getKey())).map(it ->
                Restrictions.eqWith(tableAlias, it.getKey(), it.getValue())).collect(Collectors.toList()));
    }

    @Override
    public Chain orEqWith(String column, Object value) {
        return where(Restrictions.eqWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orEqWith(String tableAlias, String column, Object value) {
        return where(Restrictions.eqWith(tableAlias, column, value, Logic.OR));
    }

    @Override
    public Chain ne(String property, Object value) {
        return where(Restrictions.ne(this, property, value));
    }

    @Override
    public Chain orNe(String property, Object value) {
        return where(Restrictions.ne(this, property, value, Logic.OR));
    }

    @Override
    public Chain neWith(String column, Object value) {
        return where(Restrictions.neWith(this, column, value));
    }

    @Override
    public Chain neWith(String tableAlias, String column, Object value) {
        return where(Restrictions.neWith(tableAlias, column, value));
    }

    @Override
    public Chain orNeWith(String column, Object value) {
        return where(Restrictions.neWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orNeWith(String tableAlias, String column, Object value) {
        return where(Restrictions.neWith(tableAlias, column, value, Logic.OR));
    }

    @Override
    public Chain lt(String property, Object value) {
        return where(Restrictions.lt(this, property, value));
    }

    @Override
    public Chain orLt(String property, Object value) {
        return where(Restrictions.lt(this, property, value, Logic.OR));
    }

    @Override
    public Chain ltWith(String column, Object value) {
        return where(Restrictions.ltWith(this, column, value));
    }

    @Override
    public Chain ltWith(String tableAlias, String column, Object value) {
        return where(Restrictions.ltWith(tableAlias, column, value));
    }

    @Override
    public Chain orLtWith(String column, Object value) {
        return where(Restrictions.ltWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orLtWith(String tableAlias, String column, Object value) {
        return where(Restrictions.ltWith(tableAlias, column, value, Logic.OR));
    }

    @Override
    public Chain le(String property, Object value) {
        return where(Restrictions.le(this, property, value));
    }

    @Override
    public Chain orLe(String property, Object value) {
        return where(Restrictions.le(this, property, value, Logic.OR));
    }

    @Override
    public Chain leWith(String column, Object value) {
        return where(Restrictions.leWith(this, column, value));
    }

    @Override
    public Chain leWith(String tableAlias, String column, Object value) {
        return where(Restrictions.leWith(tableAlias, column, value));
    }

    @Override
    public Chain orLeWith(String column, Object value) {
        return where(Restrictions.leWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orLeWith(String tableAlias, String column, Object value) {
        return where(Restrictions.leWith(tableAlias, column, value, Logic.OR));
    }

    @Override
    public Chain gt(String property, Object value) {
        return where(Restrictions.gt(this, property, value));
    }

    @Override
    public Chain orGt(String property, Object value) {
        return where(Restrictions.gt(this, property, value, Logic.OR));
    }

    @Override
    public Chain gtWith(String column, Object value) {
        return where(Restrictions.gtWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain gtWith(String tableAlias, String column, Object value) {
        return where(Restrictions.gtWith(tableAlias, column, value));
    }

    @Override
    public Chain orGtWith(String column, Object value) {
        return where(Restrictions.gtWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orGtWith(String tableAlias, String column, Object value) {
        return where(Restrictions.gtWith(tableAlias, column, value, Logic.OR));
    }

    @Override
    public Chain ge(String property, Object value) {
        return where(Restrictions.ge(this, property, value));
    }

    @Override
    public Chain orGe(String property, Object value) {
        return where(Restrictions.ge(this, property, value, Logic.OR));
    }

    @Override
    public Chain geWith(String column, Object value) {
        return where(Restrictions.geWith(this, column, value));
    }

    @Override
    public Chain geWith(String tableAlias, String column, Object value) {
        return where(Restrictions.geWith(tableAlias, column, value));
    }

    @Override
    public Chain orGeWith(String column, Object value) {
        return where(Restrictions.geWith(this, column, value, Logic.OR));
    }

    @Override
    public Chain orGeWith(String tableAlias, String column, Object value) {
        return where(Restrictions.geWith(tableAlias, column, value, Logic.OR));
    }

    // endregion

    // region sub query condition

    @Override
    public Chain idEq(SubCriteria<?> sc) {
        return where(Restrictions.sq(this, TableHandler.getPrimaryKey(this.entityClass), sc, Logic.AND));
    }

    @Override
    public Chain orIdEq(SubCriteria<?> sc) {
        return where(Restrictions.sq(this, TableHandler.getPrimaryKey(this.entityClass), sc, Logic.OR));
    }

    @Override
    public Chain eq(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc));
    }

    @Override
    public Chain orEq(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Logic.OR));
    }

    @Override
    public Chain eqWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc));
    }

    @Override
    public Chain eqWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc));
    }

    @Override
    public Chain orEqWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Logic.OR));
    }

    @Override
    public Chain orEqWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Logic.OR));
    }

    @Override
    public Chain ne(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NE));
    }

    @Override
    public Chain orNe(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NE, Logic.OR));
    }

    @Override
    public Chain neWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NE));
    }

    @Override
    public Chain orNeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NE, Logic.OR));
    }

    @Override
    public Chain neWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NE));
    }

    @Override
    public Chain orNeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NE, Logic.OR));
    }

    @Override
    public Chain lt(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LT));
    }

    @Override
    public Chain orLt(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LT, Logic.OR));
    }

    @Override
    public Chain ltWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LT));
    }

    @Override
    public Chain orLtWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LT, Logic.OR));
    }

    @Override
    public Chain ltWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LT));
    }

    @Override
    public Chain orLtWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LT, Logic.OR));
    }

    @Override
    public Chain le(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LE));
    }

    @Override
    public Chain orLe(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LE, Logic.OR));
    }

    @Override
    public Chain leWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LE));
    }

    @Override
    public Chain orLeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LE, Logic.OR));
    }

    @Override
    public Chain leWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LE));
    }

    @Override
    public Chain orLeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LE, Logic.OR));
    }

    @Override
    public Chain gt(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.GT));
    }

    @Override
    public Chain orGt(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.GT, Logic.OR));
    }

    @Override
    public Chain gtWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.GT));
    }

    @Override
    public Chain orGtWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.GT, Logic.OR));
    }

    @Override
    public Chain gtWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.GT));
    }

    @Override
    public Chain orGtWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.GT, Logic.OR));
    }

    @Override
    public Chain ge(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.GE));
    }

    @Override
    public Chain orGe(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.GE, Logic.OR));
    }

    @Override
    public Chain geWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.GE));
    }

    @Override
    public Chain orGeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.GE, Logic.OR));
    }

    @Override
    public Chain geWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.GE));
    }

    @Override
    public Chain orGeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.GE, Logic.OR));
    }

    @Override
    public Chain like(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LIKE));
    }

    @Override
    public Chain orLike(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.LIKE, Logic.OR));
    }

    @Override
    public Chain notLike(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NOT_LIKE));
    }

    @Override
    public Chain orNotLike(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NOT_LIKE, Logic.OR));
    }

    @Override
    public Chain likeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LIKE));
    }

    @Override
    public Chain orLikeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.LIKE, Logic.OR));
    }

    @Override
    public Chain likeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LIKE));
    }

    @Override
    public Chain orLikeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.LIKE, Logic.OR));
    }

    @Override
    public Chain notLikeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NOT_LIKE));
    }

    @Override
    public Chain orNotLikeWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NOT_LIKE, Logic.OR));
    }

    @Override
    public Chain notLikeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NOT_LIKE));
    }

    @Override
    public Chain orNotLikeWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NOT_LIKE, Logic.OR));
    }

    @Override
    public Chain in(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.IN));
    }

    @Override
    public Chain orIn(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.IN, Logic.OR));
    }

    @Override
    public Chain notIn(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NOT_IN));
    }

    @Override
    public Chain orNotIn(String property, SubCriteria<?> sc) {
        return where(Restrictions.sq(this, property, sc, Symbol.NOT_IN, Logic.OR));
    }

    @Override
    public Chain inWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.IN));
    }

    @Override
    public Chain orInWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.IN, Logic.OR));
    }

    @Override
    public Chain inWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.IN));
    }

    @Override
    public Chain orInWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.IN, Logic.OR));
    }

    @Override
    public Chain notInWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NOT_IN));
    }

    @Override
    public Chain orNotInWith(String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(this, column, sc, Symbol.NOT_IN, Logic.OR));
    }

    @Override
    public Chain notInWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NOT_IN));
    }

    @Override
    public Chain orNotInWith(String tableAlias, String column, SubCriteria<?> sc) {
        return where(Restrictions.sqWith(tableAlias, column, sc, Symbol.NOT_IN, Logic.OR));
    }

    @Override
    public Chain exists(SubCriteria<?> sc) {
        return where(Restrictions.exists(this, sc));
    }

    @Override
    public Chain orExists(SubCriteria<?> sc) {
        return where(Restrictions.exists(this, sc, Logic.OR));
    }

    @Override
    public Chain notExists(SubCriteria<?> sc) {
        return where(Restrictions.notExists(this, sc));
    }

    @Override
    public Chain orNotExists(SubCriteria<?> sc) {
        return where(Restrictions.notExists(this, sc, Logic.OR));
    }
    // endregion

    // region empty condition

    @Override
    public Chain isNull(String property) {
        return where(Restrictions.isNull(this, property));
    }

    @Override
    public Chain orIsNull(String property) {
        return where(Restrictions.isNull(this, property, Logic.OR));
    }

    @Override
    public Chain isNullWith(String column) {
        return where(Restrictions.isNullWith(this, column));
    }

    @Override
    public Chain isNullWith(String tableAlias, String column) {
        return where(Restrictions.isNullWith(tableAlias, column));
    }

    @Override
    public Chain orIsNullWith(String column) {
        return where(Restrictions.isNullWith(this, column, Logic.OR));
    }

    @Override
    public Chain orIsNullWith(String tableAlias, String column) {
        return where(Restrictions.isNullWith(tableAlias, column, Logic.OR));
    }

    @Override
    public Chain notNull(String property) {
        return where(Restrictions.notNull(this, property));
    }

    @Override
    public Chain orNotNull(String property) {
        return where(Restrictions.notNull(this, property, Logic.OR));
    }

    @Override
    public Chain notNullWith(String column) {
        return where(Restrictions.notNullWith(this, column));
    }

    @Override
    public Chain notNullWith(String tableAlias, String column) {
        return where(Restrictions.notNullWith(tableAlias, column));
    }

    @Override
    public Chain orNotNullWith(String column) {
        return where(Restrictions.notNullWith(this, column, Logic.OR));
    }

    @Override
    public Chain orNotNullWith(String tableAlias, String column) {
        return where(Restrictions.notNullWith(tableAlias, column, Logic.OR));
    }

    // endregion

    // region range condition

    @Override
    public Chain in(String property, Collection<Object> values) {
        return where(Restrictions.in(this, property, values));
    }

    @Override
    public Chain orIn(String property, Collection<Object> values) {
        return where(Restrictions.in(this, property, values, Logic.OR));
    }

    @Override
    public Chain inWith(String column, Collection<Object> values) {
        return where(Restrictions.inWith(this, column, values));
    }

    @Override
    public Chain inWith(String tableAlias, String column, Collection<Object> values) {
        return where(Restrictions.inWith(tableAlias, column, values));
    }

    @Override
    public Chain orInWith(String column, Collection<Object> values) {
        return where(Restrictions.inWith(this, column, values, Logic.OR));
    }

    @Override
    public Chain orInWith(String tableAlias, String column, Collection<Object> values) {
        return where(Restrictions.inWith(tableAlias, column, values, Logic.OR));
    }

    @Override
    public Chain notIn(String property, Collection<Object> values) {
        return where(Restrictions.notIn(this, property, values));
    }

    @Override
    public Chain orNotIn(String property, Collection<Object> values) {
        return where(Restrictions.notIn(this, property, values, Logic.OR));
    }

    @Override
    public Chain notInWith(String column, Collection<Object> values) {
        return where(Restrictions.notInWith(this, column, values));
    }

    @Override
    public Chain notInWith(String tableAlias, String column, Collection<Object> values) {
        return where(Restrictions.notInWith(tableAlias, column, values));
    }

    @Override
    public Chain orNotInWith(String column, Collection<Object> values) {
        return where(Restrictions.notInWith(this, column, values, Logic.OR));
    }

    @Override
    public Chain orNotInWith(String tableAlias, String column, Collection<Object> values) {
        return where(Restrictions.notInWith(tableAlias, column, values, Logic.OR));
    }

    @Override
    public Chain between(String property, Object begin, Object end) {
        return where(Restrictions.between(this, property, begin, end));
    }

    @Override
    public Chain orBetween(String property, Object begin, Object end) {
        return where(Restrictions.between(this, property, begin, end, Logic.OR));
    }

    @Override
    public Chain betweenWith(String column, Object begin, Object end) {
        return where(Restrictions.betweenWith(this, column, begin, end));
    }

    @Override
    public Chain betweenWith(String tableAlias, String column, Object begin, Object end) {
        return where(Restrictions.betweenWith(tableAlias, column, begin, end));
    }

    @Override
    public Chain orBetweenWith(String column, Object begin, Object end) {
        return where(Restrictions.betweenWith(this, column, begin, end, Logic.OR));
    }

    @Override
    public Chain orBetweenWith(String tableAlias, String column, Object begin, Object end) {
        return where(Restrictions.betweenWith(tableAlias, column, begin, end, Logic.OR));
    }

    @Override
    public Chain notBetween(String property, Object begin, Object end) {
        return where(Restrictions.notBetween(this, property, begin, end));
    }

    @Override
    public Chain orNotBetween(String property, Object begin, Object end) {
        return where(Restrictions.notBetween(this, property, begin, end, Logic.OR));
    }

    @Override
    public Chain notBetweenWith(String column, Object begin, Object end) {
        return where(Restrictions.notBetweenWith(this, column, begin, end));
    }

    @Override
    public Chain notBetweenWith(String tableAlias, String column, Object begin, Object end) {
        return where(Restrictions.notBetweenWith(tableAlias, column, begin, end));
    }

    @Override
    public Chain orNotBetweenWith(String column, Object begin, Object end) {
        return where(Restrictions.notBetweenWith(this, column, begin, end, Logic.OR));
    }

    @Override
    public Chain orNotBetweenWith(String tableAlias, String column, Object begin, Object end) {
        return where(Restrictions.notBetweenWith(tableAlias, column, begin, end, Logic.OR));
    }

    // endregion

    // region fuzzy condition

    @Override
    public Chain like(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, escape));
    }

    @Override
    public Chain orLike(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, escape, Logic.OR));
    }

    @Override
    public Chain likeLeft(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, Match.END, escape));
    }

    @Override
    public Chain orLikeLeft(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain likeRight(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, Match.START, escape));
    }

    @Override
    public Chain orLikeRight(String property, String value, Character escape) {
        return where(Restrictions.like(this, property, value, Match.START, escape, Logic.OR));
    }

    @Override
    public Chain notLike(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, escape));
    }

    @Override
    public Chain orNotLike(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, escape, Logic.OR));
    }

    @Override
    public Chain notLikeLeft(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, Match.END, escape));
    }

    @Override
    public Chain orNotLikeLeft(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain notLikeRight(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, Match.START, escape));
    }

    @Override
    public Chain orNotLikeRight(String property, String value, Character escape) {
        return where(Restrictions.notLike(this, property, value, Match.START, escape, Logic.OR));
    }

    @Override
    public Chain likeWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, escape));
    }

    @Override
    public Chain likeWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, escape));
    }

    @Override
    public Chain orLikeWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, escape, Logic.OR));
    }

    @Override
    public Chain orLikeWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, escape, Logic.OR));
    }

    @Override
    public Chain likeLeftWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, Match.END, escape));
    }

    @Override
    public Chain likeLeftWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, Match.END, escape));
    }

    @Override
    public Chain orLikeLeftWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain orLikeLeftWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain likeRightWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, Match.START, escape));
    }

    @Override
    public Chain likeRightWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, Match.START, escape));
    }

    @Override
    public Chain orLikeRightWith(String column, String value, Character escape) {
        return where(Restrictions.likeWith(this, column, value, Match.START, escape, Logic.OR));
    }

    @Override
    public Chain orLikeRightWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.likeWith(tableAlias, column, value, Match.START, escape, Logic.OR));
    }

    @Override
    public Chain notLikeWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, escape));
    }

    @Override
    public Chain notLikeWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, escape));
    }

    @Override
    public Chain orNotLikeWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, escape, Logic.OR));
    }

    @Override
    public Chain orNotLikeWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, escape, Logic.OR));
    }

    @Override
    public Chain notLikeLeftWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, Match.END, escape));
    }

    @Override
    public Chain notLikeLeftWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, Match.END, escape));
    }

    @Override
    public Chain orNotLikeLeftWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain orNotLikeLeftWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, Match.END, escape, Logic.OR));
    }

    @Override
    public Chain notLikeRightWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, Match.START, escape));
    }

    @Override
    public Chain notLikeRightWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, Match.START, escape));
    }

    @Override
    public Chain orNotLikeRightWith(String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(this, column, value, Match.START, escape, Logic.OR));
    }

    @Override
    public Chain orNotLikeRightWith(String tableAlias, String column, String value, Character escape) {
        return where(Restrictions.notLikeWith(tableAlias, column, value, Match.START, escape, Logic.OR));
    }

    // endregion

    // region template condition

    @Override
    public Chain template(String template, String property, Object value) {
        return where(Restrictions.template(this, property, value, template));
    }

    @Override
    public Chain orTemplate(String template, String property, Object value) {
        return where(Restrictions.template(this, property, value, template, Logic.OR));
    }

    @Override
    public Chain template(String template, String property, Collection<Object> values) {
        return where(Restrictions.template(this, property, values, template));
    }

    @Override
    public Chain orTemplate(String template, String property, Collection<Object> values) {
        return where(Restrictions.template(this, property, values, template, Logic.OR));
    }

    @Override
    public Chain template(String template, String property, Map<String, Object> values) {
        return where(Restrictions.template(this, property, values, template));
    }

    @Override
    public Chain orTemplate(String template, String property, Map<String, Object> values) {
        return where(Restrictions.template(this, property, values, template, Logic.OR));
    }

    @Override
    public Chain templateWith(String template, Object value) {
        return where(Restrictions.templateWith(template, value));
    }

    @Override
    public Chain templateWith(String template, Collection<Object> values) {
        return where(Restrictions.templateWith(template, values));
    }

    @Override
    public Chain templateWith(String template, Map<String, Object> values) {
        return where(Restrictions.templateWith(template, values));
    }

    @Override
    public Chain templateWith(String template, String column, Object value) {
        return where(Restrictions.templateWith(this, column, value, template));
    }

    @Override
    public Chain templateWith(String template, String column, Collection<Object> values) {
        return where(Restrictions.templateWith(this, column, values, template));
    }

    @Override
    public Chain templateWith(String template, String column, Map<String, Object> values) {
        return where(Restrictions.templateWith(this, column, values, template));
    }

    @Override
    public Chain orTemplateWith(String template, Object value) {
        return where(Restrictions.templateWith(template, value, Logic.OR));
    }

    @Override
    public Chain orTemplateWith(String template, Collection<Object> values) {
        return where(Restrictions.templateWith(template, values, Logic.OR));
    }

    @Override
    public Chain orTemplateWith(String template, Map<String, Object> values) {
        return where(Restrictions.templateWith(template, values, Logic.OR));
    }

    @Override
    public Chain orTemplateWith(String template, String column, Object value) {
        return where(Restrictions.templateWith(this, column, value, template, Logic.OR));
    }

    @Override
    public Chain orTemplateWith(String template, String column, Collection<Object> values) {
        return where(Restrictions.templateWith(this, column, values, template, Logic.OR));
    }

    @Override
    public Chain orTemplateWith(String template, String column, Map<String, Object> values) {
        return where(Restrictions.templateWith(this, column, values, template, Logic.OR));
    }

    // endregion

    // region nested condition

    @Override
    public Chain and(Collection<Criterion<?>> conditions) {
        return where(Restrictions.nested(this, Logic.AND, conditions));
    }

    @Override
    public Chain and(Criteria<?> criteria, Collection<Criterion<?>> conditions) {
        return where(Restrictions.nested(criteria, Logic.AND, conditions));
    }

    @Override
    public Chain and(Function<Chain, Chain> function) {
        return doIt(function, true);
    }

    @Override
    public Chain or(Collection<Criterion<?>> conditions) {
        return where(Restrictions.nested(this, Logic.OR, conditions));
    }

    @Override
    public Chain or(Criteria<?> criteria, Collection<Criterion<?>> conditions) {
        return where(Restrictions.nested(criteria, Logic.OR, conditions));
    }

    @Override
    public Chain or(Function<Chain, Chain> function) {
        return doIt(function, false);
    }

    private Chain doIt(Function<Chain, Chain> function, boolean isAnd) {
        Chain ctx = newInstance();
        if (ctx != null) {
            Chain instance = function.apply(ctx);
            List<Criterion<?>> conditions = instance.segmentManager.getConditions();
            if (CollectionUtil.hasElement(conditions)) {
                if (isAnd) {
                    and(conditions);
                } else {
                    or(conditions);
                }
            }
        }
        return this.context;
    }

    // endregion

    // endregion

    // region add methods

    @Override
    public Chain where(Criterion<?>... array) {
        return where(ArrayUtil.toList(array));
    }

    @Override
    public Chain where(Collection<Criterion<?>> list) {
        this.segmentManager.addCondition(list.stream().filter(Objects::nonNull).peek(it -> {
            if (it.getCriteria() == null) {
                it.criteria(this);
            }
        }).collect(Collectors.toList()));
        return this.context;
    }

    @Override
    public Chain where(SubCriteria<?>... array) {
        return addSub(ArrayUtil.toList(array));
    }

    @Override
    public Chain addSub(Collection<SubCriteria<?>> list) {
        if (CollectionUtil.hasElement(list)) {
            list.stream().filter(Objects::nonNull).forEach(it -> {
                this.subCriteriaSet.add(it);
                if (StringUtil.hasText(it.as())) {
                    this.subCriteriaCache.put(it.as(), it);
                }
            });
        }
        return this.context;
    }

    // endregion 

    // region sub criteria

    @Override
    public <E> SubCriteria<E> sub(Class<E> entityClass, String alias, Collection<Criterion<?>> clauses) {
        return new SubCriteria<>(entityClass, alias, (AbstractCriteriaWrapper<?>) this, clauses);
    }

    @Override
    public <E> SubCriteria<E> sub(Class<E> entityClass, Consumer<SubCriteria<E>> consumer) {
        SubCriteria<E> instance = sub(entityClass);
        consumer.accept(instance);
        return instance;
    }

    @Override
    public <E> SubCriteria<E> sub(Class<E> entityClass,
                                  BiConsumer<AbstractCriteriaWrapper<T>, SubCriteria<E>> consumer) {
        SubCriteria<E> instance = sub(entityClass);
        consumer.accept(instance.getMaster(), instance);
        return instance;
    }
    // endregion

    // region dep methods
    @Override
    public <E> SubCriteria<E> searchSub(String alias) {
        if (StringUtil.hasText(alias) && CollectionUtil.hasElement(subCriteriaSet)) {
            return (SubCriteria<E>) Optional.ofNullable(subCriteriaCache.get(alias)).orElse(null);
        }
        return null;
    }

    @Override
    public <E> SubCriteria<E> searchSub(Class<E> entity) {
        if (entity != null && CollectionUtil.hasElement(subCriteriaSet)) {
            SubCriteria<?> subCriteria = subCriteriaSet.stream().filter(criteria ->
                    entity.equals(criteria.getEntityClass())).findFirst().orElse(null);
            return (SubCriteria<E>) Optional.ofNullable(subCriteria).orElse(null);
        }
        return null;
    }

    @Override
    public <E> SubCriteria<E> searchSub(String alias, Class<E> entity) {
        if (CollectionUtil.hasElement(subCriteriaSet)) {
            boolean hasTempAlias = StringUtil.hasText(alias);
            boolean hasEntity = entity != null;
            if (hasTempAlias || hasEntity) {
                if (hasTempAlias && hasEntity) {
                    SubCriteria<E> criteria = searchSub(entity);
                    if (criteria != null && alias.equals(criteria.as())) {
                        return criteria;
                    }
                } else if (hasTempAlias) {
                    return searchSub(alias);
                } else {
                    return searchSub(entity);
                }
            }
        }
        return null;
    }

    @Override
    public String placeholder(boolean need, String template, Object... values) {
        if (!need || StringUtil.isBlank(template)) {
            return null;
        }
        if (!ArrayUtil.isEmpty(values)) {
            int size = values.length;
            for (int i = 0; i < size; i++) {
                String paramName = PARAMETER_KEY_PREFIX + parameterSequence.incrementAndGet();
                template = template.replace(String.format(PARAMETER_PLACEHOLDER, i),
                        String.format(PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName));
                this.paramValueMappings.put(paramName, values[i]);
            }
        }
        return template;
    }

    @Override
    public ArrayList<String> placeholders(String template, Collection<Object> values) {
        if (StringUtil.hasText(template) && CollectionUtil.hasElement(values)) {
            return values.stream()
                    .filter(Objects::nonNull)
                    .map(it -> {
                        String paramName = PARAMETER_KEY_PREFIX + parameterSequence.incrementAndGet();
                        this.paramValueMappings.put(paramName, it);
                        return template.replace(String.format(PARAMETER_PLACEHOLDER, 0),
                                String.format(PARAMETER_VALUE_PLACEHOLDER, getParameterAlias(), paramName));
                    }).collect(Collectors.toCollection(ArrayList::new));
        }
        return null;
    }

    /**
     * 创建新实例
     * @return {@code this}
     */
    protected Chain newInstance() {
        return null;
    }

    /**
     * 复制属性
     * @param target 目标对象
     * @param source 源对象
     */
    final void copy(AbstractCriteriaWrapper<T> target, AbstractCriteriaWrapper<T> source) {
        if (target != null && source != null) {
            target.builtinAlias = source.builtinAlias;
            target.parameterSequence = source.parameterSequence;
            target.aliasSequence = source.aliasSequence;
            target.paramValueMappings = source.paramValueMappings;
            target.enableAlias = source.enableAlias;
            target.segmentManager = new SegmentManager();
        }
    }

    /**
     * 获取乐观锁字段
     * @return 字段包装对象
     */
    final ColumnWrapper optimisticLockingColumn() {
        return TableHandler.getTable(this.entityClass).getOptimisticLockingColumn();
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
        String as = as().trim();
        this.tableName = realTableName + (StringUtil.hasText(as) ? (" AS " + as) : as);
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
        String cacheTableName = TABLE_NAME_CACHE.getOrDefault(key, null);
        if (StringUtil.hasText(cacheTableName)) {
            return cacheTableName;
        } else {
            TableWrapper table = TableHandler.getTable(this.entityClass);
            String realTableName;
            if (StringUtil.hasText(table.getSchema())) {
                realTableName = table.getSchema() + "." + table.getName();
            } else if (StringUtil.hasText(table.getCatalog())) {
                realTableName = table.getCatalog() + "." + table.getName();
            } else {
                realTableName = table.getName();
            }
            if (TABLE_NAME_CACHE.containsKey(key)) {
                return TABLE_NAME_CACHE.get(key);
            } else {
                TABLE_NAME_CACHE.putIfAbsent(key, realTableName);
                return realTableName;
            }
        }
    }

    @Override
    public String as() {
        if (this.enableAlias) {
            if (StringUtil.hasText(this.tableAlias)) {
                return Constants.SPACE + this.tableAlias;
            }
            return Constants.SPACE + this.builtinAlias;
        }
        return Constants.EMPTY;
    }

    @Override
    public Chain as(boolean enabled) {
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
        if (isHasCondition()) {
            String condition = getSegment();
            if (AND_OR_PATTERN.matcher(condition).matches()) {
                return " WHERE " + condition.replaceFirst(AND_OR_REGEX, "$2");
            }
            return condition;
        }
        return Constants.EMPTY;
    }
    // endregion
}
