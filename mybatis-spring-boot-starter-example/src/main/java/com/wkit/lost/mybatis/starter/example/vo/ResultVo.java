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
 * 成绩信息
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResultVo implements Serializable {

    private static final long serialVersionUID = 4481859944544999070L;
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
