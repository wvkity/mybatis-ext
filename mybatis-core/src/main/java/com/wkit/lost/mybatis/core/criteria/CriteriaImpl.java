package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.lambda.Property;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Criteria查询条件实现类
 * @param <T> 类型
 * @author wvkity
 */
@Accessors( chain = true )
public class CriteriaImpl<T> extends AbstractQueryCriteria<T> implements Modify<T, CriteriaImpl<T>> {

    private static final long serialVersionUID = 505287408705513144L;

    /**
     * 
     */
    private UpdateCriteria<T> delegate;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public CriteriaImpl( Class<T> entityClass ) {
        this.entityClass = entityClass;
        this.init();
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
        this.delegate = new UpdateCriteria<>( entityClass, this.getAlias() );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     */
    public CriteriaImpl( Class<T> entityClass, String alias ) {
        this.entityClass = entityClass;
        this.init();
        this.setAlias( alias );
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
        this.delegate = new UpdateCriteria<>( entityClass, this.getAlias() );
    }

    /**
     * 构造方法
     * @param entityClass            实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    private CriteriaImpl( Class<T> entityClass, AtomicInteger parameterSequence, AtomicInteger aliasSequence,
                          Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        this.entityClass = entityClass;
        this.parameterSequence = parameterSequence;
        this.aliasSequence = aliasSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
        this.delegate = new UpdateCriteria<>( entityClass, this.getAlias() );
    }

    @Override
    public CriteriaImpl<T> update( Property<T, ?> property, Object value ) {
        this.delegate.update( property, value );
        return this;
    }

    @Override
    public CriteriaImpl<T> update( String property, Object value ) {
        this.delegate.update( property, value );
        return this;
    }

    @Override
    public CriteriaImpl<T> update( Map<String, Object> map ) {
        this.delegate.update( map );
        return this;
    }

    @Override
    public CriteriaImpl<T> updateVersion( Object version ) {
        this.delegate.updateVersion( version );
        return this;
    }

    @Override
    public String getUpdateSegment() {
        return this.delegate.getUpdateSegment();
    }

    @Override
    public CriteriaImpl<T> deepClone() {
        return CriteriaCopierFactory.clone( this );
    }

    @Override
    protected CriteriaImpl<T> instance( AtomicInteger parameterSequence, AtomicInteger aliasSequence,
                                        Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new CriteriaImpl<>( entityClass, parameterSequence, aliasSequence, parameterValueMappings,
                new SegmentManager() );
    }
}
