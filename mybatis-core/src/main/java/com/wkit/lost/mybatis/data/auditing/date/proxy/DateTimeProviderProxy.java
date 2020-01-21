package com.wkit.lost.mybatis.data.auditing.date.proxy;

import com.wkit.lost.mybatis.data.auditing.date.provider.AbstractProvider;
import com.wkit.lost.mybatis.data.auditing.date.provider.DateTimeProvider;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 时间提供者动态代理
 * @author wvkity
 */
public class DateTimeProviderProxy implements InvocationHandler {

    /**
     * 目标对象
     */
    private AbstractProvider target;

    /**
     * 构造方法
     * @param target 目标对象
     */
    public DateTimeProviderProxy( AbstractProvider target ) {
        this.target = target;
    }

    public DateTimeProvider getTarget() {
        return ( DateTimeProvider ) Proxy.newProxyInstance( target.getClass().getClassLoader(),
                AbstractProvider.class.getInterfaces(), this );
    }

    @Override
    public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable {
        return method.invoke( target, args );
    }
}
