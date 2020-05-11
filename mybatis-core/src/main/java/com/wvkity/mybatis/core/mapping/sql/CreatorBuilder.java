package com.wvkity.mybatis.core.mapping.sql;

public interface CreatorBuilder<T extends Creator> {

    /**
     * 获取SQL构建器
     * @return SQL构建器
     */
    T target();
}
