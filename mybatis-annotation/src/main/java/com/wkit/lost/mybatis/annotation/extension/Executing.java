package com.wkit.lost.mybatis.annotation.extension;

/**
 * SQL执行时机
 * @author wvkity
 */
public enum Executing {

    /**
     * 插入前执行
     */
    BEFORE,

    /**
     * 插入后执行
     */
    AFTER,

    /**
     * 全局
     */
    DEFAULT;

    /**
     * 转换成boolean值
     * @return boolean
     */
    public boolean value() {
        return this != AFTER;
    }
}
