package com.wkit.lost.mybatis.spring.boot.data.auditing.config;

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
     * 是否开启自动识别
     * @return true: 是 false: 否
     */
    boolean enableAutomatic();

    /**
     * {@link com.wkit.lost.mybatis.core.data.auditing.AuditorAware}实例名
     * @return {@link com.wkit.lost.mybatis.core.data.auditing.AuditorAware}实例名
     */
    String getAuditorAwareRef();
}
