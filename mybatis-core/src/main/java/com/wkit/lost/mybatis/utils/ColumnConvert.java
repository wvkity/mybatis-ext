package com.wkit.lost.mybatis.utils;

import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.core.criteria.Execute;
import com.wkit.lost.mybatis.core.criteria.Operator;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.incrementer.SequenceKeyGenerator;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

public final class ColumnConvert {

    private ColumnConvert() {
    }

    /**
     * 转换成条件参数
     * @param column  字段信息
     * @param argName 参数
     * @return 条件字符串
     */
    public static String convertToTestCondition( final ColumnWrapper column, final String argName ) {
        StringBuilder builder = new StringBuilder();
        if ( Ascii.hasText( argName ) ) {
            builder.append( argName ).append( "." );
        }
        builder.append( column.getProperty() ).append( " != null" );
        if ( column.getJavaType() == String.class ) {
            builder.append( " and " );
            if ( Ascii.hasText( argName ) ) {
                builder.append( argName ).append( "." );
            }
            builder.append( column.getProperty() ).append( " != ''" );
        }
        return builder.toString();
    }

    /**
     * 转换成INSERT参数字符串
     * @param column 字段映射对象
     * @return 字符串
     * @see #convertToArg(ColumnWrapper, Execute, String, String, String, String)
     */
    public String convertToInsertArg( final ColumnWrapper column ) {
        return convertToArg( column, Execute.INSERT );
    }

    /**
     * 转换成参数字符串
     * @param column  字段映射对象
     * @param execute 执行操作类型
     * @return 字符串
     * @see #convertToArg(ColumnWrapper, Execute, String, String, String, String)
     */
    public static String convertToArg( final ColumnWrapper column, final Execute execute ) {
        return convertToArg( column, execute, null, null, null, null );
    }

    /**
     * 转换成参数字符串
     * @param column  字段映射对象
     * @param execute 执行操作类型
     * @param argName 参数名称 [可选]
     * @return 字符串
     */
    public static String convertToArg( final ColumnWrapper column, final Execute execute, final String argName ) {
        return convertToArg( column, execute, argName, null, null, null );
    }

    /**
     * 转换成参数字符串
     * @param column  字段映射对象
     * @param execute 执行操作类型
     * @param argName 参数名称 [可选]
     * @param alias   别名 [可选]
     * @return 字符串
     * @see #convertToArg(ColumnWrapper, Execute, String, String, String, String)
     */
    public static String convertToArg( final ColumnWrapper column, final Execute execute,
                                       final String argName, final String alias ) {
        return convertToArg( column, execute, argName, alias, null, null );
    }

    /**
     * 转换成参数字符串
     * @param column   字段映射对象
     * @param execute  执行操作类型
     * @param argName  参数名称 [可选]
     * @param alias    别名 [可选]
     * @param operator 操作符 [可选]
     * @return 字符串
     * @see #convertToArg(ColumnWrapper, Execute, String, String, String, String)
     */
    public static String convertToArg( final ColumnWrapper column, final Execute execute, final String argName,
                                       final String alias, String operator ) {
        return convertToArg( column, execute, argName, alias, operator, null );
    }


    /**
     * 转换成参数字符串
     * <p>
     * 别名：{@code A} <br>
     * 字段名：{@code USER_NAME} <br>
     * 对应属性: {@code userName} <br>
     * 参数名：{@code entity} <br>
     * eg:<br>
     * 条件: <br>
     * &nbsp;&nbsp;A.USER_NAME = #{entity.userName} <br>
     * &nbsp;&nbsp;A.USER_NAME = #{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br><br>
     * 保存操作: <br>
     * &nbsp;&nbsp;#{entity.userName} <br>
     * &nbsp;&nbsp;#{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br><br>
     * 更新操作: <br>
     * &nbsp;&nbsp;USER_NAME = #{entity.userName} <br>
     * &nbsp;&nbsp;USER_NAME = #{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br>
     * </p>
     * @param column    字段映射对象
     * @param execute   执行操作类型
     * @param argName   参数名称 [可选]
     * @param alias     别名 [可选]
     * @param operator  操作符 [可选]
     * @param separator 分隔符 [可选]
     * @return 字符串
     */
    public static String convertToArg( final ColumnWrapper column, final Execute execute, final String argName,
                                       final String alias, String operator, String separator ) {
        StringBuffer buffer = new StringBuffer( 60 );
        if ( execute != Execute.INSERT ) {
            // 别名
            if ( StringUtil.hasText( alias ) ) {
                buffer.append( alias ).append( "." );
            }
            // 操作符[=, >, <, ...]
            if ( StringUtil.isBlank( operator ) ) {
                operator = Operator.EQ.getSqlSegment();
            }
            // 字段
            buffer.append( column.getColumn() ).append( " " ).append( operator );
        }
        buffer.append( " #{" );
        // 参数属性名
        if ( execute == Execute.NONE ) {
            buffer.append( "value" );
        } else {
            if ( StringUtil.hasText( argName ) ) {
                buffer.append( argName ).append( "." );
            }
            buffer.append( column.getProperty() );
        }
        appendValue( column, buffer );
        buffer.append( "}" );
        if ( StringUtil.hasText( separator ) ) {
            buffer.append( separator );
        }
        return buffer.toString();
    }

