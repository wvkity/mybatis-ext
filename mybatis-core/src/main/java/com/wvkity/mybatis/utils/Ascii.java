package com.wvkity.mybatis.utils;

public final class Ascii {

    private static final char CASE_MASK = 0x20;
    private static final String EMPTY = "";
    private static final String NULL = null;

    private Ascii() {
    }

    /**
     * 检查字符是否为小写字母
     * @param c 待检查字符
     * @return true: 是, false: 否
     */
    public static boolean isLowerCase(char c) {
        return 'a' <= c && c <= 'z';
    }

    /**
     * 检查字符是否为大写字母
     * @param c 待检查字符
     * @return true: 是, false: 否
     */
    public static boolean isUpperCase(char c) {
        return 'A' <= c && c <= 'Z';
    }

    /**
     * 大写字母转小写字母
     * @param c 待转换字符
     * @return 大写字母
     */
    public static char toLowerCase(char c) {
        return isLowerCase(c) ? c : (char) (c ^ CASE_MASK);
    }

    /**
     * 小写字母转大写字母
     * @param c 待转换字母
     * @return 大写字母
     */
    public static char toUpperCase(char c) {
        return isLowerCase(c) ? (char) (c ^ CASE_MASK) : c;
    }

    /**
     * 大写字母字符串转小写
     * @param string 待转换字符串
     * @return 字符串转换后的副本
     */
    public static String toLowerCase(String string) {
        if (string != null && !string.trim().isEmpty()) {
            int size = string.length();
            for (int i = 0; i < size; i++) {
                if (isUpperCase(string.charAt(i))) {
                    char[] chars = string.toCharArray();
                    for (; i < size; i++) {
                        char c = chars[i];
                        if (isUpperCase(c)) {
                            chars[i] = (char) (c ^ CASE_MASK);
                        }
                    }
                    return String.valueOf(chars);
                }
            }
        }
        return string;
    }

    /**
     * 大写字母字符串转小写
     * @param chars 待转换字符串
     * @return 字符串转换后的副本
     */
    public static String toLowerCase(CharSequence chars) {
        if (chars != null) {
            if (chars instanceof String) {
                return toLowerCase((String) chars);
            }
            int size = chars.length();
            if (size == 0) {
                return EMPTY;
            }
            char[] charArray = new char[size];
            for (int i = 0; i < size; i++) {
                charArray[i] = toLowerCase(chars.charAt(i));
            }
            return String.valueOf(charArray);
        }
        return null;
    }

    /**
     * 小写字母字符串转大写
     * @param string 待转换字符串
     * @return 字符串转换后的副本
     */
    public static String toUpperCase(String string) {
        if (string != null && !string.trim().isEmpty()) {
            int size = string.length();
            for (int i = 0; i < size; i++) {
                if (isLowerCase(string.charAt(i))) {
                    char[] chars = string.toCharArray();
                    for (; i < size; i++) {
                        char c = chars[i];
                        if (isLowerCase(c)) {
                            chars[i] = (char) (c ^ CASE_MASK);
                        }
                    }
                    return String.valueOf(chars);
                }
            }
        }
        return string;
    }

    /**
     * 小写字母字符串转大写
     * @param chars 待转换字符串
     * @return 字符串转换后的副本
     */
    public static String toUpperCase(CharSequence chars) {
        if (chars != null) {
            if (chars instanceof String) {
                return toLowerCase((String) chars);
            }
            int size = chars.length();
            if (size == 0) {
                return EMPTY;
            }
            char[] charArray = new char[size];
            for (int i = 0; i < size; i++) {
                charArray[i] = toUpperCase(chars.charAt(i));
            }
            return String.valueOf(charArray);
        }
        return null;
    }

    /**
     * null转空字符
     * @param string 待转换字符串
     * @return 如果为null则返回空字符串，否则原字符串
     */
    public static String nullToEmpty(String string) {
        return string == null ? EMPTY : string;
    }

    /**
     * 空字符串转null
     * @param string 待转换字符串
     * @return 如果为空字符串则返回null，否则返回原字符串
     */
    public static String emptyToNull(String string) {
        return isNullOrEmpty(string) ? NULL : string;
    }

    /**
     * 检查字符串是否为null或者为空
     * @param string 待检查字符串
     * @return true: 是，false: 否
     */
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    /**
     * 检查字符串是否存在内容
     * @param string 待检查字符串
     * @return true: 是，false: 否
     */
    public static boolean hasText(String string) {
        return !isNullOrEmpty(string) && !string.trim().isEmpty();
    }

    /**
     * 字符串转boolean
     * @param string 待转换字符串
     * @return boolean
     */
    public static boolean toBool(String string) {
        return hasText(string) && ("true".equalsIgnoreCase(string) || "1".equals(string));
    }
}
