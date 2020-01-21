package com.wkit.lost.mybatis.starter.example.entity;

import com.wkit.lost.mybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 成绩信息
 */
@Table( catalog = "STUDENT_MANAGEMENT" )
@Data
@EqualsAndHashCode
@ToString
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@Getter
@Setter
public class Result implements Serializable {
    private static final long serialVersionUID = -127005466439690613L;

    /**
     * 成绩ID
     */
    private Long id;
    /**
     * 学生ID(学号)
     */
    private String studentId;
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
