package com.wkit.lost.mybatis.naming;

import com.wkit.lost.mybatis.annotation.naming.NamingStrategy;

/**
 * 默认命名策略
 * @author wvkity
 */
public class DefaultPhysicalNamingStrategy implements PhysicalNamingStrategy {
    
    @Override
    public String tableNameValueOf( String tableName, NamingStrategy strategy ) {
        return valueOf( tableName, strategy );
    }

    @Override
    public String columnNameValueOf( String columnName, NamingStrategy strategy ) {
        return valueOf( columnName, strategy );
    }
    
    protected String valueOf( String value, NamingStrategy strategy ) {
        return NamingStrategyUtil.valueOf( value, strategy );
    }
}
