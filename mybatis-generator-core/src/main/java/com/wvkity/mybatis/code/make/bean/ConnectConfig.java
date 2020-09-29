package com.wvkity.mybatis.code.make.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 连接配置
 * @author wvkity
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ConnectConfig implements Serializable {
    private static final long serialVersionUID = -1025950471261656529L;
    /**
     * ID
     */
    private Long id;
    /**
     * 连接名称
     */
    private String name;
    /**
     * 配置信息
     */
    private String config;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreated;
    /**
     * 最后修改时间
     */
    private LocalDateTime gmtLastModified;
}
