package com.wvkity.mybatis.annotation.naming;

/**
 * 命名策略方式
 * @author wvkity
 */
public enum NamingStrategy {

    /**
     * 原值
     */
    NORMAL,

    /**
     * 小写
     */
    LOWERCASE,

    /**
     * 大写
     */
    UPPERCASE,

    /**
     * 驼峰转下划线
     */
    CAMEL_HUMP,

    /**
     * 驼峰转下划线小写
     */
    CAMEL_HUMP_LOWERCASE,

    /**
     * 驼峰转下划线大写
     */
    CAMEL_HUMP_UPPERCASE

}
