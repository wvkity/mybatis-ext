package com.wvkity.mybatis.starter.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 教师-班级-科目关系表
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
@EqualsAndHashCode
@Accessors(chain = true)
public class Relevance implements Serializable {
    
    private static final long serialVersionUID = -7467852724827097042L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 老师ID
     */
    private Long teacherId;

    /**
     * 班级ID
     */
    private Long klassId;
    
    /**
     * 科目ID
     */
    private Long subjectId;

    /**
     * 学年(届)
     */
    private Integer period;

    /**
     * 年份
     */
    private Integer year;

    /**
     * 学期(1: 第一学期, 2: 第二学期)
     */
    private Integer semester;

    public Relevance(Long teacherId, Long klassId) {
        this.teacherId = teacherId;
        this.klassId = klassId;
    }

    public Relevance(Long teacherId, Long klassId, Long subjectId, Integer period, Integer year, Integer semester) {
        this.teacherId = teacherId;
        this.klassId = klassId;
        this.subjectId = subjectId;
        this.period = period;
        this.year = year;
        this.semester = semester;
    }
}
