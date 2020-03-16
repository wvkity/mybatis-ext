package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.core.wrapper.QueryManager;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class QueryCriteria<T> extends AbstractQueryCriteria<T> {

    private static final long serialVersionUID = -1253597753461923353L;

    /**
     * 构造方法
     * @param entityClass 实体类
     */
    public QueryCriteria( Class<T> entityClass ) {
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
    public QueryCriteria( Class<T> entityClass, String alias ) {
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
    private QueryCriteria( Class<T> entityClass, AtomicInteger parameterSequence, AtomicInteger aliasSequence,
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
    protected QueryCriteria<T> instance( AtomicInteger parameterSequence, 
                                                 AtomicInteger aliasSequence, 
                                                 Map<String, Object> parameterValueMappings, 
                                                 SegmentManager segmentManager ) {
        return new QueryCriteria<>( entityClass, parameterSequence, aliasSequence, parameterValueMappings, 
                new SegmentManager() );
    }

    @Override
    public Object getModifyVersionValue() {
        return null;
    }

    @Override
    public AbstractQueryCriteria<T> deepClone() {
        return null;
    }
}
