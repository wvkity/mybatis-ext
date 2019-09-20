package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.factory.InstanceCopierFactory;
import lombok.experimental.Accessors;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Criteria查询条件实现类
 * @param <T> 类型
 * @author DT
 */
@Accessors( chain = true )
public class CriteriaImpl<T> extends AbstractCriteria<T> {

    private static final long serialVersionUID = 505287408705513144L;

    /**
     * 构造方法
     * @param entity 实体类
     */
    public CriteriaImpl( Class<T> entity ) {
        this.entity = entity;
        this.init();
        this.initMappingCache( this.entity.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
    }

    /**
     * 构造方法
     * @param entity 实体类
     * @param alias  表别名
     */
    public CriteriaImpl( Class<T> entity, String alias ) {
        this.entity = entity;
        this.init();
        this.setAlias( alias );
        this.initMappingCache( this.entity.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    private CriteriaImpl( Class<T> entity, AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        this.entity = entity;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.initMappingCache( this.entity.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
    }

    @Override
    public CriteriaImpl<T> deepClone() {
        return InstanceCopierFactory.clone( this );
    }

    @Override
    protected CriteriaImpl<T> instance( AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new CriteriaImpl<>( entity, parameterSequence, parameterValueMappings, new SegmentManager() );
    }
}
