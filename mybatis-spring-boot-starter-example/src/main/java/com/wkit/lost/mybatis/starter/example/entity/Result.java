package com.wkit.lost.mybatis.starter.example.entity;

import com.wkit.lost.mybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;


/**
 * 成绩信息
 */
@Table(catalog = "STUDENT_MANAGEMENT")
@Data
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode(callSuper = true)
public class Result extends BaseEntity {
    private static final long serialVersionUID = -127005466439690613L;

    /**
     * 学生ID(学号)
     */
    private Long studentId;
    /**
     * 所属科目
     */
    private Long subjectId;
    /**
     * 分数
     */
    private Integer score;
    /**
     * 考试日期
     */
    private LocalDateTime examDate;
}
