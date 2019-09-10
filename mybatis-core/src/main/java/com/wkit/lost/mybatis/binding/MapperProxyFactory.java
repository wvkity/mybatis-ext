package com.wkit.lost.mybatis.binding;

import lombok.Getter;
import org.apache.ibatis.session.SqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>重写{@link org.apache.ibatis.binding.MapperProxyFactory}</p>
 * @param <T> 类型
 */
public class MapperProxyFactory<T> {

    /**
     * 接口
     */
    @Getter
    private final Class<T> mapperInterface;

    /**
     * 方法映射缓存
     */
    @Getter
    private final Map<Method, MapperMethod> methodCache = new ConcurrentHashMap<>( 256 );

    /**
     * 构造方法
     * @param mapperInterface 接口
     */
    public MapperProxyFactory( Class<T> mapperInterface ) {
        this.mapperInterface = mapperInterface;
    }

    public T newInstance( SqlSession sqlSession ) {
        final MapperProxy<T> mapperProxy = new MapperProxy<>( sqlSession, this.mapperInterface, this.methodCache );
        return newInstance( mapperProxy );
    }
    
    @SuppressWarnings( "unchecked" )
    public T newInstance( MapperProxy<T> mapperProxy ) {
        return (T) Proxy.newProxyInstance( this.mapperInterface.getClassLoader(), new Class[]{ this.mapperInterface }, mapperProxy );
    }

}
