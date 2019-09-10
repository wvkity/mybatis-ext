package com.wkit.lost.mybatis.core.schema;

import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.incrementer.SequenceKeyGenerator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 数据库表字段映射信息
 * @author DT
 */
@Accessors( chain = true )
@EqualsAndHashCode
@ToString
public class Column {

    /**
     * 实体类
     */
    @Getter
    private Class<?> entity;

    /**
     * 属性对象
     */
    @Getter
    @Setter
    private Attribute attribute;

    /**
     * 属性
     */
    @Getter
    private String property;

    /**
     * 字段映射
     */
    @Getter
    private String column;

    /**
     * Java类型
     */
    @Getter
    @Setter
    private Class<?> javaType;

    /**
     * Jdbc类型
     */
    @Getter
    @Setter
    private JdbcType jdbcType;

    /**
     * 类型处理器
     */
    @Getter
    @Setter
    private Class<? extends TypeHandler<?>> typeHandler;

    /**
     * 序列名称
     */
    @Getter
    @Setter
    private String sequenceName;

    /**
     * 是否为主键
     */
    @Getter
    @Setter
    private boolean primaryKey = false;

    /**
     * 是否为UUID主键
     */
    @Getter
    @Setter
    private boolean uuid = false;

    /**
     * 是否为自增主键
     */
    @Getter
    @Setter
    private boolean identity = false;

    /**
     * 是否为Blob类型
     */
    @Getter
    @Setter
    private boolean blob = false;

    /**
     * 是否可保存
     */
    @Getter
    @Setter
    private boolean insertable = true;

    /**
     * 是否可修改
     */
    @Getter
    @Setter
    private boolean updatable = true;

    /**
     * SQL语句是否设置Java类型
     */
    @Getter
    @Setter
    private boolean useJavaType = false;

    /**
     * 字符串非空校验
     */
    @Getter
    @Setter
    private boolean checkNotEmpty;

    /**
     * 排序方式
     */
    @Getter
    @Setter
    private String orderBy;

    /**
     * 主键生成方式
     */
    @Getter
    @Setter
    private String generator;

    /**
     * SQL执行时机
     */
    @Getter
    @Setter
    private Executing executing;

    /**
     * 值
     */
    @Getter
    @Setter
    private Object value;

    /**
     * 构造方法
     * @param entity   实体类
     * @param property 属性名
     * @param column   列名
     */
    public Column( Class<?> entity, String property, String column ) {
        this.entity = entity;
        this.property = property;
        this.column = column;
    }

    /**
     * 获取{@link Table}映射信息
     * @return {@link Table}映射信息
     */
    public Table getTable() {
        return EntityHandler.getTable( this.entity );
    }

    /**
     * 转换成INSERT参数字符串
     * @return 字符串
     * @see #convertToArg(Execute, String, String, String, String)
     */
    public String convertToInsertArg() {
        return convertToArg( Execute.INSERT );
    }

    /**
     * 转换成参数字符串
     * @param execute 执行操作类型
     * @return 字符串
     * @see #convertToArg(Execute, String, String, String, String)
     */
    public String convertToArg( final Execute execute ) {
        return convertToArg( execute, null, null, null, null );
    }

    /**
     * 转换成参数字符串
     * @param execute 执行操作类型
     * @param argName 参数名称 [可选]
     * @return 字符串
     */
    public String convertToArg( final Execute execute, final String argName ) {
        return convertToArg( execute, argName, null, null, null );
    }

    /**
     * 转换成参数字符串
     * @param execute 执行操作类型
     * @param argName 参数名称 [可选]
     * @param alias   别名 [可选]
     * @return 字符串
     * @see #convertToArg(Execute, String, String, String, String)
     */
    public String convertToArg( final Execute execute, final String argName, final String alias ) {
        return convertToArg( execute, argName, alias, null, null );
    }

    /**
     * 转换成参数字符串
     * @param execute  执行操作类型
     * @param argName  参数名称 [可选]
     * @param alias    别名 [可选]
     * @param operator 操作符 [可选]
     * @return 字符串
     * @see #convertToArg(Execute, String, String, String, String)
     */
    public String convertToArg( final Execute execute, final String argName, final String alias, String operator ) {
        return convertToArg( execute, argName, alias, operator, null );
    }


