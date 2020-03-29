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
 * 老师
 */
@Data
@EqualsAndHashCode
@ToString
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Teacher implements Serializable {
    private static final long serialVersionUID = 1278029478672232127L;

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
     * 所属年级
     */
    private Long gradeId;

    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
}
