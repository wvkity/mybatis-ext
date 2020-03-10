package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.batch.BatchDataBeanWrapper;
import com.wkit.lost.mybatis.binding.MyBatisMapperMethod;
import com.wkit.lost.mybatis.core.criteria.Criteria;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.factory.AbstractCriteriaBuilderFactory;
import com.wkit.lost.mybatis.mapper.BaseMapperExecutor;
import com.wkit.lost.mybatis.session.SqlSessionUtil;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.ClassUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.paging.Pageable;
import org.apache.ibatis.session.SqlSession;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 泛型接口
 * @param <T> 实体类
 * @param <R> 返回值类型
 * @author wvkity
 */
public abstract class AbstractBaseServiceExecutor<Executor extends BaseMapperExecutor<T, R>, T, R>
        extends AbstractCriteriaBuilderFactory<T> implements BaseServiceExecutor<T, R> {

    protected static final String METHOD_INSERT = "insert";
    protected static final String METHOD_INSERT_NOT_WITH_NULL = "insertNotWithNull";

    @Inject
    protected Executor executor;

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int save( T entity ) {
        return this.executor.insert( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int saveNotWithNull( T entity ) {
        return this.executor.insertNotWithNull( entity );
    }

    @SuppressWarnings( { "unchecked" } )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSave( T... entities ) {
        return batchSave( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSave( Collection<T> entities ) {
        return batchSave( entities, DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSave( Collection<T> entities, int batchSize ) {
        return batchSave( BatchDataBeanWrapper.wrap( entities, batchSize ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSave( BatchDataBeanWrapper<T> wrapper ) {
        return executor.batchInsert( wrapper );
    }

    @SuppressWarnings( { "unchecked" } )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSaveNotWithAudit( T... entities ) {
        return batchSaveNotWithAudit( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSaveNotWithAudit( Collection<T> entities ) {
        return batchSaveNotWithAudit( entities, DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSaveNotWithAudit( Collection<T> entities, int batchSize ) {
        return batchSaveNotWithAudit( BatchDataBeanWrapper.wrap( entities, batchSize ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchSaveNotWithAudit( BatchDataBeanWrapper<T> wrapper ) {
        return executor.batchInsertNotWithAudit( wrapper );
    }

    @SuppressWarnings( { "unchecked" } )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSave( T... entities ) {
        return embeddedBatchSave( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSave( Collection<T> entities ) {
        return embeddedBatchSave( entities, DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSave( Collection<T> entities, int batchSize ) {
        return execBatchMethod( entities, batchSize, METHOD_INSERT );
    }

    @SuppressWarnings( { "unchecked" } )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSaveNotWithNull( T... entities ) {
        return embeddedBatchSaveNotWithNull( ArrayUtil.toList( entities ), DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSaveNotWithNull( Collection<T> entities ) {
        return embeddedBatchSaveNotWithNull( entities, DEFAULT_BATCH_SIZE );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int embeddedBatchSaveNotWithNull( Collection<T> entities, int batchSize ) {
        return execBatchMethod( entities, batchSize, METHOD_INSERT_NOT_WITH_NULL );
    }

    private int execBatchMethod( Collection<T> entities, int batchSize, String method ) {
        if ( CollectionUtil.isEmpty( entities ) ) {
            return 0;
        }
        SqlSession session = batchSession();
        int i = 0;
        String statement = sqlStatement( method );
        Map<String, Object> param = new MyBatisMapperMethod.ParamMap<>();
        for ( T entity : entities ) {
            param.put( Constants.PARAM_ENTITY, entity );
            session.insert( statement, param );
            if ( i >= 1 && i % batchSize == 0 ) {
                session.flushStatements();
            }
            i++;
        }
        session.flushStatements();
        return i;
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int update( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.update( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int update( Criteria<T> criteria ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateByCriteria( criteria.enableAlias( false ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int updateNotWithNull( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateNotWithNull( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int updateNotWithNull( T entity, Criteria<T> criteria ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.mixinUpdateNotWithNull( entity, criteria.enableAlias( false ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int updateNotWithLocking( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateNotWithLocking( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int updateNotWithNullAndLocking( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateNotWithNullAndLocking( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int delete( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.delete( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int delete( Serializable id ) {
        if ( id == null ) {
            throw new MyBatisException( "The primary key parameter cannot be empty" );
        }
        return executor.deleteById( id );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int delete( Criteria<T> criteria ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The specified criteria parameter cannot be null" );
        }
        return executor.deleteByCriteria( criteria.enableAlias( false ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int logicDelete( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.logicDelete( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int logicDelete( Criteria<T> criteria ) {
        if ( criteria == null ) {
            throw new MyBatisException( "The specified criteria parameter cannot be null" );
        }
        return executor.logicDeleteByCriteria( criteria );
    }

    @SuppressWarnings( "unchecked" )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDelete( T... entities ) {
        return batchDeleteByEntities( Arrays.asList( entities ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDeleteByEntities( Collection<T> entities ) {
        if ( entities == null ) {
            throw new MyBatisException( "The entity object collection parameter cannot be empty." );
        }
        List<T> list = entities.stream().filter( Objects::nonNull ).collect( Collectors.toCollection( ArrayList::new ) );
        if ( CollectionUtil.isEmpty( list ) ) {
            throw new MyBatisException( "The entity object collection parameter cannot be empty." );
        }
        return executor.batchDelete( list );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDelete( Serializable... idArray ) {
        return batchDelete( ArrayUtil.toList( idArray ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDelete( Collection<? extends Serializable> idList ) {
        if ( CollectionUtil.isEmpty( idList ) ) {
            throw new MyBatisException( "Primary key set parameters cannot be empty" );
        }
        List<? extends Serializable> list = idList.stream()
                .filter( Objects::nonNull )
                .collect( Collectors.toCollection( ArrayList::new ) );
        if ( CollectionUtil.isEmpty( list ) ) {
            throw new MyBatisException( "Primary key set parameters cannot be empty" );
        }
        return executor.batchDeleteById( list );
    }

    @Override
    public boolean exists( T entity ) {
        return executor.exists( entity ) > 0;
    }

    @Override
    public boolean exists( Criteria<T> criteria ) {
        return executor.existsByCriteria( criteria ) > 0;
    }

    @Override
    public boolean exists( Serializable id ) {
        return this.executor.existsById( id ) > 0;
    }

    @Override
    public long count( T entity ) {
        return executor.count( entity );
    }

    @Override
    public Optional<R> selectOne( Serializable id ) {
        return id == null ? Optional.empty() : executor.selectOne( id );
    }

    @Override
    public List<R> list( Serializable... idArray ) {
        return ArrayUtil.isEmpty( idArray ) ? new ArrayList<>() : list( Arrays.asList( idArray ) );
    }

    public List<R> list( Collection<? extends Serializable> idList ) {
        List<? extends Serializable> pks = Optional.ofNullable( idList )
                .orElse( new ArrayList<>() )
                .stream()
                .filter( Objects::nonNull )
                .collect( Collectors.toList() );
        return CollectionUtil.isEmpty( pks ) ? new ArrayList<>() : executor.list( pks );
    }

    @Override
    public List<R> list( T entity ) {
        return entity == null ? new ArrayList<>() : executor.listByEntity( entity );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<R> list( T... entities ) {
        return ArrayUtil.isEmpty( entities ) ? new ArrayList<>() : listByEntities( Arrays.asList( entities ) );
    }

    @Override
    public List<R> listByEntities( Collection<T> entities ) {
        List<T> list = Optional.ofNullable( entities )
                .orElse( new ArrayList<>() )
                .stream()
                .filter( Objects::nonNull )
                .collect( Collectors.toList() );
        return CollectionUtil.isEmpty( list ) ? new ArrayList<>() : executor.listByEntities( list );
    }

    @Override
    public List<R> list( Criteria<T> criteria ) {
        return criteria == null ? new ArrayList<>() : executor.listByCriteria( criteria.resultMap( null ).resultType( null ) );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <E> List<E> custom( Criteria<T> criteria ) {
        if ( criteria != null && ( criteria.resultType() != null || StringUtil.hasText( criteria.resultMap() ) ) ) {
            List<Object> result = executor.objectList( criteria );
            return Optional.ofNullable( result ).map( value -> ( List<E> ) value ).orElse( new ArrayList<>() );
        }
        return new ArrayList<>();
    }

    @Override
    public List<Object> objects( Criteria<T> criteria ) {
        return executor.objectList( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<Object[]> array( Criteria<T> criteria ) {
        return executor.arrayList( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<Map<String, Object>> map( Criteria<T> criteria ) {
        return executor.mapList( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<R> list( T entity, Pageable pageable ) {
        return executor.pageableList( entity, pageable );
    }

    @Override
    public List<R> list( Criteria<T> criteria, Pageable pageable ) {
        return executor.pageableListByCriteria( criteria.resultMap( null ).resultType( null ), pageable );
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }

    /**
     * 获取SQL statement
     * @param method 方法名
     * @return sql statement
     */
    protected String sqlStatement( String method ) {
        return TableHandler.getTable( getEntityClass() ).getSqlStatement( method );
    }

    /**
     * 获取实体类
     * @return {@link Class}
     */
    @SuppressWarnings( { "unchecked" } )
    protected Class<T> getEntityClass() {
        return ( Class<T> ) ClassUtil.getGenericType( getClass(), 1 );
    }

    /**
     * 获取批量操作{@link SqlSession}
     * @return {@link SqlSession}
     */
    protected SqlSession batchSession() {
        return SqlSessionUtil.batchSession( getEntityClass() );
    }

    /**
     * 释放批量操作{@link SqlSession}
     * @param session {@link SqlSession}
     */
    protected void closeSession( SqlSession session ) {
        SqlSessionUtil.closeSession( session, getEntityClass() );
    }
}
