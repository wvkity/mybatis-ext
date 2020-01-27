package com.wkit.lost.mybatis.plugins.paging.dbs.sql;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.statement.select.SetOperationList;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * 总记录SQL解析器
 * @author wvkity
 */
public class OriginalSqlForCountParser {

    public static final String KEEP_ORDER_BY = "/*keep orderby*/";
    private static final Alias TABLE_ALIAS;

    /**
     * 忽略聚合函数
     */
    private final Set<String> IGNORE_FUNCTION_CACHE = new HashSet<>();
    /**
     * 不通过聚合函数
     */
    private final Set<String> NOT_THROUGH_FUNCTION_CACHE = new HashSet<>();

    /**
     * 聚合函数缓存
     */
    private static final Set<String> AGGREGATE_FUNCTION_CACHE = new HashSet<>();

    static {
        TABLE_ALIAS = new Alias( "TABLE_RECORD" );
        TABLE_ALIAS.setUseAs( false );
        // 聚合函数列表
        AGGREGATE_FUNCTION_CACHE.add( "APPROX_COUNT_DISTINCT" );
        AGGREGATE_FUNCTION_CACHE.add( "ARRAY_AGG" );
        AGGREGATE_FUNCTION_CACHE.add( "AVG" );
        AGGREGATE_FUNCTION_CACHE.add( "BIT_" );
        AGGREGATE_FUNCTION_CACHE.add( "BOOL_" );
        AGGREGATE_FUNCTION_CACHE.add( "CHECKSUM_AGG" );
        AGGREGATE_FUNCTION_CACHE.add( "COLLECT" );
        AGGREGATE_FUNCTION_CACHE.add( "CORR" );
        AGGREGATE_FUNCTION_CACHE.add( "COUNT" );
        AGGREGATE_FUNCTION_CACHE.add( "COVAR" );
        AGGREGATE_FUNCTION_CACHE.add( "CUME_DIST" );
        AGGREGATE_FUNCTION_CACHE.add( "DENSE_RANK" );
        AGGREGATE_FUNCTION_CACHE.add( "EVERY" );
        AGGREGATE_FUNCTION_CACHE.add( "FIRST" );
        AGGREGATE_FUNCTION_CACHE.add( "GROUP" );
        AGGREGATE_FUNCTION_CACHE.add( "JSON_" );
        AGGREGATE_FUNCTION_CACHE.add( "LAST" );
        AGGREGATE_FUNCTION_CACHE.add( "LISTAGG" );
        AGGREGATE_FUNCTION_CACHE.add( "MAX" );
        AGGREGATE_FUNCTION_CACHE.add( "MEDIAN" );
        AGGREGATE_FUNCTION_CACHE.add( "MIN" );
        AGGREGATE_FUNCTION_CACHE.add( "PERCENT_" );
        AGGREGATE_FUNCTION_CACHE.add( "RANK" );
        AGGREGATE_FUNCTION_CACHE.add( "REGR_" );
        AGGREGATE_FUNCTION_CACHE.add( "SELECTIVITY" );
        AGGREGATE_FUNCTION_CACHE.add( "STATS_" );
        AGGREGATE_FUNCTION_CACHE.add( "STD" );
        AGGREGATE_FUNCTION_CACHE.add( "STRING_AGG" );
        AGGREGATE_FUNCTION_CACHE.add( "SUM" );
        AGGREGATE_FUNCTION_CACHE.add( "SYS_OP_ZONE_ID" );
        AGGREGATE_FUNCTION_CACHE.add( "SYS_XMLAGG" );
        AGGREGATE_FUNCTION_CACHE.add( "VAR" );
        AGGREGATE_FUNCTION_CACHE.add( "XMLAGG" );
    }

    /**
     * 智能转换成统计总记录数SQL
     * @param originalSql 原SQL
     * @return 总记录数SQL
     */
    public String smartTransform( final String originalSql ) {
        return smartTransform( originalSql, "0" );
    }

