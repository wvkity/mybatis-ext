package com.wkit.lost.mybatis.core;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.schema.Column;
import com.wkit.lost.mybatis.core.schema.Table;
import com.wkit.lost.mybatis.handler.EntityHandler;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 抽象Criteria条件类
 * @param <T> 类型
 * @author DT
 */
@SuppressWarnings( "serial" )
public abstract class AbstractCriteria<T> extends AbstractChainCriteriaWrapper<T, AbstractCriteria<T>> {

    /**
     * 查询字段(SQL片段)
     */
    private String querySegment;

    /**
     * 联表SQL片段
     */
    private String foreignSegment;

    /**
     * 更新字段(SQL片段)集合
     */
    private Set<String> updateSegment = new LinkedHashSet<>();

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

    @Override
    protected String getGroupSegment() {
        return " GROUP BY " + getQuerySegment( false );
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

    /**
     * 获取联表查询SQL片段
     * @return SQL片段
     */
    public String getForeignSegment() {
        if ( CollectionUtil.hasElement( foreignCriteriaSet ) ) {
            return foreignCriteriaSet.stream().map( criteria -> {
                Criteria<?> master = criteria.getMaster();
                Foreign linked = criteria.getForeign();
                Column masterColumn = master.searchColumn( linked.getMaster() );
                Column assistantColumn = criteria.searchColumn( linked.getForeign() );
                Table table = EntityHandler.getTable( criteria.getEntity() );
                String catalog = table.getCatalog();
                String schema = table.getSchema();
                String subAlias = criteria.getAlias();
                // 拼接条件
                StringBuilder builder = new StringBuilder( 60 );
                builder.append( linked.getJoinMode().getSqlSegment() );
                if ( StringUtil.hasText( schema ) ) {
                    builder.append( schema ).append( "." );
                } else if ( StringUtil.hasText( catalog ) ) {
                    builder.append( catalog ).append( "." );
                }
                builder.append( table.getName() ).append( " " ).append( subAlias );
                builder.append( " ON " ).append( subAlias ).append( "." ).append( assistantColumn.getColumn() );
                builder.append( " = " ).append( master.getAlias() ).append( "." ).append( masterColumn.getColumn() );
                // 拼接其他条件
                if ( criteria.isHasCondition() ) {
                    builder.append( " " ).append( criteria.getSqlSegment() );
                }
                return builder.toString();
            } ).collect( Collectors.joining( " \n" ) );
        }
        return "";
    }

    @Override
    public String getUpdateSegment() {
        return null;
    }
}
