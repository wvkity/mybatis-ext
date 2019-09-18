package com.wkit.lost.mybatis.starter.example.beans;

import com.wkit.lost.mybatis.annotation.Id;
import com.wkit.lost.mybatis.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@Accessors( chain = true )
@Table( prefix = "TB_GB_" )
public class UserVisitRecord implements Serializable {
    private static final long serialVersionUID = -4748357761067935520L;

    @Id
    private String visitId;
    private String userId;
    private String userName;
    private String visitCity;
    private Long visitTime;
    private String visitIp;
    private String clientId;
    private OffsetDateTime createTime;
    private String idCard;
    private String mobile;
    private String aesKey;
    private String longitude;
    private String latitude;
    private String mobileModel;
    private String ext1;
    private String ext2;
    private String ext3;
    private String ext4;
    private String ext5;
    private String browser;
    private String openId;
    private String sex;
    private String age;
}
