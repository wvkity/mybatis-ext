package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.condition.ConditionManager;
import com.wkit.lost.mybatis.core.condition.criterion.Criterion;
import com.wkit.lost.mybatis.core.segment.SegmentManager;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Criteria子查询条件类
 * @param <T> 泛型类型
 * @author DT
 */
@Accessors( chain = true )
public class SubCriteria<T> extends AbstractQueryCriteria<T> {

    private static final long serialVersionUID = 4463145832039884030L;

    /**
     * 构造方法
     * @param entity 实体类
     */
    private SubCriteria( Class<T> entity ) {
        this.entity = entity;
    }

    /**
     * 创建实例(用于复制)
     * @param entity 实体类
     * @param <T>    泛型类型
     * @return 实例
     */
    static <T> SubCriteria<T> newInstanceForClone( Class<T> entity ) {
        return new SubCriteria<>( entity );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param master                 主表
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, AbstractQueryCriteria<E> master, AtomicInteger parameterSequence,
                            Map<String, Object> parameterValueMappings ) {
        this( entity, null, master, null, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  别名
     * @param master                 主表
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, AtomicInteger parameterSequence,
                            Map<String, Object> parameterValueMappings ) {
        this( entity, alias, master, null, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param master                 主表
     * @param subTempTabAlias        临时表别名
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings ) {
        this( entity, null, master, subTempTabAlias, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  别名
     * @param master                 主表
     * @param subTempTabAlias        临时表别名
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings ) {
        this( entity, alias, master, subTempTabAlias, parameterSequence, parameterValueMappings, null );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param master                 主表
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, AbstractQueryCriteria<E> master, AtomicInteger parameterSequence,
                            Map<String, Object> parameterValueMappings, Collection<Criterion<?>> withClauses ) {
        this( entity, null, master, null, parameterSequence, parameterValueMappings, withClauses );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  别名
     * @param master                 主表
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, AtomicInteger parameterSequence,
                            Map<String, Object> parameterValueMappings, Collection<Criterion<?>> withClauses ) {
        this( entity, alias, master, null, parameterSequence, parameterValueMappings, withClauses );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param master                 主表
     * @param subTempTabAlias        临时表别名
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                            Collection<Criterion<?>> withClauses ) {
        this( entity, null, master, subTempTabAlias, parameterSequence, parameterValueMappings, withClauses );
    }

    /**
     * 构造方法
     * @param entity                 实体类
     * @param alias                  别名
     * @param master                 主表
     * @param subTempTabAlias        临时表别名
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param withClauses            条件
     * @param <E>                    泛型类型
     */
    public <E> SubCriteria( Class<T> entity, String alias, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                            Collection<Criterion<?>> withClauses ) {
        this.entity = entity;
        this.master = master;
        this.subTempTabAlias = subTempTabAlias;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = new SegmentManager();
        this.initMappingCache( this.entity.getName(), true );
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
     * @param parameterValueMappings 参数值映射
     * @param segmentManager         SQL片段管理器
     */
    private SubCriteria( Class<T> entity, AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                         SegmentManager segmentManager ) {
        this.entity = entity;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.initMappingCache( this.entity.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
    }

    @Override
    protected AbstractQueryCriteria<T> instance( AtomicInteger parameterSequence,
                                                 Map<String, Object> parameterValueMappings,
                                                 SegmentManager segmentManager ) {
        return new SubCriteria<>( entity, parameterSequence, parameterValueMappings, new SegmentManager() );
    }

    @Override
    public AbstractQueryCriteria<T> deepClone() {
        return CriteriaCopierFactory.clone( this );
    }

    public String getSqlSegmentForCondition() {
        StringBuilder buffer = new StringBuilder( 100 );
        buffer.append( "(" ).append( "SELECT " ).append( this.getQuerySegment() ).append( " FROM " );
        buffer.append( getTableName() ).append( " " ).append( getForeignSegment() );
        if ( isHasCondition() ) {
            /*buffer.append( " WHERE " );
            String condition = getSqlSegment().trim();
            if ( condition.startsWith( "AND" ) ) {
                buffer.append( condition.substring( 3 ) );
            } else if ( condition.startsWith( "OR" ) ) {
                buffer.append( condition.substring( 2 ) );
            } else {
                buffer.append( condition );
            }*/
            buffer.append( getSqlSegment() );
        }
        buffer.append( ")" );
        return buffer.toString();
    }
}
