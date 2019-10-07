package com.wkit.lost.mybatis.core.meta;

import com.wkit.lost.mybatis.annotation.extension.Executing;
import com.wkit.lost.mybatis.annotation.extension.FillingRule;
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

}

