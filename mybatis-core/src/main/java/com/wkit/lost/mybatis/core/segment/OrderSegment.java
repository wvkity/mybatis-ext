package com.wkit.lost.mybatis.core.segment;

import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.core.Criteria;
import com.wkit.lost.mybatis.core.Order;
import com.wkit.lost.mybatis.core.function.Aggregation;
import com.wkit.lost.mybatis.core.schema.Column;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 排序SQL分段类
 * @author DT
 */
public class OrderSegment extends AbstractSegment {

    private static final long serialVersionUID = -725413569300627180L;

    @Override
    public String getSqlSegment() {
        if ( isNotEmpty() ) {
            List<Order<?>> orders = this.segments.stream().filter( order -> order instanceof Order ).map( segment -> ( Order<?> ) segment )
                    .collect( Collectors.toList() );
            boolean currentSort;
            if ( CollectionUtil.hasElement( orders ) ) {
                Order<?> first = orders.remove( 0 );
                currentSort = first.isAscending();
                StringBuffer buffer = new StringBuffer( 60 );
                buffer.append( " ORDER BY " );
                append( first, buffer );
                for ( Order<?> order : orders ) {
                    boolean sort = order.isAscending();
                    // 对比前后两次排序方式是否一致
                    if ( currentSort != sort ) {
                        buffer.append( currentSort ? " ASC" : " DESC" );
                        currentSort = sort;
                    }
                    buffer.append( ", " );
                    append( order, buffer );
                }
                buffer.append( currentSort ? " ASC" : " DESC" );
                return buffer.toString();
            }
        }
        return "";
    }

    private void append( Order<?> order, StringBuffer buffer ) {
        if ( order != null && buffer != null ) {
            if ( order.isFunc() ) {
                // 聚合函数
                Set<Aggregation> aggregations = order.getAggregations();
                if ( CollectionUtil.hasElement( aggregations ) ) {
                    buffer.append( aggregations.stream().map( Aggregation::toOrderSqlSegment ).collect( Collectors.joining( ", " ) ) );
                }
            } else {
                // 列
                Set<Column> columns = order.getColumns();
                if ( CollectionUtil.hasElement( columns ) ) {
                    Criteria<?> criteria = order.getCriteria();
                    boolean isEnable = criteria.isEnableAlias();
                    String alias = isEnable ? ( criteria.getAlias() + "." ) : "";
                    buffer.append( columns.stream().map( column -> alias + column.getColumn() ).collect( Collectors.joining( ", " ) ) );
                }
            }
        }
    }
}
