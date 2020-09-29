package com.wvkity.mybatis.code.make.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 数据库连接信息
 * @author wvkity
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConnectConfiguration implements Serializable {
    private static final long serialVersionUID = -1025950471261656529L;

    /**
     * ID
     */
    private Long id;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 连接名称
     */
    private String name;
    /**
     * 数据库主机地址
     */
    private String host;
    /**
     * 数据库端口
     */
    private String port;
    /**
     * 数据库名称
     */
    private String schema;
    /**
     * 数据库用户名
     */
    private String userName;
    /**
     * 数据库密码
     */
    private String password;
    /**
     * 编码
     */
    private String encoding;
    /**
     * JDBC地址
     */
    private String jdbcUrl;
    /**
     * SSH主机
     */
    private String sshHost;
    /**
     * SSH端口
     */
    private String sshPort;
    /**
     * SSH密码
     */
    private String sshPassword;
    /**
     * 本地端口
     */
    private String localPort;
    /**
     * 目标端口
     */
    private String targetPort;
    /**
     * 是否使用SSH
     */
    private boolean useSsh;
}
