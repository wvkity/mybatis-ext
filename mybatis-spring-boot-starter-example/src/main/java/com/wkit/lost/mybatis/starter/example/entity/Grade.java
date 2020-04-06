package com.wkit.lost.mybatis.starter.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

/**
 * 年级
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Grade extends BaseEntity {
    
    private static final long serialVersionUID = -4653601420462226184L;

    /**
     * 年级名称
     */
    private String name;
    
}

