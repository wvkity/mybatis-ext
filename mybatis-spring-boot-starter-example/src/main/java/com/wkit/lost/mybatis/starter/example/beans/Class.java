package com.wkit.lost.mybatis.starter.example.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors( chain = true )
@NoArgsConstructor
@AllArgsConstructor
public class Class implements Serializable {

    private static final long serialVersionUID = 7494774025086292968L;
    private Long id;
    private String name;
    private Long teacherId;
}
