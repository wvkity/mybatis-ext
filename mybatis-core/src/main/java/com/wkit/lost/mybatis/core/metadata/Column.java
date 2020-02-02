package com.wkit.lost.mybatis.core.metadata;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.data.auditing.AuditMatching;
import com.wkit.lost.mybatis.handler.EntityHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 数据库表字-实体属性段映射信息
 * @author wvkity
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
    private Field field;

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
    private boolean snowflakeSequence = false;

    /**
     * 是否为雪花算法字符串主键
     */
    private boolean snowflakeSequenceString = false;

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

    // 审计
    /**
     * 标识保存操作时间是否自动填充
     */
    private boolean createdDate;

    /**
     * 标识保存操作用户标识是否自动填充
     */
    private boolean createdUser;

    /**
     * 标识保存操作用户名是否自动填充
     */
    private boolean createdUserName;

    /**
     * 标识删除操作时间是否自动填充
     */
    private boolean deletedDate;

    /**
     * 标识删除操作用户标识是否自动填充
     */
    private boolean deletedUser;

    /**
     * 标识删除操作用户名是否自动填充
     */
    private boolean deletedUserName;

    /**
     * 标识更新操作时间是否自动填充
     */
    private boolean lastModifiedDate;

    /**
     * 标识更新操作用户标识是否自动填充
     */
    private boolean lastModifiedUser;

    /**
     * 标识更新操作用户名是否自动填充
     */
    private boolean lastModifiedUserName;

    /**
     * 是否为逻辑删除属性
     */
    private boolean logicDelete;

    /**
     * 逻辑删除真值
     */
    private String logicDeletedTrueValue;

    /**
     * 逻辑删除假值
     */
    private String logicDeletedFalseValue;

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
     * 检查当前字段是否可自动审计
     * @return true: 是, false: 否
     */
    public boolean isAuditable() {
        return ( this.insertable || this.updatable ) && !primaryKey && !logicDelete;
    }

    /**
     * 检查当前字段是否可自动审计
     * @param matching 规则
     * @return true: 是, false: 否
     */
    public boolean isAuditable( AuditMatching matching ) {
        switch ( matching ) {
            case INSERTED:
                return enableInsertedAuditable() && this.insertable;
            case MODIFIED:
                return enableModifiedAuditable() && this.updatable && !primaryKey;
            case DELETED:
                return enableDeletedAuditable() && !logicDelete;
            default:
                return false;
        }
    }
    
    public boolean enableInsertedAuditable() {
        return this.createdDate || this.createdUser || this.createdUserName;
    }
    
    public boolean enableModifiedAuditable() {
        return this.lastModifiedDate || this.lastModifiedUser || this.lastModifiedUserName;
    }
    
    public boolean enableDeletedAuditable() {
        return this.deletedDate || this.deletedUser || this.deletedUserName;
    }
    
}

