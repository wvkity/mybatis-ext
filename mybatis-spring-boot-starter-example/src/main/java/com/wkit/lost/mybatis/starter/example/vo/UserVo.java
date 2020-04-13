package com.wkit.lost.mybatis.starter.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {
    
    private static final long serialVersionUID = 28456670935528429L;

    private Long id;
    private String userName;
    private String password;
    private Integer state;
    private Integer score;
    private Integer sex;
    private Boolean deleted;
    private Integer version;
    private String createdUserName;
    private Long createdUserId;
    private LocalDateTime gmtCreated;
    private String lastModifiedUserName;
    private Long lastModifiedUserId;
    private LocalDateTime gmtLastModified;
    private String deletedUserName;
    private Long deletedUserId;
    private LocalDateTime gmtDeleted;
}
