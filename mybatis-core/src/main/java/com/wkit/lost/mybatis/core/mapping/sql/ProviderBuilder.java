package com.wkit.lost.mybatis.core.mapping.sql;

public interface ProviderBuilder<T extends Provider> {

    /**
     * 获取SQL构建器
     * @return SQL构建器
     */
    T target();
}
