package com.wkit.lost.mybatis.core.mapping.sql.utils;

import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.type.JdbcType;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * SQL脚本工具类
 * @author wvkity
 */
public final class ScriptUtil {

    private ScriptUtil() {
    }

    /**
     * 安全参数拼接
     * <pre>
     *     // Examples:
     *     ScriptUtil.safeJoin("criteria", ".", "name")
     *     return:
     *     #{criteria.name}
     * </pre>
     * @param args 参数列表
     * @return String
     */
    public static String safeJoint( String... args ) {
        String result = Arrays.stream( args ).filter( Objects::nonNull ).collect( Collectors.joining( "" ) );
        return StringUtil.isBlank( result ) ? "" : Constants.HASH_OPEN_BRACE + result + Constants.CLOSE_BRACE;
    }

    /**
     * 安全参数拼接
     * <pre>
     *     // Examples:
     *     ScriptUtil.safeJointBeforePart("AND USER_NAME LIKE", "criteria", ".", "name")
     *     return:
     *     AND USER_NAME LIKE #{criteria.name}
     * </pre>
     * @param part      前部分
     * @param jointArgs 参数列表
     * @return String
     */
    public static String safeJointBeforePart( final String part, final String... jointArgs ) {
        return part + " " + safeJoint( jointArgs );
    }

    /**
     * 非安全参数拼接
     * <pre>
     *     // Examples:
     *     ScriptUtil.safeJoin("criteria", ".", "name")
     *     return:
     *     ${criteria.name}
     * </pre>
     * @param args 参数列表
     * @return String
     */
    public static String unSafeJoint( String... args ) {
        String result = Arrays.stream( args ).filter( Objects::nonNull ).collect( Collectors.joining( "" ) );
        return StringUtil.isBlank( result ) ? "" : Constants.DOLLAR_OPEN_BRACE + result + Constants.CLOSE_BRACE;
    }

    /**
     * 转换成if条件标签脚本
     * <pre>
     *     // Examples:
     *     ScriptUtil.convertIfTag("age != null and age > 0",
     *          ScriptUtil.safeJointBeforePart("AND AGE >=", "age" ), true);
     *     return:
     *     &lt;if test="age != null and age > 0"&gt;
     *      AND AGE >= #{age}
     *     &lt;/if&gt;
     *
     *     ScriptUtil.convertIfTag("userName != null and userName != ''", "AND USER_NAME LIKE #{userName}", true);
     *     return:
     *     &lt;if test="userName != null and userName != ''"&gt;
     *       AND USER_NAME LIKE #{userName}
     *     &lt;/if&gt;
     * </pre>
     * @param condition 条件
     * @param segment   SQL片段
     * @param newLine   是否换行
     * @return if条件标签脚本
     */
    public static String convertIfTag( final String condition, final String segment, boolean newLine ) {
        String newScript;
        if ( newLine ) {
            newScript = Constants.NEW_LINE + segment + Constants.NEW_LINE;
        } else {
            newScript = segment;
        }
        return String.format( "<if test=\"%s\">%s</if>", condition, newScript );
    }

