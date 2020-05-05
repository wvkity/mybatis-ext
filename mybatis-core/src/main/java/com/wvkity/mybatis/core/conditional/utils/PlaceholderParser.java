package com.wvkity.mybatis.core.conditional.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 占位符解析工具类
 * <pre>
 *     Example:
 *     // NO1.
 *     String template1 = "USER_NAME = ?0 AND PASSWORD = ?1 AND AGE = \\?2";
 *     String result1 = PlaceholderParser.format(template1, "admin", "123456", 22);
 *     // return
 *     // USER_NAME = admin AND PASSWORD = 123456 AND AGE = \\?2
 *
 *     // NO2.
 *     String template2 = "USER_NAME = :userName AND PASSWORD = :password AND AGE = \\:age";
 *     Map&lt;String, Object&gt; args = new HashMap&lt;&gt;();
 *     args.put("userName", "admin");
 *     args.put("password", "123456");
 *     args.put("age", 22);
 *     String result2 = PlaceholderParser.format(template2, args);
 *     // return
 *     // USER_NAME = admin AND PASSWORD = 123456 AND AGE = \\:age
 *
 *     // NO3.
 *     String template3 = "USER_NAME = :0 AND PASSWORD = :1 AND AGE = \\:2";
 *     Map&lt;String, Object&gt; args1 = new HashMap&lt;&gt;();
 *     args.put("0", "admin");
 *     args.put("1", "123456");
 *     args.put("2", 22);
 *     String result3 = PlaceholderParser.format(template3, args1);
 *     // return
 *     // USER_NAME = admin AND PASSWORD = 123456 AND AGE = \\:age
 *     // OR
 *     String result4 = PlaceholderParser.format(template3, "admin", "123456", 22);
 *     // return
 *     // USER_NAME = admin AND PASSWORD = 123456 AND AGE = \\:2
 * </pre>
 * @author wvkity
 */
public final class PlaceholderParser {

    private PlaceholderParser() {
    }

    /**
     * 数字占位符
     */
    private static final Pattern PLACEHOLDER_DIGIT_MATCHER = Pattern.compile(".*((?<!\\\\)\\?(\\d+)).*");
    /**
     * 数字占位符
     */
    private static final Pattern PLACEHOLDER_DIGIT = Pattern.compile("((?<!\\\\)\\?(\\d+))");
    /**
     * 字符占位符
     */
    private static final Pattern PLACEHOLDER_CHAR_MATCHER = Pattern.compile(".*((?<!\\\\):(\\w+)).*");
    /**
     * 字符占位符
     */
    private static final Pattern PLACEHOLDER_CHAR = Pattern.compile("((?<!\\\\):(\\w+))");

    /**
     * 解析模板
     * @param template 模板
     * @param arg      参数
     * @return 格式化后的字符串
     */
    @SuppressWarnings("unchecked")
    public static String parse(final String template, final Object arg) {
        if (template == null || template.trim().isEmpty() || arg == null) {
            return template;
        }
        boolean isMap = arg instanceof Map;
        List<Object> args;
        Map<String, Object> map;
        if (isMap) {
            map = (Map<String, Object>) arg;
            args = new ArrayList<>(map.values());
        } else {
            if (arg.getClass().isArray()) {
                args = new ArrayList<>(Arrays.asList((Object[]) arg));
            } else if (arg instanceof List) {
                args = (List<Object>) arg;
            } else {
                args = new ArrayList<>();
                args.add(arg);
            }
            map = null;
        }
        if (args.isEmpty()) {
            return template;
        }
        int size = args.size() - 1;
        if (PLACEHOLDER_DIGIT_MATCHER.matcher(template).matches()) {
            String temp = template;
            Matcher matcher = PLACEHOLDER_DIGIT.matcher(temp);
            while (matcher.find()) {
                String key = matcher.group(2);
                int i = Integer.parseInt(key);
                Object value = i > size ? null : args.get(i);
                temp = temp.replace(matcher.group(), String.valueOf(value));
            }
            return temp;
        }
        if (PLACEHOLDER_CHAR_MATCHER.matcher(template).matches()) {
            String temp = template;
            Matcher matcher = PLACEHOLDER_CHAR.matcher(temp);
            while (matcher.find()) {
                String key = matcher.group(2);
                Object value;
                if (isMap) {
                    value = map.get(key);
                } else {
                    int i = Integer.parseInt(key);
                    value = i > size ? null : args.get(i);
                }
                temp = temp.replace(matcher.group(), String.valueOf(value));
            }
            return temp;
        }
        return template;
    }

    private static List<Object> asList(Object... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, Object... args) {
        return parse(template, asList(args));
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, List<Object> args) {
        return parse(template, args);
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format(final String template, Map<String, Object> args) {
        return parse(template, args);
    }

}
