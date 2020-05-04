package com.wvkity.mybatis.starter.example.entity;

import com.wvkity.mybatis.annotation.LogicDeletion;
import com.wvkity.mybatis.annotation.Table;
import com.wvkity.mybatis.annotation.Version;
import com.wvkity.mybatis.annotation.auditing.CreatedDate;
import com.wvkity.mybatis.annotation.auditing.CreatedUser;
import com.wvkity.mybatis.annotation.auditing.CreatedUserName;
import com.wvkity.mybatis.annotation.auditing.DeletedDate;
import com.wvkity.mybatis.annotation.auditing.DeletedUser;
import com.wvkity.mybatis.annotation.auditing.DeletedUserName;
import com.wvkity.mybatis.annotation.auditing.LastModifiedDate;
import com.wvkity.mybatis.annotation.auditing.LastModifiedUser;
import com.wvkity.mybatis.annotation.auditing.LastModifiedUserName;
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
    private Integer sex;
    /**
     * 电话号码
     */
    private String phone;
    @LogicDeletion
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
