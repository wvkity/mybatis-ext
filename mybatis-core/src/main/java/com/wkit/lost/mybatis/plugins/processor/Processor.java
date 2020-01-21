package com.wkit.lost.mybatis.plugins.processor;

import com.wkit.lost.mybatis.plugins.filter.Filter;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

public abstract class Processor implements Filter {

    /**
     * 属性
     */
    @Getter
    @Setter
    protected Properties properties;

    /**
     * 字符串类型值转换成基本数据类型值
     * @param javaType Java类型
     * @param value    值
     * @return 转换后的值
     */
    protected Object primitiveConvert( Class<?> javaType, String value ) {
        if ( javaType == null || value == null || javaType == String.class ) {
            return value;
        }
        if ( javaType == Long.class || javaType == long.class ) {
            return Long.valueOf( value );
        }
        if ( javaType == Integer.class || javaType == int.class ) {
            return Integer.valueOf( value );
        }
        if ( javaType == Short.class || javaType == short.class ) {
            return Short.valueOf( value );
        }
        if ( javaType == Character.class || javaType == char.class ) {
            return value.charAt( 0 );
        }
        if ( javaType == Boolean.class || javaType == boolean.class ) {
            return Boolean.valueOf( value );
        }
        return value;
    }


    /**
     * 拦截
     * @param invocation 代理对象
     * @return 结果
     * @throws Throwable 异常信息
     */
    public abstract Object intercept( Invocation invocation ) throws Throwable;

}
