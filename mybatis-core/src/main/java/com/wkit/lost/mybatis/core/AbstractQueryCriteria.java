package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.ColumnUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    protected Map<String, Column> propertyForQueryCache = new ConcurrentSkipListMap<>();

    /**
     * 属性-别名缓存
     */
    protected Map<String, String> propertyForQueryAliasCache = new ConcurrentSkipListMap<>();

    /**
     * 子查询列缓存
     */
    protected Map<String, Map<String, String>> columnForSubQueryCache = new ConcurrentSkipListMap<>();

    /**
     * 排除列(属性)
     */
    protected Set<String> excludes = new ConcurrentSkipListSet<>();

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
            Column column = searchColumn( property );
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
    public AbstractQueryCriteria<T> queryFromSub( String subTempTabAlias, String... columns ) {
        if ( StringUtil.hasText( subTempTabAlias ) && !ArrayUtil.isEmpty( columns ) ) {
            Map<String, String> columnCache = this.columnForSubQueryCache.get( subTempTabAlias );
            boolean notExists = columnCache == null;
            if ( notExists ) {
                columnCache = new LinkedHashMap<>();
            }
            for ( String column : columns ) {
                if ( StringUtil.hasText( column ) ) {
                    columnCache.put( column, "" );
                }
            }
            if ( notExists ) {
                this.columnForSubQueryCache.put( subTempTabAlias, columnCache );
            }
        }
        return this;
    }

    @SuppressWarnings( "unchecked" )
    @Override
    public <E> AbstractQueryCriteria<T> queryFromSub( SubCriteria<E> subCriteria, Property<E, ?>... properties ) {
        if ( subCriteria != null && !ArrayUtil.isEmpty( properties ) ) {
            String tempTabAlias = subCriteria.getSubTempTabAlias();
            Map<String, String> columnCache = this.columnForSubQueryCache.get( tempTabAlias );
            boolean notExists = columnCache == null;
            if ( notExists ) {
                columnCache = new LinkedHashMap<>();
            }
            for ( Property<E, ?> property : properties ) {
                if ( property != null ) {
                    Column column = subCriteria.searchColumn( methodToProperty( property ) );
                    if ( column != null ) {
                        columnCache.put( column.getColumn(), "" );
                    }
                }
            }
            if ( notExists ) {
                this.columnForSubQueryCache.put( tempTabAlias, columnCache );
            }
        }
        return this;
    }

    @Override
    public <E> AbstractQueryCriteria<T> queryFromSub( SubCriteria<E> subCriteria, Property<E, ?> property, String columnAlias ) {
        return queryFromSub( subCriteria.getSubTempTabAlias(), methodToProperty( property ), columnAlias  );
    }

    @Override
    public AbstractQueryCriteria<T> queryFromSub( String subTempTabAlias, String column, String alias ) {
        if ( StringUtil.hasText( subTempTabAlias ) && StringUtil.hasText( column ) ) {
            Map<String, String> map = columnForSubQueryCache.get( subTempTabAlias );
            if ( map == null ) {
                map = new LinkedHashMap<>( 8 );
                map.put( column, alias );
                columnForSubQueryCache.put( subTempTabAlias, map );
            } else {
                map.put( column, alias );
            }
        }
        return this;
    }

    @Override
    public AbstractQueryCriteria<T> query( String property, String columnAlias ) {
        if ( StringUtil.hasText( property ) ) {
            Column column = searchColumn( property );
            if ( column != null ) {
                this.propertyForQueryCache.put( property, column );
                this.propertyForQueryAliasCache.put( property, columnAlias );
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
    protected Map<String, Column> getQueryColumns() {
        Map<String, Column> realQueries;
        if ( CollectionUtil.hasElement( propertyForQueryCache ) ) {
            // 显式指定查询列
            realQueries = propertyForQueryCache;
        } else {
            // 所有列
            realQueries = Collections.synchronizedMap(
                    EntityHandler.getTable( entity )
                            .getColumns()
                            .stream()
                            .collect( Collectors.toMap( Column::getProperty, Function.identity(), ( oldValue, newValue ) -> newValue, LinkedHashMap::new ) )
            );
        }
        // 排除
        exclude( realQueries, this.excludes );
        return realQueries;
    }

    protected final void exclude( Map<String, Column> columns, Set<String> excludes ) {
        if ( CollectionUtil.hasElement( columns ) && CollectionUtil.hasElement( excludes ) ) {
            Iterator<Map.Entry<String, Column>> iterator = columns.entrySet().iterator();
            while ( iterator.hasNext() ) {
                Map.Entry<String, Column> entry = iterator.next();
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
        Map<String, Column> realQueries = getQueryColumns();
        List<String> queryColumns = new ArrayList<>();
        // 主表查询字段
        if ( CollectionUtil.hasElement( realQueries ) ) {
            for ( Map.Entry<String, Column> entry : realQueries.entrySet() ) {
                Column column = entry.getValue();
                String property = column.getProperty();
                String realColumnAlias = propertyForQueryAliasCache.get( property );
                if ( realColumnAlias == null ) {
                    queryColumns.add( ColumnUtil.convertToQueryArg( column, this.enableAlias ? getAlias() : null, applyQuery ? this.reference : null, applyQuery && this.autoMappingAlias ) );
                } else {
                    queryColumns.add( ColumnUtil.convertToQueryArg( column.getColumn(), realColumnAlias, this.enableAlias ? getAlias() : null ) );
                }
            }
        }
        // 子查询列
        loopSubQueries( this, queryColumns );
        // 副表查询字段
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            foreignCriteriaSet.stream()
                    .filter( Objects::nonNull )
                    .forEach( foreignCriteria -> {
                        Map<String, Column> foreignQueries = foreignCriteria.getQueryColumns();
                        Map<String, String> queryAliasCache = foreignCriteria.propertyForQueryAliasCache;
                        if ( CollectionUtil.hasElement( foreignQueries ) ) {
                            for ( Map.Entry<String, Column> entry : foreignQueries.entrySet() ) {
                                Column column = entry.getValue();
                                String property = column.getProperty();
                                String realColumnAlias = queryAliasCache.get( property );
                                if ( realColumnAlias == null ) {
                                    queryColumns.add( ColumnUtil.convertToQueryArg( column, foreignCriteria.getAlias(), applyQuery ? foreignCriteria.getReference() : null, applyQuery && foreignCriteria.isAutoMappingAlias() ) );
                                } else {
                                    queryColumns.add( ColumnUtil.convertToQueryArg( column.getColumn(), realColumnAlias, foreignCriteria.getAlias() ) );
                                }
                            }
                        }
                        // 子查询列
                        loopSubQueries( foreignCriteria, queryColumns );
                    } );
        }
        return CollectionUtil.hasElement( queryColumns ) ? String.join( ", ", queryColumns ) : "";
    }

    protected void loopSubQueries( AbstractQueryCriteria<?> queryCriteria, List<String> columnWrapper ) {
        Map<String, Map<String, String>> subQueries = queryCriteria.columnForSubQueryCache;
        if ( !subQueries.isEmpty() ) {
            // 遍历临时表
            for ( Map.Entry<String, Map<String, String>> rootEntry : subQueries.entrySet() ) {
                String tempTabAlias = rootEntry.getKey();
                Map<String, String> columns = rootEntry.getValue();
                // 遍历查询字段
                if ( !columns.isEmpty() ) {
                    for ( Map.Entry<String, String> columnEntry : columns.entrySet() ) {
                        String column = columnEntry.getKey();
                        String alias = columnEntry.getValue();
                        columnWrapper.add( ColumnUtil.convertToQueryArg( column, alias, tempTabAlias ) );
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
