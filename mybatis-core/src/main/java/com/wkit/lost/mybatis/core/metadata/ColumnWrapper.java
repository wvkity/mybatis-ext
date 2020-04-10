package com.wkit.lost.mybatis.core.metadata;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.core.data.auditing.AuditMatching;
import com.wkit.lost.mybatis.core.handler.TableHandler;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * 实体属性-数据库表字段映射
 * @author wvkity
 */
@Getter
@Setter(AccessLevel.PACKAGE)
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class ColumnWrapper {

    /**
     * 实体类
     */
    private final Class<?> entity;

    /**
     * 属性对象
     */
    private final FieldWrapper field;

    /**
     * 属性
     */
    private final String property;

    /**
     * 字段映射
     */
    private final String column;

    /**
     * Java类型
     */
    private final Class<?> javaType;

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
    private final boolean primaryKey;

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
    private Object logicDeletedTrueValue;

    /**
     * 逻辑删除假值
     */
    private Object logicDeletedFalseValue;

    /**
     * 构造方法
     * @param entity     实体类
     * @param field      属性对象
     * @param property   属性
     * @param column     字段
     * @param javaType   Java类型
     * @param primaryKey 是否为主键
     */
    public ColumnWrapper(Class<?> entity, FieldWrapper field, String property,
                         String column, Class<?> javaType, boolean primaryKey) {
        this.entity = entity;
        this.field = field;
        this.property = property;
        this.column = column;
        this.javaType = javaType;
        this.primaryKey = primaryKey;
    }

    /**
     * 获取数据库表映射对象
     * @return 数据库表映射对象
     */
    public TableWrapper table() {
        return TableHandler.getTable(this.entity);
    }

    /**
     * 检查当前字段是否可自动审计
     * @return true: 是, false: 否
     */
    public boolean isAuditable() {
        return (this.insertable || this.updatable) && !primaryKey && !logicDelete;
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
                return !this.logicDelete && deletedAuditable();
            default:
                return false;
        }
    }

    /**
     * 检查当前字段是否开启保存操作审计
     * @return true: 是, false: 否
     */
    public boolean insertedAuditable() {
        return this.createdDate || this.createdUser || this.createdUserName;
    }

    /**
     * 检查当前字段是否开启更新操作审计
     * @return true: 是, false: 否
     */
    public boolean modifiedAuditable() {
        return this.lastModifiedDate || this.lastModifiedUser || this.lastModifiedUserName;
    }

    /**
     * 检查当前字段是否开启删除操作审计
     * @return true: 是, false: 否
     */
    public boolean deletedAuditable() {
        return this.deletedDate || this.deletedUser || this.deletedUserName;
    }
}
