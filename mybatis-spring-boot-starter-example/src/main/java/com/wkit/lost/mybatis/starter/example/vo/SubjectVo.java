package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 科目信息
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SubjectVo implements Serializable {

    private static final long serialVersionUID = -2982102142884620496L;
    /**
     * 科目ID
     */
    private Long id;

    /**
     * 科目名称
     */
    private String name;

    /**
     * 课程学时
     */
    private Integer hours;

    /**
     * 所属年级
     */
    private Long gradeId;
}
