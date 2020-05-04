package com.wvkity.mybatis.starter.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;


/**
 * 考试信息
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
@Accessors(chain = true)
public class Exam {
    private static final long serialVersionUID = -127005466439690613L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 学生ID(学号)
     */
    private Long studentId;

    /**
     * 考试代号
     */
    private String examCode;
    
    /**
     * 所属科目
     */
    private Long subjectId;
    
    /**
     * 分数
     */
    private Integer score;

    /**
     * 年份
     */
    private Integer examYear;

    /**
     * 学年(届)
     */
    private Integer period;

    /**
     * 年级: 1(初一年级), 2(初三年级), 3(初三年级)
     */
    private Integer grade;

    /**
     * 学期(1: 第一学期, 2: 第二学期)
     */
    private Integer semester;
    
    /**
     * 考试日期
     */
    private LocalDateTime examDate;

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

