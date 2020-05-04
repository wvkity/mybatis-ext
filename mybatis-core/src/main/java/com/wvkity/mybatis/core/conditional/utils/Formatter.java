package com.wvkity.mybatis.core.conditional.utils;

import com.wvkity.mybatis.utils.ArrayUtil;
import com.wvkity.mybatis.utils.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 字符串格式化工具类
 * @author wvkity
 * @see org.apache.ibatis.parsing.GenericTokenParser
 */
public final class Formatter {

    private Formatter() {
    }

    /**
     * 格式化字符串
     * @param open     开始标识
     * @param close    结束标识
     * @param template 模板
     * @param arg      参数
     * @return 格式化后的字符串
     */
    @SuppressWarnings({"unchecked"})
    public static String parse(final String open, final String close,
                               final String template, final Object arg) {
        if (StringUtil.isBlank(open) || StringUtil.isBlank(close)
                || StringUtil.isBlank(template) || arg == null) {
            return template;
        }
        int start = template.indexOf(open);
        if (start == -1) {
            return template;
        }
        List<Object> values;
        Map<String, Object> mapValues = null;
        boolean isMap = false;
        if (ArrayUtil.isArray(arg)) {
            values = ArrayUtil.toList(arg);
        } else if (arg instanceof List) {
            values = (List<Object>) arg;
        } else if (arg instanceof Map) {
            mapValues = (Map<String, Object>) arg;
            values = new ArrayList<>(mapValues.values());
            isMap = true;
        } else {
            values = new ArrayList<>(1);
            values.add(arg);
        }
        if (values.isEmpty()) {
            return template;
        }
        char[] src = template.toCharArray();
        int offset = 0;
        int argsIndex = 0;
        int size = values.size() - 1;
        final StringBuilder builder = new StringBuilder();
        StringBuilder expression = null;
        while (start > -1) {
            if (start > 0 && src[start - 1] == '\\') {
                builder.append(src, offset, start - offset - 1).append(open);
                offset = start + open.length();
            } else {
                if (expression == null) {
                    expression = new StringBuilder();
                } else {
                    expression.setLength(0);
                }
                builder.append(src, offset, start - offset);
                offset = start + open.length();
                int end = template.indexOf(close, offset);
                while (end > -1) {
                    if (end > offset && src[end - 1] == '\\') {
                        expression.append(src, offset, end - offset - 1).append(close);
                        offset = end + close.length();
                        end = template.indexOf(close, offset);
                    } else {
                        expression.append(src, offset, end - offset);
                        break;
                    }
                }
                if (end == -1) {
                    builder.append(src, start, src.length - start);
                    offset = src.length;
                } else {
                    String value;
                    if (isMap) {
                        String key = expression.toString();
                        value = String.valueOf(mapValues.getOrDefault(key, ""));
                    } else {
                        value = (argsIndex <= size) ?
                                (values.get(argsIndex) == null ? "" : values.get(argsIndex).toString())
                                : expression.toString();
                    }
                    builder.append(value);
                    offset = end + close.length();
                    argsIndex++;
                }
            }
            start = template.indexOf(open, offset);
        }
        if (offset < src.length) {
            builder.append(src, offset, src.length - offset);
        }
        return builder.toString();
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, List<Object> args) {
        return parse("{", "}", template, args);
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, Object... args) {
        return parse("{", "}", template, ArrayUtil.toList(args));
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, Map<String, Object> args) {
        return parse("${", "}", template, args);
    }
}
