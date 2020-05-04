package com.wvkity.mybatis.utils;

/**
 * 字符工具类
 * @author wvkity
 */
public abstract class CharUtil {

    /**
     * 斜杠
     */
    public static final char SLASH = '/';

    /**
     * 反斜杠
     */
    public static final char BACKSLASH = '\\';

    /**
     * 占位符开始部分
     */
    public static final char BRACE_START = '{';

    /**
     * 占位符结束部分
     */
    public static final char BRACE_END = '}';

    /**
     * 检测是否为空白字符
     * @param ch 待检测字符
     * @return boolean
     */
    public static boolean isBlank(final char ch) {
        return isBlank((int) ch);
    }

    /**
     * 检测是否为空白字符
     * @param codePoint 字符
     * @return boolean
     */
    public static boolean isBlank(final int codePoint) {
        return Character.isWhitespace(codePoint) || Character.isSpaceChar(codePoint) || codePoint == '\ufeff' || codePoint == '\u202a';
    }
}
