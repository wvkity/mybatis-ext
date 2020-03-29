package com.wkit.lost.mybatis.core.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 参数值占位符转换接口
 * @author wvkity
 */
public interface PlaceholderConverter {

    /**
     * 默认模板
     */
    String PLACEHOLDER_TEMPLATE = "{0}";

    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    default String defaultPlaceholder( Object... values ) {
        return placeholder( PLACEHOLDER_TEMPLATE, values );
    }

    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    default ArrayList<String> defaultPlaceholders( Object... values ) {
        return placeholders( PLACEHOLDER_TEMPLATE, values );
    }

    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    default ArrayList<String> defaultPlaceholders( Collection<Object> values ) {
        return placeholders( PLACEHOLDER_TEMPLATE, values );
    }

    /**
     * 参数值转占位符
     * @param template SQL模板
     * @param values   值
     * @return SQL字符串
     */
    default String placeholder( String template, Object... values ) {
        return placeholder( true, template, values );
    }

    /**
     * 参数值转占位符
     * @param template SQL模板
     * @param values   值
     * @return SQL字符串
     */
    default ArrayList<String> placeholders( String template, Object... values ) {
        return placeholders( template, Arrays.asList( values ) );
    }

    /**
     * 参数值转占位符
     * @param template SQL模板
     * @param values   值
     * @return SQL字符串
     */
    ArrayList<String> placeholders( String template, Collection<Object> values );

    /**
     * 参数值转占位符
     * @param format   是否需要格式化
     * @param template SQL模板
     * @param values   值
     * @return SQL字符串
     */
    String placeholder( boolean format, String template, Object... values );
}
