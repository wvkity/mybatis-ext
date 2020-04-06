package com.wkit.lost.mybatis.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author wvkity
 */
public abstract class StringUtil {

    /**
     * 空字符串
     */
    public static final String EMPTY = "";

    /**
     * 花括号占位符
     */
    public static final String BRACES = "{}";

    /**
     * 下划线
     */
    public static final String UNDERLINE = "_";

    /**
     * 默认占位符正则表达式
     */
    public static final String DEFAULT_REPLACE_REGEX = "\\{(.+?)\\}";

    /**
     * 检测是否为空字符串(null || 空)
     * @param source 待检测字符串
     * @return boolean
     */
    public static boolean isEmpty( final CharSequence source ) {
        return source == null || source.length() == 0;
    }

    /**
     * 检测字符串是否为空白
     * @param source 待检测字符串
     * @return boolean
     * @since jdk.11
     */
    public static boolean isBlank( final CharSequence source ) {
        if ( !isEmpty( source ) ) {
            int size = source.length();
            for ( int i = 0; i < size; i++ ) {
                if ( !Character.isWhitespace( source.charAt( i ) ) ) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 检测字符串是否非空
     * @param source 待检测字符串
     * @return boolean
     * @see #isBlank(CharSequence)
     */
    public static boolean hasText( final CharSequence source ) {
        return !isBlank( source );
    }

    /**
     * 类名首字母转小写
     * @param target 待处理类
     * @return 首字母小写字符串
     */
    public static String getSimpleNameOfFirstLower( final Class<?> target ) {
        return target == null ? null : lowerCaseForFirstLetter( target.getSimpleName() );
    }

    /**
     * 获取类名首字母
     * @param target 待处理类
     * @return 首字母大写字符串
     */
    public static String getSimpleNameOfSplitFirstUpper( final Class<?> target ) {
        if ( target == null ) {
            return null;
        }
        return getSimpleNameOfSplitFirstUpper( target.getSimpleName() );
    }

    /**
     * 获取字符串首字母并转大写
     * @param value 待处理字符串
     * @return 首字母大写字符串
     */
    public static String getSimpleNameOfSplitFirstUpper( final String value ) {
        if ( value == null ) {
            return null;
        }
        StringBuffer buffer = new StringBuffer( 10 );
        buffer.append( Character.toUpperCase( value.charAt( 0 ) ) );
        char[] array = value.toCharArray();
        int size = array.length;
        for ( int i = 1; i < size; i++ ) {
            char ch = array[ i ];
            if ( Character.isUpperCase( ch ) ) {
                buffer.append( ch );
            }
        }
        return buffer.toString();
    }

    /**
     * 字符串首字母转小写
     * @param value 待处理字符串
     * @return 首字母小写字符串
     */
    public static String lowerCaseForFirstLetter( final String value ) {
        if ( hasText( value ) ) {
            if ( Character.isLowerCase( value.charAt( 0 ) ) ) {
                return value;
            } else {
                return Character.toLowerCase( value.charAt( 0 ) ) + value.substring( 1 );
            }
        }
        return value;
    }

    /**
     * 编码字符串
     * @param source  字符串
     * @param charset 编码
     * @return 编码后的字节数组
     */
    public static byte[] toBytes( final CharSequence source, final Charset charset ) {
        if ( source == null ) {
            return null;
        }
        if ( charset == null ) {
            return source.toString().getBytes( StandardCharsets.UTF_8 );
        }
        return source.toString().getBytes( charset );
    }

    /**
     * 字节数组转字符串
     * @param bytes 字节数组
     * @return 字符串
     */
    public static String toUtf8String( final byte[] bytes ) {
        return toString( bytes, StandardCharsets.UTF_8 );
    }

    /**
     * 对象转字符串
     * @param source 待转换对象
     * @return 字符串
     */
    public static String toUtf8String( final Object source ) {
        return toString( source, StandardCharsets.UTF_8 );
    }

    /**
     * 对象转字符串
     * @param source  待转换对象
     * @param charset 编码
     * @return 字符串
     */
    public static String toString( final Object source, final String charset ) {
        return toString( source, Charset.forName( charset ) );
    }

    /**
     * 对象转字符串
     * @param source  待转换对象
     * @param charset 编码
     * @return 字符串
     */
    public static String toString( final Object source, final Charset charset ) {
        if ( source == null ) {
            return null;
        }
        if ( CharSequence.class.isAssignableFrom( source.getClass() ) ) {
            return source.toString();
        } else if ( source instanceof byte[] ) {
            return toString( ( byte[] ) source, charset );
        } else if ( source.getClass().isArray() ) {
            return Arrays.deepToString( ( Object[] ) source );
        }
        return source.toString();
    }

    /**
     * 字节数组转字符串
     * @param bytes   字节数组
     * @param charset 编码
     * @return 字符串
     */
    public static String toString( final byte[] bytes, final Charset charset ) {
        if ( bytes == null ) {
            return null;
        }
        if ( charset == null ) {
            return new String( bytes, StandardCharsets.UTF_8 );
        }
        return new String( bytes, charset );
    }

    /**
     * 去除字符串首尾空格
     * @param source 待处理字符串
     * @return 处理后的字符串
     * @since jdk.11
     */
    public static String strip( final CharSequence source ) {
        return strip( source, null );
    }

    public static String strip( final CharSequence source, String stripChars ) {
        if ( isEmpty( source ) ) {
            return source.toString();
        }
        String newValue = stripStart( source, stripChars );
        return stripEnd( newValue, stripChars );
    }

    public static String stripStart( final CharSequence source, String stripChars ) {
        if ( isEmpty( source ) ) {
            return source == null ? null : source.toString();
        }
        String value = source.toString();
        int size = value.length();
        int start = 0;
        if ( stripChars == null ) {
            while ( ( start != size ) && ( Character.isWhitespace( value.charAt( start ) ) ) ) {
                start++;
            }
        } else {
            if ( stripChars.isEmpty() ) {
                return value;
            }
            while ( ( start != size ) && ( stripChars.indexOf( value.charAt( start ) ) != -1 ) ) {
                start++;
            }
        }
        return start == 0 ? value : value.substring( start );
    }

    public static String stripEnd( final CharSequence source, String stripChars ) {
        if ( isEmpty( source ) ) {
            return source == null ? null : source.toString();
        }
        String value = source.toString();
        int end = value.length();
        if ( stripChars == null ) {
            while ( ( end != 0 ) && ( Character.isWhitespace( value.charAt( end - 1 ) ) ) ) {
                end--;
            }
        } else {
            if ( stripChars.isEmpty() ) {
                return value;
            }
            while ( ( end != 0 ) && ( stripChars.indexOf( value.charAt( end - 1 ) ) != -1 ) ) {
                end--;
            }
        }
        return value.substring( 0, end );
    }

    /**
     * 多个单词字符串转换成下划线连接
     * @param source 待转换字符串
     * @return 转换后的字符串
     */
    public static String camelHumpToUnderline( final String source ) {
        if ( hasText( source ) ) {
            StringBuffer buffer = new StringBuffer( strip( source ) );
            int i = 1;
            int max = buffer.length() - 1;
            for ( ; i < max; i++ ) {
                if ( isUnderscoreRequired( buffer.charAt( i - 1 ), buffer.charAt( i ), buffer.charAt( i + 1 ) ) ) {
                    buffer.insert( i++, UNDERLINE );
                }
            }
            return buffer.toString();
        }
        return source;
    }

    /**
     * 是否强制需要转换
     * @param before  前一个字符
     * @param current 当前字符
     * @param after   后一个字符
     * @return boolean
     */
    public static boolean isUnderscoreRequired( final char before, final char current, final char after ) {
        return Character.isLowerCase( before ) && Character.isUpperCase( current ) && Character.isLowerCase( after );
    }

    /**
     * 比较两个字符串值是否一样
     * @param first 待比较字符串
     * @param last  待比较字符串
     * @return boolean
     */
    public static boolean equals( final String first, final String last ) {
        return Objects.equals( first, last );
    }

    /**
     * 取值
     * <p>如果值为空则取默认值</p>
     * @param value        值
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String nvl( final String value, final String defaultValue ) {
        return nvl( hasText( value ), value, defaultValue );
    }

    /**
     * 取值
     * <p>如果值为空则取默认值</p>
     * @param expression   表达式
     * @param value        值
     * @param defaultValue 默认值
     * @return 字符串
     */
    public static String nvl( final boolean expression, final String value, final String defaultValue ) {
        return expression ? value : defaultValue;
    }

    /**
     * 取值
     * <p>如果值、默认值都为空则抛出{@link NullPointerException}异常</p>
     * @param value         值
     * @param defaultValue  默认值
     * @param errorTemplate 异常模板
     * @return 字符串
     * @see #format(String, Object...)
     */
    public static String required( final String value, final String defaultValue, final String errorTemplate ) {
        if ( hasText( value ) ) {
            return value;
        }
        if ( hasText( defaultValue ) ) {
            return defaultValue;
        }
        throw new NullPointerException( format( errorTemplate, defaultValue ) );
    }

    /**
     * 字符串转Boolean值
     * @param value 字符串
     * @return true | false
     */
    public static boolean toBoolean( final String value ) {
        return !isBlank( value ) && ( "true".equals( value.toLowerCase( Locale.ROOT ) ) || "1".equals( value ) );
    }

    /**
     * 检测字符串数组中是否包含指定字符串
     * @param array 字符串数组
     * @param value 指定字符串
     * @return true: 包含 | false: 不包含
     */
    public static boolean include( final String[] array, final String value ) {
        if ( ArrayUtil.isEmpty( array ) || value == null ) {
            return false;
        }
        return Arrays.asList( array ).contains( value );
    }

    /**
     * 字符串格式化
     * @param template     模板
     * @param startMatcher 开始标识
     * @param endMatcher   结束标识
     * @param values       值
     * @return 格式化后的字符串
     */
    public static String transform( final String template, String startMatcher, String endMatcher, final Map<String, Object> values ) {
        if ( StringUtil.isBlank( startMatcher ) ) {
            startMatcher = "\\{";
        }
        if ( StringUtil.isBlank( endMatcher ) ) {
            endMatcher = "\\}";
        }
        return transform( template, startMatcher + "(.+?)" + endMatcher, values );
    }

    /**
     * 字符串格式化
     * @param template 模板
     * @param key      键
     * @param value    值
     * @return 格式化后的字符串
     */
    public static String transform( final String template, final String key, final Object value ) {
        Map<String, Object> values = new HashMap<>( 1 );
        values.put( key, value );
        return transform( template, DEFAULT_REPLACE_REGEX, values );
    }

    /**
     * 字符串格式化
     * @param template 模板
     * @param regex    正则表达式
     * @param values   值
     * @return 格式化后的字符串
     */
    public static String transform( final String template, final String regex, final Map<String, Object> values ) {
        if ( isBlank( template ) ) {
            return "";
        }
        if ( isBlank( regex ) || CollectionUtil.isEmpty( values ) ) {
            return template;
        }
        try {
            StringBuffer buffer = new StringBuffer();
            Pattern pattern = Pattern.compile( regex );
            Matcher matcher = pattern.matcher( template );
            while ( matcher.find() ) {
                String key = matcher.group( 1 );
                Object value = values.get( key );
                String realValue = "";
                if ( value != null ) {
                    realValue = value.toString();
                }
                matcher.appendReplacement( buffer, realValue );
            }
            matcher.appendTail( buffer );
            return buffer.toString();
        } catch ( Exception e ) {
            // ignore
            e.printStackTrace();
        }
        return template;
    }

    /**
     * 格式化字符串
     * @param template 模板
     * @param args     参数
     * @return 格式化后的字符串
     */
    public static String format( final String template, final Object... args ) {
        if ( isBlank( template ) || ArrayUtil.isEmpty( args ) ) {
            return template;
        }
        final int size = template.length();
        StringBuilder buffer = new StringBuilder( size + 50 );
        int position = 0;
        int delimitIndex;
        int argIndex = 0;
        int argSize = args.length;
        for ( ; argIndex < argSize; argIndex++ ) {
            delimitIndex = template.indexOf( BRACES, position );
            // 检测是否存在占位符
            if ( delimitIndex == -1 ) {
                if ( position == 0 ) {
                    return template;
                } else {
                    buffer.append( template, position, size );
                    return buffer.toString();
                }
            } else {
                // 检测是否存在转义符
                if ( delimitIndex > 0 && template.charAt( delimitIndex - 1 ) == CharUtil.BACKSLASH ) {
                    // 检测是否为双转义符
                    if ( delimitIndex > 1 && template.charAt( delimitIndex - 2 ) == CharUtil.BACKSLASH ) {
                        // 占位符依然有效
                        buffer.append( template, position, delimitIndex - 1 );
                        buffer.append( toUtf8String( args[ argIndex ] ) );
                        position = delimitIndex + 2;
                    } else {
                        // 占位符转义
                        argIndex--;
                        buffer.append( template, position, delimitIndex - 1 );
                        buffer.append( CharUtil.BRACE_START );
                        position = delimitIndex + 1;
                    }
                } else {
                    buffer.append( template, position, delimitIndex );
                    buffer.append( toUtf8String( args[ argIndex ] ) );
                    position = delimitIndex + 2;
                }
            }
        }
        // 拼接剩余部分
        buffer.append( template, position, size );
        return buffer.toString();
    }
}
