package com.wkit.lost.mybatis.resolver;

import com.wkit.lost.mybatis.core.meta.Table;

/**
 * 实体解析器
 * @author wvkity
 */
@FunctionalInterface
public interface EntityResolver {

    /**
     * 解析实体映射信息
     * @param entity 实体类
     * @return 表映射信息
     */
    Table resolve( final Class<?> entity );
}
