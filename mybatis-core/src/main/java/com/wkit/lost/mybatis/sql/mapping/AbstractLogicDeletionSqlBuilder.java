package com.wkit.lost.mybatis.sql.mapping;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.utils.Constants;

import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractLogicDeletionSqlBuilder extends AbstractSqlBuilder {

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
        StringBuilder builder = new StringBuilder( 40 );
        builder.append( "<trim prefix=\"SET\" suffixOverrides=\",\">\n" );
        builder.append( logicalDeletionColumn.getColumn() ).append( " = #{" )
                .append( Constants.PARAM_LOGIC_DELETED_AUTO_KEY );
        if ( logicalDeletionColumn.getJdbcType() != null ) {
            builder.append( ", jdbcType=" ).append( logicalDeletionColumn.getJdbcType().getClass().getName() );
        }
        if ( logicalDeletionColumn.getTypeHandler() != null ) {
            builder.append( ", typeHandler=" ).append( logicalDeletionColumn.getTypeHandler().getName() );
        }
        if ( logicalDeletionColumn.isUseJavaType() && !logicalDeletionColumn.getJavaType().isArray() ) {
            builder.append( ", javaType=" ).append( logicalDeletionColumn.getJavaType().getName() );
        }
        builder.append( "}, " );
        // 自动填充部分
        if ( !deleteFillings.isEmpty() ) {
            builder.append( deleteFillings.stream().map( column -> convertToIfTagOfNotNull( true, 
                    Execute.REPLACE, false, 1, Constants.PARAM_ENTITY, 
                    column, ",", "" ) )
                    .collect( Collectors.joining( "", "\n", "\n" ) ) );
        }
        builder.append( "</trim>" );
        return update( builder.toString(), condition );
    }
}
