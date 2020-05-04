package com.wvkity.mybatis.starter.example.entity;

import com.wvkity.mybatis.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;

/**
 * 学生信息
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
@Accessors(chain = true)
public class Student {
    private static final long serialVersionUID = -5414906538791067259L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 出生日期
     */
    private LocalDateTime birthday;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 所属班级
     */
    private Long klassId;

    /**
     * 学届
     */
    private Integer period;

    /**
     * 版本
     */
    @Version
    private Integer version;
    
    /**
     * 删除标识
     */
    private Boolean deleted;

    /**
     * 创建人
     */
    private String createdUserName;

    /**
     * 创建人ID
     */
    private Long createdUserId;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;

    /**
     * 最后更新人
     */
    private String lastModifiedUserName;

    /**
     * 最后更新人ID
     */
    private Long lastModifiedUserId;

    /**
     * 最后更新时间
     */
    private LocalDateTime gmtLastModified;

    /**
     * 删除人ID
     */
    private Long deletedUserId;

    /**
     * 删除人名称
     */
    private String deletedUserName;

    /**
     * 删除时间
     */
    private LocalDateTime gmtDeleted;

}