    /**
     * 转换成参数字符串
     * <p>
     * 别名：{@code A} <br>
     * 字段名：{@code USER_NAME} <br>
     * 对应属性: {@code userName} <br>
     * 参数名：{@code record} <br>
     * eg:<br>
     * 条件: <br>
     * &nbsp;&nbsp;A.USER_NAME = #{record.userName} <br>
     * &nbsp;&nbsp;A.USER_NAME = #{record.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br><br>
     * 保存操作: <br>
     * &nbsp;&nbsp;#{record.userName} <br>
     * &nbsp;&nbsp;#{record.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br><br>
     * 更新操作: <br>
     * &nbsp;&nbsp;USER_NAME = #{record.userName} <br>
     * &nbsp;&nbsp;USER_NAME = #{record.userName, javaType="java.lang.String", jdbcType="VARCHAR", typeHandler="com.wkit.lost.mybatis.type.handlers.StandardInstantTypeHandler"} <br>
     * </p>
     * @param execute   执行操作类型
     * @param argName   参数名称 [可选]
     * @param alias     别名 [可选]
     * @param operator  操作符 [可选]
     * @param separator 分隔符 [可选]
     * @return 字符串
     */
    public String convertToArg( final Execute execute, final String argName, final String alias, String operator, String separator ) {
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
            buffer.append( this.column ).append( " " ).append( operator );
        }
        buffer.append( " #{" );
        // 参数属性名
        if ( execute == Execute.NONE ) {
            buffer.append( "value" );
        } else {
            if ( StringUtil.hasText( argName ) ) {
                buffer.append( argName ).append( "." );
            }
            buffer.append( this.property );
        }
        appendValue( buffer );
        buffer.append( "}" );
        if ( StringUtil.hasText( separator ) ) {
            buffer.append( separator );
        }
        return buffer.toString();
    }

    /**
     * 转换成查询字段
     * @param alias     别名
     * @param reference 实体内的引用属性名
     * @param apply     是否带上属性名
     * @return 字符串
     */
    public String convertToQueryArg( final String alias, final String reference, final boolean apply ) {
        StringBuffer buffer = new StringBuffer( 60 );
        if ( StringUtil.hasText( alias ) ) {
            buffer.append( alias ).append( "." );
        }
        buffer.append( this.column );
        if ( apply ) {
            buffer.append( " " );
            if ( StringUtil.hasText( reference ) ) {
                buffer.append( "\"" ).append( reference ).append( "." ).append( this.property ).append( "\"" );
            } else {
                buffer.append( this.property );
            }
        }
        return buffer.toString();
    }

    /**
     * 拼接自定义参数
     * @param paramValuePlaceHolder 参数值占位符
     * @param alias                 别名
     * @param operator              操作符
     * @param join                  连接符
     * @return SQL字符串
     */
    public String convertToCustomArg( final String paramValuePlaceHolder, final String alias, Operator operator, String join ) {
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
        buffer.append( this.column ).append( " " ).append( operator.getSqlSegment() );
        if ( Operator.filter( operator ) ) {
            buffer.append( " #{" );
            // 参数属性名
            buffer.append( paramValuePlaceHolder );
            appendValue( buffer );
            buffer.append( "}" );
        }
        return buffer.toString();
    }

    private void appendValue( StringBuffer buffer ) {
        if ( buffer != null ) {
            // 指定JDBC类型
            if ( this.jdbcType != null ) {
                buffer.append( ", jdbcType=" ).append( this.jdbcType.toString() );
            }
            // 指定类型处理器
            if ( this.typeHandler != null ) {
                buffer.append( ", typeHandler=" ).append( this.typeHandler.getCanonicalName() );
            }
            // 指定Java类型
            if ( this.useJavaType && !this.javaType.isArray() ) {
                buffer.append( ", javaType=" ).append( this.javaType.getCanonicalName() );
            }
        }
    }

    /**
     * 获取序列脚本
     * @param configuration MyBatis配置对象
     * @return 序列SQL
     */
    public String getSequenceScript( final Configuration configuration ) {
        return getSequenceScript( MyBatisConfigCache.getDialect( configuration ) );
    }

    /**
     * 获取序列脚本
     * @param dialect 数据库类型
     * @return 序列SQL
     */
    public String getSequenceScript( final Dialect dialect ) {
        return SequenceKeyGenerator.getInstance( dialect ).toSqlString( this.sequenceName );
    }

}