    /**
     * 智能转换成统计总记录数SQL
     * @param originalSql 原SQL
     * @param columnName  字段名称
     * @return 总记录数SQL
     */
    public String smartTransform( final String originalSql, String columnName ) {
        if ( originalSql.contains( KEEP_ORDER_BY ) ) {
            return transformSimpleRecordSql( originalSql, columnName );
        }
        Statement stmt;
        try {
            stmt = CCJSqlParserUtil.parse( originalSql );
        } catch ( Exception e ) {
            return transformSimpleRecordSql( originalSql, columnName );
        }
        Select select = ( Select ) stmt;
        SelectBody selectBody = select.getSelectBody();
        try {
            // 处理body移除order by
            processSelectBodyForRemoveOrderBy( selectBody );
        } catch ( Exception e ) {
            return transformSimpleRecordSql( originalSql, columnName );
        }
        // 处理WithItem
        processWithItemsListForRemoveOrderBy( select.getWithItemsList() );
        // 转换成查询总记录数SQL
        transformToQueryRecordSql( select, columnName );
        return select.toString();
    }

    /**
     * // 处理SelectBody移除order by
     * @param selectBody {@link SelectBody}对象
     */
    public void processSelectBodyForRemoveOrderBy( SelectBody selectBody ) {
        if ( selectBody instanceof PlainSelect ) {
            processPlainSelectForRemoveOrderBy( ( PlainSelect ) selectBody );
        } else if ( selectBody instanceof WithItem ) {
            WithItem withItem = ( WithItem ) selectBody;
            Optional.ofNullable( withItem.getSelectBody() ).ifPresent( this::processSelectBodyForRemoveOrderBy );
        } else {
            SetOperationList operationList = ( SetOperationList ) selectBody;
            if ( CollectionUtil.hasElement( operationList.getSelects() ) ) {
                operationList.getSelects().forEach( this::processSelectBodyForRemoveOrderBy );
            }
            if ( notHasOrderByParameters( operationList.getOrderByElements() ) ) {
                operationList.setOrderByElements( null );
            }
        }
    }

    /**
     * 处理{@link PlainSelect}类型的{@link SelectBody}对象
     * @param plainSelect {@link PlainSelect}
     */
    public void processPlainSelectForRemoveOrderBy( PlainSelect plainSelect ) {
        if ( notHasOrderByParameters( plainSelect.getOrderByElements() ) ) {
            plainSelect.setOrderByElements( null );
        }
        Optional.ofNullable( plainSelect.getFromItem() ).ifPresent( this::processFromItem );
        if ( CollectionUtil.hasElement( plainSelect.getJoins() ) ) {
            plainSelect.getJoins()
                    .stream()
                    .filter( Objects::nonNull )
                    .map( Join::getRightItem )
                    .forEach( this::processFromItem );
        }
    }

    /**
     * 转换成简单总记录数SQL
     * @param originalSql 原SQL
     * @param columnName  字段名
     * @return 总记录数SQL
     */
    public String transformSimpleRecordSql( final String originalSql, String columnName ) {
        return "SELECT COUNT(" + columnName +
                ") RECORD FROM (" +
                originalSql + ") TMP_TAB_RECORD";
    }

    /**
     * 处理子查询
     * @param fromItem {@link FromItem}
     */
    public void processFromItem( FromItem fromItem ) {
        if ( fromItem instanceof SubJoin ) {
            SubJoin subJoin = ( SubJoin ) fromItem;
            if ( CollectionUtil.hasElement( subJoin.getJoinList() ) ) {
                subJoin.getJoinList().forEach( join -> processFromItem( join.getRightItem() ) );
            }
            Optional.ofNullable( subJoin.getLeft() ).ifPresent( this::processFromItem );
        } else if ( fromItem instanceof SubSelect ) {
            SubSelect subSelect = ( SubSelect ) fromItem;
            Optional.ofNullable( subSelect.getSelectBody() ).ifPresent( this::processSelectBodyForRemoveOrderBy );
        } else if ( fromItem instanceof LateralSubSelect ) {
            LateralSubSelect lateralSubSelect = ( LateralSubSelect ) fromItem;
            Optional.ofNullable( lateralSubSelect.getSubSelect() )
                    .map( SubSelect::getSelectBody )
                    .ifPresent( this::processSelectBodyForRemoveOrderBy );
        }
    }

