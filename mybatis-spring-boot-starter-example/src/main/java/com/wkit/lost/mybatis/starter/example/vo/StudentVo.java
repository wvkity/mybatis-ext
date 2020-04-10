package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 学生信息
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo implements Serializable {

    private static final long serialVersionUID = -3021476350176452866L;
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

    /**
     * 成绩
     */
    private Integer score;
}
