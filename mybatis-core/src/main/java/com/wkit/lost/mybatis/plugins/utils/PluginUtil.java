package com.wkit.lost.mybatis.plugins.utils;

import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.lang.reflect.Proxy;

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
}
