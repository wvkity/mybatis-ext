package com.wvkity.mybatis.core.metadata;

import com.wvkity.mybatis.annotation.extension.Executing;
import com.wvkity.mybatis.core.data.auditing.AuditMatching;
import com.wvkity.mybatis.core.handler.TableHandler;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.lang.reflect.Field;

/**
 * 实体属性-数据库表字段映射
 * @author wvkity
 */
@Getter
@ToString
@EqualsAndHashCode
public class ColumnWrapper {

    /**
     * 实体类
     */
    private final Class<?> entity;

    /**
     * 属性
     */
    private final String property;

    /**
     * 字段映射
     */
    private final String column;

    /**
     * Jdbc类型
     */
    private final JdbcType jdbcType;

    /**
     * 类型处理器
     */
    private final Class<? extends TypeHandler<?>> typeHandler;

    /**
     * 序列名称
     */
    private final String sequenceName;

    /**
     * 是否为主键
     */
    private final boolean primaryKey;

    /**
     * 是否为Blob类型
     */
    private final boolean blob;

    /**
     * 是否可保存
     */
    private final boolean insertable;

    /**
     * 是否可修改
     */
    private final boolean updatable;

    /**
     * SQL语句是否设置Java类型
     */
    private final boolean useJavaType;

    /**
     * 字符串非空校验
     */
    private final boolean checkNotEmpty;

    /**
     * 乐观锁
     */
    private final boolean version;

    /**
     * 基础信息
     */
    @Getter(AccessLevel.NONE)
    private final Descriptor descriptor;

    /**
     * 主键信息
     */
    @Getter(AccessLevel.NONE)
    private final Unique unique;

    /**
     * 审计信息
     */
    @Getter(AccessLevel.NONE)
    private final Auditor auditor;

    /**
     * 构造方法
     * @param entity        实体类
     * @param property      属性
     * @param column        字段名
     * @param jdbcType      JDBC类型
     * @param typeHandler   类型处理器
     * @param sequenceName  序列名称
     * @param primaryKey    是否为主键
     * @param blob          是否为BLOB类型
     * @param insertable    是否可保存
     * @param updatable     是否可更新
     * @param useJavaType   是否使用java类型
     * @param checkNotEmpty 是否执行非空检查
     * @param version       是否为乐观锁
     * @param descriptor    基本信息包装对象
     * @param unique        主键包装对象
     * @param auditor       审计包装对象
     */
    public ColumnWrapper(Class<?> entity, String property, String column, JdbcType jdbcType,
                         Class<? extends TypeHandler<?>> typeHandler, String sequenceName, boolean primaryKey,
                         boolean blob, boolean insertable, boolean updatable, boolean useJavaType,
                         boolean checkNotEmpty, boolean version, Descriptor descriptor, Unique unique,
                         Auditor auditor) {
        this.entity = entity;
        this.property = property;
        this.column = column;
        this.jdbcType = jdbcType;
        this.typeHandler = typeHandler;
        this.sequenceName = sequenceName;
        this.primaryKey = primaryKey;
        this.blob = blob;
        this.insertable = insertable;
        this.updatable = updatable;
        this.useJavaType = useJavaType;
        this.checkNotEmpty = checkNotEmpty;
        this.version = version;
        this.descriptor = descriptor;
        this.unique = unique;
        this.auditor = auditor;
    }

    /**
     * 获取数据库表映射对象
     * @return 数据库表映射对象
     */
    public TableWrapper table() {
        return TableHandler.getTable(this.entity);
    }

    /**
     * JAVA类型
     * @return Class
     */
    public Class<?> getJavaType() {
        return this.descriptor.getJavaType();
    }

    /**
     * 字段信息
     * @return {@link Field}
     */
    public Field getField() {
        return this.descriptor.getField();
    }

    /**
     * SQL执行时机
     * @return {@link Executing}
     */
    public Executing getExecuting() {
        return unique.getExecuting();
    }

    /**
     * 是否为自增主键
     * @return true: 是, false: 否
     */
    public boolean isIdentity() {
        return this.unique.isIdentity();
    }

    /**
     * 是否为UUID主键
     * @return true: 是, false: 否
     */
    public boolean isUuid() {
        return this.unique.isUuid();
    }

    /**
     * 是否为雪花算法主键
     * @return true: 是, false: 否
     */
    public boolean isSnowflakeSequence() {
        return this.unique.isSnowflakeSequence();
    }

    /**
     * 是否为雪花算法字符串主键
     * @return true: 是, false: 否
     */
    public boolean isSnowflakeSequenceString() {
        return this.unique.isSnowflakeSequenceString();
    }

    /**
     * 获取逻辑删除真值
     * @return Object
     */
    public Object getLogicDeletedTrueValue() {
        return this.auditor.getLogicDeletedTrueValue();
    }

    /**
     * 获取逻辑删除假值
     * @return Object
     */
    public Object getLogicDeletedFalseValue() {
        return this.auditor.getLogicDeletedFalseValue();
    }

    /**
     * 是否为逻辑删除字段
     * @return boolean
     */
    public boolean isLogicDelete() {
        return this.auditor.isLogicDelete();
    }

    /**
     * 检查当前字段是否可自动审计
     * @return true: 是, false: 否
     */
    public boolean isAuditable() {
        return (this.insertable || this.updatable) && !primaryKey && !auditor.isLogicDelete();
    }

    /**
     * 检查当前字段是否可自动审计
     * @param matching 审计类型
     * @return true: 是, false: 否
     */
    public boolean isAuditable(AuditMatching matching) {
        switch (matching) {
            case INSERTED:
                return this.insertable && this.insertedAuditable();
            case MODIFIED:
                return this.updatable && !primaryKey && this.modifiedAuditable();
            case DELETED:
                return !this.auditor.isLogicDelete() && deletedAuditable();
            default:
                return false;
        }
    }

    /**
     * 检查当前字段是否开启保存操作审计
     * @return true: 是, false: 否
     */
    public boolean insertedAuditable() {
        return this.auditor.isCreatedDate() || this.auditor.isCreatedUser() || this.auditor.isCreatedUserName();
    }

    /**
     * 检查当前字段是否开启更新操作审计
     * @return true: 是, false: 否
     */
    public boolean modifiedAuditable() {
        return this.auditor.isLastModifiedDate() || this.auditor.isLastModifiedUser()
                || this.auditor.isLastModifiedUserName();
    }

    /**
     * 检查当前字段是否开启删除操作审计
     * @return true: 是, false: 否
     */
    public boolean deletedAuditable() {
        return this.auditor.isDeletedDate() || this.auditor.isDeletedUser() || this.auditor.isDeletedUserName();
    }

    /**
     * 检查当前字段是否开启时间审计
     * @return true: 是, false: 否
     */
    public boolean isDateAuditable() {
        return this.auditor.isCreatedDate() || this.auditor.isLastModifiedDate() || this.auditor.isDeletedDate();
    }

    /**
     * 检查当前字段是否开启用户标识审计
     * @return true: 是, false: 否
     */
    public boolean isUserAuditable() {
        return this.auditor.isCreatedUser() || this.auditor.isLastModifiedUser() || this.auditor.isDeletedUser();
    }

    /**
     * 检查当前字段是否开启用户名审计
     * @return true: 是, false: 否
     */
    public boolean isUserNameAuditable() {
        return this.auditor.isCreatedUserName() || this.auditor.isLastModifiedUserName()
                || this.auditor.isDeletedUserName();
    }
}
