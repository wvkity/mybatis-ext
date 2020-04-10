package com.wkit.lost.mybatis.starter.example.entity;

import com.wkit.lost.mybatis.annotation.Column;
import com.wkit.lost.mybatis.annotation.LogicDeletion;
import com.wkit.lost.mybatis.annotation.Table;
import com.wkit.lost.mybatis.annotation.Version;
import com.wkit.lost.mybatis.annotation.auditing.CreatedDate;
import com.wkit.lost.mybatis.annotation.auditing.CreatedUser;
import com.wkit.lost.mybatis.annotation.auditing.CreatedUserName;
import com.wkit.lost.mybatis.annotation.auditing.DeletedDate;
import com.wkit.lost.mybatis.annotation.auditing.DeletedUser;
import com.wkit.lost.mybatis.annotation.auditing.DeletedUserName;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedDate;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedUser;
import com.wkit.lost.mybatis.annotation.auditing.LastModifiedUserName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Table(name = "SYS_USER")
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
    private Integer score;
    private Integer sex;
    @LogicDeletion
    // @Column( name = "IS_DELETED" )
    private Boolean deleted;
    @Version
    private Integer version;
    @CreatedUserName
    private String createdUserName;
    @CreatedUser
    private Long createdUserId;
    @CreatedDate
    private LocalDateTime gmtCreated;
    @LastModifiedUserName
    private String lastModifiedUserName;
    @LastModifiedUser
    private Long lastModifiedUserId;
    @LastModifiedDate
    private LocalDateTime gmtLastModified;
    @DeletedUserName
    private String deletedUserName;
    @DeletedUser
    private Long deletedUserId;
    @DeletedDate
    private LocalDateTime gmtDeleted;

}
