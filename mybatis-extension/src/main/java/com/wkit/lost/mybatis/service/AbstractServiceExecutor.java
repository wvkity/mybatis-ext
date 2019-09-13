package com.wkit.lost.mybatis.service;

import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.exception.MyBatisException;
import com.wkit.lost.mybatis.factory.AbstractCriteriaBuilderFactory;
import com.wkit.lost.mybatis.mapper.MapperExecutor;
import com.wkit.lost.paging.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
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
 * @param <T>  实体类
 * @param <PK> 主键类型
 * @param <R>  返回值类型
 * @author DT
 */
public abstract class AbstractServiceExecutor<Executor extends MapperExecutor<T, PK, R>, T, PK, R> extends AbstractCriteriaBuilderFactory<T>
        implements ServiceExecutor<T, PK, R> {

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
    public int updateSelective( T entity ) {
        if ( entity == null ) {
            throw new MyBatisException( "The specified object parameter cannot be null" );
        }
        return executor.updateSelective( entity );
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
    public int deleteById( PK primaryKey ) {
        if ( primaryKey == null ) {
            throw new MyBatisException( "The primary key parameter cannot be empty" );
        }
        return executor.deleteById( primaryKey );
    }

    @SuppressWarnings( "unchecked" )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDelete( T... entities ) {
        return batchDelete( Arrays.asList( entities ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDelete( Collection<T> entities ) {
        if ( CollectionUtil.isEmpty( entities ) ) {
            throw new MyBatisException( "The specified object collection parameter cannot be empty" );
        }
        List<T> list = entities.stream().filter( Objects::nonNull ).collect( Collectors.toCollection( ArrayList::new ) );
        if ( CollectionUtil.isEmpty( list ) ) {
            throw new MyBatisException( "The specified object collection parameter cannot be empty" );
        }
        return executor.batchDelete( list );
    }

    @SuppressWarnings( "unchecked" )
    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDeleteById( PK... primaryKeys ) {
        return batchDeleteById( Arrays.asList( primaryKeys ) );
    }

    @Transactional( rollbackFor = Exception.class )
    @Override
    public int batchDeleteById( Collection<PK> primaryKeys ) {
        if ( CollectionUtil.isEmpty( primaryKeys ) ) {
            throw new MyBatisException( "The primary key collection parameter cannot be empty" );
        }
        List<PK> list = primaryKeys.stream().filter( Objects::nonNull ).collect( Collectors.toCollection( ArrayList::new ) );
        if ( CollectionUtil.isEmpty( list ) ) {
            throw new MyBatisException( "The primary key collection parameter cannot be empty" );
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
    public boolean existsById( PK primaryKey ) {
        return this.executor.existsById( primaryKey ) > 0;
    }

    @Override
    public long count( T entity ) {
        return executor.count( entity );
    }

    @Override
    public Optional<R> selectOne( PK primaryKey ) {
        return primaryKey == null ? Optional.empty() : executor.selectOne( primaryKey );
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public List<R> listById( PK... primaryKeys ) {
        return ArrayUtil.isEmpty( primaryKeys ) ? new ArrayList<>() : listById( Arrays.asList( primaryKeys ) );
    }

    @Override
    public List<R> listById( Collection<PK> primaryKeys ) {
        List<PK> pks = Optional.ofNullable( primaryKeys )
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
        return ArrayUtil.isEmpty( entities ) ? new ArrayList<>() : list( Arrays.asList( entities ) );
    }

    @Override
    public List<R> list( Collection<T> entities ) {
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
    public List<R> pageableList( Pageable pageable, T entity ) {
        return executor.pageableList( pageable, entity );
    }

    @Override
    public List<R> pageableList( Pageable pageable, Criteria<T> criteria ) {
        return executor.pageableListByCriteria( pageable, criteria.resultMap( null ).resultType( null ) );
    }

    @Override
    public Executor getExecutor() {
        return executor;
    }
}
