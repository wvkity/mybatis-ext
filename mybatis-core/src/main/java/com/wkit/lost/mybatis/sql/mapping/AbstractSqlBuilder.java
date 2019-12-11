package com.wkit.lost.mybatis.sql.mapping;

import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.Logic;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.core.meta.Column;
import com.wkit.lost.mybatis.core.meta.Table;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.sql.SqlTemplate;
import com.wkit.lost.mybatis.utils.Assert;
import com.wkit.lost.mybatis.utils.ColumnUtil;
import com.wkit.lost.mybatis.utils.StringUtil;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

/**
 * 抽象SQL构建器
 * @author wvkity
 */
@Log4j2
public abstract class AbstractSqlBuilder implements SqlBuilder {

    /**
     * AND连接符
     */
    public static final String AND = Logic.AND.getSqlSegment();

    /**
     * OR连接符
     */
    public static final String OR = Logic.OR.getSqlSegment();

    /**
     * 实体类
     */
    @Getter
    protected Class<?> entity;

    /**
     * 表映射信息
     */
    @Getter
    protected Table table;

    /**
     * 别名
     */
    @Getter
    protected String alias;

    @Override
    public String buildSqlString( Class<?> entity, Table table, String alias ) {
        Assert.isTrue( entity != null || table != null, "A build SQL statement must specify the corresponding entity class or table mapping information" );
        if ( entity != null && table == null ) {
            this.entity = entity;
            this.table = EntityHandler.getTable( entity );
        } else if ( entity == null ) {
            this.entity = table.getEntity();
            this.table = table;
        } else {
            this.entity = entity;
            this.table = table;
        }
        this.alias = alias;
        return this.build();
    }

    /**
     * 空格缩进
     * @param size 空格数
     * @return 空格字符串
     */
    protected String indentOfSpace( int size ) {
        if ( size < 1 ) {
            return "";
        }
        StringBuilder builder = new StringBuilder( size );
        for ( int i = 0; i < size; i++ ) {
            builder.append( " " );
        }
        return builder.toString();
    }

    /**
     * 查询SQL
     * @param querySegment 查询字段部分
     * @param condition    条件
     * @return SQL字符串
     */
    protected String select( String querySegment, String condition ) {
        return toSqlString( SqlTemplate.SELECT, querySegment, condition );
    }

    /**
     * Criteria查询SQL
     * @param querySegment 查询字段部分
     * @param condition    条件
     * @return SQL字符串
     */
    protected String criteriaSelect( String querySegment, String condition ) {
        return toSqlString( SqlTemplate.CRITERIA_SELECT, querySegment, condition );
    }

    /**
     * 添加SQL
     * @param columnSegment 字段部分
     * @param valueSegment  值部分
     * @return SQL字符串
     */
    protected String insert( String columnSegment, String valueSegment ) {
        return toSqlString( SqlTemplate.INSERT, columnSegment, valueSegment );
    }

    /**
     * 更新SQL
     * @param valueSegment 值部分
     * @param condition    条件
     * @return SQL字符串
     */
    protected String update( String valueSegment, String condition ) {
        return toSqlString( SqlTemplate.UPDATE, valueSegment, condition );
    }

    /**
     * 删除SQL
     * @param condition 条件
     * @return SQL字符串
     */
    protected String delete( String condition ) {
        return toSqlString( SqlTemplate.DELETE, "", condition );
    }

    /**
     * 将字段映射信息转换成IF条件标签
     * <p>
     * eg: <br><br>
     * 条件: <br>
     * &lt;if userName != null and userName !="''"&gt; <br>
     * &nbsp;&nbsp;AND USER_NAME = #{userName, javaType="", jdbcType="", typeHandler=""} <br>
     * &lt;/if&gt;
     * <br><br>
     * 保存操作: <br>
     * &lt;if userName != null and userName !="''"&gt; <br>
     * &nbsp;&nbsp;#{userName, javaType="", jdbcType="", typeHandler=""} <br>
     * &lt;/if&gt;
     * <br><br>
     * 更新操作: <br>
     * &lt;if userName != null and userName !="''"&gt; <br>
     * &nbsp;&nbsp;USER_NAME = #{userName, javaType="", jdbcType="", typeHandler=""} <br>
     * &lt;/if&gt;
     * </p>
     * @param toValue   是否为值填充
     * @param execute   执行操作类型
     * @param isQuery   是否为查询操作
     * @param indent    缩进
     * @param argName   参数名称[@Param("xxx")]
     * @param column    字段映射信息
     * @param separator 分隔符
     * @param join      连接符
     * @return XML-IF标签字符串
     * @see ColumnUtil#convertToArg(Column, Execute, String, String, String, String)
     */
    protected String convertToIfTagOfNotNull( final boolean toValue, final Execute execute, final boolean isQuery, final int indent, final String argName, final Column column, final String separator, String join ) {
        boolean hasArgName = StringUtil.hasText( argName );
        String property = column.getProperty();
        StringBuilder buffer = new StringBuilder( 60 );
        buffer.append( indentOfSpace( indent ) ).append( "<if test=\"" );
        // 参数名称
        if ( hasArgName ) {
            buffer.append( argName ).append( "." );
        }
        buffer.append( property ).append( " != null" );
        // 针对字符串做空值判断
        if ( column.isCheckNotEmpty() && column.getJavaType().equals( String.class ) ) {
            buffer.append( " and " );
            if ( hasArgName ) {
                buffer.append( argName ).append( "." );
            }
            buffer.append( property ).append( " != ''" );
        }
        // 闭合标签
        buffer.append( "\">\n" );
        // 值部分
        if ( join == null ) {
            join = "";
        }
        buffer.append( indentOfSpace( indent + 1 ) );
        if ( toValue ) {
            buffer.append( join ).append( " " ).append( ColumnUtil.convertToArg( column, execute, argName, isQuery ? this.alias : null, Operator.EQ.getSqlSegment(), separator ) ).append( "\n" );
        } else {
            buffer.append( column.getColumn() ).append( ", \n" );
        }
        buffer.append( indentOfSpace( indent ) ).append( "</if>\n" );
        return buffer.toString();
    }
    
    protected String convertIfTagForLocker(final boolean toValue, final String argName, final Column column, final String separator, final int indent ) {
        String property = column.getProperty();
        StringBuilder buffer = new StringBuilder( 60 );
        buffer.append( indentOfSpace( indent ) ).append( "<if test=\"" ).append( argName ).append( " != null" );
        if ( column.isCheckNotEmpty() && column.getJavaType().equals( String.class ) ) {
            buffer.append( " and " ).append( argName ).append( " != ''" );
        }
        // 闭合标签
        buffer.append( "\">\n" );
        buffer.append( indentOfSpace( indent + 1 ) );
        if ( toValue ) {
            buffer.append( " " ).append( column.getColumn() ).append( " = #{" )
                    .append( argName ).append( "}" ).append( separator ).append( "\n" );
        } else {
            buffer.append( column.getColumn() ).append( ", \n" );
        }
        buffer.append( indentOfSpace( indent ) ).append( "</if>\n" );

        return buffer.toString();
    }

    /**
     * 转成SQL字符串
     * @param template  模板
     * @param segment   字段部分
     * @param condition 条件
     * @return SQL字符串
     */
    protected String toSqlString( SqlTemplate template, String segment, String condition ) {
        return segment == null ? "" : String.format( template.toSqlString( this.table, this.alias ), segment, condition );
    }

    /**
     * 构建SQL语句
     * @return SQL语句
     */
    public abstract String build();

}
