package com.wvkity.mybatis.spring.boot.data.auditing.config;

import com.wvkity.mybatis.core.data.auditing.AuditorAware;

/**
 * 审计注解配置
 * @author wvkity
 */
public interface AuditingConfiguration {

    /**
     * 是否开启保存审计
     * @return true: 是 false: 否
     */
    boolean enableInserted();

    /**
     * 是否开启更新审计
     * @return true: 是 false: 否
     */
    boolean enableModified();

    /**
     * 是否开启删除审计
     * @return true: 是 false: 否
     */
    boolean enableDeleted();

    /**
     * {@link AuditorAware}实例名
     * @return {@link AuditorAware}实例名
     */
    String getAuditorAwareRef();
}