    /**
     * 处理{@link WithItem}移除order by
     * @param withItems {@link WithItem}集合
     */
    public void processWithItemsListForRemoveOrderBy( List<WithItem> withItems ) {
        if ( CollectionUtil.hasElement( withItems ) ) {
            withItems.forEach( this::processSelectBodyForRemoveOrderBy );
        }
    }

    /**
     * 将SQL转成总记录数SQL
     * @param select     {@link Select}对象
     * @param columnName 字段名
     */
    public void transformToQueryRecordSql( Select select, String columnName ) {
        SelectBody selectBody = select.getSelectBody();
        // 简化总记录数SQL
        List<SelectItem> RECORD_ITEM = new ArrayList<>();
        RECORD_ITEM.add( new SelectExpressionItem( new Column( "COUNT(" + columnName + ")" ) ) );
        if ( selectBody instanceof PlainSelect && isSimpleQueryRecord( ( PlainSelect ) selectBody ) ) {
            ( ( PlainSelect ) selectBody ).setSelectItems( RECORD_ITEM );
        } else {
            PlainSelect plainSelect = new PlainSelect();
            SubSelect subSelect = new SubSelect();
            subSelect.setSelectBody( selectBody );
            subSelect.setAlias( TABLE_ALIAS );
            plainSelect.setFromItem( subSelect );
            plainSelect.setSelectItems( RECORD_ITEM );
            select.setSelectBody( plainSelect );
        }
    }

    public boolean isSimpleQueryRecord( PlainSelect plainSelect ) {
        // 检查是否包含group by
        if ( plainSelect.getGroupBy() != null ) {
            return false;
        }
        // 包含DISTINCT
        if ( plainSelect.getDistinct() != null ) {
            return false;
        }
        for ( SelectItem item : plainSelect.getSelectItems() ) {
            // 检查列中是否包含参数
            if ( item.toString().contains( "?" ) ) {
                return false;
            }
            // 检查是否包含聚合函数
            if ( item instanceof SelectExpressionItem ) {
                Expression expression = ( ( SelectExpressionItem ) item ).getExpression();
                if ( expression instanceof Function ) {
                    String name = ( ( Function ) expression ).getName();
                    if ( StringUtil.hasText( name ) ) {
                        String NAME = name.toUpperCase( Locale.ROOT );
                        if ( IGNORE_FUNCTION_CACHE.contains( NAME ) ) {
                            // ignore
                            continue;
                        }
                        if ( NOT_THROUGH_FUNCTION_CACHE.contains( NAME ) ) {
                            return false;
                        } else {
                            for ( String function : AGGREGATE_FUNCTION_CACHE ) {
                                if ( NAME.startsWith( function ) ) {
                                    NOT_THROUGH_FUNCTION_CACHE.add( NAME );
                                    return false;
                                }
                            }
                            IGNORE_FUNCTION_CACHE.add( NAME );
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 检查OrderBy是否存在参数，有参数则不能去掉
     * @param orderByElements {@link OrderByElement}集合
     * @return true: 不存在 | false: 存在
     */
    public boolean notHasOrderByParameters( List<OrderByElement> orderByElements ) {
        if ( CollectionUtil.hasElement( orderByElements ) ) {
            for ( OrderByElement element : orderByElements ) {
                if ( element.toString().contains( "?" ) ) {
                    return false;
                }
            }
        }
        return true;
    }
}