    /**
     * 转换成查询字段
     * @param column    字段映射对象
     * @param alias     别名
     * @param reference 实体内的引用属性名
     * @param apply     是否带上属性名
     * @return 字符串
     */
    public static String convertToQueryArg( final ColumnWrapper column, final String alias,
                                            final String reference, final boolean apply ) {
        StringBuilder buffer = new StringBuilder( 60 );
        if ( StringUtil.hasText( alias ) ) {
            buffer.append( alias ).append( "." );
        }
        buffer.append( column.getColumn() );
        if ( apply ) {
            buffer.append( " " );
            if ( StringUtil.hasText( reference ) ) {
                buffer.append( "\"" ).append( reference ).append( "." ).append( column.getProperty() ).append( "\"" );
            } else {
                buffer.append( column.getProperty() );
            }
        }
        return buffer.toString();
    }

    /**
     * 转换成查询字段
     * @param column      字段映
     * @param alias       别名
     * @param columnAlias 列别名
     * @return 字符串
     */
    public static String convertToQueryArg( final String column, final String columnAlias, final String alias ) {
        StringBuilder buffer = new StringBuilder( 40 );
        if ( StringUtil.hasText( alias ) ) {
            buffer.append( alias ).append( "." );
        }
        buffer.append( column );
        if ( StringUtil.hasText( columnAlias ) ) {
            buffer.append( " " );
            if ( columnAlias.contains( "." ) ) {
                buffer.append( "\"" ).append( columnAlias ).append( "\"" );
            } else {
                buffer.append( columnAlias );
            }
        }
        return buffer.toString();
    }

    /**
     * 拼接自定义参数
     * @param column                字段映射对象
     * @param paramValuePlaceHolder 参数值占位符
     * @param alias                 别名
     * @param operator              操作符
     * @param join                  连接符
     * @return SQL字符串
     */
    public static String convertToCustomArg( final ColumnWrapper column, final String paramValuePlaceHolder,
                                             final String alias, Operator operator, String join ) {
        StringBuffer buffer = new StringBuffer( 60 );
        if ( StringUtil.hasText( join ) ) {
            buffer.append( join ).append( " " );
        }
        // 别名
        if ( StringUtil.hasText( alias ) ) {
            buffer.append( alias ).append( "." );
        }
        // 操作符[=, >, <, ...]
        if ( operator == null ) {
            operator = Operator.EQ;
        }
        // 字段
        buffer.append( column.getColumn() ).append( " " ).append( operator.getSqlSegment() );
        if ( Operator.filter( operator ) ) {
            buffer.append( " #{" );
            // 参数属性名
            buffer.append( paramValuePlaceHolder );
            appendValue( column, buffer );
            buffer.append( "}" );
        }
        return buffer.toString();
    }

    /**
     * 拼接自定义参数
     * @param column                字段
     * @param paramValuePlaceHolder 参数值占位符
     * @param alias                 别名
     * @param operator              操作符
     * @param join                  连接符
     * @return SQL字符串
     */
    public static String convertToCustomArg( final String column, final String paramValuePlaceHolder,
                                             final String alias, Operator operator, String join ) {
        StringBuilder buffer = new StringBuilder( 40 );
        if ( StringUtil.hasText( join ) ) {
            buffer.append( join ).append( " " );
        }
        // 别名
        if ( StringUtil.hasText( alias ) ) {
            buffer.append( alias ).append( "." );
        }
        // 操作符[=, >, <, ...]
        if ( operator == null ) {
            operator = Operator.EQ;
        }
        // 字段
        buffer.append( column ).append( " " ).append( operator.getSqlSegment() );
        buffer.append( " #{" ).append( paramValuePlaceHolder ).append( "}" );
        return buffer.toString();
    }

    private static void appendValue( ColumnWrapper column, StringBuffer buffer ) {
        JdbcType jdbcType = column.getJdbcType();
        Class<?> javaType = column.getJavaType();
        Class<?> typeHandler = column.getTypeHandler();
        boolean useJavaType = column.isUseJavaType();
        if ( buffer != null ) {
            // 指定JDBC类型
            if ( jdbcType != null ) {
                buffer.append( ", jdbcType=" ).append( jdbcType.toString() );
            }
            // 指定类型处理器
            if ( typeHandler != null ) {
                buffer.append( ", typeHandler=" ).append( typeHandler.getCanonicalName() );
            }
            // 指定Java类型
            if ( useJavaType && !javaType.isArray() ) {
                buffer.append( ", javaType=" ).append( javaType.getCanonicalName() );
            }
        }
    }

    /**
     * 获取序列脚本
     * @param column        字段映射对象
     * @param configuration MyBatis配置对象
     * @return 序列SQL
     */
    public static String getSequenceScript( final Configuration configuration, ColumnWrapper column ) {
        return getSequenceScript( MyBatisConfigCache.getDialect( configuration ), column.getSequenceName() );
    }

    /**
     * 获取序列脚本
     * @param dialect      数据库类型
     * @param sequenceName 序列名称
     * @return 序列SQL
     */
    public static String getSequenceScript( final Dialect dialect, String sequenceName ) {
        return SequenceKeyGenerator.getInstance( dialect ).toSqlString( sequenceName );
    }
}
