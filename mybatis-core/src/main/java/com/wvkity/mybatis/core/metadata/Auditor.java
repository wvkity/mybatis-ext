package com.wvkity.mybatis.core.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 字段审计相关信息
 * @author wvkity
 */
@Getter
@ToString
@EqualsAndHashCode
public final class Auditor {

    /**
     * 标识保存操作时间是否自动填充
     */
    private final boolean createdDate;

    /**
     * 标识保存操作用户标识是否自动填充
     */
    private final boolean createdUser;

    /**
     * 标识保存操作用户名是否自动填充
     */
    private final boolean createdUserName;

    /**
     * 标识删除操作时间是否自动填充
     */
    private final boolean deletedDate;

    /**
     * 标识删除操作用户标识是否自动填充
     */
    private final boolean deletedUser;

    /**
     * 标识删除操作用户名是否自动填充
     */
    private final boolean deletedUserName;

    /**
     * 标识更新操作时间是否自动填充
     */
    private final boolean lastModifiedDate;

    /**
     * 标识更新操作用户标识是否自动填充
     */
    private final boolean lastModifiedUser;

    /**
     * 标识更新操作用户名是否自动填充
     */
    private final boolean lastModifiedUserName;

    /**
     * 是否为逻辑删除属性
     */
    private final boolean logicDelete;

    /**
     * 逻辑删除真值
     */
    private final Object logicDeletedTrueValue;

    /**
     * 逻辑删除假值
     */
    private final Object logicDeletedFalseValue;

    /**
     * 构造方法
     * @param createdDate            标识保存操作时间是否自动填充
     * @param createdUser            标识保存操作用户标识是否自动填充
     * @param createdUserName        标识保存操作用户名是否自动填充
     * @param deletedDate            标识删除操作时间是否自动填充
     * @param deletedUser            标识删除操作用户标识是否自动填充
     * @param deletedUserName        标识删除操作用户名是否自动填充
     * @param lastModifiedDate       标识更新操作时间是否自动填充
     * @param lastModifiedUser       标识更新操作用户标识是否自动填充
     * @param lastModifiedUserName   标识更新操作用户名是否自动填充
     * @param logicDelete            是否为逻辑删除属性
     * @param logicDeletedTrueValue  逻辑删除真值
     * @param logicDeletedFalseValue 逻辑删除假值
     */
    public Auditor(boolean createdDate, boolean createdUser, boolean createdUserName, boolean deletedDate,
                   boolean deletedUser, boolean deletedUserName, boolean lastModifiedDate,
                   boolean lastModifiedUser, boolean lastModifiedUserName, boolean logicDelete,
                   Object logicDeletedTrueValue, Object logicDeletedFalseValue) {
        this.createdDate = createdDate;
        this.createdUser = createdUser;
        this.createdUserName = createdUserName;
        this.deletedDate = deletedDate;
        this.deletedUser = deletedUser;
        this.deletedUserName = deletedUserName;
        this.lastModifiedDate = lastModifiedDate;
        this.lastModifiedUser = lastModifiedUser;
        this.lastModifiedUserName = lastModifiedUserName;
        this.logicDelete = logicDelete;
        this.logicDeletedTrueValue = logicDeletedTrueValue;
        this.logicDeletedFalseValue = logicDeletedFalseValue;
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
