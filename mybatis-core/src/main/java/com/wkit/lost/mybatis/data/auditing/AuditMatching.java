package com.wkit.lost.mybatis.data.auditing;

/**
 * 匹配模式
 * @author wvkity
 */
public enum AuditMatching {
    
    /**
     * 开启系统自动识别
     */
    AUTO,
    
    /**
     * 开启保存操作自动审计
     */
    INSERTED,
    
    /**
     * 开启修改操作自动审计
     */
    MODIFIED,
    
    /**
     * 开启删除操作自动审计
     */
    DELETED
}
