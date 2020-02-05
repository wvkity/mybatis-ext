package com.wkit.lost.mybatis.core.criteria;

import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.Ascii;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.ColumnConvert;
import com.wkit.lost.mybatis.utils.StringUtil;
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
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings( "serial" )
public abstract class AbstractQueryCriteria<T> extends AbstractChainCriteriaWrapper<T, AbstractQueryCriteria<T>>
        implements Query<T, AbstractQueryCriteria<T>> {

    /**
     * 是否开启自动映射列别名(自动映射属性名)
     */
    @Getter
    protected boolean autoMappingAlias = false;

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
    public AbstractQueryCriteria<T> autoMappingColumnAlias() {
        return autoMappingColumnAlias( true );
    }

    @Override
    public AbstractQueryCriteria<T> autoMappingColumnAlias( boolean enable ) {
        this.autoMappingAlias = enable;
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> query( Property<T, ?> property ) {
        return query( lambdaToProperty( property ) );
    }

    @Override
    public AbstractQueryCriteria<T> query( String property ) {
        if ( StringUtil.hasText( property ) ) {
            ColumnWrapper column = searchColumn( property );
            if ( column != null ) {
                propertyForQueryCache.put( property, column );
            }
        }
        return this;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public AbstractQueryCriteria<T> query( Property<T, ?>... properties ) {
        return query( lambdaToProperty( properties ) );
    }

    @Override
    public AbstractQueryCriteria<T> query( String... properties ) {
        return query( ArrayUtil.toList( properties ) );
    }

    @Override
    public AbstractQueryCriteria<T> query( Collection<String> properties ) {
        if ( CollectionUtil.hasElement( properties ) ) {
            properties.forEach( this::query );
        }
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> query( Property<T, ?> property, String columnAlias ) {
        return query( lambdaToProperty( property ), columnAlias );
    }

    @Override
    public AbstractQueryCriteria<T> query( String property, String columnAlias ) {
        if ( StringUtil.hasText( property ) ) {
            ColumnWrapper column = searchColumn( property );
            if ( column != null ) {
                this.propertyForQueryCache.put( property, column );
                this.propertyForQueryAliasCache.put( property, columnAlias );
            }
        }
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> subQuery( String subTempTabAlias, String... columns ) {
        if ( StringUtil.hasText( subTempTabAlias ) && !ArrayUtil.isEmpty( columns ) ) {
            SubCriteria<?> subCriteria =
                    getRootMaster().subCriteriaCache.getOrDefault( subTempTabAlias, null );
            for ( String column : columns ) {
                if ( StringUtil.hasText( column ) ) {
                    subQuery( subCriteria, column, "" );
                }
            }
        }
        return this;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <E> AbstractQueryCriteria<T> subQuery( SubCriteria<E> subCriteria, Property<E, ?>... properties ) {
        if ( subCriteria != null && !ArrayUtil.isEmpty( properties ) ) {
            for ( Property<E, ?> property : properties ) {
                if ( property != null ) {
                    subQuery( subCriteria, subCriteria.methodToProperty( property ), null );
                }
            }
        }
        return this;
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQuery( SubCriteria<E> subCriteria, Property<E, ?> property,
                                                  String columnAlias ) {
        return subQuery( subCriteria, subCriteria.methodToProperty( property ), columnAlias );
    }

    @Override
    public AbstractQueryCriteria<T> subQuery( String subTempTabAlias, String column, String alias ) {
        if ( StringUtil.hasText( subTempTabAlias ) && StringUtil.hasText( column ) ) {
            subQuery( this.subCriteriaCache.getOrDefault( subTempTabAlias, null ), column, alias );
        }
        return this;
    }

    @Override
    public <E> AbstractQueryCriteria<T> subQuery( SubCriteria<E> subCriteria, String column, String alias ) {
        if ( subCriteria != null && Ascii.hasText( column ) ) {
            Map<String, String> columns = subQueryCache.getOrDefault( subCriteria, null );
            String realColumn = Optional.ofNullable( subCriteria.searchColumn( column ) )
                    .map( ColumnWrapper::getColumn )
                    .orElse( column );
            if ( columns == null ) {
                columns = new LinkedHashMap<>( 8 );
                columns.put( realColumn, Ascii.isNullOrEmpty( alias ) ? "" : alias );
                subQueryCache.put( subCriteria, columns );
            } else {
                columns.put( realColumn, alias );
            }
        }
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> exclude( Property<T, ?> property ) {
        return exclude( lambdaToProperty( property ) );
    }

    @Override
    public AbstractQueryCriteria<T> exclude( String property ) {
        if ( StringUtil.hasText( property ) ) {
            this.excludes.add( property );
        }
        return this;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public AbstractQueryCriteria<T> exclude( Property<T, ?>... properties ) {
        return exclude( lambdaToProperty( properties ) );
    }

    @Override
    public AbstractQueryCriteria<T> exclude( String... properties ) {
        return exclude( ArrayUtil.toList( properties ) );
    }

    @Override
    public AbstractQueryCriteria<T> exclude( Collection<String> properties ) {
        if ( CollectionUtil.hasElement( properties ) ) {
            this.excludes.addAll( properties.stream().filter( StringUtil::hasText ).collect( Collectors.toList() ) );
        }
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

    @Override
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
    }

    private String getQuerySegment( boolean applyQuery ) {
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
                            applyQuery ? this.reference : null, applyQuery && this.autoMappingAlias ) );
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
                                            applyQuery && it.isAutoMappingAlias() ) );
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
    }

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
