package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.factory.AbstractCriteriaBuilderFactory;
import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.paging.Pageable;
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
 * @author DT
 */
public abstract class AbstractServiceExecutor<Executor extends MapperExecutor<T, R>, T, R> extends AbstractCriteriaBuilderFactory<T>
        implements ServiceExecutor<T, R> {

    @Inject
    protected Executor executor;

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int save( T entity ) {
        return this.executor.insert( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int saveSelective( T entity ) {
        return this.executor.insertSelective( entity );
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
    public int updateSelective( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateSelective( entity );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int updateSelective( T entity, Criteria<T> criteria ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.mixinUpdateSelective( entity, criteria.enableAlias( false ) );
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
        List<? extends Serializable> list = idList.stream().filter( Objects::nonNull ).collect( Collectors.toCollection( ArrayList::new ) );
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
    public <E> List<E> listForCustom( Criteria<T> criteria ) {
        if ( criteria != null && ( criteria.getResultType() != null || StringUtil.hasText( criteria.getResultMap() ) ) ) {
            List<Object> result = executor.listForObject( criteria );
            return Optional.ofNullable( result ).map( value -> ( List<E> ) value ).orElse( new ArrayList<>() );
        }
        return new ArrayList<>();
    }

    @Override
    public List<Object> listForObject( Criteria<T> criteria ) {
        return executor.listForObject( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<Object[]> listForArray( Criteria<T> criteria ) {
        return executor.listForArray( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<Map<String, Object>> listForMap( Criteria<T> criteria ) {
        return executor.listForMap( criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public List<R> list( Pageable pageable, T entity ) {
        return executor.pageableList( pageable, entity );
    }

    @Override
    public List<R> list( Pageable pageable, Criteria<T> criteria ) {
        return executor.pageableListByCriteria( pageable, criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }
}
