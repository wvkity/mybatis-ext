package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
public class TeacherVo implements Serializable {

    private static final long serialVersionUID = 2210189471065932457L;
    private Long id;
    private String name;
    private List<ClassVo> classes;
}
