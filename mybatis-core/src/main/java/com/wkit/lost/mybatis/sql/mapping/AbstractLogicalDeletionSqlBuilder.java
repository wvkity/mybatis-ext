package com.wkit.lost.mybatis.sql.mapping;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractLogicalDeletionSqlBuilder extends AbstractSqlBuilder {

    /**
     * 逻辑删除
     * @param condition 条件
     * @return SQL字符串片段
     */
    protected String logicDelete( String condition ) {
        Column logicalDeletionColumn = table.getLogicalDeletionColumn();
        if ( logicalDeletionColumn == null ) {
            return "";
        }
        Set<Column> deleteFillings = table.getDeleteFillings();
        // 更新字段部分
        StringBuffer buffer = new StringBuffer( 40 );
        buffer.append( "<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        buffer.append( logicalDeletionColumn.getColumn() ).append( " = #{" ).append( Constants.PARAM_LOGIC_DELETED_AUTO_KEY );
        if ( logicalDeletionColumn.getJdbcType() != null ) {
            buffer.append( ", jdbcType=" ).append( logicalDeletionColumn.getJdbcType().getClass().getName() );
        }
        if ( logicalDeletionColumn.getTypeHandler() != null ) {
            buffer.append( ", typeHandler=" ).append( logicalDeletionColumn.getTypeHandler().getName() );
        }
        if ( logicalDeletionColumn.isUseJavaType() && !logicalDeletionColumn.getJavaType().isArray() ) {
            buffer.append( ", javaType=" ).append( logicalDeletionColumn.getJavaType().getName() );
        }
        buffer.append( "}, " );
        // 自动填充部分
        if ( !deleteFillings.isEmpty() ) {
            buffer.append( deleteFillings.stream().map( column -> convertToIfTagOfNotNull( true, Execute.REPLACE, false, 1, Constants.PARAM_ENTITY, column, ",", "" ) ).collect( Collectors.joining( "", "\n", "\n" ) ) );
        }
        buffer.append( "</trim>" );
        return update( buffer.toString(), condition );
    }
}
