package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.lambda.Property;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@SuppressWarnings( "serial" )
public abstract class AbstractQueryCriteria<T> extends AbstractChainCriteriaWrapper<T, AbstractQueryCriteria<T>>
        implements Query<T, AbstractQueryCriteria<T>> {

    /**
     * 查询字段(SQL片段)
     */
    protected String querySegment;

    /**
     * 查询列(属性)
     */
    protected Set<Column> queries = Collections.synchronizedSet( new LinkedHashSet<>() );

    /**
     * 排除列(属性)
     */
    protected Set<String> excludes = new ConcurrentSkipListSet<>();

    @Override
    public AbstractQueryCriteria<T> query( Property<T, ?> property ) {
        return query( lambdaToProperty( property ) );
    }

    @Override
    public AbstractQueryCriteria<T> query( String property ) {
        if ( StringUtil.hasText( property ) ) {
            this.queries.add( searchColumn( property ) );
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
            this.queries.addAll( properties.stream().filter( StringUtil::hasText ).map( this::searchColumn ).collect( Collectors.toList() ) );
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
    protected Set<Column> getQueryColumns() {
        Set<Column> realQueries;
        if ( CollectionUtil.hasElement( queries ) ) {
            // 显式指定查询列
            realQueries = queries;
        } else {
            // 所有列
            realQueries = new LinkedHashSet<>( EntityHandler.getTable( entity ).getColumns() );
        }
        // 排除
        exclude( realQueries, this.excludes );
        return realQueries;
    }

    protected final void exclude( Set<Column> columns, Set<String> excludes ) {
        if ( CollectionUtil.hasElement( columns ) && CollectionUtil.hasElement( excludes ) ) {
            columns.removeIf( column -> excludes.contains( column.getProperty() ) );
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
        Set<Column> realQueries = getQueryColumns();
        List<String> queryColumns = new ArrayList<>();
        // 主表查询字段
        if ( CollectionUtil.hasElement( realQueries ) ) {
            queryColumns.addAll( realQueries.stream()
                    .map( column -> column.convertToQueryArg( this.enableAlias ? getAlias() : null, applyQuery ? this.reference : null, applyQuery ) )
                    .collect( Collectors.toList() ) );
        }
        // 副表查询字段
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            foreignCriteriaSet.stream()
                    .filter( Objects::nonNull )
                    .forEach( subCriteria -> {
                        Set<Column> subQueries = subCriteria.getQueryColumns();
                        if ( CollectionUtil.hasElement( subQueries ) ) {
                            queryColumns.addAll( subQueries.stream()
                                    .map( column -> column.convertToQueryArg( subCriteria.getAlias(), applyQuery ? subCriteria.getReference() : null, applyQuery ) )
                                    .collect( Collectors.toList() ) );
                        }
                    } );
        }
        return CollectionUtil.hasElement( queryColumns ) ? String.join( ", ", queryColumns ) : "";
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
