package com.wkit.lost.mybatis.plugins.utils;

import com.wkit.lost.mybatis.utils.Ascii;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * 插件工具类
 * @author DT
 */
public abstract class PluginUtil {

    /**
     * 获取真实对象
     * @param target 目标对象
     * @return 真实处理对象
     */
    public static Object getRealTarget( final Object target ) {
        if ( Proxy.isProxyClass( target.getClass() ) ) {
            MetaObject metaObject = SystemMetaObject.forObject( target );
            return getRealTarget( metaObject.getValue( "h.target" ) );
        }
        return target;
    }

    /**
     * 获取参数值
     * @param parameter 参数对象
     * @param key       key
     * @param <T>       参数类型
     * @return 参数
     */
    @SuppressWarnings( "unchecked" )
    public static <T> T getParameter( Object parameter, String key ) {
        if ( Ascii.hasText( key ) && parameter instanceof Map ) {
            Map<String, Object> paramMap = ( Map<String, Object> ) parameter;
            if ( paramMap.containsKey( key ) ) {
                Object value = paramMap.get( key );
                if ( value != null ) {
                    return ( T ) value;
                }
            }
        }
        return null;
    }
}
