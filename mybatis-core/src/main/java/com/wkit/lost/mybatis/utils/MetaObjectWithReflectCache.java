package com.wkit.lost.mybatis.utils;

import com.wkit.lost.mybatis.plugins.exception.MyBatisPluginException;
import com.wkit.lost.mybatis.reflection.wrapper.MyBatisObjectWrapperFactory;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

/**
 * 带缓存MetaObject反射工具
 * @author wvkity
 */
public abstract class MetaObjectWithReflectCache {

    public static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    public static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new MyBatisObjectWrapperFactory();
    public static final ReflectorFactory DEFAULT_REFLECTOR_FACTORY = new DefaultReflectorFactory();

    public static MetaObject forObject(Object object) {
        try {
            return MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY,
                    DEFAULT_OBJECT_WRAPPER_FACTORY, DEFAULT_REFLECTOR_FACTORY);
        } catch (Exception e) {
            throw new MyBatisPluginException(e);
        }
    }
}
