package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.core.wrapper.AbstractQueryWrapper;
import com.wkit.lost.mybatis.core.wrapper.QueryManager;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Criteria联表查询条件类
 * @param <T> 类型
 * @author wvkity
 */
@Accessors( chain = true )
public class ForeignCriteria<T> extends AbstractQueryCriteria<T> {

    private static final long serialVersionUID = 6758914691359345877L;

    /**
     * 连接方式
     */
    @Getter
    @Setter
    private Foreign foreign;

    /**
     * 是否主动关联查询副表字段(默认: false)
     * <p>
     * <i>当excludes和queries为空时才生效</i>
     * </p>
     */
    @Getter
    @Setter
    private boolean fetch = false;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    private ForeignCriteria( Class<T> entityClass ) {
        this.entityClass = entityClass;
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     * @param master      主表查询对象
     * @param foreign     连接方式
     * @param <E>         泛型类型
     */
    public <E> ForeignCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master, Foreign foreign ) {
        this( entityClass, alias, null, master, foreign, null );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     * @param reference   引用属性
     * @param master      主表查询对象
     * @param foreign     连接方式
     * @param <E>         泛型类型
     */
    public <E> ForeignCriteria( Class<T> entityClass, String alias, String reference, AbstractQueryCriteria<E> master,
                                Foreign foreign ) {
        this( entityClass, alias, reference, master, foreign, null );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     * @param master      主表查询对象
     * @param foreign     连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     */
    public <E> ForeignCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master, Foreign foreign,
                                Collection<Criterion<?>> withClauses ) {
        this( entityClass, alias, null, master, foreign, withClauses );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     * @param reference   引用属性
     * @param master      主表查询对象
     * @param foreign     连接方式
     * @param withClauses 条件
     * @param <E>         泛型类型
     */
    public <E> ForeignCriteria( Class<T> entityClass, String alias, String reference, AbstractQueryCriteria<E> master,
                                Foreign foreign, Collection<Criterion<?>> withClauses ) {
        this.entityClass = entityClass;
        this.reference = reference;
        this.master = master;
        this.foreign = foreign;
        this.parameterSequence = master.parameterSequence;
        this.aliasSequence = master.aliasSequence;
        this.paramValueMappings = master.paramValueMappings;
        this.segmentManager = new SegmentManager();
        this.queryManager = new QueryManager( this );
        if ( this.entityClass != null ) {
            this.initMappingCache( this.entityClass.getName(), true );
        }
        this.conditionManager = new ConditionManager<>( this );
        if ( StringUtil.hasText( alias ) ) {
            this.useAlias( alias );
        }
        if ( CollectionUtil.hasElement( withClauses ) ) {
            this.add( withClauses );
        }
        this.initAlias();
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    ForeignCriteria( Class<T> entity, AtomicInteger parameterSequence, AtomicInteger aliasSequence,
                     Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        this.entityClass = entity;
        this.parameterSequence = parameterSequence;
        this.aliasSequence = aliasSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.queryManager = new QueryManager( this );
        if ( entity != null ) {
            this.initMappingCache( this.entityClass.getName(), true );
        }
        this.conditionManager = new ConditionManager<>( this );
    }

    /**
     * 创建实例(用于复制)
     * @param entityClass 实体类
     * @param <T>         泛型类型
     * @return 实例
     */
    static <T> ForeignCriteria<T> newInstanceForClone( Class<T> entityClass ) {
        return new ForeignCriteria<>( entityClass );
    }

    @Override
    public ForeignCriteria<T> deepClone() {
        return CriteriaCopierFactory.clone( this );
    }

    @Override
    protected ForeignCriteria<T> instance( AtomicInteger parameterSequence, AtomicInteger aliasSequence,
                                           Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new ForeignCriteria<>( entityClass, parameterSequence, aliasSequence, parameterValueMappings, new SegmentManager() );
    }

    @Override
    protected Map<String, ColumnWrapper> getQueryColumns() {
        Map<String, ColumnWrapper> realQueries;
        if ( CollectionUtil.hasElement( propertyForQueryCache ) ) {
            // 显式指定查询列
            realQueries = propertyForQueryCache;
        } else {
            if ( fetch ) {
                // 所有列
                realQueries = Collections.synchronizedMap(
                        TableHandler.getTable( entityClass )
                                .columns()
                                .stream()
                                .collect( Collectors.toMap( ColumnWrapper::getProperty, Function.identity(),
                                        ( oldValue, newValue ) -> newValue, LinkedHashMap::new ) )
                );
            } else {
                realQueries = new LinkedHashMap<>( 0 );
            }
        }
        // 排除
        exclude( realQueries, this.excludes );
        return realQueries;
    }

    @Override
    public Object getModifyVersionValue() {
        return null;
    }

    @Override
    public <E> AbstractQueryCriteria<E> getMaster() {
        AbstractCriteriaWrapper<E> wrapper = super.getMaster();
        return wrapper instanceof AbstractQueryCriteria ? ( AbstractQueryCriteria<E> ) wrapper : null;
    }

    @Override
    public <E> AbstractQueryCriteria<E> getRootMaster() {
        AbstractCriteriaWrapper<E> wrapper = super.getRootMaster();
        return wrapper instanceof AbstractQueryCriteria ? ( AbstractQueryCriteria<E> ) wrapper : null;
    }

    /**
     * 设置主动关联查询副表字段
     * @return 当前对象
     */
    public ForeignCriteria<T> fetch() {
        return setFetch( true );
    }

    /**
     * 设置master对象
     * @param master 主查询对象
     * @return 当前对象
     */
    public ForeignCriteria<T> setMaster( AbstractQueryCriteria<?> master ) {
        this.master = master;
        return this;
    }

    /**
     * 将当前对象添加到联表对象容器中
     * @return 当前对象
     */
    public ForeignCriteria<T> join() {
        doJoin();
        return this;
    }

    public ForeignCriteria<T> join( Function<ForeignCriteria<T>, AbstractCriteriaWrapper<T>> function ) {
        if ( doJoin() ) {
            function.apply( this );
        }
        return this;
    }

    private boolean doJoin() {
        AbstractQueryCriteria<?> masterCriteria = getMaster();
        if ( masterCriteria != null ) {
            Set<ForeignCriteria<?>> foreignList = masterCriteria.foreignCriteriaSet;
            if ( foreignList.isEmpty() ) {
                masterCriteria.addForeign( this );
            } else if ( !foreignList.contains( this ) ) {
                masterCriteria.addForeign( this );
            }
            return true;
        }
        return false;
    }
}
