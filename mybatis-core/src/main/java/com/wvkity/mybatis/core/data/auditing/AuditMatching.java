package com.wvkity.mybatis.core.data.auditing;

/**
 * 元数据审计匹配模式
 * @author wvkity
 */
public enum AuditMatching {

    /**
     * 开启保存操作自动审计
     */
    INSERTED,

    /**
     * 开启修改操作自动审计
     */
    MODIFIED,

    /**
     * 开启逻辑删除操作自动审计
     */
    DELETED
}
