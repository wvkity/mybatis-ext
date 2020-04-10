package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GradeVo implements Serializable {

    private static final long serialVersionUID = 446838895774432290L;
    /**
     * 年级编号
     */
    private Long id;

    /**
     * 年级名称
     */
    private String name;
}
