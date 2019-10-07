package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import lombok.Getter;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 子查询联表条件类
 * @param <T> 泛型类型
 * @author DT
 */
public class ForeignSubCriteria<T> extends ForeignCriteria<T> {

    private static final long serialVersionUID = -2184486345797036291L;

    /**
     * 子查询条件对象
     */
    @Getter
    protected SubCriteria<?> subCriteria;

    /**
     * 构造方法
     * @param subCriteria            子查询条件对象
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     */
    public <E> ForeignSubCriteria( SubCriteria<T> subCriteria, AbstractQueryCriteria<E> master, Foreign foreign,
                                   AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings ) {
        this( subCriteria, null, master, foreign, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param subCriteria            子查询条件对象
     * @param reference              引用属性
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     */
    public <E> ForeignSubCriteria( SubCriteria<T> subCriteria, String reference, AbstractQueryCriteria<E> master,
                                   Foreign foreign, AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings ) {
        this( subCriteria, reference, master, foreign, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param subCriteria            子查询条件对象
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     */
    public <E> ForeignSubCriteria( SubCriteria<T> subCriteria, AbstractQueryCriteria<E> master, Foreign foreign,
                                   AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                                   Collection<Criterion<?>> withClauses ) {
        this( subCriteria, null, master, foreign, parameterSequence, parameterValueMappings, withClauses );
    }

    /**
     * 构造方法
     * @param subCriteria            子查询条件对象
     * @param reference              引用属性
     * @param master                 主表查询对象
     * @param foreign                连接方式
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     */
    public <E> ForeignSubCriteria( SubCriteria<T> subCriteria, String reference, AbstractQueryCriteria<E> master,
                                   Foreign foreign, AtomicInteger parameterSequence,
                                   Map<String, Object> parameterValueMappings, Collection<Criterion<?>> withClauses ) {
        super( null, subCriteria.getSubTempTabAlias(), reference, master, foreign,
                parameterSequence, parameterValueMappings, withClauses );
        this.subCriteria = subCriteria;
    }

    /**
     * 构造方法
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数-值映射
     * @param segmentManager         SQL片段管理器
     */
    private ForeignSubCriteria( AtomicInteger parameterSequence,
                                Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        super( null, parameterSequence, parameterValueMappings, segmentManager );
        this.conditionManager = new ConditionManager<>( this );
    }

    @Override
    protected ForeignSubCriteria<T> instance( AtomicInteger parameterSequence,
                                              Map<String, Object> parameterValueMappings, SegmentManager segmentManager ) {
        return new ForeignSubCriteria<>( parameterSequence, parameterValueMappings, new SegmentManager() );
    }

    public String getTableSegment() {
        return subCriteria.getSqlSegmentForCondition();
    }

    @Override
    protected Map<String, Column> getQueryColumns() {
        if ( this.isRelation() ) {
            return subCriteria.getQueryColumns();
        }
        return new LinkedHashMap<>( 0 );
    }
}
