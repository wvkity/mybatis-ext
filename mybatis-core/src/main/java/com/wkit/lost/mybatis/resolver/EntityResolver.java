package com.wkit.lost.mybatis.resolver;

import com.wkit.lost.mybatis.config.MyBatisCustomConfiguration;
import com.wkit.lost.mybatis.core.schema.Table;

/**
 * 实体解析器
 * @author DT
 */
@FunctionalInterface
public interface EntityResolver {

    /**
     * 解析实体映射信息
     * @param entity        实体类
     * @param configuration 自定义配置对象
     * @return 表映射信息
     */
    Table resolve( final Class<?> entity, final MyBatisCustomConfiguration configuration );
}
