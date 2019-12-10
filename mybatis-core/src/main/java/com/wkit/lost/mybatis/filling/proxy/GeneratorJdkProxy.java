package com.wkit.lost.mybatis.filling.proxy;

import com.wkit.lost.mybatis.filling.gen.AbstractGenerator;
import com.wkit.lost.mybatis.filling.gen.GeneratedValue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 生成器JDK动态代理类
 * @author wvkity
 */
public class GeneratorJdkProxy implements InvocationHandler {

    /**
     * 目标对象
     */
    private AbstractGenerator target;

    public GeneratorJdkProxy( AbstractGenerator target ) {
        this.target = target;
    }

    public GeneratedValue getProxy() {
        return ( GeneratedValue ) Proxy.newProxyInstance( target.getClass().getClassLoader(), AbstractGenerator.class.getInterfaces(), this );
    }

    @Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
        return method.invoke( target, args );
    }
}
