package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
public class StudentVo implements Serializable {
    private static final long serialVersionUID = -5651897878270292608L;
    private Long id;
    private String name;
}
