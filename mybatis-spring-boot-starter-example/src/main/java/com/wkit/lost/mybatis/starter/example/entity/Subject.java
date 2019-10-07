package com.wkit.lost.mybatis.starter.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

import java.io.Serializable;

/**
 * 科目信息
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Subject implements Serializable {

    private static final long serialVersionUID = 2144459382963875592L;

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
