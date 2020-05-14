package com.wvkity.mybatis.core.naming;

import com.wvkity.mybatis.annotation.naming.NamingStrategy;

/**
 * 默认命名策略
 * @author wvkity
 */
public class DefaultPhysicalNamingStrategy implements PhysicalNamingStrategy {

    @Override
    public String tableName(String tableName, NamingStrategy strategy) {
        return valueOf(tableName, strategy);
    }

    @Override
    public String columnName(String columnName, NamingStrategy strategy) {
        return valueOf(columnName, strategy);
    }

    protected String valueOf(String value, NamingStrategy strategy) {
        return NamingStrategyUtil.valueOf(value, strategy);
    }
}
