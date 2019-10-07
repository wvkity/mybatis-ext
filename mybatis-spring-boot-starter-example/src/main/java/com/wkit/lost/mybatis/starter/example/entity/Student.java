package com.wkit.lost.mybatis.starter.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生信息
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Student implements Serializable {
    private static final long serialVersionUID = -5414906538791067259L;

    /**
     * 学生ID(学号)
     */
    private String id;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 所在年级
     */
    private Long gradeId;

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
}
