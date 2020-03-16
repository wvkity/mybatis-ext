package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.aggregate.Aggregation;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.core.wrapper.AbstractQueryWrapper;
import com.wkit.lost.mybatis.core.wrapper.AbstractWrapper;
import com.wkit.lost.mybatis.core.wrapper.ColumnQuery;
import com.wkit.lost.mybatis.core.wrapper.StringQuery;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings( "serial" )
public abstract class AbstractQueryCriteria<T> extends AbstractGeneralQueryCriteria<T, AbstractQueryCriteria<T>>
        implements Query<T, AbstractQueryCriteria<T>> {

    /**
     * 是否开启自动映射列别名(自动映射属性名)
     */
    @Getter
    protected boolean columnAliasAutoMapping = false;

    /**
     * 查询字段(SQL片段)
     */
    protected String querySegment;

    /**
     * 查询属性(列)缓存
     */
    protected Map<String, ColumnWrapper> propertyForQueryCache = new ConcurrentSkipListMap<>();

    /**
     * 属性-别名缓存
     */
    protected Map<String, String> propertyForQueryAliasCache = new ConcurrentSkipListMap<>();

    /**
     * 子查询条件对象缓存
     */
    protected Map<String, SubCriteria<?>> subCriteriaCache = new ConcurrentSkipListMap<>();

    /**
     * 子查询列缓存
     */
    protected Map<SubCriteria<?>, Map<String, String>> subQueryCache = new ConcurrentHashMap<>( 8 );

    /**
     * 排除列(属性)
     */
    protected Set<String> excludes = new ConcurrentSkipListSet<>();

    @Override
    public AbstractQueryCriteria<T> columnAliasAutoMapping() {
        return columnAliasAutoMapping( true );
    }

    @Override
    public AbstractQueryCriteria<T> columnAliasAutoMapping( boolean enable ) {
        this.columnAliasAutoMapping = enable;
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> query( String property ) {
        this.queryManager.add( ColumnQuery.Single.query( this, property ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> query( String property, String columnAlias ) {
        this.queryManager.add( ColumnQuery.Single.query( this, property, columnAlias ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQuery( String column ) {
        this.queryManager.add( StringQuery.Single.query( this, column, null ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQuery( String column, String columnAlias ) {
        this.queryManager.add( StringQuery.Single.query( column, columnAlias ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQuery( String tableAlias, String column, String columnAlias ) {
        this.queryManager.add( StringQuery.Single.query( tableAlias, column, columnAlias ) );
        return this;
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQuery( SubCriteria<E> criteria, String property, String columnAlias ) {
        return ifPresent( criteria, it ->
                this.queryManager.add( ColumnQuery.Single.query( criteria.getForeignTarget(),
                        it.searchColumn( property ), columnAlias ) ) );
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQuery( SubCriteria<E> criteria, String property ) {
        return ifPresent( criteria, it -> this.queryManager.add( ColumnQuery.Single.query( criteria.getForeignTarget(),
                it.searchColumn( property ) ) ) );
    }

    @Override
    public AbstractQueryCriteria<T> queries( Collection<String> properties ) {
        this.queryManager.add( ColumnQuery.Multi.query( this, properties ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> queries( Map<String, String> properties ) {
        this.queryManager.add( ColumnQuery.Multi.query( this, properties ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQueries( Collection<String> columns ) {
        this.queryManager.add( StringQuery.Multi.query( this, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQueries( Map<String, String> columns ) {
        this.queryManager.add( StringQuery.Multi.query( this, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQueries( String tableAlias, Map<String, String> columns ) {
        this.queryManager.add( StringQuery.Multi.query( tableAlias, columns ) );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateQueries( String tableAlias, Collection<String> columns ) {
        this.queryManager.add( StringQuery.Multi.query( tableAlias, columns ) );
        return this;
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQueries( SubCriteria<E> criteria, Collection<String> properties ) {
        return ifPresent( criteria, it -> this.queryManager.add( ColumnQuery.Multi.query( criteria.getForeignTarget(),
                CriteriaUtil.propertyToColumn( it, properties ) ) ) );
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQueries( SubCriteria<E> criteria, Map<String, String> properties ) {
        return ifPresent( criteria, it -> {
            Map<String, String> its = AbstractQueryWrapper.filterNullValue( properties );
            if ( CollectionUtil.hasElement( its ) ) {
                Criteria<?> target = criteria.getForeignTarget();
                for ( Map.Entry<String, String> entry : its.entrySet() ) {
                    this.queryManager.add( ColumnQuery.Single.query( target,
                            criteria.searchColumn( entry.getValue() ), entry.getKey() ) );
                }
            }
        } );
    }

    @Override
    public AbstractQueryCriteria<T> excludes( Collection<String> properties ) {
        this.queryManager.excludes( properties );
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> immediateExcludes( Collection<String> columns ) {
        this.queryManager.immediateExcludes( columns );
        return this;
    }

    protected <E> AbstractQueryCriteria<T> ifPresent( Criteria<E> criteria,
                                                      Consumer<? super Criteria<E>> consumer ) {
        Optional.ofNullable( criteria ).ifPresent( consumer );
        return this;
    }

    /**
     * 获取所有查询列
     * @return 查询列集合
     */
    protected Map<String, ColumnWrapper> getQueryColumns() {
        Map<String, ColumnWrapper> realQueries;
        if ( CollectionUtil.hasElement( propertyForQueryCache ) ) {
            // 显式指定查询列
            realQueries = propertyForQueryCache;
        } else {
            // 所有列
            realQueries = Collections.synchronizedMap(
                    TableHandler.getTable( entityClass )
                            .columns()
                            .stream()
                            .collect( Collectors.toMap( ColumnWrapper::getProperty, Function.identity(),
                                    ( oldValue, newValue ) -> newValue, LinkedHashMap::new ) )
            );
        }
        // 排除
        exclude( realQueries, this.excludes );
        return realQueries;
    }

    protected final void exclude( Map<String, ColumnWrapper> columns, Set<String> excludes ) {
        if ( CollectionUtil.hasElement( columns ) && CollectionUtil.hasElement( excludes ) ) {
            Iterator<Map.Entry<String, ColumnWrapper>> iterator = columns.entrySet().iterator();
            while ( iterator.hasNext() ) {
                Map.Entry<String, ColumnWrapper> entry = iterator.next();
                String key = entry.getKey();
                if ( excludes.contains( key ) ) {
                    iterator.remove();
                }
            }
        }
    }

    @Override
    protected String getGroupSegment() {
        return " GROUP BY " + getQuerySegment( false );
    }

    /*@Override
    public String getQuerySegment() {
        // 检查是否只查询聚合函数
        if ( onlyFunctionForQuery ) {
            return getFunctionSegment();
        } else {
            // 检查是否包含聚合函数查询
            if ( includeFunctionForQuery ) {
                String functionSegment = getFunctionSegment();
                if ( StringUtil.hasText( functionSegment ) ) {
                    return getQuerySegment( true ) + ", " + functionSegment;
                }
            }
        }
        return getQuerySegment( true );
    }*/

    /**
     * 获取所有查询列
     * @return 查询列集合
     */
    public List<AbstractQueryWrapper<?, ?>> getQueries() {
        return this.queryManager.getQueries();
    }

    @Override
    public String getQuerySegment() {
        if ( onlyFunctionForQuery ) {

        } else {
            return getQuerySegment( true );
        }
        return getQuerySegment( true );
    }

    private String getQuerySegment( boolean applyQuery ) {
        List<String> segments = new ArrayList<>();
        // 主表查询列
        String selfSqlSegment = this.queryManager.getSqlSegment( applyQuery );
        if ( Ascii.hasText( selfSqlSegment ) ) {
            segments.add( selfSqlSegment );
        }
        // 子查询列
        loopSubQueries( this, segments );
        // 副表查询列
        if ( CollectionUtil.hasElement( this.foreignCriteriaSet ) ) {
            this.foreignCriteriaSet.stream().filter( Objects::nonNull ).forEach( it -> {
                String sqlSegment = it.queryManager.getSqlSegment( applyQuery );
                if ( Ascii.hasText( sqlSegment ) ) {
                    segments.add( sqlSegment );
                }
                // 子查询列
                loopSubQueries( it, segments );
            } );
        }
        return CollectionUtil.hasElement( segments ) ? String.join( ", ", segments ) : "";
    }

    private void loopSubQueries( AbstractQueryCriteria<?> criteria, List<String> segments ) {

    }

    /*private String getQuerySegment( boolean applyQuery ) {
        Map<String, ColumnWrapper> realQueries = getQueryColumns();
        List<String> queryColumns = new ArrayList<>();
        // 主表查询字段
        if ( CollectionUtil.hasElement( realQueries ) ) {
            for ( Map.Entry<String, ColumnWrapper> entry : realQueries.entrySet() ) {
                ColumnWrapper column = entry.getValue();
                String property = column.getProperty();
                String realColumnAlias = propertyForQueryAliasCache.get( property );
                if ( realColumnAlias == null ) {
                    queryColumns.add( ColumnConvert.convertToQueryArg( column, this.enableAlias ? getAlias() : null,
                            applyQuery ? this.reference : null, applyQuery && this.columnAliasAutoMapping ) );
                } else {
                    queryColumns.add( ColumnConvert.convertToQueryArg( column.getColumn(), realColumnAlias,
                            this.enableAlias ? getAlias() : null ) );
                }
            }
        }
        // 子查询列
        loopSubQueries( this, queryColumns );
        // 副表查询字段
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            foreignCriteriaSet.stream()
                    .filter( Objects::nonNull )
                    .forEach( it -> {
                        Map<String, ColumnWrapper> foreignQueries = it.getQueryColumns();
                        Map<String, String> queryAliasCache = it.propertyForQueryAliasCache;
                        if ( CollectionUtil.hasElement( foreignQueries ) ) {
                            for ( Map.Entry<String, ColumnWrapper> entry : foreignQueries.entrySet() ) {
                                ColumnWrapper column = entry.getValue();
                                String property = column.getProperty();
                                String realColumnAlias = queryAliasCache.get( property );
                                if ( realColumnAlias == null ) {
                                    queryColumns.add( ColumnConvert.convertToQueryArg( column,
                                            it.getAlias(), applyQuery ? it.getReference() : null,
                                            applyQuery && it.isColumnAliasAutoMapping() ) );
                                } else {
                                    queryColumns.add( ColumnConvert.convertToQueryArg( column.getColumn(),
                                            realColumnAlias, it.getAlias() ) );
                                }
                            }
                        }
                        // 子查询列
                        loopSubQueries( it, queryColumns );
                    } );
        }
        return CollectionUtil.hasElement( queryColumns ) ? String.join( ", ", queryColumns ) : "";
    }

    protected void loopSubQueries( AbstractQueryCriteria<?> queryCriteria, List<String> queries ) {
        Map<SubCriteria<?>, Map<String, String>> subQueries = queryCriteria.subQueryCache;
        if ( !subQueries.isEmpty() ) {
            // 遍历临时表
            for ( Map.Entry<SubCriteria<?>, Map<String, String>> rootEntry : subQueries.entrySet() ) {
                SubCriteria<?> criteria = rootEntry.getKey();
                ForeignCriteria<?> foreign = criteria.getForeignTarget();
                String defAlias = foreign.getAlias();
                String tempTabAlias = foreign.subTempTabAlias;
                String realAlias = Ascii.isNullOrEmpty( tempTabAlias ) ? defAlias : tempTabAlias;
                Map<String, String> columns = rootEntry.getValue();
                // 遍历查询字段
                if ( !columns.isEmpty() ) {
                    for ( Map.Entry<String, String> columnEntry : columns.entrySet() ) {
                        String column = columnEntry.getKey();
                        String columnAlias = columnEntry.getValue();
                        queries.add( ColumnConvert.convertToQueryArg( column, columnAlias, realAlias ) );
                    }
                }
            }
        }
    }*/

    /**
     * 获取聚合函数SQL片段
     * @return SQL片段
     */
    private String getFunctionSegment() {
        if ( !this.aggregations.isEmpty() ) {
            return aggregations.stream().map( Aggregation::toQuerySqlSegment ).collect( Collectors.joining( ", " ) );
        }
        return "";
    }
}
