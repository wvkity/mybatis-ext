package com.wvkity.mybatis.code.make.utils;

/**
 * 字符串工具
 * @author wvkity
 */
public final class StringUtil {

    private StringUtil() {
    }

    /**
     * 检查字符串不为null
     * @param value 待检查字符串
     * @return boolean
     */
    public static boolean isNotNull(final String value) {
        return value != null;
    }

    /**
     * 检查字符串不为空
     * @param value 待检查字符串
     * @return boolean
     */
    public static boolean isNotEmpty(final String value) {
        return value != null && value.trim().length() != 0;
    }
}
