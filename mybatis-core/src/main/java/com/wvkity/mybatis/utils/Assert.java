package com.wvkity.mybatis.utils;

/**
 * 断言工具类
 * @author wvkity
 */
public abstract class Assert {

    /**
     * 非空判断
     * @param target  待检测对象
     * @param message 异常信息
     */
    public static void notNull(final Object target, final String message) {
        if (target == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * 表达式判断
     * @param expression 表达式
     * @param message    异常信息
     */
    public static void isTrue(final boolean expression, final String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }
}
