package com.wkit.lost.mybatis.binding;

import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * 重写{@link org.apache.ibatis.binding.MapperProxy}
 * @param <T> 类型
 * @author wvkity
 */
public class MyBatisMapperProxy<T> implements InvocationHandler, Serializable {

    private static final long serialVersionUID = -7693549967640740615L;
    private final SqlSession sqlSession;
    private final Class<T> mapperInterface;
    private final Map<Method, MyBatisMapperMethod> methodCache;

    public MyBatisMapperProxy( SqlSession sqlSession, Class<T> mapperInterface, Map<Method, MyBatisMapperMethod> methodCache ) {
        this.sqlSession = sqlSession;
        this.mapperInterface = mapperInterface;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
        try {
            if ( Object.class.equals( method.getDeclaringClass() ) ) {
                return method.invoke( this, args );
            } else if ( isDefaultMethod( method ) ) {
                return invokeDefaultMethod( proxy, method, args );
            }
        } catch ( Throwable t ) {
            throw ExceptionUtil.unwrapThrowable( t );
        }
        final MyBatisMapperMethod mapperMethod = cacheMapperMethod( method );
        return mapperMethod.execute( this.sqlSession, args );
    }

    private MyBatisMapperMethod cacheMapperMethod( Method method ) {
        return this.methodCache.computeIfAbsent( method, key -> new MyBatisMapperMethod( mapperInterface, method, this.sqlSession.getConfiguration() ) );
    }

    private Object invokeDefaultMethod( Object proxy, Method method, Object[] args ) throws Throwable {
        final Constructor<MethodHandles.Lookup> constructor = MethodHandles.Lookup.class.getDeclaredConstructor( Class.class, int.class );
        if ( !constructor.isAccessible() ) {
            constructor.setAccessible( true );
        }
        final Class<?> declaringClass = method.getDeclaringClass();
        return constructor.newInstance( declaringClass, MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC )
                .unreflectSpecial( method, declaringClass ).bindTo( proxy ).invokeWithArguments( args );
    }

    /**
     * Backport of {@link Method#isDefault()}
     * @param method 方法对象
     * @return boolean
     */
    private boolean isDefaultMethod( Method method ) {
        return (method.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) == Modifier.PUBLIC
                && method.getDeclaringClass().isInterface();
    }
}
