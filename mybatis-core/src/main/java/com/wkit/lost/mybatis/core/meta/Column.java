package com.wkit.lost.mybatis.core.meta;

import com.wkit.lost.mybatis.annotation.extension.FillingRule;
import com.wkit.lost.mybatis.handler.EntityHandler;
import com.wkit.lost.mybatis.utils.StringUtil;
import com.wkit.lost.mybatis.annotation.extension.Dialect;
import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.config.MyBatisConfigCache;
import com.wkit.lost.mybatis.core.Execute;
import com.wkit.lost.mybatis.core.Operator;
import com.wkit.lost.mybatis.incrementer.SequenceKeyGenerator;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.util.HashSet;
import java.util.Set;

/**
 * 数据库表字段映射信息
 * @author DT
 */
@Accessors( chain = true )
@EqualsAndHashCode
@ToString
@Getter
@Setter( AccessLevel.PACKAGE )
@AllArgsConstructor
public class Column {

    /**
     * 实体类
     */
    private Class<?> entity;

    /**
     * 属性对象
     */
    private Attribute attribute;

    /**
     * 属性
     */
    private String property;

    /**
     * 字段映射
     */
    private String column;

    /**
     * Java类型
     */
    private Class<?> javaType;

    /**
     * Jdbc类型
     */
    private JdbcType jdbcType;

    /**
     * 类型处理器
     */
    private Class<? extends TypeHandler<?>> typeHandler;

    /**
     * 序列名称
     */
    private String sequenceName;

    /**
     * 是否为主键
     */
    private boolean primaryKey = false;

    /**
     * 是否为UUID主键
     */
    private boolean uuid = false;

    /**
     * 是否为自增主键
     */
    private boolean identity = false;

    /**
     * 是否为雪花算法主键
     */
    private boolean worker = false;

    /**
     * 是否为雪花算法字符串主键
     */
    private boolean workerString = false;

    /**
     * 是否为Blob类型
     */
    private boolean blob = false;

    /**
     * 是否可保存
     */
    private boolean insertable = true;

    /**
     * 是否可修改
     */
    private boolean updatable = true;

    /**
     * SQL语句是否设置Java类型
     */
    private boolean useJavaType = false;

    /**
     * 字符串非空校验
     */
    private boolean checkNotEmpty;

    /**
     * 乐观锁
     */
    private boolean version = false;

    /**
     * 排序方式
     */
    private String orderBy;

    /**
     * 主键生成方式
     */
    private String generator;

    /**
     * SQL执行时机
     */
    private Executing executing;

    /**
     * 值
     */
    private Object value;

    /**
     * 保存操作是否自动填充值
     */
    private boolean insertFilling;

    /**
     * 保存操作是否自动填充值
     */
    private boolean updateFilling;

    /**
     * 保存操作是否自动填充值
     */
    private boolean deleteFilling;

    /**
     * 是否为逻辑删除属性
     */
    private boolean logicDelete;

    /**
     * 标识为已删除值
     */
    private String logicDeleteValue;

    /**
     * 标识为未删除值
     */
    private String logicNotDeleteValue;

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
     * 设置是否自动填充值
     * @param rule  填充规则
     * @param value 值
     * @return 当前对象
     */
    Column setFilling( FillingRule rule, boolean value ) {
        if ( rule != null ) {
            if ( rule != FillingRule.NORMAL ) {
                if ( rule == FillingRule.INSERT ) {
                    this.insertFilling = value && insertable && !primaryKey;
                } else if ( rule == FillingRule.UPDATE ) {
                    this.updateFilling = value && updatable;
                } else {
                    this.deleteFilling = value && updatable && !logicDelete;
                }
            }
        }
        return this;
    }

    /**
     * 检查当前字段是否支持自动填充值
     * @return true: 是 false: 否
     */
    public boolean canFilling() {
        return ( this.insertable || this.updatable ) && !primaryKey && !logicDelete;
    }

    /**
     * 检查当前字段是否支持指定填充规则
     * @param rule 指定填充规则
     * @return true: 是 false: 否
     */
    public boolean canFilling( FillingRule rule ) {
        return rule == FillingRule.INSERT && insertFilling || rule == FillingRule.UPDATE && updateFilling || rule == FillingRule.DELETE && deleteFilling;
    }

    /**
     * 检查当前字段是否存在自动填充规则
     * @return true: 是 false: 否
     */
    public boolean hasFillingRule() {
        return enableInsertFilling() || enableUpdateFilling() || enableDeleteFilling();
    }

    /**
     * 检查是否启用保存操作自动填充值
     * @return true: 是 false: 否
     */
    public boolean enableInsertFilling() {
        return insertFilling;
    }

    /**
     * 检查是否启用更新操作自动填充值
     * @return true: 是 false: 否
     */
    public boolean enableUpdateFilling() {
        return updateFilling;
    }

    /**
     * 检查是否启用逻辑删除操作自动填充值
     * @return true: 是 false: 否
     */
    public boolean enableDeleteFilling() {
        return deleteFilling;
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

