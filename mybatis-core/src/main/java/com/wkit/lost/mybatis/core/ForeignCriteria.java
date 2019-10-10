package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Criteria联表查询条件类
 * @param <T> 类型
 * @author DT
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
    private boolean relation = false;

    /**
     * 构造方法
     * @param entity 实体类
     */
    private ForeignCriteria( Class<T> entity ) {
        this.entity = entity;
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  表别名
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     */
    public <E> ForeignCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, Foreign foreign,
                                AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings ) {
        this( entity, alias, null, master, foreign, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  表别名
     * @param reference              引用属性
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     */
    public <E> ForeignCriteria( Class<T> entity, String alias, String reference, AbstractQueryCriteria<E> master,
                                Foreign foreign, AtomicInteger parameterSequence,
                                Map<String, Object> parameterValueMappings ) {
        this( entity, alias, reference, master, foreign, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  表别名
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     */
    public <E> ForeignCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, Foreign foreign,
                                AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                                Collection<Criterion<?>> withClauses ) {
        this( entity, alias, null, master, foreign, parameterSequence, parameterValueMappings, withClauses );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  表别名
     * @param reference              引用属性
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     */
    public <E> ForeignCriteria( Class<T> entity, String alias, String reference, AbstractQueryCriteria<E> master,
                                Foreign foreign, AtomicInteger parameterSequence,
                                Map<String, Object> parameterValueMappings, Collection<Criterion<?>> withClauses ) {
        this.entity = entity;
        this.reference = reference;
        this.master = master;
        this.foreign = foreign;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = new SegmentManager();
        if ( this.entity != null ) {
            this.initMappingCache( this.entity.getName(), true );
        }
        this.conditionManager = new ConditionManager<>( this );
        if ( StringUtil.hasText( alias ) ) {
            this.useAlias( alias );
        }
        if ( CollectionUtil.hasElement( withClauses ) ) {
            this.add( withClauses );
        }
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    ForeignCriteria( Class<T> entity, AtomicInteger parameterSequence,
                     Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        this.entity = entity;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        if ( entity != null ) {
            this.initMappingCache( this.entity.getName(), true );
        }
        this.conditionManager = new ConditionManager<>( this );
    }

    /**
     * 创建实例(用于复制)
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 实例
     */
    static <T> ForeignCriteria<T> newInstanceForClone( Class<T> entity ) {
        return new ForeignCriteria<>( entity );
    }

    @Override
    public ForeignCriteria<T> deepClone() {
        return CriteriaCopierFactory.clone( this );
    }

    @Override
    protected ForeignCriteria<T> instance( AtomicInteger parameterSequence,
                                           Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new ForeignCriteria<>( entity, parameterSequence, parameterValueMappings, new SegmentManager() );
    }

    @Override
    protected Map<String, Column> getQueryColumns() {
        Map<String, Column> realQueries;
        if ( CollectionUtil.hasElement( propertyForQueryCache ) ) {
            // 显式指定查询列
            realQueries = propertyForQueryCache;
        } else {
            if ( relation ) {
                // 所有列
                realQueries = Collections.synchronizedMap(
                        EntityHandler.getTable( entity )
                                .getColumns()
                                .stream()
                                .collect( Collectors.toMap( Column::getProperty, Function.identity(),
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
    public ForeignCriteria<T> appendTo() {
        AbstractQueryCriteria<?> masterCriteria = getMaster();
        if ( masterCriteria != null ) {
            Set<ForeignCriteria<?>> foreignList = masterCriteria.foreignCriteriaSet;
            if ( foreignList.isEmpty() ) {
                masterCriteria.addForeign( this );
            } else if ( !foreignList.contains( this ) ) {
                masterCriteria.addForeign( this );
            }
        }
        return this;
    }
}
