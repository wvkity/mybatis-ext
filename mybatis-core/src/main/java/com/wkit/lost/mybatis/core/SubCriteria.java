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
     * @param entityClass 实体类
     */
    private SubCriteria( Class<T> entityClass ) {
        this.entityClass = entityClass;
    }

    /**
     * 创建实例(用于复制)
     * @param entityClass 实体类
     * @param <T>         泛型类型
     * @return 实例
     */
    static <T> SubCriteria<T> newInstanceForClone( Class<T> entityClass ) {
        return new SubCriteria<>( entityClass );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表
     * @param <E>         泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, AbstractQueryCriteria<E> master ) {
        this( entityClass, null, master, null, null );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     * @param master      主表
     * @param <E>         泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master ) {
        this( entityClass, alias, master, null, null );
    }

    /**
     * 构造方法
     * @param entityClass     实体类
     * @param master          主表
     * @param subTempTabAlias 临时表别名
     * @param <E>             泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, AbstractQueryCriteria<E> master, String subTempTabAlias ) {
        this( entityClass, null, master, subTempTabAlias, null );
    }

    /**
     * 构造方法
     * @param entityClass     实体类
     * @param alias           别名
     * @param master          主表
     * @param subTempTabAlias 临时表别名
     * @param <E>             泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master,
                            String subTempTabAlias ) {
        this( entityClass, alias, master, subTempTabAlias, null );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param master      主表
     * @param withClauses 条件
     * @param <E>         泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, AbstractQueryCriteria<E> master, Collection<Criterion<?>> withClauses ) {
        this( entityClass, null, master, null, withClauses );
    }

    /**
     * 构造方法
     * @param entityClass 实体类
     * @param alias       别名
     * @param master      主表
     * @param withClauses 条件
     * @param <E>         泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master,
                            Collection<Criterion<?>> withClauses ) {
        this( entityClass, alias, master, null, withClauses );
    }

    /**
     * 构造方法
     * @param entityClass     实体类
     * @param master          主表
     * @param subTempTabAlias 临时表别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            Collection<Criterion<?>> withClauses ) {
        this( entityClass, null, master, subTempTabAlias, withClauses );
    }

    /**
     * 构造方法
     * @param entityClass     实体类
     * @param alias           别名
     * @param master          主表
     * @param subTempTabAlias 临时表别名
     * @param withClauses     条件
     * @param <E>             泛型类型
     */
    public <E> SubCriteria( Class<T> entityClass, String alias, AbstractQueryCriteria<E> master, String subTempTabAlias,
                            Collection<Criterion<?>> withClauses ) {
        this.entityClass = entityClass;
        this.master = master;
        this.subTempTabAlias = subTempTabAlias;
        this.parameterSequence = master.parameterSequence;
        this.paramValueMappings = master.paramValueMappings;
        this.segmentManager = new SegmentManager();
        this.initMappingCache( this.entityClass.getName(), true );
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
     * @param entityClass            实体类
     * @param parameterSequence      参数序列
     * @param parameterValueMappings 参数值映射
     * @param segmentManager         SQL片段管理器
     */
    private SubCriteria( Class<T> entityClass, AtomicInteger parameterSequence, Map<String, Object> parameterValueMappings,
                         SegmentManager segmentManager ) {
        this.entityClass = entityClass;
        this.parameterSequence = parameterSequence;
        this.paramValueMappings = parameterValueMappings;
        this.segmentManager = segmentManager;
        this.initMappingCache( this.entityClass.getName(), true );
        this.conditionManager = new ConditionManager<>( this );
    }

    @Override
    protected AbstractQueryCriteria<T> instance( AtomicInteger parameterSequence,
                                                 Map<String, Object> parameterValueMappings,
                                                 SegmentManager segmentManager ) {
        return new SubCriteria<>( entityClass, parameterSequence, parameterValueMappings, new SegmentManager() );
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
            buffer.append( getWhereSqlSegment() );
        }
        buffer.append( ")" );
        return buffer.toString();
    }
}
