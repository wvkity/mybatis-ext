package com.wkit.lost.mybatis.core.mapping.sql.utils;

import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.core.constant.Execute;
import com.wkit.lost.mybatis.core.constant.Logic;
import com.wkit.lost.mybatis.core.constant.Symbol;
import com.wkit.lost.mybatis.core.metadata.ColumnWrapper;
import com.wkit.lost.mybatis.incrementer.SequenceKeyGenerator;
import com.wkit.lost.mybatis.utils.ArrayUtil;
import com.wkit.lost.mybatis.utils.CollectionUtil;
import com.wkit.lost.mybatis.utils.Constants;
import com.wkit.lost.mybatis.utils.StringUtil;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;

import java.util.Arrays;
import java.util.List;
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
     * 转成if条件部分
     * @param argName 参数名称
     * @param column  字段包装对象
     * @return if条件字符串
     */
    public static String convertIfTest( final String argName, final ColumnWrapper column ) {
        StringBuilder builder = new StringBuilder();
        boolean hasArgName = StringUtil.hasText( argName );
        if ( hasArgName ) {
            builder.append( argName ).append( Constants.CHAR_DOT );
        }
        builder.append( column.getProperty() ).append( " != null" );
        if ( String.class.isAssignableFrom( column.getJavaType() ) ) {
            builder.append( " and " );
            if ( hasArgName ) {
                builder.append( argName ).append( Constants.CHAR_DOT );
            }
            builder.append( column.getProperty() ).append( " != ''" );
        }
        return builder.toString();
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
     * @param script    SQL片段
     * @param newLine   是否换行
     * @return if条件标签脚本
     */
    public static String convertIfTag( final String condition, final String script, boolean newLine ) {
        String newScript;
        if ( newLine ) {
            newScript = Constants.NEW_LINE + script + Constants.NEW_LINE;
        } else {
            newScript = script;
        }
        return String.format( Constants.NEW_LINE + "<if test=\"%s\">%s</if>", condition, newScript );
    }

    /**
     * 转成if条件标签脚本
     * @param tableAlias 表别名
     * @param column     字段包装对象
     * @param argName    参数名称
     * @param toValue    是否转成值
     * @param isQuery    是否为查询
     * @param symbol     条件符号
     * @param logic      逻辑符号
     * @param separator  分隔符
     * @param execute    执行类型
     * @return if标签脚本
     */
    public static String convertIfTagWithNotNull( final String tableAlias, final ColumnWrapper column,
                                                  final String argName, final boolean toValue,
                                                  final boolean isQuery, final Symbol symbol, final Logic logic,
                                                  final String separator, final Execute execute ) {
        // 条件部分
        StringBuilder condition = new StringBuilder( 45 );
        boolean hasArgName = StringUtil.hasText( argName );
        String property = column.getProperty();
        if ( hasArgName ) {
            condition.append( argName ).append( Constants.CHAR_DOT );
        }
        condition.append( property ).append( " != null" );
        if ( column.isCheckNotEmpty() && String.class.isAssignableFrom( column.getJavaType() ) ) {
            condition.append( " and " );
            if ( hasArgName ) {
                condition.append( argName ).append( Constants.CHAR_DOT );
            }
            condition.append( property ).append( " != ''" );
        }
        StringBuilder script = new StringBuilder( 45 );
        if ( toValue ) {
            if ( logic != null ) {
                script.append( logic.getSegment() ).append( Constants.CHAR_SPACE );
            }
            script.append( convertPartArg( isQuery ? tableAlias : null, column, argName, symbol, separator, execute ) );
        } else {
            script.append( column.getColumn() ).append( ", " ).append( Constants.NEW_LINE );
        }
        return convertIfTag( condition.toString(), script.toString(), true );
    }

    /**
     * 转换成trim标签脚本
     * @param script          SQL脚本
     * @param prefix          前缀
     * @param suffix          后缀
     * @param prefixOverrides 干掉最前一个
     * @param suffixOverrides 干掉最后一个
     * @return trim脚本
     */
    public static String convertTrimTag( final String script, final String prefix, final String suffix,
                                         final String prefixOverrides, final String suffixOverrides ) {
        StringBuilder builder = new StringBuilder( 60 );
        builder.append( "<trim" );
        if ( StringUtil.hasText( prefix ) ) {
            builder.append( " prefix=\"" ).append( prefix ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( suffix ) ) {
            builder.append( " suffix=\"" ).append( suffix ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( prefixOverrides ) ) {
            builder.append( " prefixOverrides=\"" ).append( prefixOverrides ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( suffixOverrides ) ) {
            builder.append( " suffixOverrides=\"" ).append( suffixOverrides ).append( Constants.CHAR_QUOTE );
        }
        return builder.append( Constants.CHAR_GT ).append( Constants.NEW_LINE )
                .append( script ).append( Constants.NEW_LINE ).append( "</trim>" ).toString();
    }

    /**
     * 转成choose标签脚本
     * <pre>
     *     // Examples:
     *     ScriptUtil.convertChooseTag("entity.version != null", "AND VERSION = #{entity.version}", "AND VERSION IS NULL");
     *     return:
     *     &lt;choose&gt;
     *      &lt;when test="entity.version != null">
     *       AND VERSION = #{entity.version}
     *      &lt;/when&gt;
     *      &lt;otherwise&gt;
     *       AND VERSION IS NULL
     *      &lt;/otherwise&gt;
     *     &lt;/choose&gt;
     * </pre>
     * @param whenCondition   when条件
     * @param whenScript      when脚本
     * @param otherwiseScript otherwise脚本
     * @return choose标签脚本
     */
    public static String convertChooseTag( final String whenCondition, final String whenScript,
                                           final String otherwiseScript ) {
        return "<choose>" + Constants.NEW_LINE +
                " <when test=\"" + whenCondition + Constants.CHAR_QUOTE + Constants.CHAR_GT + Constants.NEW_LINE +
                "  " + whenScript + Constants.NEW_LINE +
                " </when>" + Constants.NEW_LINE +
                " <otherwise>" + "  " + otherwiseScript + Constants.NEW_LINE +
                " </otherwise>" + Constants.NEW_LINE +
                "</choose>";
    }

    /**
     * 转换成where脚本标签
     * @param script SQL脚本
     * @return where脚本标签
     */
    public static String convertWhereTag( final String script ) {
        return "<where>" + Constants.NEW_LINE + Constants.CHAR_SPACE +
                script + Constants.NEW_LINE +
                "</where>";
    }

    /**
     * 转换成foreach脚本标签
     * @param script     SQL脚本
     * @param collection 数据集
     * @param item       数据
     * @param open       前缀
     * @param close      后缀
     * @param separator  分隔符
     * @return foreach脚本标签
     */
    public static String convertForeachTag( final String script, final String collection, final String item,
                                            final String open, final String close, final String separator ) {
        return convertForeachTag( script, collection, null, item, open, close, separator );
    }

    /**
     * 转换成foreach脚本标签
     * @param script     SQL脚本
     * @param collection 数据集
     * @param index      索引
     * @param item       数据
     * @param open       前缀
     * @param close      后缀
     * @param separator  分隔符
     * @return foreach脚本标签
     */
    public static String convertForeachTag( final String script, final String collection, final String index,
                                            final String item, final String open, final String close,
                                            final String separator ) {
        StringBuilder builder = new StringBuilder( "<foreach" );
        if ( StringUtil.hasText( collection ) ) {
            builder.append( " collection=\"" ).append( collection ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( index ) ) {
            builder.append( " index=\"" ).append( index ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( item ) ) {
            builder.append( " item=\"" ).append( item ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( open ) ) {
            builder.append( " open=\"" ).append( open ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( close ) ) {
            builder.append( " close=\"" ).append( close ).append( Constants.CHAR_QUOTE );
        }
        if ( StringUtil.hasText( separator ) ) {
            builder.append( " separator=\"" ).append( separator ).append( Constants.CHAR_QUOTE );
        }
        return builder.append( Constants.CHAR_GT ).append( Constants.NEW_LINE )
                .append( script ).append( "</foreach>" ).append( Constants.NEW_LINE ).toString();
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
     * @param column  字段包装对象
     * @param argName 参数名称
     * @param execute 执行类型
     * @return 参数字符串
     */
    public static String convertPartArg( final ColumnWrapper column, final String argName, final Execute execute ) {
        return convertPartArg( null, column, argName, Symbol.EQ, execute );
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
     *     // Examples: 
     *     // execute = {@link Execute#REPLACE} && tableAlias == null
     *     ScriptUtil.convertPartArg()
     *     return: 
     *     USER_NAME = #{entity.userName}
     *     return: 
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
     * @param tableAlias   表别名
     * @param column       字段
     * @param symbol       条件符号
     * @param logic        逻辑符号
     * @param placeholders 参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final ColumnWrapper column,
                                              final Symbol symbol, final Logic logic, final String... placeholders ) {
        return convertConditionArg( tableAlias, column.getColumn(), column.getJdbcType(),
                column.getTypeHandler(), column.getJavaType(), column.isUseJavaType(), symbol, logic, placeholders );
    }

    /**
     * 转换成条件参数
     * @param tableAlias   表别名
     * @param column       字段
     * @param symbol       条件符号
     * @param logic        逻辑符号
     * @param placeholders 参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final ColumnWrapper column,
                                              final Symbol symbol, final Logic logic, final List<String> placeholders ) {
        return convertConditionArg( tableAlias, column.getColumn(), column.getJdbcType(),
                column.getTypeHandler(), column.getJavaType(), column.isUseJavaType(), symbol, logic, placeholders );
    }

    /**
     * 转换成条件参数
     * @param tableAlias   表别名
     * @param column       字段
     * @param symbol       条件符号
     * @param logic        逻辑符号
     * @param placeholders 参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column,
                                              final Symbol symbol, final Logic logic, final String... placeholders ) {
        return convertConditionArg( tableAlias, column, null, null, null,
                false, symbol, logic, placeholders );
    }

    /**
     * 转换成条件参数
     * @param tableAlias   表别名
     * @param column       字段
     * @param symbol       条件符号
     * @param logic        逻辑符号
     * @param placeholders 参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column,
                                              final Symbol symbol, final Logic logic, final List<String> placeholders ) {
        return convertConditionArg( tableAlias, column, null, null, null,
                false, symbol, logic, placeholders );
    }

    /**
     * 转换成条件参数
     * @param tableAlias    表别名
     * @param column        字段
     * @param jdbcType      JDBC类型
     * @param typeHandler   类型处理器
     * @param javaType      Java类型
     * @param isUseJavaType 是否使用Java类型
     * @param symbol        条件符号
     * @param logic         逻辑符号
     * @param placeholders  参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column,
                                              final JdbcType jdbcType, final Class<?> typeHandler,
                                              final Class<?> javaType, final boolean isUseJavaType,
                                              final Symbol symbol, final Logic logic, final String... placeholders ) {
        return convertConditionArg( tableAlias, column, jdbcType, typeHandler, javaType, isUseJavaType,
                symbol, logic, ArrayUtil.toList( placeholders ) );
    }


    /**
     * 转换成条件参数
     * @param tableAlias    表别名
     * @param column        字段
     * @param jdbcType      JDBC类型
     * @param typeHandler   类型处理器
     * @param javaType      Java类型
     * @param isUseJavaType 是否使用Java类型
     * @param symbol        条件符号
     * @param logic         逻辑符号
     * @param placeholders  参数值
     * @return 参数字符串
     */
    public static String convertConditionArg( final String tableAlias, final String column,
                                              final JdbcType jdbcType, final Class<?> typeHandler,
                                              final Class<?> javaType, final boolean isUseJavaType,
                                              final Symbol symbol, final Logic logic, final List<String> placeholders ) {
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
        if ( Symbol.filter( realSymbol ) && CollectionUtil.hasElement( placeholders ) ) {
            switch ( symbol ) {
                case EQ:
                case NE:
                case LT:
                case LE:
                case GT:
                case GE:
                case LIKE:
                case NOT_LIKE:
                    builder.append( safeJoint( placeholders.get( 0 ), concatIntactArg( javaType, jdbcType,
                            typeHandler, isUseJavaType ) ) );
                    break;
                case IN:
                case NOT_IN:
                    builder.append( placeholders.stream().map( it -> safeJoint( it,
                            concatIntactArg( javaType, jdbcType, typeHandler, isUseJavaType ) ) )
                            .collect( Collectors.joining( ", ", "(", ")" ) ) );
                    break;
                case BETWEEN:
                case NOT_BETWEEN:
                    builder.append( placeholders.stream().limit( 2 ).map( it -> safeJoint( it,
                            concatIntactArg( javaType, jdbcType, typeHandler, isUseJavaType ) ) )
                            .collect( Collectors.joining( " AND " ) ) );
                    break;
            }
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