    /**
     * 转成参数
     * @param tableAlias 表别名
     * @param column     字段包装对象
     * @param execute    执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final String tableAlias, final ColumnWrapper column,
                                         final Execute execute ) {
        return convertPartArg( tableAlias, column, null, execute );
    }

    /**
     * 转成参数
     * @param tableAlias 表别名
     * @param column     字段包装对象
     * @param argName    参数名称
     * @param execute    执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final String tableAlias, final ColumnWrapper column,
                                         final String argName, final Execute execute ) {
        return convertPartArg( tableAlias, column, argName, Symbol.EQ, execute );
    }

    /**
     * 转成参数
     * @param tableAlias 表别名
     * @param column     字段包装对象
     * @param argName    参数名称
     * @param symbol     条件符号
     * @param execute    执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final String tableAlias, final ColumnWrapper column,
                                         final String argName, final Symbol symbol, final Execute execute ) {
        return convertPartArg( tableAlias, column, argName, symbol, null, execute );
    }

    /**
     * 转成参数
     * @param tableAlias 表别名
     * @param column     字段包装对象
     * @param argName    参数名
     * @param symbol     条件符号
     * @param separator  分隔符
     * @param execute    执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final String tableAlias, final ColumnWrapper column, final String argName,
                                         final Symbol symbol, final String separator, final Execute execute ) {
        return convertPartArg( tableAlias, column.getColumn(), argName, column.getProperty(), symbol,
                column.getJdbcType(), column.getTypeHandler(), column.getJavaType(),
                column.isUseJavaType(), separator, execute );
    }

    /**
     * 转换成参数
     * <pre>
     *     // execute = {@link Execute#REPLACE} && tableAlias == null
     *     USER_NAME = #{entity.userName}
     *     USER_NAME = #{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="xx.xxx.ClassName"}
     *
     *     // execute = {@link Execute#REPLACE} && tableAlias != null
     *     tableAlias.USER_NAME = #{entity.userName}
     *     tableAlias.USER_NAME = #{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="xx.xxx.ClassName"}
     *
     *     // execute = {@link Execute#INSERT}
     *     #{entity.userName}
     *     #{entity.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="xx.xxx.ClassName"}
     * </pre>
     * @param tableAlias    表别名
     * @param column        字段
     * @param argName       参数名称
     * @param property      属性
     * @param symbol        条件符号
     * @param jdbcType      JDBC类型
     * @param typeHandler   类型处理器
     * @param javaType      java类型
     * @param isUseJavaType 是否使用java类型
     * @param separator     分隔符
     * @param execute       执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final String tableAlias, final String column, final String argName,
                                         final String property, final Symbol symbol, final JdbcType jdbcType,
                                         final Class<?> typeHandler, final Class<?> javaType,
                                         final boolean isUseJavaType, final String separator,
                                         final Execute execute ) {
        StringBuilder builder = new StringBuilder( 60 );
        if ( execute != Execute.INSERT ) {
            if ( StringUtil.hasText( tableAlias ) ) {
                builder.append( tableAlias ).append( Constants.CHAR_DOT );
            }
            builder.append( column ).append( Constants.CHAR_SPACE );
            if ( symbol == null ) {
                builder.append( Symbol.EQ.getSegment() );
            } else {
                builder.append( symbol.getSegment() );
            }
        }
        builder.append( Constants.CHAR_SPACE );
        if ( execute == Execute.NONE ) {
            builder.append( safeJoint( "value" ) );
        } else {
            builder.append( safeJoint( StringUtil.hasText( argName ) ? ( argName + Constants.CHAR_DOT ) : argName,
                    property, concatIntactArg( javaType, jdbcType, typeHandler, isUseJavaType ) ) );
        }
        if ( StringUtil.hasText( separator ) ) {
            builder.append( separator );
        }
        return builder.toString();
    }

    /**
     * 转换成条件参数
     * @param tableAlias  表别名
     * @param column      字段
     * @param placeholder 参数值
     * @param symbol      条件符号
     * @param logic       逻辑符号
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final ColumnWrapper column,
                                              final String placeholder, final Symbol symbol, final Logic logic ) {
        return convertConditionArg( tableAlias, column.getColumn(), placeholder, column.getJdbcType(),
                column.getTypeHandler(), column.getJavaType(), column.isUseJavaType(), symbol, logic );
    }

    /**
     * 转换成条件参数
     * @param tableAlias    表别名
     * @param column        字段
     * @param placeholder   参数值
     * @param symbol        条件符号
     * @param logic         逻辑符号
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column, final String placeholder,
                                              final Symbol symbol, final Logic logic ) {
        return convertConditionArg( tableAlias, column, placeholder, null, null,
                null, false, symbol, logic );
    }


    /**
     * 转换成条件参数
     * @param tableAlias    表别名
     * @param column        字段
     * @param placeholder   参数值
     * @param jdbcType      JDBC类型
     * @param typeHandler   类型处理器
     * @param javaType      Java类型
     * @param isUseJavaType 是否使用Java类型
     * @param symbol        条件符号
     * @param logic         逻辑符号
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column, final String placeholder,
                                              final JdbcType jdbcType, final Class<?> typeHandler,
                                              final Class<?> javaType, final boolean isUseJavaType,
                                              final Symbol symbol, final Logic logic ) {
        StringBuilder builder = new StringBuilder( 60 );
        if ( logic != null ) {
            builder.append( logic.getSegment() ).append( Constants.CHAR_SPACE );
        }
        if ( StringUtil.hasText( tableAlias ) ) {
            builder.append( tableAlias ).append( Constants.CHAR_DOT );
        }
        Symbol realSymbol;
        if ( symbol == null ) {
            realSymbol = Symbol.EQ;
        } else {
            realSymbol = symbol;
        }
        builder.append( column ).append( Constants.CHAR_SPACE ).append( realSymbol.getSegment() )
                .append( Constants.CHAR_SPACE );
        if ( Symbol.filter( realSymbol ) ) {
            builder.append( safeJoint( placeholder, concatIntactArg( javaType, jdbcType,
                    typeHandler, isUseJavaType ) ) );
        }
        return builder.toString();
    }

    public static String concatIntactArg( final Class<?> javaType, final JdbcType jdbcType,
                                          final Class<?> typeHandler, final boolean isUseJavaType ) {
        StringBuilder builder = new StringBuilder( 30 );
        if ( jdbcType != null ) {
            builder.append( ", jdbcType=" ).append( jdbcType.toString() );
        }
        if ( typeHandler != null ) {
            builder.append( ", typeHandler=" ).append( typeHandler.getCanonicalName() );
        }
        if ( isUseJavaType && javaType != null ) {
            builder.append( ", javaType=" ).append( javaType.getCanonicalName() );
        }
        return builder.toString();
    }
}
