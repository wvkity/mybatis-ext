package com.wkit.lost.mybatis.core.criteria;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 抽象参数值转占位符转换器
 * @author wvkity
 */
public abstract class AbstractParamValueConverter implements ParamValuePlaceholderConverter {
    
    /**
     * 默认模板
     */
    protected static final String PLACEHOLDER_TEMPLATE = "{0}";
    
    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    public String defaultConvertToPlaceholder( Object... values ) {
        return convertToPlaceholder( PLACEHOLDER_TEMPLATE, values );
    }
    
    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    public ArrayList<String> defaultConvertToPlaceholders( Object... values ) {
        return placeholders( PLACEHOLDER_TEMPLATE, values );
    }
    
    /**
     * 默认参数值转占位符
     * @param values 参数值
     * @return SQL字符串
     */
    public ArrayList<String> defaultConvertToPlaceholders( Collection<Object> values ) {
        return placeholders( PLACEHOLDER_TEMPLATE, values );
    }
    
    /**
     * 参数值转占位符
     * @param template SQL模板
     * @param values   值
     * @return SQL字符串
     */
    public String convertToPlaceholder( String template, Object... values ) {
        return placeholder( true, template, values );
    }
    
}
