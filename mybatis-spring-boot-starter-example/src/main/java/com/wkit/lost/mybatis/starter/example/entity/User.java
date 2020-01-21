package com.wkit.lost.mybatis.starter.example.entity;

import com.wkit.lost.mybatis.annotation.auditing.CreatedUserName;
import com.wkit.lost.mybatis.annotation.Table;
import com.wkit.lost.mybatis.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Table( name = "SYS_USER" )
@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = -2937196426923756731L;

    private Long id;
    private String userName;
    private String password;
    private Integer state;
    @CreatedUserName
    private String createUser;
    private String modifyUser;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModify;
    private Integer score;
    private Integer sex;
    private Integer deleted;
    private String deleteUser;
    private LocalDateTime gmtDelete;
    @Version
    private Integer integerVersion;
    private Date timeVersion;
    
}
