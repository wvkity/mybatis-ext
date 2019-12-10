package com.wkit.lost.mybatis.utils;

import com.wkit.lost.mybatis.exception.MyBatisException;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.ReflectorFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.lang.reflect.Method;

/**
 * MetaObject工具类
 * @author wvkity
 */
public abstract class MetaObjectUtil {

    private static Method method;

    static {
        try {
            Class.forName( "org.apache.ibatis.reflection.ReflectorFactory" );
            Class<?> metaClass = Class.forName( "com.wkit.lost.mybatis.utils.MetaObjectWithReflectCache" );
            method = metaClass.getDeclaredMethod( "forObject", Object.class );
        } catch ( Exception e ) {
            try {
                Class<?> metaClass = Class.forName( "org.apache.ibatis.reflection.SystemMetaObject" );
                method = metaClass.getDeclaredMethod( "forObject", Object.class );
            } catch ( Exception e1 ) {
                try {
                    Class<?> metaClass = Class.forName( "org.apache.ibatis.reflection.MetaObject" );
                    method = metaClass.getDeclaredMethod( "forObject", Object.class, ObjectFactory.class, 
                            ObjectWrapperFactory.class, ReflectorFactory.class );
                } catch ( Exception e2 ) {
                    throw new MyBatisException( e2 );
                }
            }
        }
    }

    public static MetaObject forObject( Object... args ) {
        try {
            return ( MetaObject ) method.invoke( null, args );
        } catch ( Exception e ) {
            throw new MyBatisException( e );
        }
    }
}
