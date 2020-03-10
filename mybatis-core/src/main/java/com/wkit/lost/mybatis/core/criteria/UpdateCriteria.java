package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.segment.SegmentManager;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UpdateCriteria<T> extends AbstractUpdateCriteria<T> {

    private static final long serialVersionUID = -4569380372671941501L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public UpdateCriteria( Class<T> entityClass ) {
        this.entityClass = entityClass;
        this.init();
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       表别名
     */
    public UpdateCriteria( Class<T> entityClass, String alias ) {
        this.entityClass = entityClass;
        this.init();
        this.setAlias( alias );
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
    }

    /**
     * 构造方法
     * @param entityClass            实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    private UpdateCriteria( Class<T> entityClass, AtomicInteger parameterSequence, AtomicInteger aliasSequence,
                          Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        this.entityClass = entityClass;
        this.parameterSequence = parameterSequence;
        this.aliasSequence = aliasSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
        this.initAlias();
    }

    @Override
    protected UpdateCriteria<T> instance( AtomicInteger parameterSequence, AtomicInteger aliasSequence, 
                                          Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new UpdateCriteria<>( entityClass, parameterSequence, aliasSequence, parameterValueMappings,
                new SegmentManager() );
    }

    @Override
    public AbstractCriteriaWrapper<T> deepClone() {
        return null;
    }
}
