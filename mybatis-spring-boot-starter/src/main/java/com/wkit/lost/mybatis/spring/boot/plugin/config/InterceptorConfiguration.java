package com.wkit.lost.mybatis.spring.boot.plugin.config;

import java.util.List;

/**
 * Mybatis拦截器配置接口
 * @author wvkity
 */
public interface InterceptorConfiguration {

    /**
     * 获取插件列表
     * @return 插件列表
     */
    List<InterceptorDefinition> interceptors();
}
